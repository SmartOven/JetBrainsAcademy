package tracker.util;

import tracker.data.storage.PointsStorage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class Statistics {

    private static final Map<Course, Map<Metric, Double>> statistics;
    private static final Map<Course, Set<UUID>> coursesStudentSets;

    private static final Map<Metric, Double> defaultCourseMetrics;

    private static boolean statisticsWritten;

    static {
        statisticsWritten = false;
        defaultCourseMetrics = new HashMap<>();

        for (Metric metric : Metric.values()) {
            defaultCourseMetrics.put(metric, 0.0);
        }

        statistics = new HashMap<>();
        coursesStudentSets = new HashMap<>();

        for (Course course : Course.values()) {
            statistics.put(course, new HashMap<>(defaultCourseMetrics));
            coursesStudentSets.put(course, new HashSet<>());
        }
    }

    public static void update(UUID studentID, List<Integer> points) {
        statisticsWritten = true;
        Course[] values = Course.values();
        for (int i = 0; i < values.length; i++) {
            Course course = values[i];
            int coursePoints = points.get(i);

            // If activity is not empty
            if (coursePoints > 0) {
                Map<Metric, Double> courseStatistics = statistics.get(course);
                // Increment course activity
                courseStatistics.merge(Metric.ACTIVITY, 1.0, Double::sum);

                // If this is new student, increment popularity
                Set<UUID> studentsSet = coursesStudentSets.get(course);
                if (!studentsSet.contains(studentID)) {
                    studentsSet.add(studentID);
                    courseStatistics.merge(Metric.POPULARITY, 1.0, Double::sum);
                }
            }
        }
    }

    public static List<String> getCourseInfo(String courseName) {
        Course course;
        try {
            course = Course.fromString(courseName.toLowerCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            return null;
        }
        int courseID = course.ordinal();
        int completionPoints = Course.courseCompletionPoints.get(course);

        List<String> info = new LinkedList<>();
        info.add(course.toString());
        info.add("id points completed");
        info.addAll(PointsStorage.getInstance()
                .findAll().stream()
                .filter(points -> points.getCoursesPoints().get(courseID) > 0)
                .map(points -> {
                    UUID studentID = points.getStudentID();
                    int coursePoints = points.getCoursesPoints().get(courseID);
                    double completionPercent = (double) coursePoints * 100 / completionPoints;
                    double roundedCompletionPercent = BigDecimal.valueOf(completionPercent).setScale(1, RoundingMode.HALF_UP).doubleValue();
                    return new StudentStats(studentID, coursePoints, roundedCompletionPercent);
                })
                .sorted()
                .map(StudentStats::toString)
                .collect(Collectors.toList()));
        return info;
    }

    public static String getMostPopular() {
        if (!statisticsWritten) {
            return "n/a";
        }

        // Getting max popularity
        double maxPopularity = getMaxMetricValue(Metric.POPULARITY);

        // Getting list of most popular courses
        List<Course> mostPopularCourses = getCourseListByMetricValue(Metric.POPULARITY, maxPopularity);

        // Returning result
        return coursesListToPlainString(mostPopularCourses);
    }

    public static String getLeastPopular() {
        if (!statisticsWritten) {
            return "n/a";
        }

        // Getting min popularity
        double minPopularity = getMinMetricValue(Metric.POPULARITY);

        // Getting list of most popular courses
        List<Course> leastPopularCourses = getCourseListByMetricValue(Metric.POPULARITY, minPopularity);

        // Returning result
        if (leastPopularCourses.size() == Course.values().length) {
            return "n/a";
        }
        return coursesListToPlainString(leastPopularCourses);
    }

    public static String getHighestActivity() {
        if (!statisticsWritten) {
            return "n/a";
        }

        // Getting max activity
        double maxActivity = getMaxMetricValue(Metric.ACTIVITY);

        // Getting list of most popular courses
        List<Course> highestActivityCourses = getCourseListByMetricValue(Metric.ACTIVITY, maxActivity);

        // Returning result
        return coursesListToPlainString(highestActivityCourses);
    }

    public static String getLowestActivity() {
        if (!statisticsWritten) {
            return "n/a";
        }

        // Getting min activity
        double minActivity = getMinMetricValue(Metric.ACTIVITY);

        // Getting list of most popular courses
        List<Course> lowestActivityCourses = getCourseListByMetricValue(Metric.ACTIVITY, minActivity);

        // Returning result
        if (lowestActivityCourses.size() == Course.values().length) {
            return "n/a";
        }
        return coursesListToPlainString(lowestActivityCourses);
    }

    public static String getEasiestCourse() {
        if (!statisticsWritten) {
            return "n/a";
        }

        calculateDifficulty();

        // Getting min difficulty
        double minDifficulty = getMinMetricValue(Metric.DIFFICULTY);

        // Getting list of most popular courses
        List<Course> easiestCourses = getCourseListByMetricValue(Metric.DIFFICULTY, minDifficulty);

        // Returning result
        return coursesListToPlainString(easiestCourses);
    }

    public static String getHardestCourse() {
        if (!statisticsWritten) {
            return "n/a";
        }

        calculateDifficulty();

        // Getting max difficulty
        double maxDifficulty = getMaxMetricValue(Metric.DIFFICULTY);

        // Getting list of most popular courses
        List<Course> hardestCourses = getCourseListByMetricValue(Metric.DIFFICULTY, maxDifficulty);

        // Returning result
        if (hardestCourses.size() == Course.values().length) {
            return "n/a";
        }
        return coursesListToPlainString(hardestCourses);
    }

    private static List<Course> getCourseListByMetricValue(Metric metric, double metricValue) {
        List<Course> courses = new ArrayList<>(4);
        for (Course course : statistics.keySet()) {
            if (metricValue == statistics.get(course).get(metric)) {
                courses.add(course);
            }
        }
        return courses;
    }

    private static double getMaxMetricValue(Metric metric) {
        double maxValue = Integer.MIN_VALUE;
        for (Course course : statistics.keySet()) {
            maxValue = Math.max(maxValue, statistics.get(course).get(metric));
        }
        return maxValue;
    }

    private static double getMinMetricValue(Metric metric) {
        double minValue = Integer.MAX_VALUE;
        for (Course course : statistics.keySet()) {
            minValue = Math.min(minValue, statistics.get(course).get(metric));
        }
        return minValue;
    }

    private static String coursesListToPlainString(List<Course> courses) {
        StringBuilder result = new StringBuilder(courses.get(0).toString());
        for (int i = 1; i < courses.size(); i++) {
            result.append(", ").append(courses.get(i).toString());
        }
        return result.toString();
    }

    private static void calculateDifficulty() {
        for (Course course : statistics.keySet()) {
            List<Integer> pointsList = PointsStorage.getInstance()
                    .findAll().stream()
                    .map(points -> points.getCoursesPoints().get(course.ordinal()))
                    .filter(points -> points > 0)
                    .collect(Collectors.toList());
            int studentsCount = pointsList.size();

            double avgPoints = 0.0;
            for (Integer studentPoints : pointsList) {
                avgPoints += studentPoints;
            }
            avgPoints /= studentsCount;

            // Set average student points with minus so bigger number of points mean less difficulty
            statistics.get(course).put(Metric.DIFFICULTY, -avgPoints);
        }
    }

    private static final class StudentStats
            implements Comparable<StudentStats> {
        private final UUID id;
        private final int points;
        private final double completionPercent;

        private StudentStats(UUID id, int points, double completionPercent) {
            this.id = id;
            this.points = points;
            this.completionPercent = completionPercent;
        }

        public UUID getId() {
            return id;
        }

        public int getPoints() {
            return points;
        }

        public double getCompletionPercent() {
            return completionPercent;
        }

        @Override
        public int compareTo(StudentStats o) {
            int oPoints = o.getPoints();
            if (points != oPoints) {
                return oPoints - points;
            }
            return String.valueOf(id).compareTo(String.valueOf(o.getId()));
        }

        @Override
        public String toString() {
            return id + " " + points + " " + completionPercent + "%";
        }

        public UUID id() {
            return id;
        }

        public int points() {
            return points;
        }

        public double completionPercent() {
            return completionPercent;
        }
    }
}

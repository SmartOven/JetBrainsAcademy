package phonebook;

import java.io.File;
import java.util.Scanner;

public class Manager {
    private static String dataFileName;
    private static String searchQueryFileName;
    private static PhoneBookFileSearcher searcher;

    static {
        // Use for faster testing
        // dataFileName = new File("PhoneBook/src/main/resources/small_directory.txt").getAbsolutePath();
        // searchQueryFileName = new File("PhoneBook/src/main/resources/small_find.txt").getAbsolutePath();

        // Use for performance testing
        dataFileName = new File("PhoneBook/src/main/resources/directory.txt").getAbsolutePath();
        searchQueryFileName = new File("PhoneBook/src/main/resources/find.txt").getAbsolutePath();
    }

    public static void setDataFileName(String dataFileName) {
        Manager.dataFileName = dataFileName;
    }

    public static void setSearchQueryFileName(String searchQueryFileName) {
        Manager.searchQueryFileName = searchQueryFileName;
    }

    public static void work(Scanner scanner) {

        // Read data file
        Timer.begin();
        System.out.println("Reading data...");
        searcher = new PhoneBookFileSearcher(dataFileName);
        searcher.setSearchingQuery(searchQueryFileName);
        Timer.end();
//        System.out.println("Data was read successfully. Time taken: " + Timer.getTimeDifferenceMillis());

        // Linear search
        System.out.println("\nStart searching (linear search)...");
        linearSearch();
        System.out.println("Found " + foundEntries + " / " + searcher.getSearchQuerySize() + ". Time taken: " + Timer.convertMillisToTime(linearSearchTimeCost) + ".");

        // Bubble sorting
        System.out.println("\nStart searching (bubble sort + jump search)...");
        bubbleSort();

        if (!isSortingStopped) {
            // Jump-search if not stopped
            jumpSearch();

            System.out.println("Found " + foundEntries + " / " + searcher.getSearchQuerySize() + ". Time taken: " + Timer.convertMillisToTime(bubbleSortTimeCost + jumpSearchTimeCost));
            System.out.println("Sorting time: " + Timer.convertMillisToTime(bubbleSortTimeCost) + ".");
            System.out.println("Searching time: " + Timer.convertMillisToTime(jumpSearchTimeCost));
        } else {
            // Linear search if stopped
            linearSearch();

            System.out.println("Found " + foundEntries + " / " + searcher.getSearchQuerySize() + ". Time taken: " + Timer.convertMillisToTime(bubbleSortTimeCost + linearSearchTimeCost));
            System.out.println("Sorting time: " + Timer.convertMillisToTime(bubbleSortTimeCost) + " - STOPPED, moved to linear search");
            System.out.println("Searching time: " + Timer.convertMillisToTime(linearSearchTimeCost));
        }

        // Quick sort + binary search
        System.out.println("\nStart searching (quick sort + binary search)...");
        quickSort();
        binarySearch();
        System.out.println("Found " + foundEntries + " / " + searcher.getSearchQuerySize() + ". Time taken: " + Timer.convertMillisToTime(quickSortTimeCost + binarySearchTimeCost));
        System.out.println("Sorting time: " + Timer.convertMillisToTime(quickSortTimeCost));
        System.out.println("Searching time: " + Timer.convertMillisToTime(binarySearchTimeCost));

        // Create hash table + hash table search
        System.out.println("\nStart searching (hash table)...");
        createHashTable();
        hashTableSearch();
        System.out.println("Found " + "500" + " / " + searcher.getSearchQuerySize() + ". Time taken: " + Timer.convertMillisToTime(hashTableCreationTimeCost + hashTableSearchTimeCost));
        System.out.println("Creating time: " + Timer.convertMillisToTime(hashTableCreationTimeCost));
        System.out.println("Searching time: " + Timer.convertMillisToTime(hashTableSearchTimeCost));
    }

    private static long linearSearchTimeCost;
    private static long bubbleSortTimeCost;
    private static long jumpSearchTimeCost;
    private static long quickSortTimeCost;
    private static long binarySearchTimeCost;
    private static long hashTableCreationTimeCost;
    private static long hashTableSearchTimeCost;

    private static long foundEntries;
    private static boolean isSortingStopped;

    private static void linearSearch() {
        Timer.begin();
        foundEntries = searcher.linearSearch();
        Timer.end();
        linearSearchTimeCost = Timer.getTimeDifferenceMillis();
    }

    private static void bubbleSort() {
        BubbleSortUntilTooLong sortingWorker = new BubbleSortUntilTooLong(searcher);
        Thread bubbleSortingThread = new Thread(sortingWorker);
        bubbleSortingThread.start();

        Timer.begin();
        long timeSpent = 0;
        while (!bubbleSortingThread.isInterrupted()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Main thread is interrupted!");
                e.printStackTrace();
            }
            Timer.end();
            timeSpent = Timer.getTimeDifferenceMillis();
            if (timeSpent >= 10 * linearSearchTimeCost) {
                bubbleSortTimeCost = timeSpent;
                isSortingStopped = true;
                bubbleSortingThread.interrupt();
                return;
            }
        }
        bubbleSortTimeCost = timeSpent;
        isSortingStopped = false;
    }

    private static void jumpSearch() {
        Timer.begin();
        foundEntries = searcher.jumpSearch();
        Timer.end();
        jumpSearchTimeCost = Timer.getTimeDifferenceMillis();
    }

    private static void quickSort() {
        Timer.begin();
        searcher.quickSort();
        Timer.end();
        quickSortTimeCost = Timer.getTimeDifferenceMillis();
    }

    private static void binarySearch() {
        Timer.begin();
        foundEntries = searcher.binarySearch();
        Timer.end();
        binarySearchTimeCost = Timer.getTimeDifferenceMillis();
    }

    private static void createHashTable() {
        Timer.begin();
        searcher.createHashTable();
        Timer.end();
        hashTableCreationTimeCost = Timer.getTimeDifferenceMillis();
    }

    private static void hashTableSearch() {
        Timer.begin();
        foundEntries = searcher.hashTableSearch();
        Timer.end();
        hashTableSearchTimeCost = Timer.getTimeDifferenceMillis();
    }

    private static class BubbleSortUntilTooLong implements Runnable {
        private final PhoneBookFileSearcher searcher;

        public BubbleSortUntilTooLong(PhoneBookFileSearcher searcher) {
            this.searcher = searcher;
        }

        @Override
        public void run() {
            Thread current = Thread.currentThread();
            while (!current.isInterrupted()) {
                searcher.bubbleSort();
                Thread.currentThread().interrupt();
            }
        }
    }
}

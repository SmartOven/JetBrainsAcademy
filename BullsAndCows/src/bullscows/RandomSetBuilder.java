package bullscows;

import java.util.*;

/**
 * Generator for sets of unique random integers in range [min, max)
 */
public class RandomSetBuilder {
    private final List<Integer> result;
    private final Map<Integer, Integer> usedNumbers;
    private final Random randomGenerator;
    private int currentMin; // 0 by default
    private final int max; // 100 by default

    public RandomSetBuilder(int max) {
        this(0, max);
    }

    public RandomSetBuilder(int min, int max) {
        this(min, max, System.currentTimeMillis() * (long)(Math.random() % 17 + 13));
    }

    public RandomSetBuilder(int min, int max, long seed) {
        this.currentMin = min;
        this.max = max - 1;

        randomGenerator = new Random(seed);
        usedNumbers = new HashMap<>();
        result = new LinkedList<>();
    }

    public List<Integer> toList() {
        return new LinkedList<>(result);
    }

    public Set<Integer> toSet() {
        return new HashSet<>(result);
    }

    public Queue<Integer> toQueue() {
        return new LinkedList<>(result);
    }

    public Deque<Integer> toDeque() {
        return new LinkedList<>(result);
    }

    public int[] toArray() {
        int[] array = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            array[i] = result.get(i);
        }
        return array;
    }

    /**
     * Adds "count" number of next unique random integers to the result set
     *
     * @param count count of numbers to be added to set
     */
    public RandomSetBuilder append(int count) {
        int maxCount = Math.min(count, max - currentMin + 1);
        for (int i = 0; i < maxCount; i++) {
            addNextRandomInt();
        }
        return this;
    }

    public RandomSetBuilder appendAll() {
        return append(max - currentMin + 1);
    }

    /**
     * Generates next unique integer
     * <p>
     * Imagine sorted range of integers from min to max (both included)
     * (for example: [0, 1, 2, 3, 4])
     * <p>
     * Let's generate random int from the range
     * (for example: 3)
     * <p>
     * We want to generate only unique integers, so we follow this steps:
     * 1. When we generate integer from the range, we "swap" the generated one with the first not generated one (in ascending order)
     * (using example range and example generated integer it would be: [3, 1, 2, 0, 4])
     * We swapped 3 and 0, and then we increment min number of the generating range, so we will not generate 3 again
     * <p>
     * We will do it by using currentIndex instead of min and storing swapped numbers in the hash map
     * So the example with [0, 1, 2, 3, 4] and 3 would look like:
     * currentIndex = 1, hashMap = [[3: 0]]
     * <p>
     * And when we generate integer we need to check if we already generated it before (if hash map contains it)
     * If so we "generate" the value stored with key = generated integer
     * <p>
     * Means if we have currentIndex = 1, hashMap = [[3: 0]] and if we generate 3 again
     * we will use 0 instead because hash map has key = 3 with value = 0
     */
    private void addNextRandomInt() {
        // Getting next random integer
        int nextIndex = randomGenerator.nextInt(max - currentMin + 1) + currentMin;

        // Replacing it with the swapped one if we already generated it before
        int next = findNextByIndex(nextIndex);

        // Add it to the result list
        result.add(next);

        // "Swap" it with the first not generated one (if it doesn't equal to itself or less, because instead it will be useless)
        if (nextIndex != currentMin) {
            usedNumbers.put(nextIndex, currentMin);
        }

        // Increment current index
        currentMin++;
    }

    /**
     * Recursively finding value that need to be generated at index nextIndex
     *
     * @param nextIndex index of value to be found in hash map
     * @return value that need to be generated at index nextIndex
     */
    private Integer findNextByIndex(int nextIndex) {
        int next = usedNumbers.getOrDefault(nextIndex, nextIndex);
        if (next == nextIndex) {
            return next;
        }

        return findNextByIndex(next);
    }


    public RandomSetBuilder sort() {
        return sort(false);
    }

    public RandomSetBuilder sort(boolean DESC) {
        if (DESC) {
            result.sort(Collections.reverseOrder());
        } else {
            Collections.sort(result);
        }
        return this;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int num : result) {
            stringBuilder.append(num);
        }
        return stringBuilder.toString();
    }
}

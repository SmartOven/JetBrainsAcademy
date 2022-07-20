package phonebook;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class PhoneBookFileSearcher {

    /**
     * Linear searching
     *
     * @return count of found entries
     */
    public long linearSearch() {
        long counter = 0;
        for (String name : searchQueryList) {
            // Algorithm
            for (PhoneBookEntry entry : phoneBookEntryList) {
                if (name.equals(entry.getName())) {
                    counter++;
                }
            }
        }
        return counter;
    }

    /**
     * Bubble sorting data
     */
    public void bubbleSort() {
        boolean isSorted = false;
        while (!isSorted) {
            isSorted = true;
            for (int i = 0; i < phoneBookEntryList.size() - 1; i++) {
                PhoneBookEntry current = phoneBookEntryList.get(i);
                PhoneBookEntry next = phoneBookEntryList.get(i + 1);

                // If wrong order -> array is not sorted
                if (current.compareTo(next) > 0) {
                    synchronized (this) {
                        isSorted = false;
                        phoneBookEntryList.set(i, next);
                        phoneBookEntryList.set(i + 1, current);
                    }
                }
            }
        }
    }

    /**
     * Jump-searching
     *
     * @return count of found entries
     */
    public long jumpSearch() {
        long counter = 0;
        for (String name : searchQueryList) {
            // Algorithm
            counter += jumpSearchHelper(name) ? 1 : 0;
        }
        return counter;
    }

    private boolean jumpSearchHelper(String name) {
        int step = (int) Math.floor(Math.sqrt(phoneBookEntryList.size()));
        int current = 0;
        while (current < phoneBookEntryList.size()) {
            String currentName = phoneBookEntryList.get(current).getName();
            if (name.equals(currentName)) {
                return true;
            } else if (name.compareTo(currentName) < 0) {
                int index = current - 1;
                while (index > current - step && index >= 0) {
                    String indexName = phoneBookEntryList.get(index).getName();
                    if (name.equals(indexName)) {
                        return true;
                    }
                    index--;
                }
                return false;
            }
            current += step;
        }
        int index = phoneBookEntryList.size() - 1;
        while (index > current - step) {
            String indexName = phoneBookEntryList.get(index).getName();
            if (name.equals(indexName)) {
                return true;
            }
            index--;
        }
        return false;
    }

    /**
     * Quick sorting
     */
    public void quickSort() {
        quickSortHelper(0, phoneBookEntryList.size());
    }

    private void quickSortHelper(int start, int end) {
        /*Base case*/
        if (start >= end) {
            return;
        }// End of the base case

        int pIndex = partition(start, end);// The index of the pivot.
        //System.out.println(pIndex);

        /*Recursive cases*/
        quickSortHelper(start, pIndex - 1);// Left side of the array
        quickSortHelper(pIndex + 1, end);// Right side of the array

    }

    private int partition(int start, int end) {
        PhoneBookEntry pivot = phoneBookEntryList.get(end - 1); // Select the pivot to be the last element
        int pIndex = start;// (Partition index) Indicates where the pivot will be.

        for (int i = start; i < end; i++) {
            if (phoneBookEntryList.get(i).compareTo(pivot) < 0) {

                // if a number is smaller than the pivot, it gets swapped with the Partition index
                PhoneBookEntry temp = phoneBookEntryList.get(pIndex);
                phoneBookEntryList.set(pIndex, phoneBookEntryList.get(i));
                phoneBookEntryList.set(i, temp);

                pIndex++; // increments the partition index, only stops when pivot is in the right area
            }
        }

        // This swaps the pivot with the element that stopped where the pivot should be
        phoneBookEntryList.set(end - 1, phoneBookEntryList.get(pIndex));
        phoneBookEntryList.set(pIndex, pivot);
        return pIndex;
    }

    /**
     * Binary searching
     *
     * @return count of found entries
     */
    public long binarySearch() {
        long counter = 0;
        for (String name : searchQueryList) {
            // Algorithm
            counter += binarySearchHelper(name, 0, phoneBookEntryList.size()) ? 1 : 0;
        }
        return counter;
    }

    private boolean binarySearchHelper(String name, int start, int end) {
        if (end - start <= 1) {
            return name.equals(phoneBookEntryList.get(start).getName()) || name.equals(phoneBookEntryList.get(end).getName());
        }
        int mid = (end + start) / 2;
        String midName = phoneBookEntryList.get(mid).getName();
        int comparingResult = name.compareTo(midName);
        if (comparingResult > 0) {
            binarySearchHelper(name, mid, end);
        } else if (comparingResult < 0) {
            binarySearchHelper(name, start, mid - 1);
        }
        return true;
    }

    /**
     * Creating hash map of entries
     */
    public void createHashTable() {
        entryHashMap = new HashMap<>(phoneBookEntryList.size());
        for (PhoneBookEntry entry : phoneBookEntryList) {
            entryHashMap.put(entry.getName(), entry);
        }
    }

    /**
     * Searching in the hash map
     *
     * @return count of found entries
     */
    public long hashTableSearch() {
        long counter = 0;
        for (String name : searchQueryList) {
            // Algorithm
            counter += hashTableSearchHelper(name) ? 1 : 0;
        }
        return counter;
    }

    private boolean hashTableSearchHelper(String name) {
        return entryHashMap.getOrDefault(name, null) != null;
    }

    private final List<PhoneBookEntry> phoneBookEntryList;
    private List<String> searchQueryList;
    private HashMap<String, PhoneBookEntry> entryHashMap;

    public PhoneBookFileSearcher(String dataFileName) {
        this.phoneBookEntryList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(dataFileName))) {
            while (reader.ready()) {
                phoneBookEntryList.add(PhoneBookEntry.parseEntry(reader.readLine()));
            }
        } catch (IOException e) {
            System.out.println("Something went wrong while reading phone book data...");
        }
    }

    public void setSearchingQuery(String searchQueryFileName) {
        searchQueryList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(searchQueryFileName))) {
            while (reader.ready()) {
                searchQueryList.add(reader.readLine());
            }
        } catch (IOException e) {
            System.out.println("Something went wrong while reading search query data...");
        }
    }

    public int getSearchQuerySize() {
        return searchQueryList.size();
    }

    private static class PhoneBookEntry implements Comparable<PhoneBookEntry> {
        private final String number;
        private final String name;

        public PhoneBookEntry(String number, String name) {
            this.number = number;
            this.name = name;
        }

        public static PhoneBookEntry parseEntry(String line) {
            String[] params = line.split(" ");
            return switch (params.length) {
                case 3 -> new PhoneBookEntry(params[0], params[1] + " " + params[2]);
                case 2 -> new PhoneBookEntry(params[0], params[1]);
                default -> null;
            };
        }

        public String getNumber() {
            return number;
        }

        public String getName() {
            return name;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PhoneBookEntry that = (PhoneBookEntry) o;
            return Objects.equals(number, that.number) && Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(number, name);
        }

        @Override
        public String toString() {
            return number + " " + name;
        }

        @Override
        public int compareTo(PhoneBookEntry o) {
            return this.getName().compareTo(o.getName());
        }
    }
}

package banking.customerIDGenerator;

public class CustomerIDGenerator {
    /**
     * Single Thread Singleton implementation
     */
    private static CustomerIDGenerator instance = null;
    public static CustomerIDGenerator getInstance() {
        if (instance == null) {
            instance = new CustomerIDGenerator();
        }
        return instance;
    }

    private long nextAvailableID;
    private CustomerIDGenerator() {
        nextAvailableID = 0;
    }

    public long getNextAvailableID() {
        return nextAvailableID++;
    }
}

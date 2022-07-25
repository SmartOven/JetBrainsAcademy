package carsharing.db.tables;

public class Company {
    private final int id;
    private final String name;

    public Company(String companyName) {
        this(-1, companyName);
    }

    public Company(int id, String companyName) {
        this.id = id;
        this.name = companyName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

package carsharing.db.tables;

/**
 * Schema
 * <pre>{@code
 *     ID         INT PRIMARY KEY AUTO_INCREMENT,
 *     NAME       VARCHAR(40) UNIQUE NOT NULL,
 *     COMPANY_ID INT                NOT NULL,
 *     FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY (ID)
 * }</pre>
 */
public class Car {
    private final int id;
    private final String name;
    private final int company_id;

    public Car(int id, String name, int company_id) {
        this.id = id;
        this.name = name;
        this.company_id = company_id;
    }

    public Car(String name, int company_id) {
        this(-1, name, company_id);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCompany_id() {
        return company_id;
    }
}

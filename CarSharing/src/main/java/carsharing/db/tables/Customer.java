package carsharing.db.tables;

public class Customer {
    private final int id;
    private final String name;
    private int rented_car_id;

    public Customer(int id, String name, int rented_car_id) {
        this.id = id;
        this.name = name;
        this.rented_car_id = rented_car_id;
    }

    public Customer(int id, String name) {
        this(id, name, -1);
    }

    public Customer(String name) {
        this(-1, name, -1);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRented_car_id() {
        return rented_car_id;
    }

    public void setRented_car_id(int rented_car_id) {
        this.rented_car_id = rented_car_id;
    }
}

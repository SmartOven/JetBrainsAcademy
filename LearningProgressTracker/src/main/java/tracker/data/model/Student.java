package tracker.data.model;

import java.util.UUID;

public class Student {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final UUID id;

    public Student(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.id = UUID.randomUUID();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public UUID getId() {
        return id;
    }
}

package easyinventory.backend.inventory.domain.model.aggregates;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private String birthday;
    private String email;
    private Integer phone;

    public Customer(String name, String lastName, String birthday, String email, Integer phone) {
        this.name = name;
        this.lastName = lastName;
        this.birthday = birthday;
        this.email = email;
        this.phone = phone;
    }

    public boolean isValidPhone(Integer phone) {
        if (phone == null) throw new IllegalArgumentException("Phone is required");
        if (phone.toString().length() != 9) throw new IllegalArgumentException("Phone must have 9 digits");
        return true;
    }

    public boolean isValidEmail(String email) {
        if (email == null) throw new IllegalArgumentException("Email is required");
        if (!email.contains("@")) throw new IllegalArgumentException("Email must contain @");
        return true;
    }


    public Customer() {
    }
}

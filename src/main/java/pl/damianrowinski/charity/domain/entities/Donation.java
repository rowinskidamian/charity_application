package pl.damianrowinski.charity.domain.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Entity
@Transactional
@Table(name = Donation.TABLE_NAME)
@Getter @Setter
@ToString(exclude = "categories")
public class Donation {

    final static String TABLE_NAME = "donations";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Integer quantity;

    @ManyToMany
    private List<Category> categories;

    @ManyToOne
    private Institution institution;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @Column(name = "pick_up_date", nullable = false)
    private LocalDate pickUpDate;

    @Column(name = "pick_up_time", nullable = false)
    private LocalTime pickUpTime;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Donation donation = (Donation) o;
        return id == donation.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

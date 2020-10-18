package pl.damianrowinski.charity.domain.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@Transactional
@Table(name = Category.TABLE_NAME)
@Getter @Setter @ToString @EqualsAndHashCode
public class Category {

    final static String TABLE_NAME = "categories";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;
}

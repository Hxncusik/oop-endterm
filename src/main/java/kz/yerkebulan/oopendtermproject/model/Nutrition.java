package kz.yerkebulan.oopendtermproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@Table(name = "nutritions")
public final class Nutrition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String foodName;
    private int calories;
    private int protein;
    private int carbs;
    private int fats;
    private Date date;
}

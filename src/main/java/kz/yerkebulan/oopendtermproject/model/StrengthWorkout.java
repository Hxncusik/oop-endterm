package kz.yerkebulan.oopendtermproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "workouts")
public final class StrengthWorkout implements Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String workoutType;
    private String title;
    private int duration;
    private int caloriesBurned;
    private String intensity;
    private LocalDateTime date;
    private String notes;
}


package kz.yerkebulan.oopendtermproject.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Table(name = "workouts")
public interface Workout {
    Long getId();
    Long getUserId();
    void setUserId(Long id);
    String getTitle();
    void setTitle(String title);
    int getDuration();
    void setDuration(int duration);
    String getIntensity();
    void setIntensity(String intensity);
    LocalDateTime getDate();
    void setDate(LocalDateTime date);
    String getNotes();
    void setNotes(String notes);
    int getCaloriesBurned();
}


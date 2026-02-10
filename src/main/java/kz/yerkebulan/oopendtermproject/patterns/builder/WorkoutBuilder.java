package kz.yerkebulan.oopendtermproject.patterns.builder;

import kz.yerkebulan.oopendtermproject.model.Exercise;
import kz.yerkebulan.oopendtermproject.model.Workout;
import kz.yerkebulan.oopendtermproject.patterns.factory.WorkoutFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkoutBuilder {
    private Long userId;
    private String workoutType;
    private String title;
    private Integer duration;
    private String intensity;
    private LocalDateTime date;
    private String notes;
    private List<Exercise> exercises;
    private Integer caloriesBurned;
    private Double distance;
    private Integer averageHeartRate;

    public WorkoutBuilder() {
        this.exercises = new ArrayList<>();
        this.date = LocalDateTime.now();
    }

    public WorkoutBuilder userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public WorkoutBuilder workoutType(String workoutType) {
        this.workoutType = workoutType;
        return this;
    }

    public WorkoutBuilder title(String title) {
        this.title = title;
        return this;
    }

    public WorkoutBuilder duration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public WorkoutBuilder intensity(String intensity) {
        this.intensity = intensity;
        return this;
    }

    public WorkoutBuilder date(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public WorkoutBuilder notes(String notes) {
        this.notes = notes;
        return this;
    }

    public WorkoutBuilder addExercise(Exercise exercise) {
        this.exercises.add(exercise);
        return this;
    }

    public WorkoutBuilder exercises(List<Exercise> exercises) {
        this.exercises = exercises;
        return this;
    }

    public WorkoutBuilder caloriesBurned(Integer calories) {
        this.caloriesBurned = calories;
        return this;
    }

    public WorkoutBuilder distance(Double distance) {
        this.distance = distance;
        return this;
    }

    public WorkoutBuilder averageHeartRate(Integer heartRate) {
        this.averageHeartRate = heartRate;
        return this;
    }

    public Workout build() {
        validateRequiredFields();

        Workout workout = WorkoutFactory.createWorkout(workoutType, buildParams());
        workout.setUserId(userId);
        workout.setTitle(title);
        workout.setDuration(duration);
        workout.setIntensity(intensity);
        workout.setDate(date);
        workout.setNotes(notes);

        return workout;
    }

    private void validateRequiredFields() {
        if (userId == null) throw new IllegalStateException("User ID is required");
        if (workoutType == null) throw new IllegalStateException("Workout type is required");
        if (duration == null) throw new IllegalStateException("Duration is required");
    }

    private Map<String, Object> buildParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("duration", duration);
        params.put("exercises", exercises);
        params.put("calories", caloriesBurned);
        params.put("distance", distance);
        params.put("heartRate", averageHeartRate);
        return params;
    }
}

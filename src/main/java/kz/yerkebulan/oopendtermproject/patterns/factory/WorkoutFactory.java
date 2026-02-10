package kz.yerkebulan.oopendtermproject.patterns.factory;

import kz.yerkebulan.oopendtermproject.model.CardioWorkout;
import kz.yerkebulan.oopendtermproject.model.StrengthWorkout;
import kz.yerkebulan.oopendtermproject.model.Workout;
import kz.yerkebulan.oopendtermproject.model.YogaWorkout;
import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public final class WorkoutFactory {
    public static Workout createWorkout(String type, Map<String, Object> params) {
        switch (type.toUpperCase()) {
            case "CARDIO":
                return createCardioWorkout(params);
            case "STRENGTH":
                return createStrengthWorkout(params);
            case "YOGA":
                return createYogaWorkout(params);
            default:
                throw new IllegalArgumentException("Unknown workout type: " + type);
        }
    }

    private static CardioWorkout createCardioWorkout(Map<String, Object> params) {
        CardioWorkout workout = new CardioWorkout();
        workout.setDuration((Integer) params.get("duration"));
        workout.setTitle((String) params.get("title"));
        workout.setWorkoutType("CARDIO");
        return workout;
    }

    private static StrengthWorkout createStrengthWorkout(Map<String, Object> params) {
        StrengthWorkout workout = new StrengthWorkout();
        workout.setDuration((Integer) params.get("duration"));
        workout.setTitle((String) params.get("title"));
        workout.setWorkoutType("STRENGTH");
        return workout;
    }

    private static YogaWorkout createYogaWorkout(Map<String, Object> params) {
        YogaWorkout workout = new YogaWorkout();
        workout.setDuration((Integer) params.get("duration"));
        workout.setTitle((String) params.get("title"));
        workout.setWorkoutType("YOGA");
        return workout;
    }
}

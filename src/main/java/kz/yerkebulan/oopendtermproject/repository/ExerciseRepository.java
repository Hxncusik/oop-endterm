package kz.yerkebulan.oopendtermproject.repository;

import kz.yerkebulan.oopendtermproject.model.Exercise;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<@NonNull Exercise, @NonNull Long>  {
}

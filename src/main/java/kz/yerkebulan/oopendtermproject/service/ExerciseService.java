package kz.yerkebulan.oopendtermproject.service;

import kz.yerkebulan.oopendtermproject.model.Exercise;
import kz.yerkebulan.oopendtermproject.repository.ExerciseRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public final class ExerciseService {
    private final ExerciseRepository repository;

    public Exercise create(Exercise exercise) {
        return repository.save(exercise);
    }

    public List<Exercise> getAll() {
        return repository.findAll().stream().filter(exercise -> !exercise.isDeleted()).toList();
    }

    public Exercise getById(Long id) {
        Exercise exercise = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        if (exercise.isDeleted()) {
            return null;
        }
        return exercise;
    }

    public Exercise update(Long id, Exercise exercise) {
        Exercise existing = getById(id);
        if (existing == null) {
            return null;
        }
        existing.setName(exercise.getName());
        existing.setReps(exercise.getReps());
        existing.setSets(exercise.getSets());
        existing.setWeight(exercise.getWeight());
        existing.setDeleted(exercise.isDeleted());
        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.findById(id).ifPresent(exercise -> {
            exercise.setDeleted(true);
            repository.save(exercise);
        });
    }
}

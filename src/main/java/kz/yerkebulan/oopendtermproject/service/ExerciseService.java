package kz.yerkebulan.oopendtermproject.service;

import kz.yerkebulan.oopendtermproject.model.Exercise;
import kz.yerkebulan.oopendtermproject.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public final class ExerciseService {
    private final ExerciseRepository repository;

    public Exercise create(Exercise exercise) {
        return repository.save(exercise);
    }

    public List<Exercise> getAll() {
        return repository.findAll();
    }

    public Exercise getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public Exercise update(Long id, Exercise exercise) {
        Exercise existing = getById(id);
        existing.setName(exercise.getName());
        existing.setReps(exercise.getReps());
        existing.setSets(exercise.getSets());
        existing.setWeight(exercise.getWeight());
        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}

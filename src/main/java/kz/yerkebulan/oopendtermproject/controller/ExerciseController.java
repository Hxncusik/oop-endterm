package kz.yerkebulan.oopendtermproject.controller;

import kz.yerkebulan.oopendtermproject.model.Exercise;
import kz.yerkebulan.oopendtermproject.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/exercise/")
public final class ExerciseController {
    private final ExerciseService service;

    @PostMapping
    public Exercise create(@RequestBody Exercise exercise) {
        return service.create(exercise);
    }

    // READ ALL
    @GetMapping
    public List<Exercise> getAll() {
        return service.getAll();
    }

    // READ BY ID
    @GetMapping("/{id}")
    public Exercise getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Exercise update(@PathVariable Long id, @RequestBody Exercise exercise) {
        return service.update(id, exercise);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

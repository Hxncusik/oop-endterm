package kz.yerkebulan.oopendtermproject.controller;

import kz.yerkebulan.oopendtermproject.dto.User;
import kz.yerkebulan.oopendtermproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService service;

    @PostMapping
    public User create(@RequestBody User user) {
        return service.create(user);
    }

    // READ ALL
    @GetMapping
    public List<User> getAll() {
        return service.getAll();
    }

    // READ BY ID
    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user) {
        return service.update(id, user);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

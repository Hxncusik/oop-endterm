package kz.yerkebulan.oopendtermproject.service;

import kz.yerkebulan.oopendtermproject.dto.User;
import kz.yerkebulan.oopendtermproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public final class UserService {
    private final UserRepository repository;

    public User create(User user) {
        return repository.save(user);
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public User getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public User update(Long id, User user) {
        User existing = getById(id);
        existing.setUsername(user.getUsername());
        existing.setAge(user.getAge());
        existing.setWeight(user.getWeight());
        existing.setEmail(user.getEmail());
        existing.setPassword(user.getPassword());
        existing.setHeight(user.getHeight());
        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}

package kz.yerkebulan.oopendtermproject.service;

import kz.yerkebulan.oopendtermproject.cache.UserCache;
import kz.yerkebulan.oopendtermproject.dto.User;
import kz.yerkebulan.oopendtermproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public final class UserService {
    private final UserRepository repository;
    private final UserCache userCache;

    public User create(User user) {
        userCache.put(user);
        return repository.save(user);
    }

    public Collection<User> getAll() {
        if (userCache.isEmpty()) {
            return repository.findAll();
        }
        return userCache.getAll();
    }

    public User getById(Long id) {
        User user = userCache.get(id);
        if (user == null) {
            return repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }
        return user;
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
        repository.delete(userCache.remove(id));
    }
}

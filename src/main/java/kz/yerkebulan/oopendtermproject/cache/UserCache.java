package kz.yerkebulan.oopendtermproject.cache;

import kz.yerkebulan.oopendtermproject.dto.User;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserCache {
    private final Map<Long, User> userCache = new ConcurrentHashMap<>();

    public User get(Long id) {
        return userCache.get(id);
    }

    public Collection<User> getAll() {
        return userCache.values();
    }

    public User put(User user) {
        return userCache.put(user.getId(), user);
    }

    public void putAll(List<User> users) {
        for (User user : users) {
            userCache.put(user.getId(), user);
        }
    }

    public void putAll(Map<Long, User> users) {
        userCache.putAll(users);
    }

    public User remove(Long id) {
        return userCache.remove(id);
    }

    public boolean isEmpty() {
        return userCache.isEmpty();
    }

    public void clear() {
        userCache.clear();
    }
}

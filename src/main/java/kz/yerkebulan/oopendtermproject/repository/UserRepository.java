package kz.yerkebulan.oopendtermproject.repository;

import kz.yerkebulan.oopendtermproject.dto.User;
import kz.yerkebulan.oopendtermproject.model.Exercise;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<@NonNull User, @NonNull Long> {

}
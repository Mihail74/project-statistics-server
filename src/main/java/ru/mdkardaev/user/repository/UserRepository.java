package ru.mdkardaev.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mdkardaev.user.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);
}

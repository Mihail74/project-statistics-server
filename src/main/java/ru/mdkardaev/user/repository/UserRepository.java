package ru.mdkardaev.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mdkardaev.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);
}

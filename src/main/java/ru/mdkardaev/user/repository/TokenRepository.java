package ru.mdkardaev.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mdkardaev.security.dtos.TokenType;
import ru.mdkardaev.user.entity.Token;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, String> {

    Token findByRawToken(String rawToken);

    List<Token> findTokenByUser_idAndType(Long userId, TokenType type);

    @Query("DELETE FROM Token t WHERE t.expiredTime <= :expiredTime")
    @Modifying
    void deleteExpiredTokens(@Param("expiredTime") Long expiredTime);
}

package ru.mdkardaev.user.entity;

import lombok.*;
import ru.mdkardaev.security.dtos.TokenType;

import javax.persistence.*;

@Entity
@Table(name = "tokens")
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(length = 4096)
    private String rawToken;

    private Long expiredTime;

    @Enumerated(EnumType.STRING)
    private TokenType type;

    @OneToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user"))
    private User user;
}

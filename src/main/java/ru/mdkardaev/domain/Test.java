package ru.mdkardaev.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Test")
@Data
@NoArgsConstructor
public class Test {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "testString")
    private String testString;
}

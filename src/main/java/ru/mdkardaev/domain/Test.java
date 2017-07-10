package ru.mdkardaev.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Mihail on 10.07.2017.
 */
@Entity
@Table(name = "Test")
@Data
@NoArgsConstructor
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "testString")
    private String testString;
}

package com.tymur.tuhaibei.EWATest.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * @author tymur.tuhaibei
 */
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "day_of_birthday", nullable = false)
    private LocalDate dayOfBirthday;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

}

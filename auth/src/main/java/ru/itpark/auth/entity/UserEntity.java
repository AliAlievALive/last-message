package ru.itpark.auth.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import ru.itpark.auth.dto.Role;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "user", schema = "public")
@Data
@EqualsAndHashCode
public class UserEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate createDate;

    @UpdateTimestamp
    private LocalDate modificationDate;

    private String firstName;

    private String lastName;

    private String middleName;

    @Column(unique = true)
    private String login;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

}

package ru.skypro.homework.model;

import jakarta.persistence.*;
import lombok.*;
import ru.skypro.homework.dto.Role;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User extends ParentIDEntity {
    /**
     * логин пользователя
     */
    @Column(name = "username")
    private String username;
    /**
     * пароль пользователя
     */
    @Column(name = "password", nullable = false, unique = true)
    private String password;
    /**
     * имя пользователя
     */
    @Column(name = "firstName")
    private String firstName;
    /**
     * фамилия пользователя
     */
    @Column(name = "lastName")
    private String lastName;
    /**
     * телефон пользователя
     */
    @Column(name = "phone")
    private String phone;
    /**
     * роль пользователя
     */
    @Column(name = "role")
    private Role role;
    /**
     * аватар пользователя
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "avatar_id", referencedColumnName = "id")
    private Image avatar;
}

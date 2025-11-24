package com.kinevid.backend.ModUser.Entity;

import com.kinevid.backend.ModUser.Enums.UserStatus;
import com.kinevid.backend.common.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
public class User extends AuditableEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_USER_ID_GENERATOR", sequenceName = "SEQ_USER_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USER_ID_GENERATOR")
    private Long id;

    @Basic
    @Column(name = "USERNAME", nullable = false, length = 30, unique = true)
    private String username;

    @Basic
    @Column(name = "NAME", nullable = false, length = 60)
    private String name;

    @Basic
    @Column(name = "LASTNAME", nullable = false, length = 60)
    private String lastname;

    @Basic
    @Column(name = "EMAIL", nullable = false, length = 50, unique = true)
    private String email;

    @Basic
    @Column(name = "PASSWORD", nullable = false, length = 30)
    private String password;

    @Basic
    @Column(name = "PHONE", nullable = false, length = 15)
    private String phone;

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "STATE", nullable = false, length = 30)
    private UserStatus status;

}

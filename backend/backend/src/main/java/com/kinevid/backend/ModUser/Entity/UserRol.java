package com.kinevid.backend.ModUser.Entity;

import com.kinevid.backend.ModUser.Enums.EntityState;
import com.kinevid.backend.common.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_ROL")
public class UserRol extends AuditableEntity implements Serializable {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_USER_ROL_ID_GENERATOR", sequenceName = "SEQ_USER_ROL_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USER_ROL_ID_GENERATOR")
    private Long id;

    @Column(name = "STATE", nullable = false)
    @Enumerated(EnumType.STRING)
    private EntityState state;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "ID", name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "ID", name = "ROL_ID", nullable = false)
    private Rol role;
}

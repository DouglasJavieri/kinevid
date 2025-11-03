package com.kinevid.backend.ModUser.Entity;

import com.kinevid.backend.ModUser.Enums.RolName;
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
@Table(name = "ROL")
public class Rol extends AuditableEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_ROL_ID_GENERATOR", sequenceName = "SEQ_ROL_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ROL_ID_GENERATOR")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "NAME", nullable = false, unique = true, length = 50)
    private RolName name;

    @Basic
    @Column(name = "DESCRIPTION", length = 250)
    private String description;

}

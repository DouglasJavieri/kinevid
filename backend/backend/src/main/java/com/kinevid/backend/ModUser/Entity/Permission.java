package com.kinevid.backend.ModUser.Entity;

import com.kinevid.backend.ModUser.Enums.PermissionRol;
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
@Table(name = "PERMISSION")
public class Permission extends AuditableEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_PERMISSION_ID_GENERATOR", sequenceName = "SEQ_PERMISSION_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PERMISSION_ID_GENERATOR")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "NAME", nullable = false, unique = true, length = 100)
    private PermissionRol name;

    @Basic
    @Column(name = "DESCRIPTION", length = 250)
    private String description;

}

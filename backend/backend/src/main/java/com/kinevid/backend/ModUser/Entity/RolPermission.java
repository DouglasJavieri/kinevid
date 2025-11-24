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
@Table(name = "ROL_PERMISSION")
public class RolPermission extends AuditableEntity implements Serializable {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_ROL_PERMISSION_ID_GENERATOR", sequenceName = "SEQ_ROL_PERMISSION_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ROL_PERMISSION_ID_GENERATOR")
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "ID", name = "ROL_ID", nullable = false)
    private Rol rol;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "ID", name = "PERMISSION_ID", nullable = false)
    private Permission permission;
}

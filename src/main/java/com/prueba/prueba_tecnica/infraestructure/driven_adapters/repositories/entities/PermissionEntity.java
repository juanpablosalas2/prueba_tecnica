package com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.entities;

import com.prueba.prueba_tecnica.domain.model.Permission;
import lombok.*;


import javax.persistence.*;

@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "permission")
public class PermissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String typePermission;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "album_id")
    private AlbumEntity albumEntity;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;


    public static PermissionEntity convertToEntity(Permission permission) {
        return PermissionEntity.builder()
                .id(permission.getId())
                .typePermission(permission.getTypePermission())
                .albumEntity(AlbumEntity.builder().id(permission.getAlbum()).build())
                .userEntity(UserEntity.builder().id(permission.getUser()).build())
                .build();
    }

    public static Permission convertToModel(PermissionEntity permissionEntity) {
        return Permission.builder()
                .id(permissionEntity.getId())
                .typePermission(permissionEntity.getTypePermission())
                .album(permissionEntity.getAlbumEntity().getId())
                .user(permissionEntity.getUserEntity().getId())
                .build();
    }

}

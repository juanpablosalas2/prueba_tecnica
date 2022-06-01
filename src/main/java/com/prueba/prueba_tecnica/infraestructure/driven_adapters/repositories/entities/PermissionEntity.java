package com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.entities;

import com.prueba.prueba_tecnica.domain.model.Permission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "permission")
public class PermissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String typePermission;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "album_id")
    private AlbumEntity albumEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;


    public static PermissionEntity convertToEntity(Permission permission) {
        return PermissionEntity.builder()
                .id(permission.getId())
                .typePermission(permission.getTypePermission())
                .albumEntity(AlbumEntity.convertToEntity(permission.getAlbum()))
                .userEntity(UserEntity.convertToEntity(permission.getUser()))
                .build();
    }

    public static Permission convertToModel(PermissionEntity permissionEntity) {
        return Permission.builder()
                .id(permissionEntity.getId())
                .typePermission(permissionEntity.getTypePermission())
                .album(AlbumEntity.convertToModel(permissionEntity.getAlbumEntity()))
                .user(UserEntity.convertToModel(permissionEntity.getUserEntity()))
                .build();
    }

}

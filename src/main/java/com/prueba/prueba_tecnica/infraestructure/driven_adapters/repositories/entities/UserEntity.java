package com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.entities;


import com.prueba.prueba_tecnica.domain.model.Album;
import com.prueba.prueba_tecnica.domain.model.Permission;
import com.prueba.prueba_tecnica.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Data
@Builder(toBuilder = true)
@Entity(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String phone;
    @Column
    private String website;

    @OneToMany(mappedBy = "userEntity",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<AlbumEntity> albums;

    @OneToMany(mappedBy = "userEntity",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PermissionEntity> permissions;

    public static UserEntity convertToEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .website(user.getWebsite())
                .albums((List<AlbumEntity>) user.getAlbums())
                .permissions((List<PermissionEntity>) user.getPermissions())
                .build();
    }

    public static User convertToModel(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .phone(userEntity.getPhone())
                .website(userEntity.getWebsite())
                .albums((Flux<Album>) userEntity.getAlbums())
                .permissions((Flux<Permission>) userEntity.getPermissions())
                .build();
    }

}

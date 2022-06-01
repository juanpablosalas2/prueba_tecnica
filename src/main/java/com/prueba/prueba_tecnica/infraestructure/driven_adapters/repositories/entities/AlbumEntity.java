package com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.entities;


import com.prueba.prueba_tecnica.domain.model.Album;
import com.prueba.prueba_tecnica.domain.model.Permission;
import com.prueba.prueba_tecnica.domain.model.Photo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;

import javax.persistence.*;
import java.util.Collection;

@Builder(toBuilder = true)
@Data
@Entity(name = "album")
@NoArgsConstructor
@AllArgsConstructor
public class AlbumEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String title;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "albumEntity",cascade = CascadeType.MERGE)
    private Collection<PermissionEntity> permissions;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "albumEntity",cascade = CascadeType.MERGE)
    private Collection<PhotoEntity> photos;


    public static AlbumEntity convertToEntity(Album album) {
        return AlbumEntity.builder()
                .id(album.getId())
                .title(album.getTitle())
                .userEntity(UserEntity.convertToEntity(album.getUser()))
                .permissions((Collection<PermissionEntity>) album.getPermissions())
                .photos((Collection<PhotoEntity>) album.getPhotos())
                .build();
    }

    public static Album convertToModel(AlbumEntity albumEntity) {
        return Album.builder()
                .id(albumEntity.getId())
                .title(albumEntity.getTitle())
                .user(UserEntity.convertToModel(albumEntity.getUserEntity()))
                .permissions((Flux<Permission>) albumEntity.getPermissions())
                .photos((Flux<Photo>) albumEntity.getPhotos())
                .build();
    }
}

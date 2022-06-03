package com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.entities;


import com.prueba.prueba_tecnica.domain.model.Album;
import com.prueba.prueba_tecnica.domain.model.Permission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "album")
@Entity
public class AlbumEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;
    @Column
    private Long userCreate;


    @OneToMany(mappedBy = "albumEntity", cascade = CascadeType.MERGE)
    private List<PermissionEntity> permissions;

    @OneToMany(mappedBy = "albumEntity",cascade = CascadeType.MERGE)
    private List<PhotoEntity> photos;


    public static AlbumEntity convertToEntity(Album album) {
        return AlbumEntity.builder()
                .id(album.getId())
                .title(album.getTitle())
                .userCreate(album.getUserCreate())
                .build();
    }

    public static Album convertToModel(AlbumEntity albumEntity) {
        return Album.builder()
                .id(albumEntity.getId())
                .title(albumEntity.getTitle())
                .userCreate(albumEntity.getUserCreate())
                .build();
    }
}

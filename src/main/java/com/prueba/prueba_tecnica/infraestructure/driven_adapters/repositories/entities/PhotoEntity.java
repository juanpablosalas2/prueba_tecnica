package com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.entities;


import com.prueba.prueba_tecnica.domain.model.Photo;
import lombok.*;


import javax.persistence.*;


@Getter
@Setter
@Table(name = "photo")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder(toBuilder = true)
public class PhotoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String url;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "album_id")
    private AlbumEntity albumEntity;



    public static PhotoEntity convertToEntity(Photo photo) {
        return PhotoEntity.builder()
                .id(photo.getId())
                .title(photo.getTitle())
                .url(photo.getUrl())
                .albumEntity(AlbumEntity.builder().id(photo.getAlbum()).build())
                .build();
    }

    public static Photo convertToModel(PhotoEntity photoEntity) {
        return Photo.builder()
                .id(photoEntity.getId())
                .title(photoEntity.getTitle())
                .url(photoEntity.getUrl())
                .album(photoEntity.getAlbumEntity().getId())
                .build();
    }
}

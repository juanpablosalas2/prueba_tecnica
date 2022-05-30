package com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Table(name = "photo")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class PhotoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String title;
    @Column
    private String url;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private AlbumEntity albumEntity;
}

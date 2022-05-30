package com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Builder(toBuilder = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "album")
public class AlbumEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @OneToMany(mappedBy = "albumEntity")
    private Collection<PermissionEntity> permissions;

    @OneToMany(mappedBy = "albumEntity")
    private Collection<PhotoEntity> photos;
}

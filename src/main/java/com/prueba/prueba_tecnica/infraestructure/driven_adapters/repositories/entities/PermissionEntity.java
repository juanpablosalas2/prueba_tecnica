package com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "permission")
@Entity
public class PermissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String typePermission;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private AlbumEntity albumEntity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;


}

package com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Data
@Builder(toBuilder = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
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

    @OneToMany(mappedBy = "userEntity")
    private Collection<AlbumEntity> albums;

    @OneToMany(mappedBy = "userEntity")
    private Collection<PermissionEntity> permissions;

}

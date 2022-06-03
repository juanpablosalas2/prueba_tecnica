package com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.entities;


import com.prueba.prueba_tecnica.domain.model.User;
import lombok.*;
import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@Builder(toBuilder = true)
@Table(name = "user")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String website;

    @OneToMany(mappedBy = "userEntity")
    List<PermissionEntity> permissionEntities;


    public static UserEntity convertToEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .website(user.getWebsite())
                .build();
    }

    public static User convertToModel(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .phone(userEntity.getPhone())
                .website(userEntity.getWebsite())
                .build();
    }

}

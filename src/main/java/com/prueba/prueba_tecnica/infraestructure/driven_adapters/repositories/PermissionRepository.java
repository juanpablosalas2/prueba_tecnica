package com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories;

import com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.entities.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PermissionRepository  extends JpaRepository<PermissionEntity,Long> {

    @Query("SELECT a,b,c FROM permission a JOIN a.albumEntity b JOIN a.userEntity c WHERE c.id =?1 and b.id = ?2")
    PermissionEntity getTypePermissionByIds(Long idUser,Long idAlbum);
}

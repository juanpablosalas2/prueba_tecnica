package com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories;

import com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.entities.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface PermissionRepository  extends JpaRepository<PermissionEntity,Long> {

    @Query("select a,b,c from PermissionEntity a inner join a.albumEntity b join a.userEntity c WHERE c.id =?1 and b.id = ?2" )
    PermissionEntity getTypePermissionByIds(Long idUser,Long idAlbum);

    List<PermissionEntity> findAllByUserEntity_Id(Long idUser);
    List<PermissionEntity> findAllByAlbumEntity_Id(Long idUser);

    List<PermissionEntity> findAllByAlbumEntity_IdAndTypePermission(Long idAlbum, String permission);
}

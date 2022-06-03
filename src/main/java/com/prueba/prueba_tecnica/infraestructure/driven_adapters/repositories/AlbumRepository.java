package com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories;

import com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.entities.AlbumEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
@Transactional
public interface AlbumRepository extends JpaRepository<AlbumEntity,Long> {
    List<AlbumEntity> findAllByUserCreate(Long user);

}

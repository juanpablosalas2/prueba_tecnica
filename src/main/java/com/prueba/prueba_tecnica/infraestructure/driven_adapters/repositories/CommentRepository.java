package com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories;

import com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.entities.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository  extends JpaRepository<CommentEntity,Long> {

    CommentEntity findByEmail(String email);

    CommentEntity findByName(String name);
}

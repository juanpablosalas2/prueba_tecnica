package com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories;

import com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.entities.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository  extends JpaRepository<PermissionEntity,Long> {
}

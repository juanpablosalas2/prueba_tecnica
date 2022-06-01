package com.prueba.prueba_tecnica.domain.usecase;

import com.prueba.prueba_tecnica.domain.model.exceptions.UserException;
import com.prueba.prueba_tecnica.domain.usecase.interfaces.ValidatePermissionUser;
import com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.PermissionRepository;
import com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ValidatePermissionUserImp implements ValidatePermissionUser {

    public static final String ID_NOT_FOUND = "User With ID %d not found";
    public static final String WRITE = "write";
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    @Override
    @Transactional
    public Mono<Boolean> validatePermissionUser(Long userId,Long albumId) {
        return Mono.just(userId)
                .map(userRepository::findById)
                .switchIfEmpty(Mono.error(new UserException(String.format(ID_NOT_FOUND,userId))))
                .map(userEntity -> permissionRepository.getTypePermissionByIds(userId,albumId))
                .map(permissionEntity -> permissionEntity.getTypePermission().equals(WRITE) ? Boolean.TRUE: Boolean.FALSE);

    }


}

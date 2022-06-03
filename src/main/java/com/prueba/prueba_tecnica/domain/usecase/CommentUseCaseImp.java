package com.prueba.prueba_tecnica.domain.usecase;

import com.prueba.prueba_tecnica.domain.model.Comment;
import com.prueba.prueba_tecnica.domain.model.exceptions.CommentException;
import com.prueba.prueba_tecnica.domain.model.exceptions.UserException;
import com.prueba.prueba_tecnica.domain.usecase.interfaces.CommentUseCase;
import com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.CommentRepository;
import com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.entities.CommentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CommentUseCaseImp implements CommentUseCase {
    private static final String ID_NOT_FOUND = "Comment With name %d not found";
    private final CommentRepository commentRepository;

    @Override
    public Mono<Comment> getCommentByName(String name) {
        return Mono.just(name)
                .map(commentRepository::findByName)
                .switchIfEmpty(Mono.error(new UserException(ID_NOT_FOUND + name)))
                .map(CommentEntity::convertToModel)
                .onErrorResume(throwable -> Mono.error(new CommentException("Comment could not be found"+throwable.getMessage(), throwable)));

    }

    @Override
    public Mono<Comment> getCommentByEmail(String email) {
        return Mono.just(email)
                .map(commentRepository::findByEmail)
                .switchIfEmpty(Mono.error(new UserException(ID_NOT_FOUND + email)))
                .map(CommentEntity::convertToModel)
                .onErrorResume(throwable -> Mono.error(new CommentException("Comment could not be found "+throwable.getMessage(), throwable)));
    }
}

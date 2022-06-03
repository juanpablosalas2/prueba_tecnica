package com.prueba.prueba_tecnica.domain.usecase.interfaces;

import com.prueba.prueba_tecnica.domain.model.Comment;
import reactor.core.publisher.Mono;

public interface CommentUseCase {

    Mono<Comment> getCommentByName(String name);

    Mono<Comment> getCommentByEmail(String email);
}

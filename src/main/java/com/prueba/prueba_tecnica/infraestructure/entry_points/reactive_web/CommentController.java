package com.prueba.prueba_tecnica.infraestructure.entry_points.reactive_web;

import com.prueba.prueba_tecnica.domain.model.Comment;
import com.prueba.prueba_tecnica.domain.model.GeneralResponse;
import com.prueba.prueba_tecnica.domain.usecase.interfaces.CommentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@RestController
@RequestMapping("comment")
@RequiredArgsConstructor
public class CommentController {

    private static final String SEARCH_SUCCESS = "Comment founded successfully" ;
    private final CommentUseCase commentUseCase;

    private static final String COMMENT = "Comment";
    private final GeneralResponse<Comment> generalResponse;


    @GetMapping("/byEmail/{email}")
    public Mono<ResponseEntity<GeneralResponse<Comment>>> getCommentByEmail(@PathVariable String email){
        return commentUseCase.getCommentByEmail(email)
                .map(comment -> {
                    HashMap<String, Comment> data = new HashMap<>();
                    data.put(COMMENT, comment);
                    return generalResponse.generateGeneralResponseSuccess(Boolean.TRUE, SEARCH_SUCCESS, data, HttpStatus.CREATED);
                })
                .onErrorResume(throwable -> Mono.just(generalResponse.generateGeneralResponseError(Boolean.FALSE, throwable.getMessage(), HttpStatus.NOT_FOUND)));
    }


    @GetMapping("/byName/{name}")
    public Mono<ResponseEntity<GeneralResponse<Comment>>> getCommentByName(@PathVariable String name){
        return commentUseCase.getCommentByName(name)
                .map(comment -> {
                    HashMap<String, Comment> data = new HashMap<>();
                    data.put(COMMENT, comment);
                    return generalResponse.generateGeneralResponseSuccess(Boolean.TRUE, SEARCH_SUCCESS, data, HttpStatus.CREATED);
                })
                .onErrorResume(throwable -> Mono.just(generalResponse.generateGeneralResponseError(Boolean.FALSE, throwable.getMessage(), HttpStatus.NOT_FOUND)));
    }
}

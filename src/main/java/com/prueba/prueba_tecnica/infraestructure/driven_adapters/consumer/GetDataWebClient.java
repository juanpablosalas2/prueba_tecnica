package com.prueba.prueba_tecnica.infraestructure.driven_adapters.consumer;

import com.prueba.prueba_tecnica.domain.model.Album;
import com.prueba.prueba_tecnica.domain.model.Comment;
import com.prueba.prueba_tecnica.domain.model.Photo;
import com.prueba.prueba_tecnica.domain.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class GetDataWebClient {

    private final WebClient webClient;
    private final String BASE = "https://jsonplaceholder.typicode.com/";

    public GetDataWebClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl(BASE).build();
    }


    public List<Comment> getComments(){
        return webClient.get()
                .uri("/comments")
                .retrieve()
                .bodyToFlux(Comment.class)
                .collectList().block();
    }

    public List<Album> getAlbums(){
        return webClient.get()
                .uri("/albums")
                .retrieve()
                .bodyToFlux(Album.class)
                .collectList().block();
    }

    public List<Photo> getPhotos(){
        return webClient.get()
                .uri("/photos")
                .retrieve()
                .bodyToFlux(Photo.class)
                .collectList().block();
    }

    public List<User> getUsers(){
        return webClient.get()
                .uri("/users")
                .retrieve()
                .bodyToFlux(User.class)
                .collectList().block();
    }


}

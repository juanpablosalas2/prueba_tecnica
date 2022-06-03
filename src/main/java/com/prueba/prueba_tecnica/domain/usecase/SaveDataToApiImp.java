package com.prueba.prueba_tecnica.domain.usecase;

import com.prueba.prueba_tecnica.domain.usecase.interfaces.AlbumUseCase;
import com.prueba.prueba_tecnica.domain.usecase.interfaces.SaveDataToApi;
import com.prueba.prueba_tecnica.domain.usecase.interfaces.UserUseCase;
import com.prueba.prueba_tecnica.infraestructure.driven_adapters.consumer.GetDataWebClient;
import com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.CommentRepository;
import com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.PhotoRepository;
import com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.entities.CommentEntity;
import com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.entities.PhotoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveDataToApiImp implements SaveDataToApi, ApplicationListener<ContextRefreshedEvent> {

    private final GetDataWebClient getDataWebClient;
    private final CommentRepository commentRepository;

    private final AlbumUseCase albumUseCase;

    private final PhotoRepository photoRepository;

    private final UserUseCase userUseCase;

    @Override
    public void saveComments() {
            getDataWebClient.getComments()
                    .forEach(comment -> {
                        CommentEntity commentEntity = CommentEntity.convertToEntity(comment);
                        commentRepository.save(commentEntity);
                    });
    }

    @Override
    public void saveAlbums() {
        getDataWebClient.getAlbums()
                .forEach(album -> albumUseCase.createAlbum(album).subscribe());
    }

    @Override
    public void savePhotos() {
        getDataWebClient.getPhotos()
                .forEach(photo -> {
                    PhotoEntity photoEntity = PhotoEntity.convertToEntity(photo);
                    photoRepository.save(photoEntity);
                });
    }

    @Override
    public void saveUsers() {
        getDataWebClient.getUsers()
                .forEach(user -> userUseCase.createUser(user).subscribe());
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        saveUsers();
        saveAlbums();
        savePhotos();
        saveComments();
    }
}

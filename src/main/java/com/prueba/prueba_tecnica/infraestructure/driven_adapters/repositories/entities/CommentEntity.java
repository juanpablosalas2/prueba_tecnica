package com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.entities;

import com.prueba.prueba_tecnica.domain.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Table(name = "comment")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Lob
    private String body;


    public static CommentEntity convertToEntity(Comment comment) {
        return CommentEntity.builder()
                .id(comment.getId())
                .name(comment.getName())
                .email(comment.getEmail())
                .body(comment.getBody())
                .build();
    }

    public static Comment convertToModel(CommentEntity commentEntity) {
        return Comment.builder()
                .id(commentEntity.getId())
                .name(commentEntity.getName())
                .email(commentEntity.getEmail())
                .body(commentEntity.getBody())
                .build();
    }
}

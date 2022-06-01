package com.prueba.prueba_tecnica.domain.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
@Component
public class GeneralResponse<T> implements Serializable {

    boolean isSuccess;

    String message;

    Map<String, T> data;




    public ResponseEntity<GeneralResponse<T>> generateGeneralResponseSuccess(Boolean isSuccess, String message,
                                                                             HashMap<String, T> data, HttpStatus status) {
        return new ResponseEntity<>(
                GeneralResponse.<T>builder()
                        .isSuccess(isSuccess)
                        .message(message)
                        .data(data)
                        .build(),
                status
        );
    }


    public ResponseEntity<GeneralResponse<T>> generateGeneralResponseError(Boolean isSuccess, String message, HttpStatus status) {
        return new ResponseEntity<>(
                GeneralResponse.<T>builder()
                        .isSuccess(isSuccess)
                        .message(message)
                        .build(),
                status
        );
    }
}


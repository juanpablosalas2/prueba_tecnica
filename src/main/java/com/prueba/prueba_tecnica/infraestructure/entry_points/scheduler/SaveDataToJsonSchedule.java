package com.prueba.prueba_tecnica.infraestructure.entry_points.scheduler;

import com.prueba.prueba_tecnica.domain.usecase.interfaces.SaveDataToApi;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class SaveDataToJsonSchedule {

    private final SaveDataToApi saveDataToApi;


    @Scheduled(fixedDelay =  3600000)
    void saveDataToJson(){
//        saveDataToApi.saveComments();
    }
}

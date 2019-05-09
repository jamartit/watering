package it.jamart.maniek.garden.watering.program;

import it.jamart.maniek.garden.watering.model.SystemContainer;
import it.jamart.maniek.garden.watering.web.WateringController;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class ScheduledWatering {

    @Autowired
    private WateringController wateringController;

    @Autowired
    private SystemContainer systemContainer;

    @Getter
    private WateringJob wateringJob;

    @PostConstruct
    private void init() {
        wateringJob = new WateringJob(wateringController, systemContainer);
    }

    @Scheduled(cron = "0 0 4 * * *")
    public void defaultWatering() {
        log.info("starting scheduled default watering");
        if (!wateringJob.isRunning()) {
            wateringJob.start();
        } else {
            log.info("default watering already running... do nothing");
        }
    }


}

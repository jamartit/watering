package it.jamart.maniek.garden.watering.program;

import it.jamart.maniek.garden.watering.enums.WateringPinNames;
import it.jamart.maniek.garden.watering.model.WateringSection;
import it.jamart.maniek.garden.watering.web.WateringController;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class WateringJob implements Runnable {

    private Thread worker;

    @Getter
    private final AtomicBoolean running = new AtomicBoolean(false);

    @Getter
    private final AtomicBoolean disabled = new AtomicBoolean(false);

    private WateringController wateringController;

    private String jobName;

    public WateringJob(WateringController wateringController) {
        this.wateringController = wateringController;
    }

    public void start() {
        this.start("default");
    }

    public void start(String name) {
        worker = new Thread(this);
        this.jobName = name;
        worker.start();
    }

    public void disable() {
        disabled.set(true);
    }

    public void enable() {
        disabled.set(false);
    }

    public void interrupt() {
        running.set(false);
        worker.interrupt();
    }

    public boolean isRunning() {
        return running.get();
    }

    @Override
    public void run() {
        running.set(true);

        if(!disabled.get()) {
            try {
                executeSection(WateringPinNames.SECTION_1);
                executeSection(WateringPinNames.SECTION_2);
                executeSection(WateringPinNames.SECTION_3);
                executeSection(WateringPinNames.SECTION_4);
                executeSection(WateringPinNames.SECTION_5);
                executeSection(WateringPinNames.SECTION_6);
                executeSection(WateringPinNames.SECTION_7);
                executeSection(WateringPinNames.SECTION_8);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.warn("abort default watering " + jobName + " - " + e);
            }
            wateringController.clearAll();
        } else {
            log.info("watering disabled");
        }
        running.set(false);
    }

    private void executeSection(WateringPinNames sectionName) throws InterruptedException {
        WateringSection section = wateringController.resolveSection(sectionName);
        if (section != null && wateringController.turnOnSection(section)) {
            Thread.sleep(section.getActiveMinutes() * 100);
            wateringController.turnOffSection(section);
        }
    }
}

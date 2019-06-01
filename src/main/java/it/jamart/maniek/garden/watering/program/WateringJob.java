package it.jamart.maniek.garden.watering.program;

import it.jamart.maniek.garden.watering.enums.WateringPinNames;
import it.jamart.maniek.garden.watering.model.RainDetector;
import it.jamart.maniek.garden.watering.model.SystemContainer;
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

    private SystemContainer systemContainer;

    private String jobName;

    public WateringJob(WateringController wateringController, SystemContainer systemContainer) {
        this.wateringController = wateringController;
        this.systemContainer = systemContainer;
    }

    public void start() {
        this.start("default");
    }

    public void start(String name) {
        worker = new Thread(this);
        this.jobName = name;
        worker.run();
    }

    public void disable() {
        disabled.set(true);
    }

    public void enable() {
        disabled.set(false);
    }

    public void interrupt() {
        log.info("interrupting");
        worker.interrupt();
    }

    public boolean isRunning() {
        return running.get();
    }

    @Override
    public void run() {
        running.set(true);

        if (!disabled.get()) {
            try {
                log.info("executing watering: " + jobName);
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
        RainDetector rainDetector = systemContainer.getRainDetector();
        if (section != null && !rainDetector.isRainDetected()) {
            log.info("execute: running " + sectionName.getName() + " / for: " + section.getActiveMinutes()+" minutes");
            wateringController.turnOnSection(section);
            Thread.sleep(section.getActiveMinutes() * 1000 * 60);
            wateringController.turnOffSection(section);
        }
    }
}

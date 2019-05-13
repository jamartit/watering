package it.jamart.maniek.garden.watering.web;

import it.jamart.maniek.garden.watering.enums.WateringPinNames;
import it.jamart.maniek.garden.watering.model.ExternalDeviceSwitch;
import it.jamart.maniek.garden.watering.model.WateringSection;
import it.jamart.maniek.garden.watering.model.SystemContainer;
import it.jamart.maniek.garden.watering.program.ScheduledWatering;
import it.jamart.maniek.garden.watering.program.WateringJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class WateringController {

    @Autowired
    private ScheduledWatering scheduledWatering;

    @Autowired
    private SystemContainer systemContainer;

    @GetMapping("/heartbeat")
    public boolean heartbeat() {
        return true;
    }

    @GetMapping("/clear")
    public boolean clearAll() {
        for (WateringSection section : systemContainer.getSectionList()) {
            section.getPin().turnOff();
        }
        valvePowerOff();
        return true;
    }

    @GetMapping("/water/activate/{section}")
    public boolean activateSection(@PathVariable("section") String section) {
        return turnOnSection(resolveSection(WateringPinNames.fromString(section)));
    }

    @GetMapping("/water/deactivate/{section}")
    public boolean deactivateSection(@PathVariable("section") String section) {
        return turnOffSection(resolveSection(WateringPinNames.fromString(section)));
    }

    @GetMapping("/water/check/{section}")
    public boolean checkSection(@PathVariable("section") String section) {
        return checkSection(resolveSection(WateringPinNames.fromString(section)));
    }

    @GetMapping("/water/toggle/{section}")
    public boolean toggleSection(@PathVariable("section") String section) {
        return checkSection(section) ? deactivateSection(section) : activateSection(section);
    }

    @GetMapping("/water/execute")
    public void executeWatering() {
        WateringJob wateringJob = scheduledWatering.getWateringJob();
        if (!wateringJob.isRunning()) {
            wateringJob.start("per Request");
        } else {
            log.info("watering already running... do nothing");
        }
    }

    @GetMapping("/water/abort")
    public void abortWatering() {
        WateringJob wateringJob = scheduledWatering.getWateringJob();
        wateringJob.interrupt();
        clearAll();
    }

    @GetMapping("/water/disable")
    public void disableWatering() {
        WateringJob wateringJob = scheduledWatering.getWateringJob();
        wateringJob.disable();
    }

    @GetMapping("/water/enable")
    public void enableWatering() {
        WateringJob wateringJob = scheduledWatering.getWateringJob();
        wateringJob.enable();
    }

    @GetMapping("/water/status")
    public String statusWatering() {
        WateringJob wateringJob = scheduledWatering.getWateringJob();
        return "Status: disabled: " + wateringJob.getDisabled().get() + " / running: " + wateringJob.getRunning().get();
    }

    @GetMapping("/water/is-active")
    public boolean isWatering() {
        WateringJob wateringJob = scheduledWatering.getWateringJob();
        return wateringJob.isRunning();
    }

    @GetMapping("/water/valve/power-on")
    public void valvePowerOn() {
        ExternalDeviceSwitch valvesPower = systemContainer.getExternalDeviceByNameEnum(WateringPinNames.VALVES_POWER);
        valvesPower.getPin().turnOn();
    }

    @GetMapping("/water/valve/power-off")
    public void valvePowerOff() {
        ExternalDeviceSwitch valvesPower = systemContainer.getExternalDeviceByNameEnum(WateringPinNames.VALVES_POWER);
        valvesPower.getPin().turnOff();
    }

    @GetMapping("/water/valve/power-status")
    public boolean valvePowerStatus() {
        ExternalDeviceSwitch valvesPower = systemContainer.getExternalDeviceByNameEnum(WateringPinNames.VALVES_POWER);
        return valvesPower.getPin().isActive();
    }

    public boolean turnOnSection(WateringSection section) {
        boolean result = false;
        if (!isWatering()) {
            clearAll();
            if (section != null) {
                valvePowerOn();
                section.getPin().turnOn();
                result = true;
            }
        }
        return result;
    }

    public boolean turnOffSection(WateringSection section) {
        boolean result = false;
        if (!isWatering()) {
            if (section != null) {
                section.getPin().turnOff();
                valvePowerOff();
                result = true;
            }
        }
        return result;
    }

    private boolean checkSection(WateringSection section) {
        return section != null ? section.getPin().isActive() : false;
    }

    public WateringSection resolveSection(WateringPinNames sectionEnum) {
        WateringSection section = systemContainer.getWateringSectionByNameEnum(sectionEnum);
        if (section == null) {
            log.error("section by given enum: " + sectionEnum.getName() + " is null");
            return null;
        }
        if (section.isDisabled()) {
            log.debug("section by given enum: " + sectionEnum.getName() + " is disabled");
            return null;
        }
        return section;
    }
}


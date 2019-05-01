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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class WateringController {

    @Autowired
    private ScheduledWatering scheduledWatering;

    @GetMapping("/heartbeat")
    public boolean heartbeat() {
        return true;
    }

    @GetMapping("/clear")
    public boolean clearAll() {
        for (WateringSection section : SystemContainer.getInstance().getSectionList()) {
            section.getPin().turnOff();
        }
        valvePowerOff();
        return true;
    }

    @GetMapping("/water/activate/section-1")
    public boolean activateSection1() {
        return turnOnSection(resolveSection(WateringPinNames.SECTION_1));
    }

    @GetMapping("/water/deactivate/section-1")
    public boolean deactivateSection1() {
        return turnOffSection(resolveSection(WateringPinNames.SECTION_1));
    }

    @GetMapping("/water/check/section-1")
    public boolean checkSection1() {
        return checkSection(resolveSection(WateringPinNames.SECTION_1));
    }

    @GetMapping("/water/activate/section-2")
    public boolean activateSection2() {
        return turnOnSection(resolveSection(WateringPinNames.SECTION_2));
    }

    @GetMapping("/water/deactivate/section-2")
    public boolean deactivateSection2() {
        return turnOffSection(resolveSection(WateringPinNames.SECTION_2));
    }

    @GetMapping("/water/check/section-2")
    public boolean checkSection2() {
        return checkSection(resolveSection(WateringPinNames.SECTION_2));
    }

    @GetMapping("/water/activate/section-3")
    public boolean activateSection3() {
        return turnOnSection(resolveSection(WateringPinNames.SECTION_3));
    }

    @GetMapping("/water/deactivate/section-3")
    public boolean deactivateSection3() {
        return turnOffSection(resolveSection(WateringPinNames.SECTION_3));
    }

    @GetMapping("/water/check/section-3")
    public boolean checkSection3() {
        return checkSection(resolveSection(WateringPinNames.SECTION_3));
    }

    @GetMapping("/water/activate/section-4")
    public boolean activateSection4() {
        return turnOnSection(resolveSection(WateringPinNames.SECTION_4));
    }

    @GetMapping("/water/deactivate/section-4")
    public boolean deactivateSection4() {
        return turnOffSection(resolveSection(WateringPinNames.SECTION_4));
    }

    @GetMapping("/water/check/section-4")
    public boolean checkSection4() {
        return checkSection(resolveSection(WateringPinNames.SECTION_4));
    }

    @GetMapping("/water/activate/section-5")
    public boolean activateSection5() {
        return turnOnSection(resolveSection(WateringPinNames.SECTION_5));
    }

    @GetMapping("/water/deactivate/section-5")
    public boolean deactivateSection5() {
        return turnOffSection(resolveSection(WateringPinNames.SECTION_5));
    }

    @GetMapping("/water/check/section-5")
    public boolean checkSection5() {
        return checkSection(resolveSection(WateringPinNames.SECTION_5));
    }

    @GetMapping("/water/activate/section-6")
    public boolean activateSection6() {
        return turnOnSection(resolveSection(WateringPinNames.SECTION_6));
    }

    @GetMapping("/water/deactivate/section-6")
    public boolean deactivateSection6() {
        return turnOffSection(resolveSection(WateringPinNames.SECTION_6));
    }

    @GetMapping("/water/check/section-6")
    public boolean checkSection6() {
        return checkSection(resolveSection(WateringPinNames.SECTION_6));
    }

    @GetMapping("/water/activate/section-7")
    public boolean activateSection7() {
        return turnOnSection(resolveSection(WateringPinNames.SECTION_7));
    }

    @GetMapping("/water/deactivate/section-7")
    public boolean deactivateSection7() {
        return turnOffSection(resolveSection(WateringPinNames.SECTION_7));
    }

    @GetMapping("/water/check/section-7")
    public boolean checkSection7() {
        return checkSection(resolveSection(WateringPinNames.SECTION_7));
    }

    @GetMapping("/water/activate/section-8")
    public boolean activateSection8() {
        return turnOnSection(resolveSection(WateringPinNames.SECTION_8));
    }

    @GetMapping("/water/deactivate/section-8")
    public boolean deactivateSection8() {
        return turnOffSection(resolveSection(WateringPinNames.SECTION_8));
    }

    @GetMapping("/water/check/section-8")
    public boolean checkSection8() {
        return checkSection(resolveSection(WateringPinNames.SECTION_8));
    }

    @GetMapping("/water/check/all")
    public String checkStatusAll() {
        return new StringBuffer()
                .append(checkSection1()).append(" / ")
                .append(checkSection2()).append(" / ")
                .append(checkSection3()).append(" / ")
                .append(checkSection4()).append(" / ")
                .append(checkSection5()).append(" / ")
                .append(checkSection6()).append(" / ")
                .append(checkSection7()).append(" / ")
                .append(checkSection8()).append(" / ")
                .toString();
    }

    @GetMapping("/water/execute")
    public void executeWatering() {
        WateringJob wateringJob = scheduledWatering.getWateringJob();
        wateringJob.start("per Request");
    }

    @GetMapping("/water/abort")
    public void abortWatering() {
        WateringJob wateringJob = scheduledWatering.getWateringJob();
        wateringJob.interrupt();
        valvePowerOff();
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

    @GetMapping("/water/valve/power-on")
    public void valvePowerOn() {
        ExternalDeviceSwitch valvesPower = SystemContainer.getInstance().getExternalDeviceByNameEnum(WateringPinNames.VALVES_POWER);
        valvesPower.getPin().turnOn();
    }

    @GetMapping("/water/valve/power-off")
    public void valvePowerOff() {
        ExternalDeviceSwitch valvesPower = SystemContainer.getInstance().getExternalDeviceByNameEnum(WateringPinNames.VALVES_POWER);
        valvesPower.getPin().turnOff();
    }

    @GetMapping("/water/valve/power-status")
    public boolean valvePowerStatus() {
        ExternalDeviceSwitch valvesPower = SystemContainer.getInstance().getExternalDeviceByNameEnum(WateringPinNames.VALVES_POWER);
        return valvesPower.getPin().isActive();
    }

    public boolean turnOnSection(WateringSection section) {
        clearAll();
        boolean result = false;
        if (section != null) {
            valvePowerOn();
            section.getPin().turnOn();
            result = true;
        }
        return result;
    }

    public boolean turnOffSection(WateringSection section) {
        boolean result = false;
        if (section != null) {
            section.getPin().turnOff();
            valvePowerOff();
            result = true;
        }
        return result;
    }

    private boolean checkSection(WateringSection section) {
        return section != null ? section.getPin().isActive() : false;
    }

    public WateringSection resolveSection(WateringPinNames sectionEnum) {
        WateringSection section = SystemContainer.getInstance().getWateringSectionByNameEnum(sectionEnum);
        if (section == null) {
            log.error("section by given enum: " + sectionEnum.getName() + " is null");
            return null;
        }
        if (section.isDisabled()) {
            log.warn("section by given enum: " + sectionEnum.getName() + " is disabled");
            return null;
        }
        return section;
    }
}


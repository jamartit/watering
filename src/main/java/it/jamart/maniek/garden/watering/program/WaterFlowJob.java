package it.jamart.maniek.garden.watering.program;

import it.jamart.maniek.garden.watering.enums.WateringPinNames;
import it.jamart.maniek.garden.watering.model.ExternalDeviceSwitch;
import it.jamart.maniek.garden.watering.model.SystemContainer;
import it.jamart.maniek.garden.watering.model.WaterFlowDetector;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WaterFlowJob implements Runnable {

    private SystemContainer systemContainer;

    public WaterFlowJob(SystemContainer systemContainer) {
        this.systemContainer = systemContainer;
    }

    @Override
    public void run() {

        ExternalDeviceSwitch controlLight = systemContainer.getExternalDeviceByNameEnum(WateringPinNames.CONTROL_LIGHT);
        WaterFlowDetector waterFlowDetector = systemContainer.getWaterFlowDetector();
        while (true) {
            try {
                if (waterFlowDetector.isWaterFlowing() && !isLightOn(controlLight)) {
                    turnLightOn(controlLight);
                } else if (!waterFlowDetector.isWaterFlowing() && isLightOn(controlLight)) {
                    turnLightOff(controlLight);
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.warn("water flow indicator interrupted");
            }
        }
    }

    private boolean isLightOn(ExternalDeviceSwitch controlLight) {
        return controlLight.getPin().isActive();
    }

    private void turnLightOn(ExternalDeviceSwitch controlLight) {
        controlLight.getPin().turnOn();
    }

    private void turnLightOff(ExternalDeviceSwitch controlLight) {
        controlLight.getPin().turnOff();
    }
}

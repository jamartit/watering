package it.jamart.maniek.garden.watering.program;

import it.jamart.maniek.garden.watering.enums.WateringPinNames;
import it.jamart.maniek.garden.watering.model.ExternalDeviceSwitch;
import it.jamart.maniek.garden.watering.model.SystemContainer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class WaterFlowJob implements Runnable {

    @Getter
    private final AtomicBoolean flowing = new AtomicBoolean(false);

    @Override
    public void run() {

        while(true) {
            ExternalDeviceSwitch controlLight = SystemContainer.getInstance().getExternalDeviceByNameEnum(WateringPinNames.CONTROL_LIGHT);

        }

    }
}

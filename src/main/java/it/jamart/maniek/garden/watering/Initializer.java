package it.jamart.maniek.garden.watering;

import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import it.jamart.maniek.garden.watering.enums.WateringPinNames;
import it.jamart.maniek.garden.watering.model.*;
import it.jamart.maniek.garden.watering.pintypes.DigitalInputPin;
import it.jamart.maniek.garden.watering.pintypes.DigitalOutputPin;
import it.jamart.maniek.garden.watering.program.WaterFlowJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Initializer implements CommandLineRunner {

    @Autowired
    private SystemContainer systemContainer;

    @Override
    public void run(String... args) {
        systemContainer.setAllSections(
                new WateringSection(new DigitalOutputPin(RaspiPin.GPIO_21, PinState.HIGH, WateringPinNames.SECTION_1), 10),
                new WateringSection(new DigitalOutputPin(RaspiPin.GPIO_22, PinState.HIGH, WateringPinNames.SECTION_2), 30),
                new WateringSection(new DigitalOutputPin(RaspiPin.GPIO_23, PinState.HIGH, WateringPinNames.SECTION_3), 30),
                new WateringSection(new DigitalOutputPin(RaspiPin.GPIO_27, PinState.HIGH, WateringPinNames.SECTION_4), 30),
                new WateringSection(new DigitalOutputPin(RaspiPin.GPIO_24, PinState.HIGH, WateringPinNames.SECTION_5), 30),
                new WateringSection(new DigitalOutputPin(RaspiPin.GPIO_28, PinState.HIGH, WateringPinNames.SECTION_6), 10, true),
                new WateringSection(new DigitalOutputPin(RaspiPin.GPIO_29, PinState.HIGH, WateringPinNames.SECTION_7), 10),
                new WateringSection(new DigitalOutputPin(RaspiPin.GPIO_25, PinState.HIGH, WateringPinNames.SECTION_8), 10)
        );
        systemContainer.setRainDetector(new RainDetector(new DigitalInputPin(RaspiPin.GPIO_01, PinPullResistance.PULL_DOWN, WateringPinNames.RAIN_DETECTOR)));
        systemContainer.setWaterFlowDetector(new WaterFlowDetector(new DigitalInputPin(RaspiPin.GPIO_00, PinPullResistance.PULL_DOWN, WateringPinNames.WATER_FLOW)));

        systemContainer.setAllDevices(
                new ExternalDeviceSwitch(new DigitalOutputPin(RaspiPin.GPIO_02, PinState.HIGH, WateringPinNames.CONTROL_LIGHT)),
                new ExternalDeviceSwitch(new DigitalOutputPin(RaspiPin.GPIO_04, PinState.HIGH, WateringPinNames.VALVES_POWER))
        );

        WaterFlowJob waterFlowJob = new WaterFlowJob(systemContainer);
        waterFlowJob.run();
    }
}

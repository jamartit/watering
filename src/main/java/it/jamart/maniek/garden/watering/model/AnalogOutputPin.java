package it.jamart.maniek.garden.watering.model;

import com.pi4j.io.gpio.*;
import it.jamart.maniek.garden.watering.enums.WateringPinNames;
import lombok.NonNull;

public class AnalogOutputPin {

    final GpioController gpio = GpioFactory.getInstance();

    final GpioPinAnalogOutput pin;

    @NonNull
    private String name;

    public AnalogOutputPin(GpioPin pin, int value,  WateringPinNames name) {
        this.name = name.getName();
        pin.setMode(PinMode.ANALOG_OUTPUT);

        this.pin = gpio.provisionAnalogOutputPin(pin.getPin(), name.getName(), value);
    }

    public void setMax() {
        this.pin.setValue(255);
    }

    public void setMin() {
        this.pin.setValue(0);
    }
}

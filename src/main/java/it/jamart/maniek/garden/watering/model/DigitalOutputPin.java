package it.jamart.maniek.garden.watering.model;

import com.pi4j.io.gpio.*;
import it.jamart.maniek.garden.watering.enums.WateringPinNames;
import lombok.Data;

@Data
public class DigitalOutputPin {

    private final GpioController gpio = GpioFactory.getInstance();

    private final GpioPinDigitalOutput pin;

    private String name;

    public DigitalOutputPin(Pin pin, PinState state, WateringPinNames name) {
        this.pin = gpio.provisionDigitalOutputPin(pin, name.getName(), state);
        this.pin.setShutdownOptions(true, PinState.HIGH);
        this.name = name.getName();
    }

    public void turnOff() {
        this.pin.setState(PinState.HIGH);
    }

    public void turnOn() {
        this.pin.setState(PinState.LOW);
    }

    public boolean isActive() {
        return pin.isLow();
    }

}

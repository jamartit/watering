package it.jamart.maniek.garden.watering.pintypes;

import com.pi4j.io.gpio.*;
import it.jamart.maniek.garden.watering.enums.WateringPinNames;
import lombok.Data;

@Data
public class DigitalInputPin {

    private final GpioController gpio = GpioFactory.getInstance();

    private final GpioPinDigitalInput  pin;

    private String name;

    public DigitalInputPin(Pin pin, PinPullResistance pullResistance, WateringPinNames name) {
        this.pin = gpio.provisionDigitalInputPin(pin, pullResistance);
        this.pin.setShutdownOptions(true, PinState.HIGH);
        this.name = name.getName();
    }

}

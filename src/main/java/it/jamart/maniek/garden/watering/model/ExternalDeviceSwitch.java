package it.jamart.maniek.garden.watering.model;

import it.jamart.maniek.garden.watering.pintypes.DigitalOutputPin;
import lombok.Data;
import lombok.NonNull;

@Data
public class ExternalDeviceSwitch {

    @NonNull
    private final DigitalOutputPin pin;

    public ExternalDeviceSwitch(@NonNull DigitalOutputPin pin) {
        this.pin = pin;
    }

}

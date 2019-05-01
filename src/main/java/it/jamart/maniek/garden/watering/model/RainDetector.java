package it.jamart.maniek.garden.watering.model;

import it.jamart.maniek.garden.watering.pintypes.DigitalInputPin;
import lombok.Data;
import lombok.NonNull;

@Data
public class RainDetector {

    @NonNull
    private final DigitalInputPin pin;

    public RainDetector(@NonNull DigitalInputPin pin) {
        this.pin = pin;
    }

}

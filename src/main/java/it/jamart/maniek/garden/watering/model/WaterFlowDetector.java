package it.jamart.maniek.garden.watering.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class WaterFlowDetector {

    @NonNull
    private final DigitalInputPin pin;

    public WaterFlowDetector(@NonNull DigitalInputPin pin) {
        this.pin = pin;
    }

}
package it.jamart.maniek.garden.watering.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class WateringSection {

    @NonNull
    private final DigitalOutputPin pin;

    private boolean disabled;

    private int activeMinutes;

    public WateringSection(@NonNull DigitalOutputPin pin, int activeMinutes) {
        this.pin = pin;
        this.activeMinutes = activeMinutes;
    }

    public WateringSection(DigitalOutputPin pin, int activeMinutes, boolean disabled) {
        this.pin = pin;
        this.activeMinutes = activeMinutes;
        this.disabled = disabled;
    }

}

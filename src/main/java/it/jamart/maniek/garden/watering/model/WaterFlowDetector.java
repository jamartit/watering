package it.jamart.maniek.garden.watering.model;

import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import it.jamart.maniek.garden.watering.pintypes.DigitalInputPin;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Data
@Slf4j
public class WaterFlowDetector {

    @NonNull
    private final DigitalInputPin pin;

    private Instant lastSensorPulse;

    private long previousSensorPulse;

    private final int SECONDS_AFTER_LAST_PULSE = 1;

    public WaterFlowDetector(@NonNull DigitalInputPin pin) {
        this.pin = pin;
        this.pin.getPin().addListener((GpioPinListenerDigital) gpioPinDigitalStateChangeEvent -> markWaterFlowDetection());
    }

    private void markWaterFlowDetection() {
        if (lastSensorPulse != null) {
            previousSensorPulse = lastSensorPulse.toEpochMilli();
        }
        lastSensorPulse = Instant.now();
    }

    public boolean isWaterFlowing() {
        return lastSensorPulse != null ? Instant.now().minusSeconds(SECONDS_AFTER_LAST_PULSE).isBefore(lastSensorPulse) : false;
    }

    public long pulsesPerMinute() {
        if (lastSensorPulse != null) {
            long diff = lastSensorPulse.toEpochMilli() - previousSensorPulse + 1;
            return (1000 * 60) / diff;
        }
        return 0;
    }

}

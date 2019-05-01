package it.jamart.maniek.garden.watering.web;

import it.jamart.maniek.garden.watering.model.RainDetector;
import it.jamart.maniek.garden.watering.model.WaterFlowDetector;
import it.jamart.maniek.garden.watering.model.SystemContainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class SensorsController {

    @GetMapping("/sensor/rain")
    public boolean isRainDetected() {
        RainDetector rainDetector = SystemContainer.getInstance().getRainDetector();
        return rainDetector.getPin().getPin().isLow();
    }

    @GetMapping("/sensor/water-flow")
    public String isWaterFlowDetected() {
        WaterFlowDetector waterFlowDetector = SystemContainer.getInstance().getWaterFlowDetector();
        return waterFlowDetector.getPin().getPin().getState().toString();
    }

}

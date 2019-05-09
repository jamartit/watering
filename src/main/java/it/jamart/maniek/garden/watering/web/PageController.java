package it.jamart.maniek.garden.watering.web;

import it.jamart.maniek.garden.watering.enums.WateringPinNames;
import it.jamart.maniek.garden.watering.program.ScheduledWatering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @Autowired
    private WateringController wateringController;

    @Autowired
    private SensorsController sensorsController;

    @RequestMapping("/index.html")
    public String index(Model model) {
        return "redirect:/";
    }

    @RequestMapping("/")
    public String home(Model model) {

        model.addAttribute("section1status", wateringController.checkSection(WateringPinNames.SECTION_1.getName()));
        model.addAttribute("section2status", wateringController.checkSection(WateringPinNames.SECTION_2.getName()));
        model.addAttribute("section3status", wateringController.checkSection(WateringPinNames.SECTION_3.getName()));
        model.addAttribute("section4status", wateringController.checkSection(WateringPinNames.SECTION_4.getName()));
        model.addAttribute("section5status", wateringController.checkSection(WateringPinNames.SECTION_5.getName()));
        model.addAttribute("section6status", wateringController.checkSection(WateringPinNames.SECTION_6.getName()));
        model.addAttribute("section7status", wateringController.checkSection(WateringPinNames.SECTION_6.getName()));
        model.addAttribute("section8status", wateringController.checkSection(WateringPinNames.SECTION_8.getName()));
        model.addAttribute("rainstatus", sensorsController.isRainDetected());
        model.addAttribute("waterstatus", sensorsController.isWaterFlowDetected());
        model.addAttribute("mainwateringstatus", wateringController.isWatering());

        return "home";
    }

}

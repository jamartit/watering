package it.jamart.maniek.garden.watering.web;

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

    @Autowired
    private ScheduledWatering scheduledWatering;

    @RequestMapping("/index.html")
    public String index(Model model) {
        return "redirect:/";
    }

    @RequestMapping("/")
    public String home(Model model) {

        model.addAttribute("section1status", wateringController.checkSection1());
        model.addAttribute("section2status", wateringController.checkSection2());
        model.addAttribute("section3status", wateringController.checkSection3());
        model.addAttribute("section4status", wateringController.checkSection4());
        model.addAttribute("section5status", wateringController.checkSection5());
        model.addAttribute("section6status", wateringController.checkSection6());
        model.addAttribute("section7status", wateringController.checkSection7());
        model.addAttribute("section8status", wateringController.checkSection8());
        model.addAttribute("rainstatus", sensorsController.isRainDetected());
        model.addAttribute("waterstatus", sensorsController.isWaterFlowDetected());
        model.addAttribute("mainwateringstatus", scheduledWatering.getWateringJob().isRunning());


        return "home";
    }

}

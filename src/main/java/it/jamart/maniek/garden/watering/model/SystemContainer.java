package it.jamart.maniek.garden.watering.model;

import it.jamart.maniek.garden.watering.enums.WateringPinNames;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Slf4j
public class SystemContainer {

    private boolean systemActive;

    private static SystemContainer instance;

    private List<WateringSection> sectionList = new ArrayList<>();

    private RainDetector rainDetector;

    private WaterFlowDetector waterFlowDetector;

    private List<ExternalDeviceSwitch> externalDevices = new ArrayList<>();

    private SystemContainer() {
    }

    public static synchronized SystemContainer getInstance() {
        if (instance == null) {
            instance = new SystemContainer();
        }
        return instance;
    }

    public synchronized void setAllSections(WateringSection... sections) {
        sectionList.addAll(Arrays.asList(sections));
    }

    public synchronized void setAllDevices(ExternalDeviceSwitch... devices) {
        externalDevices.addAll(Arrays.asList(devices));
    }

    public WateringSection getWateringSectionByNameEnum(WateringPinNames pinEnum) {
        return sectionList.stream().filter(section -> verifyNames(section.getPin().getName(), pinEnum.getName())).findFirst().orElse(null);
    }

    public ExternalDeviceSwitch getExternalDeviceByNameEnum(WateringPinNames pinEnum) {
        return externalDevices.stream().filter(section -> verifyNames(section.getPin().getName(), pinEnum.getName())).findFirst().orElse(null);
    }

    private boolean verifyNames(String name1, String name2) {
        return StringUtils.equals(name1, name2);
    }

}

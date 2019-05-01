package it.jamart.maniek.garden.watering.enums;

public enum WateringPinNames {
    SECTION_1("section-1"), SECTION_2("section-2"), SECTION_3("section-3"), SECTION_4("section-4"),
    SECTION_5("section-5"), SECTION_6("section-6"), SECTION_7("section-7"), SECTION_8("section-8"),
    WATER_FLOW("water-flow"), CONTROL_LIGHT("control-light"), VALVES_POWER("valves-power"), RAIN_DETECTOR("rain-detector");

    private String name;

    WateringPinNames(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

package phi.ap.model.enums;

public enum SoilTypes {
    BasicRetaining,
    QualityRetaining,
    DeluxeRetaining,
    SpeedGro,
    GrassStarter;

    public static SoilTypes find(String input) {
        input = input.replaceAll("\\s+", "").toLowerCase();
        for (SoilTypes soilType : SoilTypes.values()) {
            if (soilType.toString().toLowerCase().equals(input)) {
                return soilType;
            }
        }
        return null;
    }
}

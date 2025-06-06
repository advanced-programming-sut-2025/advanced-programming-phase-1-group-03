package phi.ap.model.enums;

public enum SaplingTypes {
    AppleSapling,
    ApricotSapling,
    CherrySapling,
    OrangeSapling,
    PeachSapling,
    PomegranateSapling,
    BananaSapling,
    MangoSapling
    ;

    public static SaplingTypes find(String name) {
        name = name.toLowerCase().replaceAll("\\s+", "");
        for (SaplingTypes value : values()) {
            if (value.toString().toLowerCase().equals(name)) return value;
        }
        return null;
    }

    public TreeTypes getTreeType() {
        for (TreeTypes value : TreeTypes.values()) {
            if (this.equals(value.getSaplingType())) return value;
        }
        return null;
    }

    public static SaplingTypes getType(String name) {
        SaplingTypes saplingType;
        try {
            saplingType = SaplingTypes.valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
        return saplingType;
    }
}

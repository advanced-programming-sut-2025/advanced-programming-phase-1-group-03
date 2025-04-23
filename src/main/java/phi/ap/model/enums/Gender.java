package phi.ap.model.enums;

public enum Gender {
    Male,
    Female
    ;
    public static Gender fromString(String str) {
        for(Gender gender : Gender.values()) {
            if(gender.toString().equalsIgnoreCase(str)) {
                return gender;
            }
        }
        return null;
    }
}

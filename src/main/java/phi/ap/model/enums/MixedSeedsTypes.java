package phi.ap.model.enums;

import phi.ap.model.enums.Time.Seasons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum MixedSeedsTypes {
    SpringMixedSeeds(Seasons.Spring, new ArrayList<>(Arrays.asList(SeedTypes.CauliflowerSeeds, SeedTypes.ParsnipSeeds, SeedTypes.PotatoSeeds, SeedTypes.JazzSeeds, SeedTypes.TulipBulb))),
    SummerMixedSeeds(Seasons.Summer, new ArrayList<>(Arrays.asList(SeedTypes.CornSeeds,	SeedTypes.PepperSeeds,	SeedTypes.RadishSeeds,	SeedTypes.WheatSeeds,	SeedTypes.PoppySeeds,	SeedTypes.SunflowerSeeds, SeedTypes.SpangleSeeds))),
    FallMixedSeeds(Seasons.Fall, new ArrayList<>(Arrays.asList(SeedTypes.ArtichokeSeeds, SeedTypes.CornSeeds, SeedTypes.EggplantSeeds, SeedTypes.PumpkinSeeds, SeedTypes.SunflowerSeeds, SeedTypes.FairySeeds))),
    WinterMixedSeeds(Seasons.Winter, new ArrayList<>(Arrays.asList(SeedTypes.PowdermelonSeeds)));
    private final ArrayList<SeedTypes> seedTypesIncluded;
    private final Seasons season;
    MixedSeedsTypes(Seasons season, ArrayList<SeedTypes> cropsFeatures) {
        this.seedTypesIncluded = cropsFeatures;
        this.season = season;
    }
    public ArrayList<SeedTypes> getMixedSeeds() {
        return this.seedTypesIncluded;
    }

    public static MixedSeedsTypes find(String name) {
        name = name.toLowerCase();
        for (MixedSeedsTypes value : values()) {
            if (value.name().toLowerCase().equals(name)) return value;
        }
        return null;
    }

    public ArrayList<Seasons> getSeasonList() {
        if (season != Seasons.Special) return new ArrayList<>(List.of(season));
        return new ArrayList<>(List.of(Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter));
    }

}

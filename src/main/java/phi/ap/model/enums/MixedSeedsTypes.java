package phi.ap.model.enums;

import phi.ap.model.enums.Time.Seasons;

import java.util.ArrayList;
import java.util.Arrays;

public enum MixedSeedsTypes {
    SpringMixedSeeds(Seasons.Spring, new ArrayList<>(Arrays.asList(CropsTypes.CAULIFLOWER, CropsTypes.PARSNIP, CropsTypes.POTATO, CropsTypes.BlueJazz, CropsTypes.TULIP))),
    SummerMixedSeeds(Seasons.Summer, new ArrayList<>(Arrays.asList(CropsTypes.Corn,	CropsTypes.HotPepper,	CropsTypes.RADISH,	CropsTypes.WHEAT,	CropsTypes.POPPY,	CropsTypes.SUNFLOWER,	CropsTypes.SUMMER_SPANGLE))),
    FallMixedSeeds(Seasons.Fall, new ArrayList<>(Arrays.asList(CropsTypes.ARTICHOKE, CropsTypes.Corn, CropsTypes.EGGPLANT, CropsTypes.PUMPKIN, CropsTypes.SUNFLOWER, CropsTypes.FAIRY_ROSE))),
    WinterMixedSeeds(Seasons.Winter, new ArrayList<>(Arrays.asList(CropsTypes.POWDERMELON)));
    private final ArrayList<CropsTypes> cropsTypesIncluded;
    private final Seasons season;
    MixedSeedsTypes(Seasons season, ArrayList<CropsTypes> cropsFeatures) {
        this.cropsTypesIncluded = cropsFeatures;
        this.season = season;
    }
    public ArrayList<CropsTypes> getMixedSeeds() {
        return this.cropsTypesIncluded;
    }
}

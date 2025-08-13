package com.ap.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public enum MixedSeedsTypes {
    SpringMixedSeeds(Season.Spring, new ArrayList<>(List.of(CropsType.Cauliflower, CropsType.Parsnip, CropsType.Potato, CropsType.Blue_Jazz, CropsType.Tulip))),
    SummerMixedSeeds(Season.Summer, new ArrayList<>(List.of(CropsType.Corn,	CropsType.Hot_Pepper,	CropsType.Radish,	CropsType.Wheat,	CropsType.Poppy,	CropsType.Sunflower, CropsType.Summer_Spangle))),
    FallMixedSeeds(Season.Fall, new ArrayList<>(List.of(CropsType.Artichoke, CropsType.Corn,  CropsType.Pumpkin, CropsType.Sunflower, CropsType.Fairy_Rose)))
    ;
    private final ArrayList<CropsType> CropsTypeIncluded;
    private final Season season;
    MixedSeedsTypes(Season season, ArrayList<CropsType> cropsFeatures) {
        this.CropsTypeIncluded = cropsFeatures;
        this.season = season;
    }
    public CropsType getRandom() {
        return CropsTypeIncluded.get(new Random().nextInt(CropsTypeIncluded.size()));
    }

    public Season getSeason() {
        return season;
    }
}

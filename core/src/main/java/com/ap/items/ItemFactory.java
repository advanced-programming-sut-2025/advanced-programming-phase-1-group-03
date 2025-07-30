package com.ap.items;

import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.ap.items.food.Food;
import com.ap.items.mine.Mineral;
import com.ap.items.plant.Crop;
import com.ap.items.plant.Seed;
import com.ap.items.plant.Tree;
import com.ap.model.CropsType;
import com.ap.model.Minerals;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ItemFactory {
    public static ItemFactory instance = new ItemFactory();

    private AssetService assetService;

    public Item CreateGrass() {
        return new Item(ItemNames.Grass.name());
    }

    public Item CreateFiber() {
        TextureRegion icon = assetService.get(AtlasAsset.EnvIronment).findRegion("grass/Fiber");
        return new Item(ItemNames.Fiber.name(), 64, icon);
    }
    public Item CreateStone() {
        TextureRegion icon = assetService.get(AtlasAsset.EnvIronment).findRegions("stone/regular").get(0);
        return new Item(ItemNames.Stone.name(), 64, icon);
    }

    public Item CreateWood() {
        TextureRegion icon = assetService.get(AtlasAsset.EnvIronment).findRegions("wood/regular").get(0);
        return new Item(ItemNames.Wood.name(), 64, icon);
    }

    public Item CreateTree() {
        return new Tree(ItemNames.Tree.name());
    }

    public Item CreateSeed(CropsType belongingCropType) {
        TextureRegion icon = assetService.get(AtlasAsset.Crops).findRegion(belongingCropType.getName() + "_Seeds");
        return new Seed(icon, belongingCropType);
    }
    public Item CreateCrop(CropsType type) {
        TextureRegion icon = assetService.get(AtlasAsset.Crops).findRegion(type.getName());
        return new Crop(icon, type);
    }
    public Item CreateProductOfCrop(CropsType type) {
        var icon = assetService.get(AtlasAsset.Crops).findRegion(type.getName());
        if(type.getEnergyProduce() == null) {
            return new Item(type.name(), 64, icon, type.getBaseSellPrice());
        } else {
            return new Food(type.name(), 64, icon, type.getBaseSellPrice());
        }
    }
    public Item CreateMineral(Minerals type) {
        var icon = assetService.get(AtlasAsset.Mineral).findRegion(type.name());
        return new Mineral(icon, type);
    }
    public void setAssetService(AssetService assetService) {
        this.assetService = assetService;
    }
}

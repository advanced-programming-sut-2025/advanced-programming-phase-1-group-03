package phi.ap.model.items.products;

import phi.ap.model.*;
import phi.ap.model.enums.SoilTypes;

import java.util.ArrayList;

public abstract class Plant extends Product {
    private String sourceName;
    private ArrayList<Integer> stages;
    private boolean canBecomeGiant;
    private Date plantingDate;
    private Date lastWateredDate;
    private Giant giant = null;

    public Plant(int height, int width, String sourceName, ArrayList<Integer> stages, boolean canBecomeGiant, Date plantingDate) {
        super(height, width);
        setName("Plant");
        this.sourceName = sourceName;
        this.stages = stages;
        this.canBecomeGiant = canBecomeGiant;
        this.plantingDate = plantingDate;
        this.lastWateredDate = new Date(plantingDate.getHour());
    }


    public int totalHarvestTime() {
        int res = 0;
        for(Integer stage : stages) {
            res += stage;
        }
        return res;
    }

    public int getFinishedStages() {
        int d = Game.getInstance().getDate().getDay() - plantingDate.getDay();
        int sum = 0;
        int cnt = 0;
        for(Integer stage : stages) {
            sum += stage;
            if (d < sum) break;
            cnt++;
        }
        return cnt;
    }

    public boolean isStagesDone() {
        return getFinishedStages() == stages.size();
    }

    public String getSourceName() {
        return sourceName;
    }

    public ArrayList<Integer> getStages() {
        return stages;
    }

    public boolean isCanBecomeGiant() {
        return canBecomeGiant;
    }

    public Date getPlantingDate() {
        return plantingDate;
    }

    public void setPlantingDate(Date plantingDate) {
        this.plantingDate = plantingDate;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public void wateringSingle() {
        if (!isAlive()) return;
        lastWateredDate = Game.getInstance().getDate();
    }

    public void watering() {
        if (giant == null) wateringSingle();
        else {
            for (Plant member : giant.getMembers()) {
                member.wateringSingle();
            }
        }
    }
    public boolean isWateredToday() {
        return lastWateredDate.getDay() == Game.getInstance().getDate().getDay();
    }
    public boolean isAlive() { // TODO bayad bad 48 saat kharab beshe
        return Game.getInstance().getDate().getDay() - lastWateredDate.getDay() <= 2;
    }


    public void setLastWateredDate(Date lastWateredDate) {
        this.lastWateredDate = lastWateredDate;
    }

    public Giant getGiant() {
        return giant;
    }

    public void setGiant(Giant giant) {
        this.giant = giant;
    }

    @Override
    public void doTask() {

    }

    public void copy(Plant otherPlant) {
        super.copy(otherPlant);
        setSourceName(otherPlant.getSourceName());
        stages = new ArrayList<>(otherPlant.getStages());
        canBecomeGiant = otherPlant.canBecomeGiant;
        if (otherPlant.plantingDate != null) setPlantingDate(new Date(otherPlant.getPlantingDate().getHour()));
        else setPlantingDate(null);
        if (otherPlant.lastWateredDate != null)setLastWateredDate(new Date(otherPlant.getLastWateredDate().getDay()));
        else setLastWateredDate(null);
    }

    public Date getLastWateredDate() {
        return lastWateredDate;
    }

    public void setLevelsForArrayList(ArrayList<ItemStack> list) {
        for (ItemStack itemStack : list) {
            if (!(itemStack.getItem() instanceof Product product)) continue;
            if (product.getLevels() == null) continue;
            product.setLevels(Product.getRandomLevelProcessSample());
            if (getLevels() == null) continue;
            if (product.getLevels().getCurrentLevel() == 0) {
                int rand = App.getInstance().getRandomNumber(1, 2);
                if (rand == 2) product.getLevels().setCurrentLevel(getLevels().getCurrentLevel());
            }
        }
    }

    public void fertilize(SoilTypes soilType) {
        switch (soilType) {
            case DeluxeRetaining:
                watering();
                break;
            case SpeedGro:
                plantingDate.advanceHourWithoutSleep(-24);
                break;
        }
    }

}

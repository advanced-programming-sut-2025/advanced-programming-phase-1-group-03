package phi.ap.model.items.products;

import phi.ap.model.*;
import phi.ap.model.enums.Colors;
import phi.ap.model.enums.SoilTypes;

import java.util.ArrayList;

public abstract class Plant extends Product {
    private String sourceName;
    private ArrayList<Integer> stages;
    private boolean canBecomeGiant;
    private Date plantingDate = null;
    private Date lastWateredDate = null;
    private Giant giant = null;
    private ArrayList<Tile> shapeAtStage;
    private Tile deadShapeColor = new Tile(" ", Colors.fg(87), "");

    public Plant(int height, int width, String sourceName, ArrayList<Integer> stages, boolean canBecomeGiant, Date plantingDate) {
        super(height, width);
        setName("Plant");
        this.sourceName = sourceName;
        this.stages = stages;
        this.canBecomeGiant = canBecomeGiant;
        this.plantingDate = plantingDate != null ? new Date(plantingDate.getHour()) : null;
        this.lastWateredDate = Game.getInstance() != null ? new Date(Game.getInstance().getDate().getHour()) : null;
    }

    public void setShapeAtStage(ArrayList<Tile> shapeAtStage) {
        this.shapeAtStage = shapeAtStage;
    }

    public void setShapeForCurrentStage() {
        fillTile(shapeAtStage.get(getFinishedStages()));
    }

    public void show(int startY, int startX, Tile[][] map) {
        setShapeForCurrentStage();
        if (!isAlive()) fillTile(deadShapeColor);
        super.show(startY, startX, map);
    }

    public int totalHarvestTime() {
        int res = 0;
        for(Integer stage : stages) {
            res += stage;
        }
        return res;
    }

    public int getFinishedStages() {
        int d = Game.getInstance().getDate().getRawDay() - plantingDate.getRawDay();
        if (!isAlive()) {
            d = lastWateredDate.getRawDay() - plantingDate.getRawDay() + 2;
        }
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
        if (plantingDate == null) {this.plantingDate = null;}
        else this.plantingDate = new Date(plantingDate.getHour());
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public void wateringSingle() {
        if (!isAlive()) return;
        lastWateredDate = new Date(Game.getInstance().getDate().getHour());
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
        return lastWateredDate.getRawDay() == Game.getInstance().getDate().getRawDay();
    }
    public boolean isAlive() {
        if ((Game.getInstance().getDate().getRawDay() - lastWateredDate.getRawDay()) <= 2) return true;
        return false;
//        return true;
    }


    public void setLastWateredDate(Date lastWateredDate) {
        if (lastWateredDate == null) this.lastWateredDate = null;
        else this.lastWateredDate = new Date(lastWateredDate.getHour());
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
        if (otherPlant.lastWateredDate != null)setLastWateredDate(new Date(otherPlant.getLastWateredDate().getRawDay()));
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
        if (!isAlive()) return;
        switch (soilType) {
            case DeluxeRetaining:
                watering();
                break;
            case SpeedGro:
                plantingDate.advanceHourRaw(-24);
                break;
        }
    }

    public Tile getDeadShapeColor() {
        return deadShapeColor;
    }

    public void setDeadShapeColor(Tile deadShapeColor) {
        this.deadShapeColor = deadShapeColor;
    }
}

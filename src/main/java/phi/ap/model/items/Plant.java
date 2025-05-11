package phi.ap.model.items;

import phi.ap.model.Date;
import phi.ap.model.Game;
import phi.ap.model.ItemStack;

import java.util.ArrayList;

public abstract class Plant extends Item {
    private String sourceName;
    private final ArrayList<Integer> stages;
    private final boolean canBecomeGiant;
    private Date plantingDate;
    private Date lastWateredDate;

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

    public abstract ArrayList<ItemStack> getProducts(); // with scythe(dus);
    public abstract ArrayList<ItemStack> getDrops(); // with axe;
    public void watering() {
        if (!isAlive()) return;
        lastWateredDate = Game.getInstance().getDate();
    }
    public boolean isWateredToday() {
        return lastWateredDate.getDay() == Game.getInstance().getDate().getDay();
    }
    public boolean isAlive() { // TODO bayad bad 48 saat kharab beshe
        return Game.getInstance().getDate().getDay() - lastWateredDate.getDay() <= 2;
    }

    @Override
    public void doTask() {

    }
}

package phi.ap.model;

import phi.ap.model.enums.AbilityType;

public class Buff {
    private Integer hour = 0;
    private Date startTime = null;
    private AbilityType abilityType = null;
    private Integer maxEnergyEffect = null;
    private Integer day = 0;
    private Double dailyEnergyEffect = null;

    public Buff(Integer hour, AbilityType abilityType) {
        this.hour = hour;
        this.abilityType = abilityType;
    }

    public Buff(Integer hour, Integer maxEnergyEffect) {
        this.hour = hour;
        this.maxEnergyEffect = maxEnergyEffect;
    }

    public Buff(Integer day, Double dailyEnergyEffect) {
        this.day = day;
        this.dailyEnergyEffect = dailyEnergyEffect;
    }

    public Buff(Buff buff) {
        this.hour = buff.hour;
        this.day = buff.day;
        this.startTime = buff.startTime != null ? new Date(buff.startTime) : null;
        this.abilityType = buff.abilityType;
        this.maxEnergyEffect = buff.maxEnergyEffect;
        this.dailyEnergyEffect = buff.dailyEnergyEffect;
    }

    public void copy(Buff buff) {
        this.hour = buff.hour;
        this.day = buff.day;
        this.startTime = buff.startTime != null ? new Date(buff.startTime) : null;
        this.abilityType = buff.abilityType;
        this.maxEnergyEffect = buff.maxEnergyEffect;
        this.dailyEnergyEffect = buff.dailyEnergyEffect;
    }

    public int getHour() {
        return hour;
    }

    public Date getStartTime() {
        return startTime;
    }

    public AbilityType getAbilityType() {
        return abilityType;
    }

    public Integer getMaxEnergyEffect() {
        return maxEnergyEffect;
    }

    public Double getDailyEnergyEffect() {
        return dailyEnergyEffect;
    }

    public boolean isActive() {
        if (startTime == null) return false;
        if (dailyEnergyEffect != null) {
            return (Game.getInstance().getDate().getRawDay() - startTime.getRawDay() <= day) && (Game.getInstance().getDate().getRawDay() - startTime.getRawDay() > 0);
        }
        return Game.getInstance().getDate().getHour() - startTime.getHour() < hour;
    }

    public void activate() {
        startTime = new Date(Game.getInstance().getDate());
    }
}

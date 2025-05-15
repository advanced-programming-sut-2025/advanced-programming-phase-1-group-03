package phi.ap.model;

import phi.ap.model.enums.TileType;
import phi.ap.model.items.products.Plant;

import java.util.ArrayList;

public class Giant {
    ArrayList<Plant> members = new ArrayList<>();

    public Giant(ArrayList<Plant> members) {
        this.members = members;
        Plant earliest = null;
        Date lastWateringDate = null;
        for (Plant member : members) {
            if (earliest == null) earliest = member;
            else if (earliest.getPlantingDate().getHour() > member.getPlantingDate().getHour()) earliest = member;
            if (lastWateringDate == null && member.getLastWateredDate() != null) {
                lastWateringDate = new Date(member.getLastWateredDate().getHour());
            }
            if (lastWateringDate != null && member.getLastWateredDate() != null) {
                if (lastWateringDate.getHour() < member.getLastWateredDate().getHour())
                    lastWateringDate = new Date(member.getLastWateredDate().getHour());
            }
        }
        if (earliest != null) {
            earliest.setLastWateredDate(lastWateringDate);
        }
        for (Plant member : members) {
            member.copy(earliest);
        }
        for (Plant member : members) {
            member.fillTile(TileType.GiantBackGround.getTile());
        }
    }

    public ArrayList<Plant> getMembers() {
        return members;
    }
}

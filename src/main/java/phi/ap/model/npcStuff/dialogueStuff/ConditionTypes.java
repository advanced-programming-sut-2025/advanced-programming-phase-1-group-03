package phi.ap.model.npcStuff.dialogueStuff;

import phi.ap.model.enums.Time.Seasons;
import phi.ap.model.enums.npcStuff.Quests;

public class ConditionTypes {
    public static Condition always() {
        return state -> true;
    }
    public static Condition season(Seasons season) {
        return state -> state.getCurrentDate().getSeason() == season;
    }
    public static Condition friendshipLess(int level) {
        return state -> state.getFriendshipLevel() <= level;
    }
    public static Condition friendshipMore(int level) {
        return state -> state.getFriendshipLevel() >= level;
    }
    public static Condition friendshipEqual(int level) {
        return state -> state.getFriendshipLevel() == level;
    }
    public static Condition currentDayMore(int day) {
        return state -> state.getCurrentDate().getDay() >= day;
    }
    public static Condition currentDayLess(int day) {
        return state -> state.getCurrentDate().getDay() <= day;
    }
    public static Condition currentDayEqual(int day) {
        return state -> state.getCurrentDate().getDay() == day;
    }
    public static Condition QuestDone(Quests quest) {
        return state -> state.isQuestDone(quest);
    }
    public static Condition QuestActive(Quests quest) {
        return state -> state.isQuestActive(quest);
    }
    public static Condition QuestActivatedSoFar(Quests quest) {
        return state -> state.isQuestActivatedSoFar(quest);
    }
    public static Condition lastMeetDiffLess(int hour) {
        return state -> {
            if (state.getLastMeet() == null) return true;
            return state.getCurrentDate().getHour() - state.getLastMeet().getHour() <= hour;
        };
    }
    public static Condition lastMeetDiffMore(int hour) {
        return state -> {
            if (state.getLastMeet() == null) return false;
            return state.getCurrentDate().getHour() - state.getLastMeet().getHour() >= hour;
        };
    }
    public static Condition lastConversationTopicEqual(String topic) {
        return state -> {
            if (state.getLastConversation() == null) return topic.isEmpty();
            return state.getLastConversation().getTopic().equals(topic);
        };
    }

    public static Condition ans(Condition... conditions) {
        return state -> {
            for (Condition condition : conditions) {
                if (!condition.matches(state))
                    return false;
            }
            return true;
        };
    }
}

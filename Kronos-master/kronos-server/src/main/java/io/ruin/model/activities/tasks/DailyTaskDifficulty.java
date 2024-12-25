package io.ruin.model.activities.tasks;

public enum DailyTaskDifficulty {
    EASY(500, 1),
    MEDIUM(1000, 2),
    HARD(1500, 3);

    private int bmReward;
    private int points;

    DailyTaskDifficulty(int bmReward, int points) {
        this.bmReward = bmReward;
        this.points = points;
    }

    public int getBmReward() {
        return bmReward;
    }

    public int getPointsReward() {
        return points;
    }
}

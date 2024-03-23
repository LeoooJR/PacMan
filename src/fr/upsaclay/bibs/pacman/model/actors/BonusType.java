package fr.upsaclay.bibs.pacman.model.actors;

/**
 * The different possible types of bonuses along with their number of associated number of gained points
 * (not used before step 4 of the project)
 */
public enum BonusType {

    CHERRY(100), STRAWBERRY(300), PEACH(500), APPLE(700), GRAPES(1000), GALAXIAN(2000), BELL(3000), KEY(5000);

    private final int value;

    BonusType(int value) {
        this.value = value;
    }

    /**
     * Return the value of the bonus
     * @return The value
     */
    public int getValue() {
        return value;
    }
}

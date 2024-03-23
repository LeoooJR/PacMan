package fr.upsaclay.bibs.pacman.model.board;

/**
 * Represent an object to "count" things with a possible limit
 * The object will keep counting even after the limit but one can ask if the limit has been reached
 *
 * (not used before step 3)
 */
public class Counter {

    private int limit;
    private int value;

    /**
     * Create a Counter with given limit
     * At creation, the value of the counter is always 0
     * @param limit a integer
     */
    public Counter(int limit) {
        value = 0;
        this.limit = limit;
    }

    /**
     * Sets the counter limit
     * @param limit, a integer
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * Return the counter limit
     * @return an integer
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Return the current value of the counter
     * @return a non negative integer
     */
    public int getValue() {
        return value;
    }

    /**
     * Increase the ocunter value
     */
    public void inc() {
        value++;
    }

    /**
     * Reset the counter value to 0
     */
    public void reset() {
        value = 0;
    }

    /**
     * Return if the counter has reached the set limit
     * @return true if the value is equal or greater than the limit
     */
    public boolean hasReachedLimit() {
        return value >= limit;
    }

}

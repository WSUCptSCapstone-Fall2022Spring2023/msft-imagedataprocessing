package org.agicam.processor.util;

/**
 * Created By: Jordan M
 * Description:
 */
public class Couple<P1, P2>{
    private P1 itemOne;
    private P2 itemTwo;

    /**
     * Constructor for simple couple class
     * @param itemOne first item of couple
     * @param itemTwo second item of couple
     */
    public Couple(P1 itemOne, P2 itemTwo) {
        this.itemOne = itemOne;
        this.itemTwo = itemTwo;
    }

    public P1 getItemOne() {
        return itemOne;
    }

    public P2 getItemTwo() {
        return itemTwo;
    }
}

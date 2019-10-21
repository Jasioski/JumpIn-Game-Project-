package project;

/**
 * Enum containing a UI representation for each item, usually first character(s) of the item's name.
 */
public enum ItemUIRepresentation {
    EMPTY("E"),
    MUSHROOM("M"),
    FOX("F"),
    HOLE_EMPTY("HE"),
    HOLE_OCCUPIED_RABBIT("HR"),
    HOLE_MUSHROOM("HM"),
    ELEVATED("U"),
    ELEVATED_MUSHROOM("EM"),
    ELEVATED_RABBIT("ER"),
    RABBIT("R");

    /**
     * The representation, if it is specified by the user.
     */
    private String representation;

    /**
     * Creates a specific UI representation.
     * @param s The string containing the given UI representation
     */
    ItemUIRepresentation(String s){
        this.representation = s;
    }

    /**
     * Gets the UI representation.
     * @return The string containing the UI representation.
     */
    public String getRepresentation() {
        return this.representation;
    }
}

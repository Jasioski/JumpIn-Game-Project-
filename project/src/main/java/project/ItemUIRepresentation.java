package project;

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

    private String representation;

    ItemUIRepresentation(String s){
        this.representation = s;
    }

    public String getRepresentation() {
        return this.representation;
    }
}

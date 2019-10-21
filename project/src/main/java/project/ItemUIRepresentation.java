package project;

public enum ItemUIRepresentation {

    EMPTY(ANSIColor.GREEN + "E" + ANSIColor.RESET),
    MUSHROOM(ANSIColor.PURPLE + "M" + ANSIColor.RESET),
    FOX(ANSIColor.RED + "F" + ANSIColor.RESET),
    HOLE_EMPTY(ANSIColor.CYAN + "HE" + ANSIColor.RESET),
    HOLE_OCCUPIED_RABBIT(ANSIColor.BLUE + "HR" + ANSIColor.RESET),
    HOLE_MUSHROOM(ANSIColor.BLUE + "HM" + ANSIColor.RESET),
    ELEVATED(ANSIColor.WHITE + "U" + ANSIColor.RESET),
    ELEVATED_MUSHROOM(ANSIColor.PURPLE + "EM" + ANSIColor.RESET),
    ELEVATED_RABBIT(ANSIColor.YELLOW + "ER" + ANSIColor.RESET),
    RABBIT(ANSIColor.YELLOW + "R" + ANSIColor.RESET);

    private String representation;

    ItemUIRepresentation(String s){
        this.representation = s;
    }

    public String getRepresentation() {
        return this.representation;
    }
}

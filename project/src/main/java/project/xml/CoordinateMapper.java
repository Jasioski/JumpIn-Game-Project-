package project.xml;

import project.model.Coordinate;

public class CoordinateMapper {

    public static String serialize(Coordinate coordinate) {
        String str = "";

        str += "<coordinate>";

        str += "<row>" + coordinate.row + "</row>";
        str += "<column>" + coordinate.column + "</column>";

        str += "</coordinate>";

        return str;

    }

//    public static Coordinate deserialize(String xml) {
//
//    }

}

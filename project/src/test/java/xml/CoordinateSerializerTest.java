package xml;

import org.junit.jupiter.api.Test;
import project.model.Coordinate;
import project.xml.CoordinateMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoordinateSerializerTest {

    @Test
    public void testSerialize () {
        Coordinate coordinate = new Coordinate(0, 0);
        String expectedXML ="<coordinate><row>0</row><column>0</column>" +
                "</coordinate>";

        String xml = CoordinateMapper.serialize(coordinate);

        assertEquals(expectedXML, xml, "shouldb be the same");
    }


    @Test
    public void testDeserializer() {
        Coordinate coordinate = new Coordinate(0, 0);
        String expectedXML ="<coordinate><row>0</row><column>0</column>" +
                "</coordinate>";



        assertEquals(expectedXML, xml, "shouldb be the same");
    }
}

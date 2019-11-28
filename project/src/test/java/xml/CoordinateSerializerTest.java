package xml;

import org.junit.jupiter.api.Test;
import project.model.Coordinate;
import project.xml.CoordinateMapper;
import project.xml.Node;
import project.xml.XMLParser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CoordinateSerializerTest {


    @Test
    public void testDeserializer() {
        String xml ="<coordinate pair=true>"+
                "<row>0</row>"+
                "<column>2</column>"+
                "</coordinate>";

        XMLParser parser = new XMLParser(xml);

        Node parsedNode = parser.deserialize();

        assertNotNull(parsedNode);

    }
}

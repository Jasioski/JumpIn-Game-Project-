package project.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import project.xml.Mapper;

import static org.junit.jupiter.api.Assertions.*;

public class CoordinateTest  {
    @Test
    public void testSerialize() {
        ObjectMapper mapper = new Mapper();

        Coordinate coordinate = new Coordinate(0,0);

        try {
            String xml = mapper.writeValueAsString(coordinate);
            assertNotNull(xml, "should not be null");
            assertEquals("<Coordinate><row>0</row><column>0</column>" +
                            "</Coordinate>",
                    xml,
                    "the " +
                    "output should be as expected");
        } catch (JsonProcessingException e) {
            fail();
        }
    }
}

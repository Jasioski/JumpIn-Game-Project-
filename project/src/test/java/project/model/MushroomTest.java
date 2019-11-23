package project.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import project.xml.Mapper;

import static org.junit.jupiter.api.Assertions.*;

public class MushroomTest {
    @Test
    public void testSerialization() {

        ObjectMapper mapper = new Mapper();

        Mushroom item = new Mushroom(0,0);

        try {
            String xml = mapper.writeValueAsString(item);
            assertEquals("<Mushroom><coordinate><row>0</row><column>0" +
                    "</column></coordinate></Mushroom>", xml, "should " +
                    "be the same as expected");
        } catch (JsonProcessingException e) {
            fail();
        }

    }


    @Test
    public void testDeserialize() {
        ObjectMapper mapper = new Mapper();

        Mushroom expectedItem = new Mushroom(1,1);
        String xml = "<Mushroom><coordinate><row>1</row><column>1" +
                "</column></coordinate></Mushroom>";

        try {
            Mushroom deserializedItem = mapper.readValue(xml,
                    Mushroom.class);

            assertNotNull(deserializedItem, "should not be null");
            assertEquals(expectedItem, deserializedItem, "should " +
                    "be equal");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
        }
    }

}

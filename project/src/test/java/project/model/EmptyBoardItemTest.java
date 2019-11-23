package project.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import project.xml.Mapper;

import static org.junit.jupiter.api.Assertions.*;

public class EmptyBoardItemTest {

    @Test
    public void testSerialization() {
        ObjectMapper mapper = new Mapper();

        EmptyBoardItem item = new EmptyBoardItem(0,0);

        try {
            String xml = mapper.writeValueAsString(item);
            assertEquals("<EmptyBoardItem><coordinate><row>0</row><column>0" +
                    "</column></coordinate></EmptyBoardItem>", xml, "should " +
                    "be the same as expected");
        } catch (JsonProcessingException e) {
            fail();
        }

    }


    @Test
    public void testDeserialize() {
        ObjectMapper mapper = new Mapper();

        EmptyBoardItem expectedItem = new EmptyBoardItem(1,1);
        String xml = "<EmptyBoardItem><coordinate><row>1</row><column>1" +
                "</column></coordinate></EmptyBoardItem>";

        try {
            EmptyBoardItem deserializedItem = mapper.readValue(xml,
                    EmptyBoardItem.class);

            assertNotNull(deserializedItem, "should not be null");
            assertEquals(expectedItem, deserializedItem, "should " +
                    "be equal");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
        }
    }

}

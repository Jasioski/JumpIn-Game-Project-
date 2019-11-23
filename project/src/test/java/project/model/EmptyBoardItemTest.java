package project.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import project.xml.Mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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



}

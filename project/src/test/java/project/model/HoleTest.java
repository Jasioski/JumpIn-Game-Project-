package project.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import org.junit.jupiter.api.Test;
import project.xml.Mapper;

public class HoleTest {

    @Test
    public void testSerializerEmptyHole() {
        ObjectMapper mapper = new Mapper(true);

        Hole hole = new Hole(new Coordinate(0, 0), Optional.absent());

        try {
            String xml = mapper.writeValueAsString(hole);
            System.out.println(xml);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSerializerHoleWithRabbit() {
        ObjectMapper mapper = new Mapper(true);

        Rabbit rabbit = new Rabbit(0, 0);
        Hole hole = new Hole(new Coordinate(0, 0), Optional.of(rabbit));

        try {
            String xml = mapper.writeValueAsString(hole);
            System.out.println(xml);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

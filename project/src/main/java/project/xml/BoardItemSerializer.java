package project.xml;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import project.model.BoardItem;

import java.io.IOException;

public class BoardItemSerializer extends StdSerializer<BoardItem> {


    public BoardItemSerializer() {
        this(null);
    }

    public BoardItemSerializer(Class<BoardItem> t) {
        super(t);
    }

    @Override
    public void serialize(BoardItem value, JsonGenerator gen,
                          SerializerProvider provider) throws IOException {

       gen.writeStartObject();

       if (value.coordinate.isLeft()) {
           gen.writeObjectField("coordinate", value.coordinate.left().get());
       }
       else if (value.coordinate.isRight()) {
           gen.writeObjectField("coordinate", value.coordinate.right());
       }

       gen.writeEndObject();

    }
}

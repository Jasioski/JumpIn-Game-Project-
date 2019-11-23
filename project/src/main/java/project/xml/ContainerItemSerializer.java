package project.xml;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import project.model.ContainerItem;

import java.io.IOException;

public class ContainerItemSerializer extends StdSerializer<ContainerItem> {


    public ContainerItemSerializer() {
        this(null);
    }

    public ContainerItemSerializer(Class<ContainerItem> t) {
        super(t);
    }

    @Override
    public void serialize(ContainerItem value, JsonGenerator gen,
                          SerializerProvider provider) throws IOException {

        gen.writeStartObject();

        if (value.coordinate.isLeft()) {
            gen.writeObjectField("coordinate", value.coordinate.left().get());
        }
        else if (value.coordinate.isRight()) {
            gen.writeObjectField("coordinate", value.coordinate.right());
        }

        gen.writeObjectFieldStart("containingItem");

        gen.writeEndObject();

    }
}

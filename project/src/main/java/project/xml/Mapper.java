package project.xml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.pcollections.PCollectionsModule;
import project.model.Coordinate;

public class Mapper extends XmlMapper {
    public Mapper() {
        this(false);
    }

    public Mapper(boolean indentation) {
        super();

        this.registerModule(new GuavaModule());
        this.registerModule(new PCollectionsModule());

        if (indentation) {
            this.setIndentation();
        }
    }

    private void setIndentation() {
        this.enable(SerializationFeature.INDENT_OUTPUT);
    }
}

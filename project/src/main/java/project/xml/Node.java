package project.xml;

import com.google.common.base.Optional;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;

public class Node {
    String tag;
    Map<String, String> attributes;

    Optional<Node> parent;
    List<Node> children;


    public Node (String tag, Map<String, String> attributes,
                 Optional<Node> parent) {

        children = new LinkedList<>();
        this.attributes = attributes;
        this.parent = parent;
    }

    public Node (String tag, Map<String, String> attributes) {
       this(tag, attributes, Optional.absent());
    }

    public String toString() {
        String s ="";

        s+= "tag: " + tag;
        s+=  formatAttributes();

        return s;
    }

    public String formatAttributes() {
        return formatAttributes(this.attributes);
    }


    public static String formatAttributes(Map<String, String> attributes) {
        String s = "";

        for (String attributeName: attributes.keySet()){
            s+= "attribute: " + attributeName + "=" + attributes.get(attributeName) +"\n";
        }

        return s;
    }


}

package project.xml;

import java.util.*;

import io.atlassian.fugue.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class XMLParser {

    public static Logger logger = LogManager.getLogger(XMLParser.class);
    private  boolean inTag;
    private String source;
    private int position;
    private int maxPosition;

    private Node node;

    Stack<String> tagStack;

    Map<String, String> currentAttributes;
    String currentTag;

    public XMLParser(String source) {
       this.inTag = false;
       this.source = source;
       tagStack = new Stack<>();

       this.position = 0;
       this.maxPosition = source.length();
    }

    private String readTag() throws EndOfFileException {
        String s = "";

        Character c = getChar();

        while (c != ' ') {
            s += c;
            c = getChar();
        }

        return s;
    }

    private Character getChar() throws EndOfFileException {

        if (position < maxPosition) {
            Character c = source.charAt(position);
            logger.trace("reading char: '" + c + "'");
            position++;

            return c;
        }


        throw new EndOfFileException();
    }

    private Character peekChar() throws EndOfFileException {
        int internalPositition = position;

        if (internalPositition < maxPosition) {
            Character c = source.charAt(internalPositition);
            logger.trace("peeking char: '" + c + "'");

            return c;
        }

        throw new EndOfFileException();
    }


    private void seekNonWhiteSpace() throws EndOfFileException {

        while (peekChar() == ' ') {
            getChar();
        }
    }


    private Pair<String, String> readAttribute() throws EndOfFileException, NoAttributeException {
        String s = "";
        // TODO: use enums
        // 0: havent seen a quite
        // 1: seen a qutoe
        // 2: ended a quote
        int inQuote = 0;

        seekNonWhiteSpace();
        // Find closing ">"
        Character c = getChar();

        while (c != '>') {

            if (c == '"') {
                inQuote++;
                logger.trace("found a quote, inQuote=" + inQuote);
            }

            if (c == ' ' && inQuote == 2) {
                break;
            }

            s += c;
            c = getChar();
        }

        s.trim();

        if(s.split("=").length == 2) {
           logger.trace("found attribute");

            String left = s.split("=")[0];
            String right = s.split("=")[1];

            logger.trace("key: " + left);
            logger.trace("value: " + right);

            return Pair.pair(left, right);
        }

        throw new NoAttributeException();
    }

    private Map readAttributes() throws EndOfFileException {
        Map<String,String> attributes = new HashMap<>();

        seekNonWhiteSpace();
        // Find closing ">"
        Character c = peekChar();

        while (c != '>') {

            // Read an attribute

            try {
                Pair<String, String> attribute = attribute = readAttribute();
                attributes.put(attribute.left(), attribute.right());
            } catch (NoAttributeException e) {
                break;
            }

            c = peekChar();
        }

        return attributes;
    }

    public Node deserialize() {

        try {
            while (position < maxPosition) {
                Character c = getChar();

                if (c == '<') {
                    inTag = true;

                    currentTag = readTag();
                    tagStack.push(currentTag);
                    logger.debug("read tag: " + currentTag);

                    currentAttributes = readAttributes();

                    logger.debug("read attributes");
                    logger.debug(Node.formatAttributes(currentAttributes));

                } else if (c == '/') {
                    Character nextChar = peekChar();

                    if (nextChar == '>') {
                        // Exit tag
                        tagStack.pop();

                        Node newNode = new Node(currentTag, currentAttributes);
                        node = newNode;
                        logger.debug("parsed node");
                        logger.debug(newNode);
                    }

                }
                else if (c == '>') {
                    inTag = false;
                }
            }
        } catch (EndOfFileException e){
            logger.debug("reached end of file");
        }

        return node;
    }

    public static void main(String[] args) {
        String xml ="<coordinate type=\"left\" isgood=\"true\" />";

        XMLParser parser = new XMLParser(xml);
        parser.deserialize();

    }

}

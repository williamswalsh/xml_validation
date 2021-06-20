package ie.williamswalsh;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class Main {

    private static final String XML_FILENAME = "/Users/will/data.xml";
    private static final String XSD_FILENAME = "/Users/will/data.xsd";

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        Document document = retrieveXmlDoc(XML_FILENAME);
        Schema schema = retrieveXsd(XSD_FILENAME);

        Validator validator = schema.newValidator();
        validator.validate(new DOMSource(document));
    }

    private static Document retrieveXmlDoc(String absolutePath) throws ParserConfigurationException, SAXException, IOException {
        // Instantiate the Factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        // process XML securely, avoid attacks like XML External Entities (XXE)
        dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

        // parse XML file
        DocumentBuilder db = dbf.newDocumentBuilder();

        return db.parse(new File(absolutePath));
    }

    private static Schema retrieveXsd(String absolutePath) throws SAXException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Source schemaFile = new StreamSource(new File(absolutePath));
        return schemaFactory.newSchema(schemaFile);
    }
}
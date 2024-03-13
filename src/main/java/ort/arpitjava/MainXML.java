package ort.arpitjava;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;

public class MainXML {
    public static void main(String[] args) {
        // Your XML string
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<data>\n" +
                "    <name>John</name>\n" +
                "    <age>30</age>\n" +
                "    <address>\n" +
                "        <street>123 Main St</street>\n" +
                "        <city>Anytown</city>\n" +
                "        <country>USA</country>\n" +
                "    </address>\n" +
                "    <contacts>\n" +
                "        <email>john@example.com</email>\n" +
                "        <phone>+1234567890</phone>\n" +
                "    </contacts>\n" +
                "</data>";

        try {
            // Parse the XML string into a Document object
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            ByteArrayInputStream input = new ByteArrayInputStream(xmlString.getBytes());
            Document document = builder.parse(input);

            // Update attribute values recursively
            updateElementValues(document.getDocumentElement());

            // Convert the Document back to XML string
            String updatedXmlString = documentToString(document);

            // Print the updated XML string
            System.out.println(updatedXmlString);
        } catch (Exception e) {
            System.err.println("Failed to parse XML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Recursive function to update element values
    private static void updateElementValues(Element element) {
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element childElement = (Element) node;
                String tagName = childElement.getTagName();
                if ("name".equals(tagName) || "email".equals(tagName) || "street".equals(tagName) || "city".equals(tagName)) {
                    String textContent = childElement.getTextContent();
                    if (textContent.length() >= 3) {
                        String newValue = "x".repeat(textContent.length() - 3) + textContent.substring(textContent.length() - 3);
                        childElement.setTextContent(newValue);
                    } else if (textContent.length() == 2) {
                        String newValue = "xx" + textContent.substring(textContent.length() - 1);
                        childElement.setTextContent(newValue);
                    } else if (textContent.length() == 1) {
                        childElement.setTextContent("x");
                    }
                } else {
                    updateElementValues(childElement); // Recursive call for nested elements
                }
            }
        }
    }

    // Utility method to convert Document to XML string
    private static String documentToString(Document document) throws Exception {
        javax.xml.transform.TransformerFactory transformerFactory = javax.xml.transform.TransformerFactory.newInstance();
        javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();
        javax.xml.transform.dom.DOMSource source = new javax.xml.transform.dom.DOMSource(document);
        java.io.StringWriter writer = new java.io.StringWriter();
        javax.xml.transform.stream.StreamResult result = new javax.xml.transform.stream.StreamResult(writer);
        transformer.transform(source, result);
        return writer.toString();
    }
}


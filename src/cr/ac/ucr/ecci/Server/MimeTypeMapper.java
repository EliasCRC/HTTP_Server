package cr.ac.ucr.ecci.Server;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;

public class MimeTypeMapper {
    private Document mimeTypesXML;
    private HashMap<String, String> mimeTypeMap;

    MimeTypeMapper() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        this.mimeTypeMap = new HashMap<>();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            this.mimeTypesXML = builder.parse(new File("MimeTypes.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }


        NodeList mimeMappings = this.mimeTypesXML.getElementsByTagName("mime-mapping");
        for (int i = 0; i < mimeMappings.getLength(); i++) {
            Element mimeMapping = (Element) mimeMappings.item(i);
            this.mimeTypeMap.put(mimeMapping.getElementsByTagName("mime-type").item(0).getTextContent(), mimeMapping.getElementsByTagName("extension").item(0).getTextContent());
        }
    }

    public String getExtension(String mimeType) {
        return this.mimeTypeMap.get(mimeType);
    }

    public String getMimeType(String extension) {
        for (String st : this.mimeTypeMap.keySet()) {
            if (this.mimeTypeMap.get(st).equals(extension)) {
                return st;
            }
        }

        return null;
    }


}

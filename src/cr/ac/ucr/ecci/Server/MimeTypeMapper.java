package cr.ac.ucr.ecci.Server;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.util.HashMap;

public class MimeTypeMapper {
    Document mimeTypesXML;
    HashMap<String, String> mimeTypeMap;

    MimeTypeMapper() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        mimeTypeMap = new HashMap<String, String>();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            mimeTypesXML = builder.parse(new File("MimeTypes.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }


        NodeList mimeMappings = mimeTypesXML.getElementsByTagName("mime-mapping");
        for (int i = 0; i < mimeMappings.getLength(); i++) {
            Element mimeMapping = (Element) mimeMappings.item(i);
            mimeTypeMap.put(mimeMapping.getElementsByTagName("mime-type").item(0).getTextContent(), mimeMapping.getElementsByTagName("extension").item(0).getTextContent());
        }
    }

    public String getExtension(String mimeType) {
        return mimeTypeMap.get(mimeType);
    }


}

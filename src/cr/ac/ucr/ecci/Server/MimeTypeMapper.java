package cr.ac.ucr.ecci.Server;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;

/**
 * Used to map extensions to a mimetype and mimetypes to an extension
 */
public class MimeTypeMapper {
    private Document mimeTypesXML;
    private HashMap<String, String> mimeTypeMap;

    /**
     * Constructor of MimeTypeMapper uses MimeTypes.xml to do the mapping, the file was obtained from:
     * https://gist.github.com/IsmailMarmoush/7915310
     */
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

    /**
     * Gets the extension of a mime type
     * @param mimeType The mime type that we need to know the extension
     * @return The extension for that mime type
     */
    public String getExtension(String mimeType) {
        return this.mimeTypeMap.get(mimeType);
    }

    /**
     * Gets the mime type from an extension name
     * @param extension the extension that we need to know the mimetype
     * @return The extension for that mime type, null if there is no extension for that mime type
     */
    public String getMimeType(String extension) {
        for (String st : this.mimeTypeMap.keySet()) {
            if (this.mimeTypeMap.get(st).equals(extension)) {
                return st;
            }
        }

        return null;
    }


}

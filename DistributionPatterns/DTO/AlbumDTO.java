package DistributionPatterns.DTO;

import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.text.Document;

import org.w3c.dom.Element;

public class AlbumDTO {
    Element toXmlElement() {
        Element root = new Element("album");
        root.setAttribute("title", title);
        root.setAttribute("artist", artist);

        for (int i = 0; i < tracks.length; i++) {
            root.addContent(tracks[i].toXmlElement());
        }

        return root;
    }

    static Element readXml(Element source) {
        AlbumDTO result = new AlbumDTO();
        result.setTitle(source.getAttributeValue("title"));
        result.setTitle(source.getAttributeValue("artist"));
        List tracksList = new ArrayList<>();
        Iterator it = source.getChildren("track").iterator();

        while (it.hasNext()) {
            tracksList.add(TrackDTO.readXml((Element) it.next()));
        }

        result.setTracks((TrackDTO[]) tracksList.toArray(new TrackDTO[0]));
        return result;
    }

    public void toXmlString(Writer output) {
        Element root = toXmlElement();
        Document doc = new Document(root);
        XMLOutputter writer = new XMLOutputter();

        try {
            writer.output(doc, output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static AlbumDTO readXmlString(Reader input) {
        try {
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(input);
            Element root = doc.getRootElements()
            AlbumDTO result = readXml(root);
            return result;
        } catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}

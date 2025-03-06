package DistributionPatterns.DTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

public class TrackDTO {
    public Map writeMap() {
        Map result = new HashMap<>();

        result.put("title", title);
        result.put("performers", performers);

        return result;
    }

    public static TrackDTO readMap(Map arg) {
        TrackDTO result = new TrackDTO();

        result.title = (String) arg.get("title");
        result.performers = (String[]) arg.get("performers");

        return result;
    }

    Element toXmlElement() {
        Element result = new Element("track");
        result.setAttribute("title", title);

        for (int i = 0; i < performers.length; i++) {
            Element performerElement = new Element("performer");
            performerElement.setAttribute("name", performers[i]);
            result.addContent(performerElement);
        }

        return result;
    }

    static TrackDTO readXml(Element arg) {
        TrackDTO result = new TrackDTO();
        result.setTitle(arg.getAttributeValue("title"));
        Iterator it = arg.getChildren("performer").iterator();
        List<E> buffer = new ArrayList<>();

        while (it.hasNext()) {
            Element eachElement = (Element) it.next();
            buffer.add(eachElement.getAttributeValue("name"));
        }

        result.setPerformers((String[]) buffer.toArray(new String[0]));
        return result;
    }
}

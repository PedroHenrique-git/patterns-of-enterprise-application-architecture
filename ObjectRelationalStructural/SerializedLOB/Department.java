package ObjectRelationalStructural.SerializedLOB;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Element;

public class Department {
    private String name;
    private List subsidiaries = new ArrayList<>();

    public Element toXmlElement() {
        Element root = new Element("department");
        root.setAttribute("name", name);
        Iterator i = subsidiaries.iterator();

        while (i.hasNext()) {
            Department dep = (Department) i.next();
            root.addContent(dep.toXmlElement());
        }

        return root;
    }

    public static Department readXml(Element source) {
        String name = source.getAttributeValue("name");
        Department result = new Department(name);
        Iterator it = source.getChildren("department").iterator();

        while (it.hasNext())
            result.addSubsidiary(readXml((Element) it.next()));

        return result;
    }
}

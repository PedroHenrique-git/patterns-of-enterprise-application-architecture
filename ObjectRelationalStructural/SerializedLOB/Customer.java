package ObjectRelationalStructural.SerializedLOB;

import java.rmi.registry.Registry;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Customer {
    private String name;
    private List departments = new ArrayList<>();

    public long insert() {
        PreparedStatement stmt = null;

        try {
            stmt = DB.prepare(insertStatementString);
            setID(findNextDatabaseId());
            stmt.setInt(1, getID());
            stmt.setString(2, name);
            stmt.setString(3, XmlStringer.write(departmentsToXmlElement()))
            stmt.executeUpdate();
            Registry.addCustomer(this);

            return getID();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.cleanUp(stmt);
        }
    }

    public Element departmentsToXmlElement() {
        Element root = new Element("departmentsList");
        Iterator i = departments.iterator();

        while (i.hasNext()) {
            Department dep = (Department) i.next();
            root.addContent(dep.toXmlElement());
        }

        return root;
    }

    public static Customer load(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        Customer result = (Customer) Registry.getCustomer(id);

        if (result != null)
            return result;

        String name = rs.getString("name");
        String departmentLob = rs.getString("departments");

        result = new Customer(name);

        result.readDepartments(XmlStringer.read(departmentsLob));

        return result;
    }

    void readDepartments(Element source) {
        List result = new ArrayList<>();
        Iterator it = source.getChildren("department").iterator();

        while (it.hasNext())
            addDepartment(Department.readXml((Element) it.next()));
    }
}

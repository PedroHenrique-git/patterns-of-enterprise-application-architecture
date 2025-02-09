package DataSourceLayer.DataMapper.EmptyObject;

import DataSourceLayer.DataMapper.Person;

public class PersonMapper extends AbstractMapper {
    @Override
    protected DomainObjectEL createDomainObject() {
        return new Person();
    }

    protected void doLoad(DomainObjectEL obj, java.sql.ResultSet rs) throws java.sql.SQLException {
        Person person = (Person) obj;

        person.dbLoadLastName(rs.getString(2));
        person.setFirstName(rs.getString(3));
        person.setNumberOfDependents(rs.getString(4));
    }
}

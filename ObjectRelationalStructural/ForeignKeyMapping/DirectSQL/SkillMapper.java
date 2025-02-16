package ObjectRelationalStructural.ForeignKeyMapping.DirectSQL;

import java.sql.ResultSet;
import java.sql.SQLException;

import ObjectRelationalBehavioral.UnitOfWork.DomainObject;

public class SkillMapper extends AbstractMapper {
    protected DomainObject doLoad(long id, ResultSet rs) throws SQLException {
        Skill result = new Skill(id);

        result.setName(rs.getString("skillName"));

        return result;
    }
}

package ObjectRelationalStructural.ForeignKeyMapping.DirectSQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ObjectRelationalBehavioral.UnitOfWork.DomainObject;

class EmployeeMapper extends AbstractMapper {
    public Employee find(long key) {
        return (Employee) abstractFind(key);
    }

    protected String findStatement() {
        return "SELECT" + COLUMNS_LIST + " FROM employees" + " WHERE ID = ?";
    }

    public static final String COLUMN_LIST = "ID, lastname, firstname";

    @Override
    protected DomainObject doLoad(long id, ResultSet rs) throws SQLException {
        Employee result = new Employee(id);

        result.setFirstName(rs.getString("firstname"));
        result.setLastName(rs.getString("lastname"));
        result.setSkills(loadSkills(id));

        return result;
    }

    protected List loadSkills(long employeeID) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            List result = new ArrayList<>();

            stmt = DB.prepare(findSkillsStatement);

            stmt.setObject(1, employeeID);

            rs = stmt.executeQuery();

            while (rs.next()) {
                long skillId = rs.getLong(id);

                result.add((Skill) MapperRegistry.skill().loadRow(skillId, rs));
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.cleanUp(stmt, rs);
        }
    }

    private static final String findSkillStatement = "SELECT skill.ID," + SkillMapper.COLUMN_LIST
            + " FROM skills skill, employeeSkill es" + " WHERE es.employeeID = ? AND skill.ID = es.skillID";

    public List findAll() {
        return findAll(findAllStatement);
    }

    private static final String findAllStatement = "SELECT" + COLUMN_LIST + " FROM employees employee"
            + " ORDER BY employee.lastname";
}
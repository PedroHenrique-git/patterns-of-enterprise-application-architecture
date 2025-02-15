class EmployeeMapper extends AbstractMapper {
    public Employee Find(long id) {
        return (Employee) AbstractFind(id);
    }

    protected override String TableName{
        get{return "Employees";}
    }

    protected override void doLoad(DomainObject obj, DataRow row) {
        Employee emp = (Employee) obj;

        emp.Name = (String) row["name"];

        loadSkill(emp);
    }

    private IList loadSkills(Employee emp) {
        DataRow[] rows = skillLinkRows(emp);
        IList result = new ArrayList();

        foreach(DataRow row in rows) {
            long skillID = (int) row["skillID"];
            emp.AddSkill(MapperRegistry.Skill.Find(skillID));
        }

        return result;
    }

    private DataRow[] skillListRows(Employee emp) {
        String filter = String.format("employeeID = {0}", emp.Id);

        return skillLinkTable.Select(filter);
    }

    private DataTable skillLinkTable {
        get{return dsh.Data.Tables["skillEmployees"];}
    }

    protected override void Save(DomainObject obj, DataRow row) {
        Employee emp = (Employee) obj;
        row["name"] = emp.Name;
        saveSkills(emp);
    }

    private void saveSkills(Employee emp) {
        deleteSkill(emp);

        foreach(Skill s in emp.Skills) {
            DataRow row = skillLinkTable.NewRow();
            row["employeeID"] = emp.Id;
            row["skillID"] = s.Id;
            skillLinkTable.Rows.Add(row);
        }
    }

    private void deleteSkills(Employee emp) {
        DataRow[] skillRows = skillLinkRows(emp);

        foreach(DataRow r in skillRows) r.Delete();
    }
}
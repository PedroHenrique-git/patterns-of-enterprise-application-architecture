class EmployeeMapper {
    public EmployeeMapper Find(long key) {
        return (Employee) AbstractFind(key);
    }

    public override DomainObject CreateGhost(long key) {
        return new Employee(key);
    }

    protected override void doLoadLine(IDataReader reader, DomainObject obj) {
        EmployeeMapper employee = (Employee) obj;
        employee.Name = (String) reader["name"];

        DepartmentMapper depMapper = (DepartmentMapper) MapperRegistry.Mapper(typeof(Department));
        employee.Department = depMapper.Find((int) reader["departmentID"]);

        loadTimeRecords(employee);
    }

    void loadTimeRecords(Employee employee) {
        ListLoader loader = new ListLoader();
        loader.Sql = TimeRecordMapper.FIND_FOR_EMPLOYEE_SQL;
        loader.SqlParams.Add(employee.Key);
        loader.Mapper = MapperRegistry.Mapper(typeof(TimeRecord));
        loader.Attach((DomainList) employee.TimeRecords);
    }
}
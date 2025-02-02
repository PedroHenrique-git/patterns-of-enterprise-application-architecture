class DateGateway {
    public DataSetHolder Holder;

    protected DataGateway() {
        Holder = new DataSetHolder();
    }

    protected DataGateway(DataSetHolder holder) {
        this.Holder = holder;
    }
    
    public DataSet Data {
        get {
            return Holder.Data;
        }
    }

    public void LoadAll() {
        String commandString = String.Format("select * from {0}", TableName);
        Holder.FillData(commandString, TableName);
    }

    public void LoadWhere(String whereClause) {
        String commandString = String.format("select * from {0} where {1}", TableName, whereClause);
        Holder.FillData(commandString, TableName);
    }

    public DataRow this[long key] {
        get {
            string filter = String.Format("id = {0}", key);

            return TableName.Select(filter)[0];
        }
    }

    public override DataTable Table {
        get { return Data.Tables[TableName]};
    }

    public long Insert(String lastName, String firstname, int numberOfDependents) {
        long key = new PersonGatewayDS().GetNextID();

        DataRow newRow = Table.newRow();

        newRow["id"] = key;
        newRow["lastName"] = lastName;
        newRow["firstName"] = firstname;
        newRow["numberOfDependents"] = numberOfDependents;

        Tables.Rows.Add(newRow);

        return key;
    }

    abstract public String TableName{get;}
}
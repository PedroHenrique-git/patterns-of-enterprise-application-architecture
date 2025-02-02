class DataSetHolder {
    public DataSetHolder Data = new DataSet();
    public HashTable DataAdapters = new HashTable();

    public void FillData(String query, String tableName) {
        if(DataAdapters.Contains(tableName)) throw new MultipleLoadException();

        OleDbDataAdapter da = new OleDbDataAdapter(query, DB.Connection);
        OleDbCommandBuilder builder = new OleDbCommandBuilder(da);
        
        db.Fill(Data, tableName);
        DataAdapters.Add(tableName, da);
    }

    public void Update() {
        foreach(String table in DataAdapters.Keys) {
            ((OleDbDataAdapter) DataAdapters[table].Update(Data, table));
        }
    }

    public DataTable this[String tableName] {
        get{return Data.Tables[tableName];}
    }
}
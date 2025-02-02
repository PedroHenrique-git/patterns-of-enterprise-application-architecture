class PersonGateway {
    public IDataReader FindAll() {
        String sql = "select * from person";

        return new OleDbCommand(sql, DB.Connection).ExecuteReader();
    }

    public IDataReader FindWithLastName(string lastName) {
        String sql = "SELECT * FROM person WHERE lastname = ?";

        IDBCommand comm = new OleDbCommand(sql, DB.Connection);

        comm.Parameters.Add(new OleDbParameter("lastname", lastName));

        return comm.ExecuteReader();
    }

    public IDataReader FindWhere(string whereClause) {
        String sql = String.Format("select * from person where {0}", whereClause);

        return new OleDbCommand(sql, DB.Connection).ExecuteReader();
    }

    public Object[] FindRow(long key) {
        String sql = "SELECT * FROM person WHERE id = ?";

        IDbCommand comm = new OleDbCommand(sql, DB.Command);
        comm.Parameters.Add(new OleDbParameter("key", key));

        IDataReader reader = comm.ExecuteReader();

        reader.Read();

        Object[] result = new Object[reader.FieldCount];

        reader.GetValues(result);

        reader.Close();

        return result;
    }

    public void Update(long key, String lastname, String firstname, long numberOfDependents) {
        String sql = @"UPDATE person SET lastname = ?, firstname = ?, numberOfDependents = ? WHERE id = ?";

        IDbCommand comm = new OleDbCommand(sql, DB.Connection);
        comm.Parameters.Add(new OleDbParameter("last", lastname));
        comm.Parameters.Add(new OleDbParameter("first", firstname));
        comm.Parameters.Add(new OleDbParameter("numDep", numberOfDependents));
        comm.Parameters.Add(new OleDbParameter("key", key));
        comm.ExecuteNonQuery();
    }

    public long Insert(String lastname, String firstname, long numberOfDependents) {
        String sql = "INSERT INTO person VALUES(?,?,?,?)";

        long key = GetNextId();

        IDbCommand comm = new OleDbCommand(sql, DB.Connection);
        
        comm.Parameters.Add(new OleDbParameter("key", key));
        comm.Parameters.Add(new OleDbParameter("last", lastname));
        comm.Parameters.Add(new OleDbParameter("first", firstname));
        comm.Parameters.Add(new OleDbParameter("numDep", numberOfDependents));
        
        comm.ExecuteNonQuery();

        return key;
    }

    public void Delete(long key) {
        String sql = "DELETE FROM person WHERE id = ?";

        IDbCommand comm = new OleDbCommand(sql, DB.Connection);
        
        comm.Parameters.Add(new OleDbParameter("key", key));
        
        comm.ExecuteNonQuery();
    }
}
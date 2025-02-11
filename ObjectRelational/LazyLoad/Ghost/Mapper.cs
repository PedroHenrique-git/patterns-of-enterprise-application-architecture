class Mapper {
    public DomainObject AbstractFind(long key) {
        DomainObject result;
        result = (DomainObject) loadedMap[key];

        if(result == null) {
            result = CreateGhost(key);
            loadedMap.Add(key, result);
        }

        return result;
    }

    public void Load(DomainObject obj) {
        if(!obj.isGhost) return;

        IDbCommand comm = new OleDbCommand(findStatement(), DB.connection);
        comm.Parameters.Add(new OleDbParameter("key", obj.Key));
        IDataReader reader = comm.ExecuteReader();

        reader.Read();
        LoadLine(reader, obj);

        reader.Close();
    }

    protected abstract string findStatement();

    public void LoadLine(IDataReader reader, DomainObject obj) {
        if(obj.isGhost) {
            obj.MarkLoading();
            doLoadLine(reader, obj);
            obj.MarkLoaded();
        }
    }

    protected abstract void doLoadLine(IDataReader reader, DomainObject obj);

    IDictionary loadedMap = new HahsTable();

    public abstract DomainObject CreateGhost(long key);
}
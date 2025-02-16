class FootballerMapper {
    public override String TypeCode{
        get {return "F";}
    }

    protected new static String TABLENAME = "Footballers";

    public Footballer Find(long id) {
        return (Footballer) AbstractFind(id, TABLENAME);
    }

    protected override DomainObject CreateDomainObject() {
        return new Footballer();
    }

    protected override void Load(DomainObject obj) {
        base.Load(obj);

        DataRow row = FindRow(obj.Id, tableFor(TABLENAME));
        Footballer footballer = (Footballer) obj;
        footballer.club = (String) row["club"];
    }

    protected override void Save(DomainObject obj) {
        base.Save(obj);
        DataRow row = FindRow(obj.Id, tableFor(TABLENAME));
        Footballer footballer = (Footballer) obj;
        row["club"] = footballer.club;
    }

    protected override void AddRow(DomainObject obj) {
        base.AddRow(obj);
        InsertRow(obj, tableFor(TABLENAME));
    }

    public override void Delete(DomainObject obj) {
        base.Delete(obj);
        DataRow row = FindRow(obj.Id, TABLENAME);
        row.Delete():
    }
}
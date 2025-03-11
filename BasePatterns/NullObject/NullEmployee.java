package BasePatterns.NullObject;

public class NullEmployee:Employee,INull
{
    public override String name
    {
        get{return "Null Employee";}
        set {}
    }

    public override Decimal GrossToDate
    {
        get{return 0m;}
    }

    public override Contract Contract
    {
        get{return Contract.NULL;}
    }
}

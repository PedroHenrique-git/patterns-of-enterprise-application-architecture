class RevenueRecognition {
    public long Insert(long contractID, Decimal amount, DateTime date) {
        DataRow newRow = table.NewRow();
        long id = GetNextID();
        newRow["ID"] = id;
        newRow["contractID"] = contractID;
        newRow["amount"] = amount;
        newRow["date"] = String.format("{0:s}", date);
        table.Rows.Add(newRow);
        return id;
    }

    public Decimal RecognitionRevenue(long contractID, DateTime asOf) {
        String filter = String.format("ContractID = {0} AND date <= #{1:d}#", contractID, asOf);
        DataRow[] rows = table.Select(filter);
        Decimal result = 0m;

        foreach(DataRow row in rows) {
            result += (Decimal)row["amount"];
        }

        return result;
    }

    public decimal RecognizedRevenue2(long contractID, DateTime asOf) {
        String filter = String.format("ContractID = {0} AND date <= #{1:d}#", contractID, asOf);
        String computeExpression = "sum(amount)";
        Object sum = table.Compute(computeExpression, filter);
        return (sum is System.DBNull) ? 0 : (Decimal) sum;
    }
}
class BattingPage {
    override protected String[] mandatoryParameters() {
        String[] result = {"team", "innings", "match"};
        return result;
    }

    override protected DataSet getData() {
        OleDbConnection command = new OleDbConnection(SQL, db);

        command.Parameters.Add(new OleDbParameter("team", team));
        command.Parameters.Add(new OleDbParameter("innings", innings));
        command.Parameters.Add(new OleDbParameter("match", match));
        
        OleDbDataAdapter da = new OleDbDataAdapter(command);

        DataSet result = new DataSet();

        da.Fill(result, Batting.TABLE_NAME);

        return result;
    }

    private const String SQL = @"SELECT * FROM batting WHERE team = ? AND innings = ? AND matchID = ? ORDER BY battingOrder";

    override protected void applyDomainLogic(DataSet dataSet) {
        batting = new Batting(dataSet);
        batting.CalculateRates();
    }
}
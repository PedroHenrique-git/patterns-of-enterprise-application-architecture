class CricketPage {
    protected void Page_Load(object sender, System.EventArgs e) {
        db = new OleDbConnection(DB.ConnectionString);

        if(hasMissingParameters()) {
            errorTransfer(missingParameterMessage);
        }

        DataSet ds = getData();

        if(hasNoData(ds)) {
            errorTransfer("No data matches your request");
        }

        applyDomainLogic(ds);

        DataBind();

        prepareUI(ds);
    }

    abstract protected String[] mandatoryParameters();

    private Boolean hasMissingParameters() {
        foreach(String param in  mandatoryParameters())
            if(Request.Params[param] == null) return true;
        
        return false;
    }

    private String missingParameterMessage {
        get{
            String result = "<p>This page is missing mandatory parameters</p>";

            result += "<ul>";

            foreach(String param in mandatoryParameters())
                if(Request.Params[param] == null)
                    result += String.Format("<li>{0}</li>", param);
            
            result += "</ul>";

            return result;
        }
    }

    protected void errorTransfer(String message) {
        Context.Items.Add("errorMessage", message);
        Context.Server.Transfer("Error.aspx");
    }

    abstract protected DataSet getData();

    protected Boolean hasNoData(DataSet ds) {
        foreach(DataTable table in ds.Tables)
            if(table.Rows.Count != 0) return false;
        
        return true;
    }

    protected virtual void applyDomainLogic(DataSet ds) {}
}
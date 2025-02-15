class PlayerMapper {
    public IList FindForTeam(long id) {
        String filter = String.format("teamID = {0}", id);
        DataRow[] rows = table.Select(filter);

        IList result = new ArrayList();

        foreach(DataRow row in rows) {
            result.Add(Load(row));
        }

        return result;
    }

    public LinkTeam(Player player, long teamID) {
        DataRow row = FindRow(player.Id);
        row["teamID"] = teamID;
    }
}
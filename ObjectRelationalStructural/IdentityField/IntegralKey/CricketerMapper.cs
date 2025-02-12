class CriketerMapper {
    public CriketerMapper Find(long id) {
        return (Criketer) AbstractFind(id);
    }
}
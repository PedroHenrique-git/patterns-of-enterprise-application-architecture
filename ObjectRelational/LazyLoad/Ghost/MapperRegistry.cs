class MapperRegistry : IDataSource {
    public void Load(DomainObject obj) {
        Mapper(obj.GetType()).Load(obj);
    }

    public static Mapper Mapper(Type type) {
        return (Mapper) instance.mapper[type];
    }

    IDictionary mappers = new HashTable();
}
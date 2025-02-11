class Employee {
    public String Name {
        get {
            Load();

            return _name;
        }
    }

    String _name;
}
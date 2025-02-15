class Employee {
    public IList Skills {
        get {return ArrayList.ReadOnly(skillsData);}
        set {skillsData = new ArrayList(value);}
    }

    public void AddSKill(Skill arg) {
        skillsData.Add(arg);
    }

    public void RemoveSkill(Skill arg) {
        skillsData.Remove(arg);
    }

    private IList skillsData = new ArrayList();
}
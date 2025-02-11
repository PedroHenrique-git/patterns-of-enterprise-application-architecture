class DomainObject {
    LoadStatus Status;

    public DomainObject(long key) {
        this.Key = key;
    }

    public Boolean isGhost {
        get { return Status == LoadStatus.GHOST; }
    }

    public Boolean isLoaded {
        get { return Status == LoadStatus.LOADED; }
    }
    
    public void MarkLoading() {
        Debug.Assert(isGhost);
        Status = LoadStatus.LOADING;
    }

    public void MarkLoaded() {
        Debug.Assert(Status == LoadStatus.LOADING);
        Status = LoadStatus.LOADED;
    }

    protected void Load() {
        if(isGhost) {
            DataSource.Load(this);
        }
    }
}

enum LoadStaus {GHOST, LOADING, LOADED};
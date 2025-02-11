package ObjectRelationalBehavioral.LazyLoad.VirtualProxy;

import java.util.List;

public class VirtualList {
    private List source;
    private VirtualListLoader loader;

    public VirtualList(VirtualListLoader loader) {
        this.loader = loader;
    }

    private List getSource() {
        if (source == null) {
            source = loader.load();
        }

        return source;
    }

    public VirtualListLoader getLoader() {
        return loader;
    }

    public int size() {
        return getSource().size();
    }

    public boolean isEmpty() {
        return getSource().isEmpty();
    }
}

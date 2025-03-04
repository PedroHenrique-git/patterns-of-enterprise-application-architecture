package WebPresentation.ApplicationController;

import java.util.Map;

public interface ApplicationController {
    DomainCommand getDomainCommand(String commandString, Map params);

    String getView(String commandString, Map params);
}

package WebPresentation.JspHandler;

import java.io.IOException;

public class HelperGetTag {
    private String propertyName;

    public void setProperty(String propertyName) {
        this.propertyName = propertyName;
    }

    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().print(getProperty(propertyName));
        } catch (IOException e) {
            throw new JspException("unable to print no writer");
        }

        return SKIP_BODY;
    }
}

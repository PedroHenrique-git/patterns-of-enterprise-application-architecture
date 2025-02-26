package ObjectRelationalMetadataMapping.DataMapping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataMap {
    private Class domainClass;
    private String tableName;
    private List columnMaps = new ArrayList<>();

    public String columnList() {
        StringBuffer result = new StringBuffer(" ID");

        for (Iterator it = columnMaps.iterator(); it.hasNext();) {
            result.append(",");

            ColumnMap columnMap = (ColumnMap) it.next();
            result.append(columnMap.getColumnName());
        }

        return result.toString();
    }

    public String getTableName() {
        return tableName;
    }
}

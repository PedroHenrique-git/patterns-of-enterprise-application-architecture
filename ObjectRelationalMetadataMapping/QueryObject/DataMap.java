package ObjectRelationalMetadataMapping.QueryObject;

import java.util.Iterator;

import ObjectRelationalMetadataMapping.DataMapping.ColumnMap;

public class DataMap {
    public String getColumnForField(String fieldName) {
        for(Iterator it = getColumns();it.hasNext()) {
            ColumnMap columnMap = (ColumnMap) it.next();

            if(columnMap.getFieldName().equals(fieldName)) {
                return columnMap.getColumnName();
            }
        }

        throw new ApplicationException("Unable to find column for " + fieldName);

        QueryObject query = new QueryObject(Person.class);
        // query.addCriteria(Criteria.greaterThan("numberOfDependents", 0));
        // query.addCriteria(Criteria.matches("lastName", "f%"));
    }
}

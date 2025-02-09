package DataSourceLayer.DataMapper.EmptyObject;

public class Person {
    public void dbLoadLastName(String lastName) {
        assertStateIsLoading();
        this.lastName = lastName;
    }
}

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public class VehicleDAO {
    private DataSource myDataSource;

    public VehicleDAO(BasicDataSource source){
        this.myDataSource = source;
    }

    public DataSource getMyDataSource() {
        return myDataSource;
    }

    public void SearchByPriceRange(){

    }
}

package test;


import main.domain.DataSourceConfig;
import main.service.DataSourceConfigService;



public class DataSourceConfigTest {
    public static void main(String[] args) {
        DataSourceConfigService service = new DataSourceConfigService();
        DataSourceConfig config = new DataSourceConfig(999, "mysql", "user1", "password1", "localhost", "3306", "/path/to/config", "user1");

        service.add(config);
    }
}

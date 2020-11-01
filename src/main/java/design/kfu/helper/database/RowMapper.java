package design.kfu.helper.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper <T> {
    T mapRow(ResultSet resultSet) throws SQLException;
    void setResult(int result);
    int getResult();
}

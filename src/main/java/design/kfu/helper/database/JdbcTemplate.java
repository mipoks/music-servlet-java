package design.kfu.helper.database;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static design.kfu.repository.States.NOT_EXECUTED;


public class JdbcTemplate <T>  {

    private ConnectionGiver connectionGiver;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    public JdbcTemplate(ConnectionGiver connectionGiver) {
        this.connectionGiver = connectionGiver;
    }

    public Collection<T> query(String sql, RowMapper<T> rowMapper, Object... args) {
        try(Connection connection = connectionGiver.getConnection()) {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = null;
            Collection<T> result = new ArrayList<>();

            int position = 1;
            for (Object arg : args) {
                preparedStatement.setObject(position, arg);
                position++;
            }
            System.out.println(sql);
            if (sql.contains("UPDATE") || sql.toLowerCase().contains("DELETE") || sql.toLowerCase().contains("INSERT")) {
                rowMapper.setResult(preparedStatement.executeUpdate());
            } else {
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    result.add(rowMapper.mapRow(resultSet));
                }
            }
            return result;
        } catch (SQLException e) {
            e.getErrorCode();
            rowMapper.setResult(NOT_EXECUTED);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
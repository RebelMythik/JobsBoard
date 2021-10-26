package me.rebelmythik.jobsboard.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WarpedResultSet implements AutoCloseable {

    private final ResultSet resultSet;
    private final DatabaseConnection databaseConnection;
    private final Statement statement;

    public WarpedResultSet(Statement statement, ResultSet resultSet, DatabaseConnection databaseConnection) {
        this.statement = statement;
        this.resultSet = resultSet;
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void close() throws SQLException {
        resultSet.close();
        statement.close();
        databaseConnection.release();
    }
}

package ru.barinov.exampleJDBC.dbService;

import ru.barinov.exampleJDBC.dbService.dao.UsersDAO;
import ru.barinov.exampleJDBC.dbService.dataSet.UsersDataSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBService {
    private final Connection connection;

    public DBService() {
        this.connection = getMySQLConnection();
    }

    public static Connection getMySQLConnection(){
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_test", "root", "root");
            return connection;
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;

    }

    public UsersDataSet getUser(long id) throws DBException {
        try {
            return (new UsersDAO(connection).get(id));
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public long addUser(String name) throws DBException {
        try {
            connection.setAutoCommit(false);
            UsersDAO dao = new UsersDAO(connection);
            dao.createTable();
            dao.insertUser(name);
            connection.commit();
            return dao.getUserId(name);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
            }
            throw new DBException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {
            }
        }
    }

    public void cleanUp() throws DBException {
        UsersDAO dao = new UsersDAO(connection);
        try {
            dao.dropTable();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void printConnectInfo() {
        try {
            System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver: " + connection.getMetaData().getDriverName());
            System.out.println("Autocommit: " + connection.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

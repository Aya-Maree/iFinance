package se2203b.assignments.ifinance;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserAccountAdapter {
    Connection connection;

    public UserAccountAdapter(Connection conn, Boolean reset) throws SQLException {
        connection = conn;

        Statement stmt = connection.createStatement();
        if (reset) {
            try {
                // Remove tables if database tables have been created.
                // This will throw an exception if the tables do not exist
                stmt.execute("DROP TABLE UserAccount");
            } catch (SQLException ex) {
                // No need to report an error.
                // The table simply did not exist.
            }
        }


        try {
            // Create the table
            stmt.execute("CREATE TABLE UserAccount ("
                    + "uName VARCHAR(30) NOT NULL PRIMARY KEY,"
                    + "encryptedPassword VARCHAR(20) NOT NULL,"
                    + "passwordExpiryTime INT NOT NULL,"
                    + "passwordExpiryDate VARCHAR(10) NOT NULL,"
                    + "accountType VARCHAR(10) NOT NULL"
                    + ")");


        } catch (SQLException ex) {
            // No need to report an error.
            // The table exists and may have some data.
        }
    }

    // adds new record, reads from Administrator object
    public int insertRecord(UserAccount data) throws SQLException {
        Statement stmt = connection.createStatement();
        int rows = stmt.executeUpdate("INSERT INTO UserAccount ( uName,  encryptedPassword,  passwordExpiryTime, passwordExpiryDate, accountType) "
                + "VALUES ('"
                + data.getUName() + "', '"
                + data.getEncryptedPassword() + "', "
                + data.getPasswordExpiryTime() + ", '"
                + data.getPasswordExpiryDate() + "', '"
                + data.getAccountType() + "' )"
        );
        return rows;
    }

    // Modify one record based on the given object
    public void updateRecord(UserAccount data) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("UPDATE UserAccount SET "
                + "uName = '" + data.getUName() + "', "
                + "encryptedPassword = '" + data.getEncryptedPassword() + "', "
                + "passwordExpiryTime = " + data.getPasswordExpiryTime() + ", "
                + "passwordExpiryDate = '" + data.getPasswordExpiryDate() + "', "
                + "accountType = '" + data.getAccountType() + "' "
                + "WHERE uName = '" + data.getUName() + "' ");
    }

    // get one record, that matches the given name value
    public UserAccount findRecord(String uName) throws SQLException {
        UserAccount account = new UserAccount();
        ResultSet rs;

        // Create a Statement object
        Statement stmt = connection.createStatement();
        // Create a string with a SELECT statement
        String sqlStatement = "SELECT * FROM UserAccount WHERE uName = '" + uName + "' ";
        // Execute the statement and return the result
        rs = stmt.executeQuery(sqlStatement);
        while (rs.next()) {
            // note that, this loop will run only once
            account.setuName(rs.getString(1));
            account.setEncryptedPassword(rs.getString(2));
            account.setPasswordExpiryTime(rs.getInt(3));
            account.setPasswordExpiryDate(rs.getString(4));
            account.setAccountType(rs.getString(5));
        }
        return account;
    }

//    // Get all User Account Objects
//    public ObservableList<UserAccount> getUserAccountsList() throws SQLException {
//        ObservableList<UserAccount> uAccountsList = FXCollections.observableArrayList();
//
//        // Create a Statement object
//        ResultSet rs;
//        Statement stmt = connection.createStatement();
//
//        // Create a string with a SELECT statement
//        String sqlStatement = "SELECT * FROM UserAccount";
//
//        // Execute the query by sending the SQL statement to the DBMS
//        rs = stmt.executeQuery(sqlStatement);
//
//        // Use a while loop to add the contents of the result set to uAccountsList
//        while (rs.next()) {
//            uAccountsList.add(new UserAccount(
//                            rs.getString(1),
//                            rs.getString(2),
//                            rs.getInt(3),
//                            rs.getString(4),
//                            rs.getString(5)
//                    )
//            );
//        }
//        return uAccountsList;
//    }
    // Get a String list of usernames.
    public ObservableList<String> getUsernamesList() throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        ResultSet rs;

        // Create a Statement object
        Statement stmt = connection.createStatement();

        // Create a string with a SELECT statement
        String sqlStatement = "SELECT uName FROM UserAccount WHERE accountType <> 'admin'";

        // Execute the statement and return the result
        rs =  stmt.executeQuery(sqlStatement);

        while (rs.next()) {
            list.add( rs.getString(1));
        }
        return list;

    }
}

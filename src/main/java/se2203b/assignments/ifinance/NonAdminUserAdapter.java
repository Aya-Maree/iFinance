package se2203b.assignments.ifinance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NonAdminUserAdapter {
    Connection connection;

    public NonAdminUserAdapter(Connection conn, Boolean reset) throws SQLException {
        connection = conn;

        Statement stmt = connection.createStatement();
        if (reset) {
            try {
                // Remove tables if database tables have been created.
                // This will throw an exception if the tables do not exist
                stmt.execute("DROP TABLE NonAdminUser");
            } catch (SQLException ex) {
                // No need to report an error.
                // The table simply did not exist.
            }
        }

        try {
            // Create the table
            stmt.execute("CREATE TABLE NonAdminUser ("
                    + "id INT NOT NULL PRIMARY KEY,"
                    + "fullName VARCHAR(60) NOT NULL,"
                    + "address VARCHAR(50) NOT NULL,"
                    + "email VARCHAR(20) NOT NULL,"
                    + "uAccount VARCHAR(30) NOT NULL REFERENCES UserAccount(uName)"
                    + ")");
        } catch (SQLException ex) {
            // No need to report an error.
            // The table exists and may have some data.
        }
    }

    // adds new record, reads from Administrator object
    public void insertRecord(NonAdminUser data) throws SQLException {
        Statement stmt = connection.createStatement();

        stmt.executeUpdate("INSERT INTO NonAdminUser ( id, fullName, address, email, uAccount) "
                + "VALUES ("
                + (getMax() + 1) + ", '"  // We just assign a new id before we save the record
                + data.getFullName() + "', '"
                + data.getAddress() + "', '"
                + data.getEmail() + "', '"
                + data.getuAccount().getUName() + "')"
        );
    }

    public int getMax() throws SQLException {
        int num = 0;
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT MAX(id) FROM NonAdminUser");
        if (rs.next()) num = rs.getInt(1);
        return num;
    }

    // Modify one record based on the given object
    public void updateRecord(NonAdminUser data) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("UPDATE NonAdminUser "
                + "SET id = " + data.getID() + ", "
                + "fullName = '" + data.getFullName() + "', "
                + "address = '" + data.getAddress() + "', "
                + "email = '" + data.getEmail() + "', "
                + "uAccount = '" + data.getuAccount().getUName() + "' "
                + "WHERE id = " + data.getID() );
    }


    // Delete one record based on the given object
    public void deleteRecord(NonAdminUser data) throws SQLException {
        Statement stmt = connection.createStatement();
        // user profile
        System.out.println("DELETE FROM NonAdminUser WHERE id = " + data.getID());
        stmt.executeUpdate("DELETE FROM NonAdminUser WHERE id = " + data.getID() );
        // delete user account
        stmt.executeUpdate("DELETE FROM UserAccount WHERE uName = '" + data.getuAccount().getUName() + "'");
    }


    // get one record, that matches the given name value
    public NonAdminUser findRecord(String id) throws SQLException {
        NonAdminUser user = new NonAdminUser();
        ResultSet rs;

        // Create a Statement object
        Statement stmt = connection.createStatement();

        // Create a string with a SELECT statement
        String sqlStatement = "SELECT * FROM NonAdminUser WHERE uAccount = '" + id +"'";

        // Execute the statement and return the result
        rs = stmt.executeQuery(sqlStatement);
        while (rs.next()) {
            // note that, this loop will run only once
            user.setID(rs.getInt(1));
            user.setFullName(rs.getString(2));
            user.setAddress(rs.getString(3));
            user.setEmail(rs.getString(4));
        }
        return user;
    }
}

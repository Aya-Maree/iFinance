package se2203b.assignments.ifinance;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AdministratorAdapter {
    Connection connection;

    public AdministratorAdapter(Connection conn, Boolean reset) throws SQLException {
        connection = conn;

        Statement stmt = connection.createStatement();
        if (reset) {
            try {
                // Remove tables if database tables have been created.
                // This will throw an exception if the tables do not exist
                stmt.execute("DROP TABLE Administrator");
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
        try {
            // Create the table
            stmt.execute("CREATE TABLE Administrator ("
                    + "id INT NOT NULL PRIMARY KEY,"
                    + "fullName VARCHAR(60) NOT NULL,"
                    + "dateHired VARCHAR(10) ,"
                    + "dateFinished VARCHAR(10) ,"
                    + "uAccount VARCHAR(30) NOT NULL REFERENCES UserAccount(uName)"
                    + ")");


        } catch (SQLException ex) {
            // No need to report an error.
            // The table exists and may have some data.
        }
        try {
            addAmin();
        } catch (SQLException ex) {
            // No need to report an error.
            // The table exists and may have some data.
        }
    }

    // Add the default admin account
    private void addAmin() throws SQLException {
        UserAccount account = new UserAccount("admin", "admin", 0, "", "admin");
        Administrator admin = new Administrator("Admin Full-Name", "2023-03-06", account);

        // save use profile
        insertRecord(admin);
    }

    // adds new record, reads from Administrator object
    public void insertRecord(Administrator data) throws SQLException {

        Statement stmt = null;

        stmt = connection.createStatement();

        // insert password info first

        int i = new UserAccountAdapter(connection, false).insertRecord(data.getuAccount());

        // insert the user profile

        stmt.executeUpdate("INSERT INTO Administrator ( id,  fullName, dateHired, dateFinished, uAccount) "
                + "VALUES ("
                + data.getID() + ", '"
                + data.getFullName() + "', '"
                + data.getDateCreated() + "', '"
                + data.getDateCreated() + "', '"
                + data.getuAccount().getUName() + "')"
        );

    }

}

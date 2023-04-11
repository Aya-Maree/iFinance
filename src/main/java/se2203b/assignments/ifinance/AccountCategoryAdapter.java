package se2203b.assignments.ifinance;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountCategoryAdapter {
    Connection connection;

    public AccountCategoryAdapter(Connection conn, Boolean reset) throws SQLException {
        connection = conn;
        Statement stmt = connection.createStatement();

        if (reset) {
            try {
                // Remove tables if database tables have been created.
                // This will throw an exception if the tables do not exist
                stmt.execute("DROP TABLE AccountCategory");
            } catch (SQLException ex) {
                // No need to report an error.
                // The table simply did not exist.
            }
        }
        try {


           //  Create the table
            stmt.execute("CREATE TABLE AccountCategory ("
                    + "accountName VARCHAR(60) NOT NULL PRIMARY KEY,"
                    + "accountType VARCHAR(20) NOT NULL"
                    + ")");
                addAccountCategories();
        } catch (SQLException ex) {
            // No need to report an error.
            // The table exists and may have some data.
            }
    }

    private void addAccountCategories() throws SQLException {
        AccountCategory assets = new AccountCategory("Assets", "Debit");
        AccountCategory liabilities = new AccountCategory("Liabilities", "Credit");
        AccountCategory income = new AccountCategory("Income","Credit");
        AccountCategory expenses = new AccountCategory("Expenses", "Debit");
        insertRecord(assets);
        insertRecord(liabilities);
        insertRecord(income);
        insertRecord(expenses);
    }
    public ObservableList<AccountCategory> getAccountCategoryList() throws SQLException {
        ObservableList<AccountCategory> list = FXCollections.observableArrayList();
        // Create a Statement object
        ResultSet rs;
        Statement stmt = connection.createStatement();

        // Create a string with a SELECT statement
        String sqlStatement = "SELECT * FROM AccountCategory";

        // Execute the query by sending the SQL statement to the DBMS
        rs = stmt.executeQuery(sqlStatement);

        int count = 0;
        // Use a while loop to add the contents of the result set to uAccountsList
        while (rs.next()) {
            count++;
            list.add(new AccountCategory(
                            rs.getString(1),
                            rs.getString(2)
                    )
            );
        }
        return list;
    }

    public void insertRecord(AccountCategory data) throws SQLException {

        Statement stmt = connection.createStatement();


        stmt.executeUpdate("INSERT INTO AccountCategory (accountName, accountType) "
                + "VALUES ('"
                + data.getName() + "', '"
                + data.getType() + "')"
        );


    }
    public void updateRecord(AccountCategory data) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("UPDATE AccountCategory "
                + "SET accountName = " + data.getName() + ", "
                + "accountType = '" + data.getType() + "' "
                + "WHERE accountName = " + data.getName() );
    }

    public AccountCategory findRecord(String name) throws SQLException {
        AccountCategory accountCategory = new AccountCategory();
        ResultSet rs;

        // Create a Statement object
        Statement stmt = connection.createStatement();

        // Create a string with a SELECT statement
        String sqlStatement = "SELECT * FROM AccountCategory WHERE accountName = '" + name +"'";

        // Execute the statement and return the result
        rs = stmt.executeQuery(sqlStatement);
        while (rs.next()) {
            // note that, this loop will run only once
            accountCategory.setName(rs.getString(1));
            accountCategory.setType(rs.getString(2));

        }
        return accountCategory;
    }


}


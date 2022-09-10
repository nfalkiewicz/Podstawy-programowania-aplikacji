package com.example.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Partially created by pwilkin on 26.05.2022.
 */

public class DbManager {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public Connection getValidatedConnection() {
        testConnection();
        return getConnection();
    }

    public void testConnection() {
        try {
            if (connection.isClosed() || !connection.isValid(1000)) {
                obtainConnection();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void obtainConnection() {
        try {
            this.connection = DriverManager.getConnection("jdbc:hsqldb:file:library", "", "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private static void createTableIfNotExists(Connection c) throws SQLException {
        if (!tableExists(c, "BOOKS")) {
            try (Statement st = c.createStatement()) {
                st.execute("CREATE TABLE BOOKS (ID INT IDENTITY,  LASTNAME VARCHAR(255), FIRSTNAME VARCHAR(255), " +
                        "TITLE VARCHAR(255), YEAR VARCHAR(255), PRINT VARCHAR(255), DESCRIPTION VARCHAR(4000))");
                System.out.println("I am alive!");
            }
        }
        else{
            System.out.println("I wasn't dead!");
        }
    }

    private static boolean tableExists(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet resultSet = meta.getTables(null, null, tableName, new String[]{"TABLE"});
        return resultSet.next();
    }

    public void prepareDbIfNeeded() {
        try {
            Connection c = getValidatedConnection();
            createTableIfNotExists(c);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public DbManager() {
        obtainConnection();
    }

    public Book insertData(Connection c, String lastName, String firstName, String title,
                           String year, String print, String description) throws SQLException {
        //try (Connection c = DriverManager.getConnection("jdbc:hsqldb:file:libraDB", "SA", "")){
        try (PreparedStatement st = c.prepareStatement("INSERT INTO BOOKS (lastName, firstName," +
                " title, year, print, description) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, lastName);
            st.setString(2, firstName);
            st.setString(3, title);
            st.setString(4, year);
            st.setString(5, print);
            st.setString(6, description);
            st.execute();
            try (ResultSet rs = st.getGeneratedKeys()) {
                rs.next();
                Book temp = new Book(rs.getInt(1), lastName,
                        firstName, title,
                        year, print, description);
                /*System.out.println("Row [1] added "+ temp);*/

                //book.setId(rs.getInt(1));
                System.out.println("Good job! You added a book titled: "+ title);
                return temp;
            }
        }
        catch (Exception e) {
            System.out.println("Unfortunately this book breaks my code, so you can't have it");
        }
        return null;
    }

    public void deleteBook(Connection c, Book book) {
        if (book.getId() == null) {
            throw new IllegalArgumentException("Cannot delete a non-stored object");
        }
        //Connection c = dbManager.getValidatedConnection();
        try (PreparedStatement st = c.prepareStatement("DELETE FROM BOOKS WHERE ID = ?")) {
            st.setInt(1, book.getId());
            st.execute();
            book.setId(null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateBook(Connection c, Book book) {
        if (book.getId() == null) {
            throw new IllegalArgumentException("Cannot update a non-stored object");
        }
        //Connection c = db.getValidatedConnection();
        try (PreparedStatement st = c.prepareStatement("UPDATE BOOKS SET LASTNAME = ?, FIRSTNAME = ?, TITLE = ?," +
                "YEAR=?, PRINT = ?, DESCRIPTION = ? WHERE ID = ?")) {
            //System.out.println(book);
            st.setString(1, book.getLastName());
            st.setString(2, book.getFirstName());
            st.setString(3, book.getTitle());
            st.setString(4, book.getYear());
            st.setString(5, book.getPrint());
            st.setString(6, book.getDescription());
            st.setInt(7, book.getId());
            st.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<Book> findBooksByTitle(Connection c, String title) {
        ObservableList<Book> temp = FXCollections.observableArrayList();
        try (PreparedStatement st = c.prepareStatement("SELECT * FROM BOOKS WHERE TITLE = ?")) {
            st.setString(1, title);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Book book = getBookFromCursor(rs);
                    temp.add(book);
                }
            }
            return temp;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Book getBookFromCursor(ResultSet rs) throws  SQLException{
            Book book = new Book(rs.getInt("ID"),
                    rs.getString("LASTNAME"), rs.getString("FIRSTNAME"),
                    rs.getString("TITLE"),rs.getString("YEAR"),
                    rs.getString("PRINT"),rs.getString("DESCRIPTION"));
            return book;
    }

    public ObservableList<Book> showBooks(Connection c) throws SQLException {
        String SQL = "SELECT * from BOOKS";
        ResultSet rs = c.createStatement().executeQuery(SQL);
        ObservableList<Book> row = FXCollections.observableArrayList();
        while (rs.next()) {
            //Iterate Row

            Book temp = new Book(rs.getInt(1), rs.getString(2),
                    rs.getString(3), rs.getString(4),
                    rs.getString(5), rs.getString(6), rs.getString(7));
            /*System.out.println("Row [1] added "+ temp);*/
            row.add(temp);
        }
        return row;
    }


}

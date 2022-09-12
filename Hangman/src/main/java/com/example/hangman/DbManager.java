package com.example.hangman;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

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
            this.connection = DriverManager.getConnection("jdbc:hsqldb:file:hangmantest2", "", "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private static void createTableIfNotExists(Connection c) throws SQLException {
        if (!tableExists(c, "WORDS")) {
            try (Statement st = c.createStatement()) {
                st.execute("CREATE TABLE WORDS (ID INT IDENTITY,  SŁOWO VARCHAR(255), DŁUGOŚĆ VARCHAR(255), " +
                        "KATEGORIA VARCHAR(255))");
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

    public Word insertWord(Connection c, String name, String category) throws SQLException {
        String length = String.valueOf(name.length());
        try (PreparedStatement st = c.prepareStatement("INSERT INTO WORDS (SłOWO, DŁUGOŚĆ, KATEGORIA) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, name);
            st.setString(2, length);
            st.setString(3, category);
            st.execute();
            try (ResultSet rs = st.getGeneratedKeys()) {
                rs.next();
                Word temp = new Word(rs.getInt(1), name,
                        length, category);
                /*System.out.println("Row [1] added "+ temp);*/

                //book.setId(rs.getInt(1));
                System.out.println("Good job! You added a new word: "+ name);
                return temp;
            }
        }
        catch (Exception e) {
            System.out.println("Unfortunately this word breaks my code, so you can't have it");
        }
        return null;
    }

    public void deleteWord(Connection c, Word word) {
        if (word.getId() == null) {
            throw new IllegalArgumentException("Cannot delete a non-stored object");
        }
        //Connection c = dbManager.getValidatedConnection();
        try (PreparedStatement st = c.prepareStatement("DELETE FROM WORDS WHERE ID = ?")) {
            st.setInt(1, word.getId());
            st.execute();
            word.setId(null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateWord(Connection c, Word word) {
        if (word.getId() == null) {
            throw new IllegalArgumentException("Cannot update a non-stored object");
        }
        //Connection c = db.getValidatedConnection();
        try (PreparedStatement st = c.prepareStatement("UPDATE WORDS SET SŁOWO = ?, DŁUGOŚĆ = ?, KATEGORIA = ? WHERE ID = ?")) {
            //System.out.println(book);
            st.setString(1, word.getName());
            st.setString(2, word.getLength());
            st.setString(3, word.getCategory());
            st.setInt(7, word.getId());
            st.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<Word> findWordByName(Connection c, String name) {
        ObservableList<Word> temp = FXCollections.observableArrayList();
        try (PreparedStatement st = c.prepareStatement("SELECT * FROM WORDS WHERE SŁOWO = ?")) {
            st.setString(1, name);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Word word = getWordFromCursor(rs);
                    temp.add(word);
                }
            }
            return temp;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<Word> findWordByLength(Connection c, String length) {
        ObservableList<Word> temp = FXCollections.observableArrayList();
        try (PreparedStatement st = c.prepareStatement("SELECT * FROM WORDS WHERE DŁUGOŚĆ = ?")) {
            st.setString(1, length);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Word word = getWordFromCursor(rs);
                    temp.add(word);
                }
            }
            return temp;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<Word> findWordByCategory(Connection c, String category) {
        ObservableList<Word> temp = FXCollections.observableArrayList();
        try (PreparedStatement st = c.prepareStatement("SELECT * FROM WORDS WHERE KATEGORIA = ?")) {
            st.setString(1, category);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Word word = getWordFromCursor(rs);
                    temp.add(word);
                }
            }
            return temp;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Word getWordFromCursor(ResultSet rs) throws  SQLException{
        Word word = new Word(rs.getInt("ID"),
                rs.getString("SŁOWO"),
                rs.getString("DŁUGOŚĆ"),
                rs.getString("KATEGORIA"));
        return word;
    }

    public ObservableList<Word> showWords(Connection c) throws SQLException {
        String SQL = "SELECT * from WORDS";
        ResultSet rs = c.createStatement().executeQuery(SQL);
        ObservableList<Word> row = FXCollections.observableArrayList();
        while (rs.next()) {
            //Iterate Row

            Word temp = new Word(rs.getInt(1), rs.getString(2),
                    rs.getString(3), rs.getString(4));
            /*System.out.println("Row [1] added "+ temp);*/
            row.add(temp);
        }
        return row;
    }

}

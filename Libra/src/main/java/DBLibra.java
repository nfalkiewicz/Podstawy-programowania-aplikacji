import org.junit.jupiter.api.Assertions;

import java.sql.*;

public class DBLibra {
    public static void main(String[] args) {

        createTableIfNotExists();
        /*
            Mamy stworzoną tabelę.
        */
        /*try {
            prepareData("Kowalski", "Marian", "Coś",2002, "Buka", "csods");
        } catch (SQLException throwables) {
            System.out.println("A może tu nie działam? Main try catch");
            throwables.printStackTrace();
        }*/
        dataOperations();
    }

    private static void createTableIfNotExists() {
        try (Connection c = DriverManager.getConnection("jdbc:hsqldb:file:libraDB", "SA", "")) {
            if (!tableExists(c, "BOOKS")) {
                try (Statement st = c.createStatement()) {
                    st.execute("CREATE TABLE BOOKS (ID INT IDENTITY,  NAZWISKO VARCHAR(255), IMIE VARCHAR(255), " +
                            "TYTUL VARCHAR(255), ROK INT, WYDAWNICTWO VARCHAR(255), OPIS VARCHAR(4000))");
                    System.out.println("Stworzyłam się!");
                }
            }
            else{
                System.out.println("Już istnieję!");
            }
        } catch (Exception e) {
            System.out.println("No tables for you!");
            Assertions.fail(e);
        }
    }

    private static boolean tableExists(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet resultSet = meta.getTables(null, null, tableName, new String[]{"TABLE"});
        return resultSet.next();
    }

    private static void dataOperations(){
        try (Connection c = DriverManager.getConnection("jdbc:hsqldb:file:libraDB", "SA", "")){
            prepareData(c, "Kowalski", "Marian", "Coś",2002, "Buka", "csods");
            prepareData(c, "Nowak", "Marian", "Coś",2002, "Buka", "csods");
        }
        catch (Exception e) {
            System.out.println("Nic nie możesz ze mną zrobić.");
            Assertions.fail(e);
        }
    }
    private static void prepareData(Connection c, String nazwisko, String imie, String tytul, int rok, String wydawnictwo, String opis) throws SQLException{
        //try (Connection c = DriverManager.getConnection("jdbc:hsqldb:file:libraDB", "SA", "")){
            try (PreparedStatement st = c.prepareStatement("INSERT INTO BOOKS (NAZWISKO, IMIE, TYTUL, ROK, WYDAWNICTWO, OPIS) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                Book book = new Book();
                book.setNazwisko(nazwisko);
                book.setImie(imie);
                book.setTytul(tytul);
                book.setRok(rok);
                book.setWydawnictwo(wydawnictwo);
                book.setOpis(opis);

                st.setString(1, book.getNazwisko());
                st.setString(2, book.getImie());
                st.setString(3, book.getTytul());
                st.setInt(4, book.getRok());
                st.setString(5, book.getWydawnictwo());
                st.setString(6, book.getOpis());

                try (ResultSet rs = st.getGeneratedKeys()) {
                    if (rs.next()) {
                        book.setId(rs.getInt(1));
                        System.out.println("Dodałeś "+ book);
                    }
                    else{
                        System.out.println("Problem jest w rs.next()");
                    }

                }
            }
            catch (Exception e) {
                System.out.println("Nie wpiszę książki :C");
                Assertions.fail(e);
            }
        //}

    }
}

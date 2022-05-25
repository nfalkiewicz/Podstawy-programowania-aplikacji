import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.*;

public class BookDBTest {

    @BeforeAll
    public static void prepareDb() {
        try (Connection c = DriverManager.getConnection("jdbc:hsqldb:file:testdb", "SA", "")) {
            try (Statement st = c.createStatement()) {
                st.execute("CREATE TABLE BOOKS (ID INT IDENTITY,  NAZWISKO VARCHAR(255), IMIE VARCHAR(255), " +
                        "TYTUL VARCHAR(255), ROK INT, WYDAWNICTWO VARCHAR(255), OPIS VARCHAR(4000))");
            }
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @AfterAll
    public static void cleanupDb() {
        try (Connection c = DriverManager.getConnection("jdbc:hsqldb:file:testdb", "SA", "")) {
            try (Statement st = c.createStatement()) {
                st.execute("DROP TABLE BOOKS");
            }
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @Test
    public void testConnectDb() {
        try (Connection c = DriverManager.getConnection("jdbc:hsqldb:file:testdb", "SA", "")) {
            Assertions.assertFalse(c.isClosed());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @Test
    private void prepareData(Connection c) throws SQLException {
        try (PreparedStatement st = c.prepareStatement("INSERT INTO BOOKS (NAZWISKO, IMIE, TYTUL, ROK, WYDAWNICTWO, OPIS) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            Book book = new Book();
            book.setNazwisko("Kowalski");
            book.setImie("Marian");
            book.setTytul("Cosiek");
            book.setRok(2004);
            book.setWydawnictwo("problem jest");
            book.setOpis("jakis");

            st.setString(1, book.getNazwisko());
            st.setString(2, book.getImie());
            st.setString(3, book.getTytul());
            st.setInt(4, book.getRok());
            st.setString(5, book.getWydawnictwo());
            st.setString(6, book.getOpis());

            try (ResultSet rs = st.getGeneratedKeys()) {
                rs.next();
                book.setId(rs.getInt(1));
                System.out.println("Dodałeś "+ book);
            }
        }
    }

}

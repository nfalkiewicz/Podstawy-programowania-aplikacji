public class Book {
    private int id;
    private String nazwisko;
    private String imie;
    private String tytul;
    private int rok;
    private String wydawnictwo;
    private String opis;

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public int getRok() {
        return rok;
    }

    public void setRok(int rok) {
        this.rok = rok;
    }

    public String getWydawnictwo() {
        return wydawnictwo;
    }

    public void setWydawnictwo(String wydawnictwo) {
        this.wydawnictwo = wydawnictwo;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public int getId() { return id;}

    public void setId(int id) { this.id = id; }
}

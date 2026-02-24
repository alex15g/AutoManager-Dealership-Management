package ro.utcn.reprezentanta.model;
public class Angajat {

    private int idAngajat;
    private String nume;
    private String prenume;
    private String functie;
    private String email;
    private double salariu;

    public Angajat(int idAngajat, String nume, String prenume,
                   String functie, String email, double salariu) {
        this.idAngajat = idAngajat;
        this.nume = nume;
        this.prenume = prenume;
        this.functie = functie;
        this.email = email;
        this.salariu = salariu;
    }

    public int getIdAngajat() {
        return idAngajat;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getFunctie() {
        return functie;
    }

    public String getEmail() {
        return email;
    }

    public double getSalariu() {
        return salariu;
    }

    @Override
    public String toString() {
        return nume + " " + prenume + " - " + functie;
    }

}


package ro.utcn.reprezentanta.model;

public class Client {

    private int idClient;
    private String nume;
    private String prenume;
    private String telefon;
    private String email;

    public Client(int idClient, String nume, String prenume, String telefon, String email) {
        this.idClient = idClient;
        this.nume = nume;
        this.prenume = prenume;
        this.telefon = telefon;
        this.email = email;
    }

    public int getIdClient() {
        return idClient;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return nume + " " + prenume;
    }

}

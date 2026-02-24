package ro.utcn.reprezentanta.model;

import java.time.LocalDate;

public class Vanzare {

    private int idVanzare;
    private int idClient;
    private int idMasina;
    private int idAngajat;
    private LocalDate dataVanzare;
    private double pretFinal;

    // pentru afișare în tabel
    private String client;
    private String masina;
    private String angajat;

    public Vanzare(int idVanzare, LocalDate dataVanzare, double pretFinal,
                   String client, String masina, String angajat) {
        this.idVanzare = idVanzare;
        this.dataVanzare = dataVanzare;
        this.pretFinal = pretFinal;
        this.client = client;
        this.masina = masina;
        this.angajat = angajat;
    }

    public int getIdVanzare() { return idVanzare; }
    public LocalDate getDataVanzare() { return dataVanzare; }
    public double getPretFinal() { return pretFinal; }
    public String getClient() { return client; }
    public String getMasina() { return masina; }
    public String getAngajat() { return angajat; }
}

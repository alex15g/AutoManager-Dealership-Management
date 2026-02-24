package ro.utcn.reprezentanta.model;

import java.time.LocalDate;

public class Revizie {

    private int idRevizie;
    private LocalDate dataRevizie;
    private int km;
    private String descriere;
    private double cost;

    public Revizie(int idRevizie, LocalDate dataRevizie,
                   int km, String descriere, double cost) {
        this.idRevizie = idRevizie;
        this.dataRevizie = dataRevizie;
        this.km = km;
        this.descriere = descriere;
        this.cost = cost;
    }

    public int getIdRevizie() { return idRevizie; }
    public LocalDate getDataRevizie() { return dataRevizie; }
    public int getKm() { return km; }
    public String getDescriere() { return descriere; }
    public double getCost() { return cost; }
}

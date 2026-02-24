package ro.utcn.reprezentanta.model;

public class Masina {

    private int idMasina;
    private String marca;
    private String model;
    private int anFabricatie;
    private double pret;
    private int idClient;


    public Masina(int idMasina, String marca, String model, int anFabricatie,
                  double pret, int idClient) {
        this.idMasina = idMasina;
        this.marca = marca;
        this.model = model;
        this.anFabricatie = anFabricatie;
        this.pret = pret;
        this.idClient = idClient;
    }


    public int getIdMasina() {
        return idMasina;
    }

    public String getMarca() {
        return marca;
    }

    public String getModel() {
        return model;
    }

    public int getAnFabricatie() {
        return anFabricatie;
    }

    public double getPret() {
        return pret;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    @Override
    public String toString() {
        return marca + " " + model + " (" + anFabricatie + ")";
    }

}

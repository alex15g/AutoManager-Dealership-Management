package ro.utcn.reprezentanta.model;

import java.time.LocalDate;

public class CarteService {

    private int idCarteService;
    private int kmInitiali;
    private LocalDate ultimaRevizie;
    private LocalDate dataExpirareGarantie;

    public CarteService(int idCarteService, int kmInitiali,
                        LocalDate ultimaRevizie,
                        LocalDate dataExpirareGarantie) {
        this.idCarteService = idCarteService;
        this.kmInitiali = kmInitiali;
        this.ultimaRevizie = ultimaRevizie;
        this.dataExpirareGarantie = dataExpirareGarantie;
    }

    public int getIdCarteService() {
        return idCarteService;
    }

    public int getKmInitiali() {
        return kmInitiali;
    }

    public LocalDate getUltimaRevizie() {
        return ultimaRevizie;
    }

    public LocalDate getDataExpirareGarantie() {
        return dataExpirareGarantie;
    }
}

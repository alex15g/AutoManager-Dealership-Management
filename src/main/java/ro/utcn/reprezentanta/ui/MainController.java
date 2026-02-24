package ro.utcn.reprezentanta.ui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ro.utcn.reprezentanta.dao.*;
import ro.utcn.reprezentanta.model.*;

import java.time.LocalDate;
import java.util.Optional;

import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainController {

    @FXML
    private TableView<Client> clientTable;

    @FXML
    private TableColumn<Client, Integer> colId;
    @FXML
    private TableColumn<Client, String> colNume;
    @FXML
    private TableColumn<Client, String> colPrenume;
    @FXML
    private TableColumn<Client, String> colTelefon;
    @FXML
    private TableColumn<Client, String> colEmail;

    @FXML
    private TextField tfNume;
    @FXML
    private TextField tfPrenume;
    @FXML
    private TextField tfTelefon;
    @FXML
    private TextField tfEmail;

    @FXML private TableView<Masina> masinaTable;
    @FXML private TableColumn<Masina, Integer> colMasinaId;
    @FXML private TableColumn<Masina, String> colMarca;
    @FXML private TableColumn<Masina, String> colModel;
    @FXML private TableColumn<Masina, Integer> colAn;
    @FXML private TableColumn<Masina, Double> colPret;

    @FXML private TextField tfMarca;
    @FXML private TextField tfModel;
    @FXML private TextField tfAn;
    @FXML private TextField tfPret;

    @FXML private Label lblCarteId;
    @FXML private Label lblKmInitiali;
    @FXML private Label lblUltimaRevizie;
    @FXML private Label lblExpirareGarantie;

    @FXML private Button btnCreateCarte;

    @FXML private TableView<Revizie> revizieTable;
    @FXML private TableColumn<Revizie, Integer> colRevizieId;
    @FXML private TableColumn<Revizie, LocalDate> colDataRevizie;
    @FXML private TableColumn<Revizie, Integer> colKm;
    @FXML private TableColumn<Revizie, String> colDescriere;
    @FXML private TableColumn<Revizie, Double> colCost;

    @FXML private Label lblCostTotalRevizii;

    @FXML
    private Button btnDeleteRevizie;

    @FXML
    private Button btnUpdateRevizie;

    @FXML private TableView<Angajat> angajatTable;
    @FXML private TableColumn<Angajat, Integer> colAngId;
    @FXML private TableColumn<Angajat, String> colAngNume;
    @FXML private TableColumn<Angajat, String> colAngPrenume;
    @FXML private TableColumn<Angajat, String> colAngFunctie;
    @FXML private TableColumn<Angajat, String> colAngEmail;

    @FXML private TextField tfAngNume;
    @FXML private TextField tfAngPrenume;
    @FXML private TextField tfAngFunctie;
    @FXML private TextField tfAngEmail;
    @FXML private TextField tfAngSalariu;
    @FXML private TableColumn<Angajat, Double> colAngSalariu;

    @FXML private Button btnDeleteClient;
    @FXML private Button btnDeleteMasina;
    @FXML private Button btnDeleteAngajat;

    @FXML
    private Label lblUser;
    @FXML private ComboBox<Client> cbClient;
    @FXML private ComboBox<Masina> cbMasina;
    @FXML private ComboBox<Angajat> cbAngajat;
    @FXML private TextField tfPretFinal;

    @FXML private TableView<Vanzare> vanzareTable;
    @FXML private TableColumn<Vanzare, Integer> colVId;
    @FXML private TableColumn<Vanzare, LocalDate> colVData;
    @FXML private TableColumn<Vanzare, String> colVClient;
    @FXML private TableColumn<Vanzare, String> colVMasina;
    @FXML private TableColumn<Vanzare, String> colVAngajat;
    @FXML private TableColumn<Vanzare, Double> colVPret;

    private VanzareDAO vanzareDAO = new VanzareDAO();



    private AngajatDAO angajatDAO = new AngajatDAO();



    private RevizieDAO revizieDAO = new RevizieDAO();



    private MasinaDAO masinaDAO = new MasinaDAO();
    private final ClientDAO clientDAO = new ClientDAO();
    private CarteServiceDAO carteServiceDAO = new CarteServiceDAO();


    private User currentUser;


    public void setCurrentUser(User user) {
        this.currentUser = user;

        if (lblUser != null) { // doar ca safety
            String role = user.isAdmin() ? "ADMIN" : "ANGAJAT";
            lblUser.setText("👤 Logat ca: " + user.getUsername() + " (" + role + ")");
        }

        // dacă vrei și restricții (dar fără NPE):
        applyRoleRestrictions();
    }

    private void applyRoleRestrictions() {
        if (currentUser == null) return;

        boolean isAdmin = currentUser.isAdmin();

        // IMPORTANT: doar dacă butoanele există și sunt @FXML legate în FXML
        if (btnDeleteClient != null) btnDeleteClient.setDisable(!isAdmin);
        if (btnDeleteMasina != null) btnDeleteMasina.setDisable(!isAdmin);
        if (btnDeleteAngajat != null) btnDeleteAngajat.setDisable(!isAdmin);
    }





    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idClient"));
        colNume.setCellValueFactory(new PropertyValueFactory<>("nume"));
        colPrenume.setCellValueFactory(new PropertyValueFactory<>("prenume"));
        colTelefon.setCellValueFactory(new PropertyValueFactory<>("telefon"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        handleRefresh();

        clientTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldClient, newClient) -> {

                    if (newClient == null) {
                        // nimic selectat → curățăm tot
                        tfNume.clear();
                        tfPrenume.clear();
                        tfTelefon.clear();
                        tfEmail.clear();

                        masinaTable.getItems().clear();
                        clearCarteServiceUI();
                        revizieTable.getItems().clear();
                        lblCostTotalRevizii.setText("0.00 lei");
                        return;
                    }

                    // 1️⃣ populate formular client
                    tfNume.setText(newClient.getNume());
                    tfPrenume.setText(newClient.getPrenume());
                    tfTelefon.setText(newClient.getTelefon());
                    tfEmail.setText(newClient.getEmail());

                    // 2️⃣ încarcă DOAR mașinile clientului
                    masinaTable.setItems(
                            FXCollections.observableArrayList(
                                    masinaDAO.getMasiniByClient(newClient.getIdClient())
                            )
                    );

                    // 3️⃣ reset jos (carte service + revizii)
                    clearCarteServiceUI();
                    revizieTable.getItems().clear();
                    lblCostTotalRevizii.setText("0.00 lei");
                }
        );


        colMasinaId.setCellValueFactory(new PropertyValueFactory<>("idMasina"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colAn.setCellValueFactory(new PropertyValueFactory<>("anFabricatie"));
        colPret.setCellValueFactory(new PropertyValueFactory<>("pret"));


        masinaTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldMasina, newMasina) -> {

                    if (newMasina == null) {
                        clearCarteServiceUI();
                        revizieTable.getItems().clear();
                        return;
                    }

                    // populate form
                    tfMarca.setText(newMasina.getMarca());
                    tfModel.setText(newMasina.getModel());
                    tfAn.setText(String.valueOf(newMasina.getAnFabricatie()));
                    tfPret.setText(String.valueOf(newMasina.getPret()));

                    // 🔹 carte service
                    CarteService cs =
                            carteServiceDAO.getCarteServiceByMasina(newMasina.getIdMasina());

                    if (cs == null) {
                        clearCarteServiceUI();
                        revizieTable.getItems().clear();
                        lblCostTotalRevizii.setText("0.00 lei");
                    } else {
                        lblCarteId.setText("ID: " + cs.getIdCarteService());
                        lblKmInitiali.setText("KM inițiali: " + cs.getKmInitiali());
                        lblUltimaRevizie.setText("Ultima revizie: " + cs.getUltimaRevizie());
                        lblExpirareGarantie.setText("Expirare garanție: " + cs.getDataExpirareGarantie());

                        btnCreateCarte.setDisable(true);

                        // 🔹 REVIZII (CORECT)
                        revizieTable.setItems(
                                FXCollections.observableArrayList(
                                        revizieDAO.getReviziiByMasina(newMasina.getIdMasina())
                                )
                        );

                        updateCostTotalRevizii(newMasina);

                    }
                }
        );


        //clearCarteServiceUI();

        colRevizieId.setCellValueFactory(new PropertyValueFactory<>("idRevizie"));
        colDataRevizie.setCellValueFactory(new PropertyValueFactory<>("dataRevizie"));
        colKm.setCellValueFactory(new PropertyValueFactory<>("km"));
        colDescriere.setCellValueFactory(new PropertyValueFactory<>("descriere"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        // 🔹 dezactivare butoane revizie inițial
        btnDeleteRevizie.setDisable(true);
        btnUpdateRevizie.setDisable(true);

// 🔹 activare doar când e selectată o revizie
        revizieTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldSel, newSel) -> {
                    boolean hasSelection = newSel != null;
                    btnDeleteRevizie.setDisable(!hasSelection);
                    btnUpdateRevizie.setDisable(!hasSelection);
                });

// 🔹 highlight revizia cea mai recentă (prima din listă)
        revizieTable.setRowFactory(tv -> new TableRow<Revizie>() {
            @Override
            protected void updateItem(Revizie rev, boolean empty) {
                super.updateItem(rev, empty);

                if (rev == null || empty) {
                    setStyle("");
                } else if (getIndex() == 0) {
                    setStyle("-fx-background-color: #d4f5d4;");
                } else {
                    setStyle("");
                }
            }
        });

        // ================== ANGAJAȚI ==================

        colAngId.setCellValueFactory(new PropertyValueFactory<>("idAngajat"));
        colAngNume.setCellValueFactory(new PropertyValueFactory<>("nume"));
        colAngPrenume.setCellValueFactory(new PropertyValueFactory<>("prenume"));
        colAngFunctie.setCellValueFactory(new PropertyValueFactory<>("functie"));
        colAngEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAngSalariu.setCellValueFactory(new PropertyValueFactory<>("salariu"));

// load inițial
        angajatTable.setItems(
                FXCollections.observableArrayList(
                        angajatDAO.getAllAngajati()
                )
        );

// populate formular la select
        angajatTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldA, newA) -> {
                    if (newA == null) {
                        tfAngNume.clear();
                        tfAngPrenume.clear();
                        tfAngFunctie.clear();
                        tfAngEmail.clear();
                        tfAngSalariu.clear();
                        return;
                    }

                    tfAngNume.setText(newA.getNume());
                    tfAngPrenume.setText(newA.getPrenume());
                    tfAngFunctie.setText(newA.getFunctie());
                    tfAngEmail.setText(newA.getEmail());
                    tfAngSalariu.setText(String.valueOf(newA.getSalariu()));
                });

        cbClient.setItems(FXCollections.observableArrayList(clientDAO.getAllClients()));
        cbAngajat.setItems(FXCollections.observableArrayList(angajatDAO.getAllAngajati()));
        cbMasina.setItems(FXCollections.observableArrayList(masinaDAO.getMasiniDisponibile()));

        colVId.setCellValueFactory(new PropertyValueFactory<>("idVanzare"));
        colVData.setCellValueFactory(new PropertyValueFactory<>("dataVanzare"));
        colVClient.setCellValueFactory(new PropertyValueFactory<>("client"));
        colVMasina.setCellValueFactory(new PropertyValueFactory<>("masina"));
        colVAngajat.setCellValueFactory(new PropertyValueFactory<>("angajat"));
        colVPret.setCellValueFactory(new PropertyValueFactory<>("pretFinal"));

        vanzareTable.setItems(
                FXCollections.observableArrayList(vanzareDAO.getAllVanzari())
        );



    }

    @FXML
    public void handleRefresh() {
        clientTable.setItems(
                FXCollections.observableArrayList(clientDAO.getAllClients())
        );
    }

    @FXML
    public void handleAddClient() {
        if (tfNume.getText().isEmpty() || tfPrenume.getText().isEmpty()) {
            System.out.println("Completează câmpurile!");
            return;
        }

        clientDAO.addClient(
                tfNume.getText(),
                tfPrenume.getText(),
                tfTelefon.getText(),
                tfEmail.getText()
        );

        handleRefresh();
        clearFields();
    }

    @FXML
    public void handleDeleteClient() {

        Client selected = clientTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            System.out.println("Selectează un client!");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmare ștergere");
        alert.setHeaderText("Ștergere client");
        alert.setContentText(
                "Ești sigur că vrei să ștergi clientul:\n" +
                        selected.getNume() + " " + selected.getPrenume() + " ?"
        );

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isEmpty() || result.get() != ButtonType.OK) {
            return;
        }

        clientDAO.deleteClient(selected.getIdClient());
        handleRefresh();
        clearFields();
    }


    @FXML
    public void handleUpdateClient() {
        Client selected = clientTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            System.out.println("Nu ai selectat niciun client!");
            return;
        }

        clientDAO.updateClient(
                selected.getIdClient(),
                tfNume.getText(),
                tfPrenume.getText(),
                tfTelefon.getText(),
                tfEmail.getText()
        );

        handleRefresh();
        clearFields();
    }

    @FXML
    public void handleAddMasina() {
        Client client = clientTable.getSelectionModel().getSelectedItem();

        if (client == null) {
            System.out.println("Selectează un client!");
            return;
        }


        if (tfMarca.getText().isEmpty() || tfModel.getText().isEmpty()) {
            System.out.println("Completează câmpurile!");
            return;
        }

        masinaDAO.addMasina(
                tfMarca.getText(),
                tfModel.getText(),
                Integer.parseInt(tfAn.getText()),
                Double.parseDouble(tfPret.getText()),
                client.getIdClient()
        );


        tfMarca.clear();
        tfModel.clear();
        tfAn.clear();
        tfPret.clear();

        masinaTable.setItems(
                FXCollections.observableArrayList(
                        masinaDAO.getMasiniByClient(client.getIdClient())
                )
        );

    }


    @FXML
    public void handleDeleteMasina() {

        Masina masina = masinaTable.getSelectionModel().getSelectedItem();
        if (masina == null) {
            System.out.println("Nu ai selectat nicio mașină!");
            return;
        }

        // 1️⃣ confirmare
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmare");
        alert.setHeaderText("Ștergere mașină");
        alert.setContentText("Se vor șterge și cartea service + toate reviziile.\nEști sigur?");

        if (alert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        // 2️⃣ ștergere în lanț
        revizieDAO.deleteReviziiByMasina(masina.getIdMasina());
        carteServiceDAO.deleteCarteServiceByMasina(masina.getIdMasina());
        masinaDAO.deleteMasina(masina.getIdMasina());


        // 3️⃣ refresh
        Client client = clientTable.getSelectionModel().getSelectedItem();
        if (client != null) {
            masinaTable.setItems(
                    FXCollections.observableArrayList(
                            masinaDAO.getMasiniByClient(client.getIdClient())
                    )
            );
        }

        clearCarteServiceUI();
        revizieTable.getItems().clear();
        lblCostTotalRevizii.setText("0.00 lei");
    }




    @FXML
    public void handleUpdateMasina() {

        Masina selected = masinaTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            System.out.println("Nu ai selectat nicio mașină!");
            return;
        }

        if (tfMarca.getText().isEmpty() || tfModel.getText().isEmpty()) {
            System.out.println("Completează câmpurile!");
            return;
        }

        int an;
        double pret;

        try {
            an = Integer.parseInt(tfAn.getText());
            pret = Double.parseDouble(tfPret.getText());
        } catch (NumberFormatException e) {
            System.out.println("An sau preț invalid!");
            return;
        }

        MasinaDAO dao = new MasinaDAO();
        dao.updateMasina(
                selected.getIdMasina(),
                tfMarca.getText(),
                tfModel.getText(),
                an,
                pret
        );

        Client client = clientTable.getSelectionModel().getSelectedItem();
        if (client != null) {
            masinaTable.setItems(
                    FXCollections.observableArrayList(
                            masinaDAO.getMasiniByClient(client.getIdClient())
                    )
            );
        }


        clearMasinaFields();
    }


    @FXML
    private void handleViewCarteService() {
        Masina m = masinaTable.getSelectionModel().getSelectedItem();
        if (m == null) {
            System.out.println("Selectează o mașină!");
            return;
        }

        CarteService cs = carteServiceDAO.getCarteServiceByMasina(m.getIdMasina());
        if (cs == null) {
            System.out.println("Mașina NU are carte service.");
            return;
        }

        System.out.println("Carte service pentru mașina " + m.getIdMasina() + ":");
        System.out.println("ID: " + cs.getIdCarteService());
        System.out.println("KM inițiali: " + cs.getKmInitiali());
        System.out.println("Ultima revizie: " + cs.getUltimaRevizie());
        System.out.println("Expirare garanție: " + cs.getDataExpirareGarantie());
    }

    @FXML
    public void handleCreateCarteService() {

        // 1️⃣ verifică selecția
        Masina masina = masinaTable.getSelectionModel().getSelectedItem();
        if (masina == null) {
            System.out.println("Selectează o mașină!");
            return;
        }

        // 2️⃣ verifică dacă are deja carte service
        CarteServiceDAO csDao = new CarteServiceDAO();
        CarteService existent = csDao.getCarteServiceByMasina(masina.getIdMasina());

        if (existent != null) {
            System.out.println("Această mașină are deja carte service!");
            return;
        }

        // 3️⃣ cere km inițiali
        TextInputDialog kmDialog = new TextInputDialog();
        kmDialog.setTitle("Carte service");
        kmDialog.setHeaderText("Introdu kilometrii inițiali");
        kmDialog.setContentText("KM:");

        Optional<String> kmResult = kmDialog.showAndWait();
        if (kmResult.isEmpty()) return;

        int kmInitiali;
        try {
            kmInitiali = Integer.parseInt(kmResult.get());
        } catch (NumberFormatException e) {
            System.out.println("KM invalizi!");
            return;
        }

        // 4️⃣ cere data expirării garanției
        DatePicker datePicker = new DatePicker();

        Dialog<LocalDate> dateDialog = new Dialog<>();
        dateDialog.setTitle("Garanție");
        dateDialog.setHeaderText("Data expirării garanției");
        dateDialog.getDialogPane().setContent(datePicker);
        dateDialog.getDialogPane().getButtonTypes().addAll(
                ButtonType.OK, ButtonType.CANCEL
        );

        dateDialog.setResultConverter(btn ->
                btn == ButtonType.OK ? datePicker.getValue() : null
        );

        Optional<LocalDate> dateResult = dateDialog.showAndWait();
        if (dateResult.isEmpty()) return;

        // 5️⃣ creează carte service
        csDao.createCarteServiceForMasina(
                masina.getIdMasina(),
                kmInitiali,
                dateResult.get()
        );

        loadCarteServiceForMasina(masina.getIdMasina());
        // 6️⃣ feedback
        System.out.println("Carte service creată cu succes!");
    }

    private void loadCarteServiceForMasina(int idMasina) {
        CarteServiceDAO dao = new CarteServiceDAO();
        CarteService cs = dao.getCarteServiceByMasina(idMasina);

        if (cs == null) {
            lblCarteId.setText("ID: -");
            lblKmInitiali.setText("KM inițiali: -");
            lblUltimaRevizie.setText("Ultima revizie: -");
            lblExpirareGarantie.setText("Expirare garanție: -");

            btnCreateCarte.setDisable(false); // POȚI crea
        } else {
            lblCarteId.setText("ID: " + cs.getIdCarteService());
            lblKmInitiali.setText("KM inițiali: " + cs.getKmInitiali());
            lblUltimaRevizie.setText("Ultima revizie: " + cs.getUltimaRevizie());
            lblExpirareGarantie.setText("Expirare garanție: " + cs.getDataExpirareGarantie());

            btnCreateCarte.setDisable(true); // EXISTĂ deja
        }
    }

    @FXML
    public void handleAddRevizie() {

        Masina masina = masinaTable.getSelectionModel().getSelectedItem();
        if (masina == null) {
            System.out.println("Selectează o mașină!");
            return;
        }

        CarteService cs =
                carteServiceDAO.getCarteServiceByMasina(masina.getIdMasina());

        if (cs == null) {
            System.out.println("Mașina nu are carte service!");
            return;
        }

        TextInputDialog kmDialog = new TextInputDialog();
        kmDialog.setHeaderText("Kilometri la revizie");
        Optional<String> kmRes = kmDialog.showAndWait();
        if (kmRes.isEmpty()) return;

        int km = Integer.parseInt(kmRes.get());

        TextInputDialog descDialog = new TextInputDialog();
        descDialog.setHeaderText("Descriere revizie");
        Optional<String> descRes = descDialog.showAndWait();
        if (descRes.isEmpty()) return;

        TextInputDialog costDialog = new TextInputDialog();
        costDialog.setHeaderText("Cost revizie");
        Optional<String> costRes = costDialog.showAndWait();
        if (costRes.isEmpty()) return;

        double cost = Double.parseDouble(costRes.get());

        int kmMinim = cs.getKmInitiali();

        int maxRevizie = revizieDAO.getMaxKmByMasina(masina.getIdMasina());
        if (maxRevizie > kmMinim) {
            kmMinim = maxRevizie;
        }

        if (km <= kmMinim) {
            System.out.println("KM prea mici! Ultimul KM este: " + kmMinim);
            return;
        }

        revizieDAO.addRevizie(
                masina.getIdMasina(),
                km,
                descRes.get(),
                cost
        );
        carteServiceDAO.updateUltimaRevizieByMasina(masina.getIdMasina());


        revizieTable.setItems(
                FXCollections.observableArrayList(
                        revizieDAO.getReviziiByMasina(masina.getIdMasina())
                )
        );
        loadCarteServiceForMasina(masina.getIdMasina());

        updateCostTotalRevizii(masina);


    }

    @FXML
    public void handleDeleteRevizie() {

        Revizie rev = revizieTable.getSelectionModel().getSelectedItem();
        if (rev == null) {
            System.out.println("Selectează o revizie!");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmare ștergere");
        alert.setHeaderText("Ștergere revizie");
        alert.setContentText(
                "Ești sigur că vrei să ștergi revizia din data:\n" +
                        rev.getDataRevizie() + " ?"
        );

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isEmpty() || result.get() != ButtonType.OK) {
            return;
        }

        revizieDAO.deleteRevizie(rev.getIdRevizie());

        Masina masina = masinaTable.getSelectionModel().getSelectedItem();
        if (masina != null) {
            revizieTable.setItems(
                    FXCollections.observableArrayList(
                            revizieDAO.getReviziiByMasina(masina.getIdMasina())
                    )
            );
            updateCostTotalRevizii(masina);
        }
    }


    @FXML
    public void handleUpdateRevizie() {

        Revizie rev = revizieTable.getSelectionModel().getSelectedItem();
        if (rev == null) {
            System.out.println("Selectează o revizie!");
            return;
        }

        TextInputDialog kmDialog = new TextInputDialog(
                String.valueOf(rev.getKm())
        );
        kmDialog.setHeaderText("KM nou");
        int km = Integer.parseInt(kmDialog.showAndWait().orElse("0"));

        TextInputDialog descDialog = new TextInputDialog(rev.getDescriere());
        descDialog.setHeaderText("Descriere");
        String desc = descDialog.showAndWait().orElse("");

        TextInputDialog costDialog = new TextInputDialog(
                String.valueOf(rev.getCost())
        );
        costDialog.setHeaderText("Cost");
        double cost = Double.parseDouble(costDialog.showAndWait().orElse("0"));

        revizieDAO.updateRevizie(
                rev.getIdRevizie(),
                km,
                desc,
                cost
        );


        Masina masina = masinaTable.getSelectionModel().getSelectedItem();
        revizieTable.setItems(
                FXCollections.observableArrayList(
                        revizieDAO.getReviziiByMasina(masina.getIdMasina())
                )
        );
        carteServiceDAO.updateUltimaRevizieByMasina(masina.getIdMasina());
        loadCarteServiceForMasina(masina.getIdMasina());

        updateCostTotalRevizii(masina);

    }

    private void updateCostTotalRevizii(Masina masina) {

        if (masina == null) {
            lblCostTotalRevizii.setText("0.00 lei");
            return;
        }

        double total = 0;

        for (Revizie r : revizieDAO.getReviziiByMasina(masina.getIdMasina())) {
            total += r.getCost();
        }

        lblCostTotalRevizii.setText(String.format("%.2f lei", total));
    }

    @FXML
    public void handleAddAngajat() {
        double salariu;

        try {
            salariu = Double.parseDouble(tfAngSalariu.getText());
        } catch (NumberFormatException e) {
            System.out.println("Salariu invalid!");
            return;
        }

        angajatDAO.addAngajat(
                tfAngNume.getText(),
                tfAngPrenume.getText(),
                tfAngFunctie.getText(),
                tfAngEmail.getText(),
                salariu
        );


        angajatTable.setItems(
                FXCollections.observableArrayList(angajatDAO.getAllAngajati())
        );
    }

    @FXML
    public void handleUpdateAngajat() {
        Angajat a = angajatTable.getSelectionModel().getSelectedItem();
        if (a == null) return;

        double salariu;

        try {
            salariu = Double.parseDouble(tfAngSalariu.getText());
        } catch (NumberFormatException e) {
            System.out.println("Salariu invalid!");
            return;
        }


        angajatDAO.updateAngajat(
                a.getIdAngajat(),
                tfAngNume.getText(),
                tfAngPrenume.getText(),
                tfAngFunctie.getText(),
                tfAngEmail.getText(),
                salariu
        );

        angajatTable.setItems(
                FXCollections.observableArrayList(angajatDAO.getAllAngajati())
        );
    }

    @FXML
    public void handleDeleteAngajat() {
        Angajat a = angajatTable.getSelectionModel().getSelectedItem();
        if (a == null) {
            System.out.println("Selectează un angajat!");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmare ștergere");
        alert.setHeaderText("Ștergere angajat");
        alert.setContentText(
                "Ești sigur că vrei să ștergi angajatul:\n" +
                        a.getNume() + " " + a.getPrenume() + " ?"
        );

        if (alert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        angajatDAO.deleteAngajat(a.getIdAngajat());

        angajatTable.setItems(
                FXCollections.observableArrayList(angajatDAO.getAllAngajati())
        );
    }





    private void clearCarteServiceUI() {
        lblCarteId.setText("ID: -");
        lblKmInitiali.setText("KM inițiali: -");
        lblUltimaRevizie.setText("Ultima revizie: -");
        lblExpirareGarantie.setText("Expirare garanție: -");
        btnCreateCarte.setDisable(false);
    }


    private void clearMasinaFields() {
        tfMarca.clear();
        tfModel.clear();
        tfAn.clear();
        tfPret.clear();
    }

    @FXML
    public void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("login.fxml")
            );

            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) lblUser.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAddVanzare() {

        if (!currentUser.isAdmin()) {
            showAlert("Nu ai permisiuni pentru vânzări!");
            return;
        }

        Client c = cbClient.getValue();
        Masina m = cbMasina.getValue();
        Angajat a = cbAngajat.getValue();

        if (c == null || m == null || a == null) {
            showAlert("Completează toate câmpurile!");
            return;
        }

        double pret;
        try {
            pret = Double.parseDouble(tfPretFinal.getText());
        } catch (Exception e) {
            showAlert("Preț invalid!");
            return;
        }

        vanzareDAO.addVanzare(
                c.getIdClient(),
                m.getIdMasina(),
                a.getIdAngajat(),
                pret
        );

        cbMasina.setItems(FXCollections.observableArrayList(
                masinaDAO.getMasiniDisponibile()
        ));

        vanzareTable.setItems(FXCollections.observableArrayList(
                vanzareDAO.getAllVanzari()
        ));

        tfPretFinal.clear();
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(msg);
        alert.showAndWait();
    }


    private void clearFields() {
        tfNume.clear();
        tfPrenume.clear();
        tfTelefon.clear();
        tfEmail.clear();
    }
}

package controller;

import app.SceneLoader;
import data.CostDAO;
import data.EventDAO;
import data.PersonDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.LocalTimeStringConverter;
import model.*;

import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class AddEditSingleEvent_Controller extends SceneLoader implements Initializable {


    @FXML
    private Button btnInfo;

    @FXML
    private Button btnSettings;

    @FXML
    private ImageView imageLogo;

    @FXML
    private Label lbTitle;

    @FXML
    private DatePicker dpDateEvent;

    @FXML
    private TextField tfNameEvent;

    @FXML
    private TextArea taDescription;

    @FXML
    private ComboBox<Client> comboClientEvent;

    @FXML
    private ComboBox<Employee> comboEmployeeEvent;

    @FXML
    private TextField tfStartEvent;

    @FXML
    private TextField tfEndEvent;

    @FXML
    private ComboBox<Double> comboHourlyRate;

    @FXML
    private ToggleButton tglBtnHourlyRateBrutto;

    @FXML
    private ToggleButton tglBtnHourlyRateNetto;

    @FXML
    private ComboBox comboRideCostRate;

    @FXML
    private TextField tfRideCostsKm;

    @FXML
    private ToggleButton tglBtnRideCostBrutto;

    @FXML
    private ToggleButton tglBtnRideCostNetto;

    @FXML
    private TextField tfNameOtherCost;

    @FXML
    private TextField tfValueOtherCost;

    @FXML
    private ComboBox<Integer> comboTaxesOtherCost;

    @FXML
    private Button btnAddOtherCost;

    @FXML
    private Button btnDeleteOtherCost;

    @FXML
    private ListView<Costs> lvOtherCosts;

    @FXML
    private Label lbMessage;

    @FXML
    private Button btnCancelEvent;

    @FXML
    private Button btnOkEvent;

        private Object selectedItem;
        private Event editableEvent = null;
        private EventProtocol editableEventProtocol = null;
        private int errorCounter = 0;

        public Event getEditableEvent() {
            return editableEvent;
        }
        public EventProtocol getEditableEventProtocol() {
        return editableEventProtocol;
    }

        public void setEditableEvent(Event editableEvent, EventProtocol editableEventProtocol) {
            this.editableEvent = editableEvent;
            this.editableEventProtocol = editableEventProtocol;
            comboClientEvent.getItems().setAll(PersonDAO.getInstance().getClients());
            comboEmployeeEvent.getItems().setAll(PersonDAO.getInstance().getEmployees());
            comboHourlyRate.getItems().setAll(Settings.getInstance().getHourlyRates());
            if (editableEvent != null) {
                dpDateEvent.setValue(editableEvent.getDate());
                tfNameEvent.setText(editableEvent.getName());
                taDescription.setText(editableEvent.getNote());
            }
            if (editableEventProtocol != null){
                comboClientEvent.getSelectionModel().select(editableEventProtocol.getClient());
                comboEmployeeEvent.getSelectionModel().select(editableEventProtocol.getEmployee());
                tfStartEvent.setText(""+editableEventProtocol.getStartTime());
                tfEndEvent.setText(""+editableEventProtocol.getEndTime());
                comboHourlyRate.getSelectionModel().select(editableEventProtocol.getHourlyRate());
                tfRideCostsKm.setText(""+editableEventProtocol.getKm());
                lvOtherCosts.setItems(CostDAO.getInstance().getCostsByEventProtocol(editableEventProtocol));
            }
        }

        @FXML
        void btnCancelEvent_Clicked(ActionEvent event) {
            showScene("EventList");
        }

        @FXML
        void btnInfo_Clicked(ActionEvent event) {

        }

        @FXML
        void btnOkEvent_Clicked(ActionEvent event) throws SQLException {
            Event eventToAdd = new Event();
            EventProtocol eventProtocolToAdd = new EventProtocol();
            errorCounter = 0;

            try {
                LocalDateStringConverter ldsc = new LocalDateStringConverter();
                LocalDate date = ldsc.fromString(dpDateEvent.getEditor().getText());
                eventToAdd.setDate(date);
                if (date.getMonthValue() < 10){
                    eventProtocolToAdd.setYear_month("0" + date.getMonthValue() + "/" + date.getYear());
                } else {
                    eventProtocolToAdd.setYear_month(date.getMonthValue() + "/" + date.getYear());
                }

                dpDateEvent.setStyle(null);
            } catch (Exception e) {
                dpDateEvent.setStyle("-FX-Border-Color: red");
                errorCounter++;
                lbMessage.setText("Es sind " + errorCounter + " Fehler aufgetreten!");
            }

            if (!tfCheck(tfNameEvent, "^.+$")) {
                eventToAdd.setName(tfNameEvent.getText());
            }

            if (!taCheck(taDescription, "^.+$")) {
                eventToAdd.setNote(taDescription.getText());
            }

            eventToAdd.setIsGroup(false);
            eventProtocolToAdd.setClient(comboClientEvent.getSelectionModel().getSelectedItem());
            eventProtocolToAdd.setEmployee(comboEmployeeEvent.getSelectionModel().getSelectedItem());

            if (!tfCheck(tfStartEvent, "^([0-1][0-9]|[2][0-3]):([0-5][0-9])$")){
                eventProtocolToAdd.setStartTime(LocalTime.parse(tfStartEvent.getText()));
            }

            if (!tfCheck(tfEndEvent, "^([0-1][0-9]|[2][0-3]):([0-5][0-9])$")){
                if (LocalTime.parse(tfEndEvent.getText()).isAfter(eventProtocolToAdd.getStartTime())){
                    eventProtocolToAdd.setEndTime(LocalTime.parse(tfEndEvent.getText()));
                } else {
                    lbMessage.setText(lbMessage.getText() + ", Endzeit später als Startzeit");
                    errorCounter++;
                }
            }

            eventProtocolToAdd.setHourlyRate(comboHourlyRate.getSelectionModel().getSelectedItem());

            eventProtocolToAdd.setMileage(comboRideCostRate.getSelectionModel().getSelectedIndex());

            if (!tfCheck(tfRideCostsKm, "^[1-9][0-9]*$")){
                eventProtocolToAdd.setKm(Integer.parseInt(tfRideCostsKm.getText()));
            }

            eventProtocolToAdd.setEvent(eventToAdd);

            if (editableEventProtocol != null){
                eventProtocolToAdd.setId(editableEventProtocol.getId());
            }
            if (editableEvent != null){
                eventToAdd.setId(editableEvent.getId());
            }

            for (Costs c: lvOtherCosts.getItems()) {
                c.setEventprotocol(eventProtocolToAdd);
            }

            if (errorCounter == 0 && EventDAO.getInstance().addEvent(eventToAdd) && EventDAO.getInstance().addEventProtcol(eventProtocolToAdd)) {
                if (editableEvent != null && editableEventProtocol != null) {
                    EventDAO.getInstance().deleteEvent(editableEvent);
                    EventDAO.getInstance().deleteEventProtcol(editableEventProtocol);
                }
                super.showScene("EventList");
            }
        }

        @FXML
        void btnSettings_Clicked(ActionEvent event) {
            openSettings();
        }

        private boolean tfCheck(TextField tf, String regex) {
            boolean error = true;
            if (!tf.getText().matches(regex)) {
                error = true;
                errorCounter++;
                tf.setStyle("-FX-Border-Color: red");
                lbMessage.setText("Es sind " + errorCounter + " Fehler aufgetreten!");
            } else {
                error = false;
                tf.setStyle(null);
            }
            return error;
        }

        private boolean taCheck(TextArea ta, String regex) {
            boolean error = true;
            if (!ta.getText().matches(regex)) {
                error = true;
                errorCounter++;
                ta.setStyle("-FX-Border-Color: red");
                lbMessage.setText("Es sind " + errorCounter + " Fehler aufgetreten!");
            } else {
                error = false;
                ta.setStyle(null);
            }
            return error;
        }

        private void tglBtnChange(ToggleButton currentTglBtn, ToggleButton pendantTglBtn){
            if (currentTglBtn.isPressed()){
                pendantTglBtn.setSelected(true);
                currentTglBtn.setSelected(false);
            } else {
                pendantTglBtn.setSelected(false);
                currentTglBtn.setSelected(true);
            }
        }

    @FXML
    void tglBtnHourlyRateBrutto_onAction(ActionEvent event) {
        tglBtnChange(tglBtnHourlyRateBrutto, tglBtnHourlyRateNetto);
    }

    @FXML
    void tglBtnHourlyRateNetto_onAction(ActionEvent event) {
        tglBtnChange(tglBtnHourlyRateNetto, tglBtnHourlyRateBrutto);
    }

    @FXML
    void tglBtnRideCostBrutto_onAction(ActionEvent event) {
        tglBtnChange(tglBtnRideCostBrutto, tglBtnRideCostNetto);
    }

    @FXML
    void tglBtnRideCostNetto_onAction(ActionEvent event) {
        tglBtnChange(tglBtnRideCostNetto, tglBtnRideCostBrutto);
    }

    @FXML
    void btnAddOtherCost_Clicked(ActionEvent event) {
        Costs cToAdd = new Costs();
        int counter = 0;

        if (!tfCheck(tfValueOtherCost, "^\\d{1,8}([\\.,]\\d{2})?$")){
            cToAdd.setamount(Double.parseDouble(tfValueOtherCost.getText()));
        } else {
            counter++;
        }

        cToAdd.setDescription(tfNameOtherCost.getText());

        cToAdd.setTaxrate(comboTaxesOtherCost.getSelectionModel().getSelectedItem());


        if (counter == 0) {
            lvOtherCosts.getItems().add(cToAdd);
        }
    }

    @FXML
    void btnDeleteOtherCost_Clicked(ActionEvent event) {
            try {
                CostDAO.getInstance().deleteCost((Costs) selectedItem);
            } catch (Exception e){
                e.printStackTrace();
            }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboClientEvent.getItems().setAll(PersonDAO.getInstance().getClients());
        comboEmployeeEvent.getItems().setAll(PersonDAO.getInstance().getEmployees());
        comboHourlyRate.getItems().setAll(Settings.getInstance().getHourlyRates());
        comboRideCostRate.getItems().setAll(Settings.getInstance().getRideCostRate());
        comboTaxesOtherCost.getItems().setAll(Settings.getInstance().getTaxRates());

        lvOtherCosts.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null){
                selectedItem = newSelection;
                btnDeleteOtherCost.setDisable(false);
            }
        });
    }
}

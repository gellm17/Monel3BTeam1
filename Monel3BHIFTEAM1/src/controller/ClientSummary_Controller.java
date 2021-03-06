package controller;

import app.SceneLoader;
import data.PersonDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import model.Client;
import model.Person;
import model.Privacy;
import model.Salutation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ClientSummary_Controller extends SceneLoader implements Initializable {

    @FXML
    private ImageView imgClient;

    @FXML
    private Button btnDeleteClient;

    @FXML
    private Button btnEditClient;

    @FXML
    private Tab tabBasicData;

    @FXML
    private Label lbNameClient;

    @FXML
    private Label lbSsnrClient;

    @FXML
    private Label lbBirthdateClient;

    @FXML
    private Label lbStreetClient;

    @FXML
    private Label lbPlaceClient;

    @FXML
    private Label lbPhoneClient;

    @FXML
    private Label lbEmailClient;

    @FXML
    private Tab tabInfo;

    @FXML
    private Label lbDiagnosisClient;

    @FXML
    private Label lbOccupationClient;

    @FXML
    private Label lbAllergiesClient;

    @FXML
    private Label lbOtherClient;

    @FXML
    private Tab tabPrivacy;

    @FXML
    private CheckBox cbPrivacy1Client;

    @FXML
    private CheckBox cbPrivacy4Client;

    @FXML
    private CheckBox cbPrivacy3Client;

    @FXML
    private CheckBox cbPrivacy2Client;

    @FXML
    private Tab tabEsv;

    @FXML
    private Label lbNameEsv;

    @FXML
    private Label lbBirthdateEsv;

    @FXML
    private Label lbStreetEsv;

    @FXML
    private Label lbPlaceEsv;

    @FXML
    private Label lbPhoneEsv;

    @FXML
    private Label lbEmailEsv;

    @FXML
    private Tab tabContact1;

    @FXML
    private Label lbNameContact1;

    @FXML
    private Label lbBirthdateContact1;

    @FXML
    private Label lbStreetContact1;

    @FXML
    private Label lbPlaceContact1;

    @FXML
    private Label lbPhoneContact1;

    @FXML
    private Label lbEmailContact1;

    @FXML
    private Tab tabContact2;

    @FXML
    private Label lbNameContact2;

    @FXML
    private Label lbBirthdateContact2;

    @FXML
    private Label lbStreetContact2;

    @FXML
    private Label lbPlaceContact2;

    @FXML
    private Label lbPhoneContact2;

    @FXML
    private Label lbEmailContact2;

    @FXML
    private Button btnInfo;

    @FXML
    private Button btnSettings;

    @FXML
    private Label lbTitle;

    @FXML
    private Button btnBack;


    private Client editableClient = null;

    private boolean personType = false;

    public Client getEditableClient() {
        return editableClient;
    }

    public void setEditableClient(Client editableClient) {
        this.editableClient = editableClient;
        if (editableClient != null) {
            if (!personType ||editableClient.getSsnr() != 0){
                lbSsnrClient.setText(""+editableClient.getSsnr());
            }
            lbNameClient.setText(editableClient.getSalutation() + " " + editableClient.getTitle() + " " + editableClient.getFirstName() + " " + editableClient.getLastName());
            lbBirthdateClient.setText(editableClient.getBirthDate() != null? editableClient.getBirthDate().toString() : "");
            lbStreetClient.setText(editableClient.getStreetAndNr());
            lbPlaceClient.setText(editableClient.getPlace());
            lbPhoneClient.setText(editableClient.getTelNr());
            lbEmailClient.setText(editableClient.getEmail());
            if (!personType) {
                lbNameEsv.setText("Nicht angegeben");
                lbNameContact1.setText("Nicht angegeben");
                lbNameContact2.setText("Nicht angegeben");
                if (editableClient.getEsv() != null && editableClient.getEsv().getLastName() != "") {
                    lbNameEsv.setText(editableClient.getEsv().getSalutation() + " " + editableClient.getEsv().getFirstName()+ " "+ editableClient.getEsv().getLastName());
                }
                if (editableClient.getEmergencyContact1() != null  && editableClient.getEmergencyContact1().getLastName() != "") {
                    lbNameContact1.setText(editableClient.getEmergencyContact1().getSalutation() + " " + editableClient.getEmergencyContact1().getFirstName()+ " "+ editableClient.getEmergencyContact1().getLastName());
                    if (editableClient.getEmergencyContact2() != null  && editableClient.getEmergencyContact2().getLastName() != "") {
                        lbNameContact2.setText(editableClient.getEmergencyContact2().getSalutation() + " " + editableClient.getEmergencyContact2().getFirstName()+ " "+ editableClient.getEmergencyContact2().getLastName());
                    }
                }
                lbDiagnosisClient.setText(editableClient.getDiagnose());
                lbOccupationClient.setText(editableClient.getJob());
                lbAllergiesClient.setText(editableClient.getAllergies());
                lbOtherClient.setText(editableClient.getOther());
                //PRIVACY
                Privacy privacyOfEditableClient = new Privacy();
                if (editableClient.getPrivacy() != null) {
                    privacyOfEditableClient = editableClient.getPrivacy();
                }
                cbPrivacy1Client.setSelected(privacyOfEditableClient.getPrivacies().get(0));
                cbPrivacy2Client.setSelected(privacyOfEditableClient.getPrivacies().get(1));
                cbPrivacy3Client.setSelected(privacyOfEditableClient.getPrivacies().get(2));
                cbPrivacy4Client.setSelected(privacyOfEditableClient.getPrivacies().get(3));

            } else {
                tabPrivacy.setDisable(true);
                tabInfo.setDisable(true);
                btnDeleteClient.setVisible(false);
                btnEditClient.setVisible(false);
            }
        }
    }



    @FXML
    void btnDeleteClient_Clicked(ActionEvent event) throws SQLException {
        PersonDAO.getInstance().deletePerson((Person)editableClient);
        showScene("ClientList");
    }

    @FXML
    void btnEditClient_Clicked(ActionEvent event) {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("../view/AddEditClient.fxml"));
            BorderPane root = fxml.load();
            Scene scene = new Scene(root);
            this.getPrimStage().setScene(scene);
            Screen screen = Screen.getPrimary();

            //Maximized
            Rectangle2D bounds = screen.getVisualBounds();
            this.getPrimStage().setX(bounds.getMinX());
            this.getPrimStage().setY(bounds.getMinY());
            this.getPrimStage().setWidth(bounds.getWidth());
            this.getPrimStage().setHeight(bounds.getHeight());
            this.getPrimStage().show();



            AddEditClient_Controller editController = fxml.getController();
            //Pass whatever data you want. You can have multiple method calls here
            editController.setEditableClient((Client) editableClient);


            SceneLoader loader = editController;
            loader.setPrimaryStage(this.getPrimStage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*@FXML
    void btnShowContact1_Clicked(ActionEvent event) throws IOException {
        try {
            showPerson(editableClient.getEmergencyContact1());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnShowContact2_Clicked(ActionEvent event) {
        try {
            showPerson(editableClient.getEmergencyContact2());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnShowEsv_Clicked(ActionEvent event) {
        try {
            showPerson(editableClient.getEsv());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/


    private void showPerson(Person person) throws IOException {
        this.personType = true;
       setEditableClient(new Client(person.getId(), person.getSalutation(), person.getTitle(), person.getFirstName(), person.getLastName(), person.getStreetAndNr(), person.getZipCode(), person.getPlace(), person.getTelNr(), person.getEmail(),  person.getBirthDate(),  -1, null, null, null, null, null, null, null, null));
    }


    @FXML
    void btnBack_Clicked(ActionEvent event) {
        showScene("ClientList");
    }

    @FXML
    void btnInfo_Clicked(ActionEvent event) {

    }

    @FXML
    void btnSettings_Clicked(ActionEvent event) {
        openSettings();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { this.personType = false; }
}

package app;

import data.PersonDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import model.Client;
import model.Person;
import model.Privacy;
import model.Salutation;
import org.w3c.dom.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddEditClient_Controller extends SceneLoader implements Initializable{

    @FXML
    private Button btnInfo;

    @FXML
    private Button btnSettings;

    @FXML
    private ImageView imageLogo;

    @FXML
    private Label lbTitle;

    @FXML
    private ComboBox<Salutation> comboSalutationClient;

    @FXML
    private TextField tfTitleClient;

    @FXML
    private TextField tfFirstnameClient;

    @FXML
    private TextField tfLastnameClient;

    @FXML
    private TextField tfSsnrClient;

    @FXML
    private DatePicker dpBirthdateClient;

    @FXML
    private TextField tfStreetClient;

    @FXML
    private TextField tfHousenumberClient;

    @FXML
    private TextField tfZipClient;

    @FXML
    private TextField tfPlaceClient;

    @FXML
    private CheckBox cbPrivacy1Client;

    @FXML
    private CheckBox cbPrivacy2Client;

    @FXML
    private CheckBox cbPrivacy3Client;

    @FXML
    private CheckBox cbPrivacy4Client;

    @FXML
    private TabPane tbEmergencyContactsClients;

    @FXML
    private Tab tabEsv;

    @FXML
    private ComboBox<Salutation> comboSalutationEsv;

    @FXML
    private TextField tfTitleEsv;

    @FXML
    private TextField tfFirstnameEsv;

    @FXML
    private TextField tfLastnameEsv;

    @FXML
    private TextField tfSsnrEsv;

    @FXML
    private DatePicker dpBirthdateEsv;

    @FXML
    private TextField tfStreetEsv;

    @FXML
    private TextField tfHousenumberEsv;

    @FXML
    private TextField tfZipEsv;

    @FXML
    private TextField tfPlaceEsv;

    @FXML
    private TextField tfTelNrEsv;

    @FXML
    private TextField tfEmailEsv;

    @FXML
    private Tab tabEmergencyContact1;

    @FXML
    private ComboBox<Salutation> comboSalutationContact1;

    @FXML
    private TextField tfTitleContact1;

    @FXML
    private TextField tfFirstnameContact1;

    @FXML
    private TextField tfLastnameContact1;

    @FXML
    private TextField tfSsnrContact1;

    @FXML
    private DatePicker dpBirthdateContact1;

    @FXML
    private TextField tfStreetContact1;

    @FXML
    private TextField tfHousenumberContact1;

    @FXML
    private TextField tfZipContact1;

    @FXML
    private TextField tfPlaceContact1;

    @FXML
    private TextField tfTelNrContact1;

    @FXML
    private TextField tfEmailContact1;

    @FXML
    private Tab tabEmergencyContact2;

    @FXML
    private ComboBox<Salutation> comboSalutationContact2;

    @FXML
    private TextField tfTitleContact2;

    @FXML
    private TextField tfFirstnameContact2;

    @FXML
    private TextField tfLastnameContact2;

    @FXML
    private TextField tfSsnrContact2;

    @FXML
    private DatePicker dpBirthdateContact2;

    @FXML
    private TextField tfStreetContact2;

    @FXML
    private TextField tfHousenumberContact2;

    @FXML
    private TextField tfZipContact2;

    @FXML
    private TextField tfPlaceContact2;

    @FXML
    private TextField tfTelNrContact2;

    @FXML
    private TextField tfEmailContact2;

    @FXML
    private TextField tfTelNrClient;

    @FXML
    private TextField tfEmailClient;

    @FXML
    private TextArea taDiagnoseClient;

    @FXML
    private TextField tfJobClient;

    @FXML
    private TextArea taAllergiesClient;

    @FXML
    private TextField tfOtherClient;

    @FXML
    private TextField tfIbanClient;

    @FXML
    private TextField tfBicClient;

    @FXML
    private Button btnDeleteImageClients;

    @FXML
    private Button btnSelectImageClients;

    @FXML
    private Button btnOkClient;

    @FXML
    private Button btnCancelClient;


    private Client editableClient = null;
    private ArrayList<Boolean> errors = new ArrayList<Boolean>();

    public Client getEditableClient() {
        return editableClient;
    }

    public void setEditableClient(Client editableClient) {
        this.editableClient = editableClient;
        comboSalutationClient.getItems().setAll(Salutation.values());
        comboSalutationContact1.getItems().setAll(Salutation.values());
        comboSalutationContact2.getItems().setAll(Salutation.values());
        comboSalutationEsv.getItems().setAll(Salutation.values());
        if (editableClient != null) {
            comboSalutationClient.getSelectionModel().select(editableClient.getSalutation());
            tfTitleClient.setText(editableClient.getTitle());
            tfFirstnameClient.setText(editableClient.getFirstName());
            tfLastnameClient.setText(editableClient.getLastName());
            tfSsnrClient.setText(""+editableClient.getSsnr());
            dpBirthdateClient.setValue(editableClient.getBirthDate());
            //ADDRESS
            try {
                tfStreetClient.setText(editableClient.getStreetAndNr().split(" ")[0]);
                tfHousenumberClient.setText(editableClient.getStreetAndNr().split(" ")[1]);
            } catch (Exception ex) {}
            tfZipClient.setText(""+editableClient.getZipCode());
            tfPlaceClient.setText(editableClient.getPlace());
            //PRIVACY
            Privacy privacyOfEditableClient = new Privacy();
            if (editableClient.getPrivacy() != null) {
                privacyOfEditableClient = editableClient.getPrivacy();
            }
            cbPrivacy1Client.setSelected(privacyOfEditableClient.getPrivacies().get(0));
            cbPrivacy2Client.setSelected(privacyOfEditableClient.getPrivacies().get(1));
            cbPrivacy3Client.setSelected(privacyOfEditableClient.getPrivacies().get(2));
            cbPrivacy4Client.setSelected(privacyOfEditableClient.getPrivacies().get(3));
            //CONTACT
            tfTelNrClient.setText(editableClient.getTelNr());
            tfEmailClient.setText(editableClient.getEmail());
            //INFO
            taDiagnoseClient.setText(editableClient.getDiagnose());
            tfJobClient.setText(editableClient.getJob());
            taAllergiesClient.setText(editableClient.getAllergies());
            //BANK
            tfBicClient.setText(editableClient.getBic());
            tfIbanClient.setText(editableClient.getIban());
        }
    }

    @FXML
    void btnCancelClient_Clicked(ActionEvent event) {
        showScene("ClientList");
    }

    @FXML
    void btnDeleteImageClients_Clicked(ActionEvent event) {

    }

    @FXML
    void btnInfo_Clicked(ActionEvent event) {

    }

    @FXML
    void btnOkClient_Clicked(ActionEvent event) {
        Client clientToAdd = new Client();
        /*if (comboSalutationClient.getSelectionModel().getSelectedItem() != Salutation.Herr && comboSalutationClient.getSelectionModel().getSelectedItem() != Salutation.Frau && comboSalutationClient.getSelectionModel().getSelectedItem() != Salutation.Firma && comboSalutationClient.getSelectionModel().getSelectedItem() != Salutation.Sonstige) {
            comboSalutationClient.setStyle("-FX-Border-Color: red");
            errors.add(true);
        } else {
            comboSalutationClient.setStyle(null);
            errors.add(false);
        }*/

        errors.add(tfCheck(tfFirstnameClient, "^\\D+$"));
        errors.add(tfCheck(tfLastnameClient, "^\\D+$"));

        if (!errors.contains(true)){
            clientToAdd = new Client(   comboSalutationClient.getSelectionModel().getSelectedItem(),
                    tfFirstnameClient.getText(),
                    tfLastnameClient.getText()
            );}

        setAllPersonFields(clientToAdd, tfTitleClient, tfFirstnameClient, tfLastnameClient, tfSsnrClient, tfStreetClient, tfHousenumberClient, tfZipClient, tfPlaceClient, tfTelNrClient, tfEmailClient);
        /*if (!tfCheck(tfTitleClient, "^(\\D+)?$")) {
            clientToAdd.setTitle(tfTitleClient.getText());
        } else {
            errors.add(true);
        }

        if (!tfCheck(tfSsnrClient, "^([1-9][0-9]{3})?$")) {
            clientToAdd.setTitle(tfTitleClient.getText());
        } else {
            errors.add(true);
        }


        /*if (dpBirthdateClient.getValue() == null) {
            dpBirthdateClient.setStyle("-FX-Border-Color: red");
            errors.add(true);
        } else {
            dpBirthdateClient.setStyle(null);
            errors.add(false);
        }*/
        //errors.add(tfCheck(tfSsnrClient));

        /*if (!tfCheck(tfStreetClient, "^(\\D+)?$") && tfCheck(tfHousenumberClient, "^([1-9][0-9]{0-3})?$")) {
            clientToAdd.setStreetAndNr(tfStreetClient.getText() + " " + tfHousenumberClient.getText());
        } else {
            errors.add(true);
        }

        if (!tfCheck(tfZipClient, "^([1-9][0-9]{3})?$")) {
            clientToAdd.setZipCode(Integer.parseInt(tfZipClient.getText()));
        } else {
            errors.add(true);
        }

        if (!tfCheck(tfPlaceClient, "^(\\D+)?$")){
            clientToAdd.setPlace(tfPlaceClient.getText());
        } else {
            errors.add(true);
        }*/
        //TODO BIRTHDAY
        clientToAdd.setPrivacy(new Privacy(new ArrayList<Boolean>(){{add(cbPrivacy1Client.isSelected()); add(cbPrivacy2Client.isSelected()); add(cbPrivacy3Client.isSelected()); add(cbPrivacy4Client.isSelected());}}));

        Person esv = new Person();
        setAllPersonFields(esv, tfTitleEsv, tfFirstnameEsv, tfLastnameEsv, tfSsnrEsv, tfStreetEsv, tfHousenumberEsv, tfZipEsv, tfPlaceEsv, tfTelNrEsv, tfEmailEsv);
        clientToAdd.setEsv(esv);

        Person ec1 = new Person();
        setAllPersonFields(ec1, tfTitleContact1, tfFirstnameContact1, tfLastnameContact1, tfSsnrContact1, tfStreetContact1, tfHousenumberContact1, tfZipContact1, tfPlaceContact1, tfTelNrContact1, tfEmailContact1);
        clientToAdd.setEmergencyContact1(ec1);

        Person ec2 = new Person();
        setAllPersonFields(ec2, tfTitleContact2, tfFirstnameContact2, tfLastnameContact2, tfSsnrContact2, tfStreetContact2, tfHousenumberContact2, tfZipContact2, tfPlaceContact2, tfTelNrContact2, tfEmailContact2);
        clientToAdd.setEmergencyContact2(ec2);

        if (!tfCheck(tfSsnrClient, "^([1-9][0-9]{3})?$")) {
                clientToAdd.setSsnr(Integer.parseInt(tfSsnrClient.getText()));
        } else {
            errors.add(true);
        }

        if (!taCheck(taAllergiesClient,"^(\\D+)?$")) {
            clientToAdd.setAllergies(taAllergiesClient.getText());
        } else {
            errors.add(true);
        }

        if (!tfCheck(tfJobClient,"^(\\D+)?$")) {
            clientToAdd.setJob(tfJobClient.getText());
        } else {
            errors.add(true);
        }

        if (!taCheck(taDiagnoseClient,"^(\\D+)?$")) {
            clientToAdd.setDiagnose(taDiagnoseClient.getText());
        } else {
            errors.add(true);
        }


        if (!tfCheck(tfIbanClient, "^([A-Z]{2}[0-9]{2}(?:[ ]?[0-9]{4}){4}(?:[ ]?[0-9]{1,2})?)?$")) {
            clientToAdd.setIban(tfIbanClient.getText());
        } else {
            errors.add(true);
        }

        if (!tfCheck(tfBicClient, "^([a-zA-Z]{4}[a-zA-Z]{2}[a-zA-Z0-9]{2}([a-zA-Z0-9]{3})?)?$")){
            clientToAdd.setBic(tfBicClient.getText());
        } else {
            errors.add(true);
        }


        if(!errors.contains(true) && PersonDAO.getInstance().addPerson(clientToAdd)) {
            if (editableClient != null) { PersonDAO.getInstance().deletePerson(editableClient);
            }
            super.showScene("ClientList");
        }

    }

    private void setAllPersonFields(Person p, TextField tfTitle, TextField tfFirstname, TextField tfLastname, TextField tfSsnr, TextField tfStreet, TextField tfHousenumber, TextField tfZip, TextField tfPlace, TextField tfTelNr, TextField tfEmail) {
        if (!tfCheck(tfTitle, "^(\\D+)?$")) {
            p.setTitle(tfTitle.getText());
        } else {
            errors.add(true);
        }
        if (!(p instanceof Client)) {
            if (!tfCheck(tfFirstname, "^(\\D+)?$")) {
                p.setFirstName(tfFirstname.getText());
            } else {
                errors.add(true);
            }

            if(!tfCheck(tfLastname, "^(\\D+)?$")){
                p.setLastName(tfLastname.getText());
            } else {
                errors.add(true);
            }
        }




        if (!tfCheck(tfStreet, "^(\\D+)?$") && !tfCheck(tfHousenumber, "^([1-9][0-9]*)?$")) {
            p.setStreetAndNr(tfStreet.getText() + " " + tfHousenumber.getText());
        } else {
            errors.add(true);
        }

        if (!tfCheck(tfZip, "^([1-9][0-9]{3})?$")) {
            try {
                p.setZipCode(Integer.parseInt(tfZip.getText()));
            } catch (Exception e) { }

        } else {
            errors.add(true);
        }

        if (!tfCheck(tfPlace, "^(\\D+)?$")){
            p.setPlace(tfPlace.getText());
        } else {
            errors.add(true);
        }

        if (!tfCheck(tfTelNr, "^([0-9]*)?$")) {
            p.setTelNr(tfTelNr.getText());
        } else {
            errors.add(true);
        }

        if (!tfCheck(tfEmail, "^([a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+)?$")) {
            p.setEmail(tfEmail.getText());
        } else {
            errors.add(true);
        }
    }

    @FXML
    void btnSelectImageClient_Clicked(ActionEvent event) {

    }


    @FXML
    void btnSettings_Clicked(ActionEvent event) {

    }

    private boolean tfCheck(TextField tf, String regex){
        boolean error = true;
            if (!tf.getText().matches(regex)) {
                error = true;
                tf.setStyle("-FX-Border-Color: red");
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
            ta.setStyle("-FX-Border-Color: red");
        } else {
            error = false;
            ta.setStyle(null);
        }
        return error;
    }

    @FXML
    void btnDeleteImageClient_Clicked(ActionEvent event) {

    }

    private void addFocusedProperty(TextField tf, String regex){
        tf.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                errors.add(tfCheck(tf, regex));
            }
        });
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboSalutationClient.getItems().setAll(Salutation.values());
        comboSalutationContact1.getItems().setAll(Salutation.values());
        comboSalutationContact2.getItems().setAll(Salutation.values());
        comboSalutationEsv.getItems().setAll(Salutation.values());

        comboSalutationClient.getSelectionModel().select(0);
        comboSalutationContact1.getSelectionModel().select(0);
        comboSalutationContact2.getSelectionModel().select(0);
        comboSalutationEsv.getSelectionModel().select(0);

        addFocusedProperty(tfSsnrClient, "^([1-9][0-9]{3})?$");
        addFocusedProperty(tfZipClient, "^([1-9][0-9]{3})?$");
        addFocusedProperty(tfEmailClient, "^([a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+)?$");
        addFocusedProperty(tfIbanClient, "^([A-Z]{2}[0-9]{2}(?:[ ]?[0-9]{4}){4}(?:[ ]?[0-9]{1,2})?)?$");

    }
}

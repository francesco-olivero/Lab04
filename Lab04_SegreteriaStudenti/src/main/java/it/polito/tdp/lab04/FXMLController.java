package it.polito.tdp.lab04;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;
	private boolean studenteTrovato;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> tendina;

    @FXML
    private Button btnCercaIscrittiCorsi;

    @FXML
    private TextField txtMatricola;

    @FXML
    private Button btnV;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCognome;

    @FXML
    private Button btnCercaCorsi;

    @FXML
    private Button btnIscrivi;

    @FXML
    private TextArea txtOutput;

    @FXML
    private Button btnReset;

    @FXML
    void pushCercaCorsi(ActionEvent event) {
    	this.txtOutput.clear();
    	if (!this.studenteTrovato) {
    		this.txtOutput.setText("non è possibile trovare i corsi perché lo studente non esiste");
    		return;
    	}
    	String Smatricola = this.txtMatricola.getText();
    	Integer Imatricola = Integer.parseInt(Smatricola);
    	for (Corso ccc: this.model.getCorsiStudente(Imatricola)) {
    		this.txtOutput.appendText(ccc.toString() + "\n");
    	}

    }

    @FXML
    void pushCercaIscrittiCorsi(ActionEvent event) {
    	this.txtOutput.clear();
    	String nomeCorso = this.tendina.getValue();
    	if (nomeCorso.equals("")) {
    		this.txtOutput.setText("inserire un corso non vuoto");
    		return;
    	}
    	Corso c = this.model.getCorsoFromNome(nomeCorso);
    	List<Studente> st = this.model.getStudentiIscrittiAlCorso(c);
    	for (Studente ss: st) {
    		this.txtOutput.appendText(ss.toString()+"\n");
    	}

    }

    @FXML
    void pushIscrivi(ActionEvent event) {
    	if (!this.studenteTrovato) {
    		this.txtOutput.setText("non è possibile trovare i corsi perché lo studente non esiste");
    		return;
    	}
    	String Smatricola = this.txtMatricola.getText();
    	Integer Imatricola = Integer.parseInt(Smatricola);
    	if (this.model.inscriviStudenteACorso(Imatricola, this.tendina.getValue())) {
    		this.txtOutput.setText("lo studente risulta già iscritto al corso selezionato");
    		return;
    	}
    	this.txtOutput.setText("lo studente non risulta iscritto al corso selezionato");
    }

    @FXML
    void pushReset(ActionEvent event) {
    	this.txtCognome.clear();
    	this.txtNome.clear();
    	this.txtMatricola.clear();
    	this.txtOutput.clear();

    }

    @FXML
    void pushV(ActionEvent event) {
    	this.studenteTrovato = false;
    	this.txtCognome.clear();
    	this.txtNome.clear();
    	this.txtOutput.clear();
    	Integer matricola;
    	try {
    		matricola = Integer.parseInt(this.txtMatricola.getText());
    	} catch (NumberFormatException nfe) {
    		this.txtOutput.setText("devi inserire un numero per la matricola");
    		return;
    	}
    	Studente s = this.model.cercaStudente(matricola);
    	if (s!=null) {
    		this.studenteTrovato = true;
    		this.txtCognome.setText(s.getCognome());
    		this.txtNome.setText(s.getNome());
    	}else {
    		this.txtOutput.setText("studente non trovato");
    		return;
    	}

    }
    
    public void setModel (Model model) {
    	this.model = model;
    	List<String> corsiTendina =this.model.getTuttiICorsi();
    	corsiTendina.add("");
    	this.tendina.getItems().addAll(corsiTendina);
    	this.txtOutput.setStyle("-fx-font-family: monospace");
    }

    @FXML
    void initialize() {
        assert tendina != null : "fx:id=\"tendina\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaIscrittiCorsi != null : "fx:id=\"btnCercaIscrittiCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnV != null : "fx:id=\"btnV\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaCorsi != null : "fx:id=\"btnCercaCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtOutput != null : "fx:id=\"txtOutput\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'Scene.fxml'.";

    }
}

package it.polito.tdp.food;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

//controller turno A --> switchare al branch master_turnoB per turno B

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtPorzioni;

    @FXML
    private TextField txtK;

    @FXML
    private Button btnAnalisi;

    @FXML
    private Button btnCalorie;

    @FXML
    private Button btnSimula;

    @FXML
    private ComboBox<Food> boxFood;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCalorie(ActionEvent event) {

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	boxFood.getItems().clear();
    	txtResult.clear();
    	int porzioni;
    	try {
    		porzioni=Integer.parseInt(txtPorzioni.getText());
    	} catch (NumberFormatException e) {
    		txtResult.appendText("Inserire numero valido");
    		return; 
    	}
    	this.model.creaGrafo(porzioni);
    	this.boxFood.getItems().addAll(this.model.getFood());
    	txtResult.appendText(String.format("Grafo creato con %d vertici, %d archi", this.model.vertici(), this.model.archi()));
    	

    }

    @FXML
    void doSimula(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert txtPorzioni != null : "fx:id=\"txtPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCalorie != null : "fx:id=\"btnCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxFood != null : "fx:id=\"boxFood\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		
	}
}

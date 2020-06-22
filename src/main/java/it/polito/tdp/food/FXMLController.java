package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.FoodCalories;
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
    	txtResult.clear();
    	Food f= boxFood.getValue();
    	if (f==null) {
    		txtResult.appendText("ERRORE");
    		return ; 
    	}
    	else {
    	List<FoodCalories> lista= this.model.elencoCibiConnessi(f);
    	for (int i=0; i<5 && i<lista.size(); i++) {
    		txtResult.appendText(String.format("%s %f\n", lista.get(i).getFood().getDisplay_name(), lista.get(i).getCalories()));
    	}
 
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	String porzioni= txtPorzioni.getText();
    	int portions;
    	try {
    		portions=Integer.parseInt(porzioni);
    		List<Food> cibi= model.getFoods(portions);
    		boxFood.getItems().clear();
    		boxFood.getItems().addAll(cibi);
    	} catch (NumberFormatException ex) {
    		txtResult.appendText("Inserire numero valido");	
    		return ;
    	}
    	this.model.getFoods(portions);
    	txtResult.appendText(String.format("%d vertici %d archi", this.model.vertici(), this.model.archi()));
    	
  
    }

    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();
    	Food f= boxFood.getValue();
    	if (f==null) {
    		txtResult.appendText("ERRORE");
    		return ; 
    	}
    	int k;
    	try {
    		k=Integer.parseInt(txtK.getText());	
    	} catch (NumberFormatException e) {
    		txtResult.appendText("Inserire numero valido");
    		return;
    	}
    	String messaggio= this.model.simula(f, k);
    	txtResult.appendText(messaggio);
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

package org.unibl.etf.gui.plants.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dto.PlantMaintanceActivity;
import org.unibl.etf.gui.util.DisplayUtil;
import org.unibl.etf.gui.view.base.BaseController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class AddNewMaintanceActivityDialogController extends BaseController {

    @FXML
    private TextField txtActivityName;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnAddNew;

    @FXML
    void addNewAction(ActionEvent event) {
    	String activity = txtActivityName.getText();
    	if(DAOFactory.getInstance().getPlantMaintanceActivityDAO().getByActivity(activity).size() != 0) {
    		DisplayUtil.showErrorDialog("Aktivnost već postoji!");
    		return;
    	}
    	PlantMaintanceActivity item = new PlantMaintanceActivity();
    	item.setActivity(activity);
    	item.setPlantMaintanceActivityId(null);
    	DAOFactory.getInstance().getPlantMaintanceActivityDAO().insert(item);
    	result = ButtonType.OK;
    	DisplayUtil.showWarningDialog("Uspješno dodavanje.");
    	this.primaryStage.close();
    }

    @FXML
    void cancelAction(ActionEvent event) {
    	result = ButtonType.CANCEL;
    	this.primaryStage.close();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnAddNew.disableProperty().bind(txtActivityName.textProperty().isEmpty());
		result = ButtonType.CANCEL;
	}
	
	public ButtonType getResult() {
		return result;
	}
	
	private ButtonType result;

}

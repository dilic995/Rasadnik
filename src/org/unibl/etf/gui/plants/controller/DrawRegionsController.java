package org.unibl.etf.gui.plants.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dto.Region;
import org.unibl.etf.gui.util.DisplayUtil;
import org.unibl.etf.gui.view.base.BaseController;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;

public class DrawRegionsController extends BaseController {
	@FXML
	private AnchorPane parentPane;
	@FXML
	private Group elements;
	@FXML
	private Button btnSave;
	@FXML
	private Button btnSetSelectTool;
	@FXML
	private Button btnSetPolygonTool;
	@FXML
	private ImageView imgPhoto;
	@FXML
	private Label lblRegion;
	@FXML
	private Label lblName;
	@FXML
	private Label lblPlantsNum;
	
	private CanvasEditor canvasEditor;

	@FXML
	public void setSelectTool(ActionEvent event) {
		canvasEditor.invalidate();
		canvasEditor = new SelectTool(regionsMap, outlinesMap, this);
	}

	@FXML
	public void setPolygonTool(ActionEvent event) {
		canvasEditor.invalidate();
		canvasEditor = new PolygonTool(regionsMap, outlinesMap, elements);
	}

	// Event Listener on AnchorPane[#parentPane].onMouseClicked
	@FXML
	public void click(MouseEvent event) {
		canvasEditor.click(event);
	}

	@FXML
	public void save(Event event) {
	}

	// Event Listener on AnchorPane[#parentPane].onMousePressed
	@FXML
	public void press(MouseEvent event) {
	}
	@FXML
	public void aPressed(MouseEvent event) {
		canvasEditor.startDrawing(event);
	}
	// Event Listener on AnchorPane[#parentPane].onMouseReleased
	@FXML
	public void release(MouseEvent event) {
	}
	@FXML
	public void aReleased(MouseEvent event) {
		canvasEditor.finishDrawing(event);
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		regionsMap = new HashMap<Polygon, Region>();
		outlinesMap = new HashMap<Polygon, Polyline>();
		List<Region> regions = DAOFactory.getInstance().getRegionDAO().selectAll();
		for (Region r : regions) {
			Polygon p = new Polygon();
			p.getPoints().addAll(r.getCoords());
			Polyline pl = new Polyline();
			pl.getPoints().addAll(r.getCoords());
			pl.getPoints().add(r.getCoords()[0]);
			pl.getPoints().add(r.getCoords()[1]);
			// TODO eliminisati HC
			// p.setFill(new Color(0.9, 0.9, 0.9, 1));
			p.getStyleClass().add("transparent-brown");
			elements.getChildren().add(pl);
			elements.getChildren().add(p);
			regionsMap.put(p, r);
			outlinesMap.put(p, pl);
		}
		canvasEditor = new SelectTool(regionsMap, outlinesMap, this);
	}

	public void displayInfo(Region region) {
		setValues(DisplayUtil.convertFromBlob(region.getBasis().getPlant().getImage()),
				"REGION " + region.getRegionId(), region.getBasis().getPlant().getScientificName() + " ("
						+ region.getBasis().getPlant().getKnownAs() + ")", 
						"Broj biljaka: " + region.getNumberOfPlants());
	}

	public void clear() {
		setValues(null, "", "", "");
	}

	private void setValues(Image image, String region, String name, String num) {
		imgPhoto.setImage(image);
		lblRegion.setText(region);
		lblName.setText(name);
		lblPlantsNum.setText(num);
	}

	private Map<Polygon, Region> regionsMap;
	private Map<Polygon, Polyline> outlinesMap;
	// TODO observer na evente
}

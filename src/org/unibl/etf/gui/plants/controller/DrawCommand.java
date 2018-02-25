package org.unibl.etf.gui.plants.controller;

import java.util.List;
import java.util.Map;

import org.unibl.etf.dto.Region;
import org.unibl.etf.dto.ReproductionCutting;
import org.unibl.etf.gui.util.DisplayUtil;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;

public class DrawCommand implements Command {

	public DrawCommand() {
	}

	public DrawCommand(Double[] coordinates, Group elements, Map<Polygon, Region> regions,
			Map<Polygon, Polyline> outlines, List<Polygon> newPolygons, Map<Polygon, ReproductionCutting> cuttings) {
		super();
		this.coordinates = new Double[8];
		for(int i=0 ; i < 8 ; i++) {
			this.coordinates[i] = coordinates[i].doubleValue();
		}
		this.elements = elements;
		this.regions = regions;
		this.outlines = outlines;
		this.newPolygons = newPolygons;
		this.cuttings = cuttings;
	}

	@Override
	public void execute() {
		polygon = new Polygon();
		Polyline pl = new Polyline();
		polygon.getPoints().addAll(coordinates);
		pl.getPoints().addAll(coordinates);
		pl.getPoints().addAll(coordinates[0], coordinates[1]);
		polygon.getStyleClass().clear();
		polygon.getStyleClass().add("transparent-brown");
		elements.getChildren().add(pl);
		elements.getChildren().add(polygon);
		Region region = new Region(null, 0, null, null, this.coordinates, false);
		ReproductionCutting cutting = new ReproductionCutting();
		if (DisplayUtil.showConfirmationDialog("Zelite li da dodate biljke u region?").equals(ButtonType.YES)) {
			FXMLLoader loader = DisplayUtil.getLoader(getClass().getClassLoader(),
					"org/unibl/etf/gui/plants/view/AddRegionView.fxml");
			AnchorPane root = DisplayUtil.getAnchorPane(loader);
			AddRegionController controller = DisplayUtil.<AddRegionController>getController(loader);
			controller.setRegion(region);
			controller.setCutting(cutting);
			DisplayUtil.switchStage(root, 300, 200, false, "Dodavanje biljke", true);
		}
		regions.put(polygon, region);
		outlines.put(polygon, pl);
		cuttings.put(polygon, cutting);
		newPolygons.add(polygon);
	}

	@Override
	public void unexecute() {
		newPolygons.remove(polygon);
		cuttings.remove(polygon);
		regions.remove(polygon);
		elements.getChildren().remove(polygon);
		elements.getChildren().remove(outlines.get(polygon));
		outlines.remove(polygon);
	}

	private Polygon polygon;
	private Double[] coordinates;
	private Group elements;
	private Map<Polygon, Region> regions;
	private Map<Polygon, Polyline> outlines;
	private Map<Polygon, ReproductionCutting> cuttings;
	private List<Polygon> newPolygons;
}

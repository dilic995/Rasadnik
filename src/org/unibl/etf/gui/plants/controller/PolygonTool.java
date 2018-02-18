package org.unibl.etf.gui.plants.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dto.Region;
import org.unibl.etf.gui.util.DisplayUtil;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;

public class PolygonTool extends CanvasEditor {

	public PolygonTool() {
	}

	public PolygonTool(Map<Polygon, Region> regions, Map<Polygon, Polyline> outlines, Group elements) {
		super(regions, outlines);
		coordinates = new Double[8];
		this.elements = elements;
		this.newPolygons = new ArrayList<Polygon>();
	}

	@Override
	public void click(MouseEvent event) {
	}

	@Override
	public void startDrawing(MouseEvent event) {
		this.coordinates[0] = this.coordinates[6] = event.getX() + 1;
		this.coordinates[1] = this.coordinates[3] = event.getY() + 1;
	}

	@Override
	public void finishDrawing(MouseEvent event) {
		this.coordinates[2] = this.coordinates[4] = event.getX() + 1;
		this.coordinates[5] = this.coordinates[7] = event.getY() + 1;
		Polygon p = new Polygon();
		Polyline pl = new Polyline();
		p.getPoints().addAll(coordinates);
		pl.getPoints().addAll(coordinates);
		pl.getPoints().addAll(coordinates[0], coordinates[1]);
		p.getStyleClass().clear();
		p.getStyleClass().add("transparent-brown");
		elements.getChildren().add(pl);
		elements.getChildren().add(p);
		Region region = new Region(null, 0, null, null, this.coordinates, false);
		if (DisplayUtil.showConfirmationDialog("Zelite li da dodate biljke u region?").equals(ButtonType.YES)) {
			FXMLLoader loader = DisplayUtil.getLoader(getClass().getClassLoader(),
					"org/unibl/etf/gui/plants/view/AddRegionView.fxml");
			AnchorPane root = DisplayUtil.getAnchorPane(loader);
			AddRegionController controller = DisplayUtil.<AddRegionController>getController(loader);
			controller.setRegion(region);
			DisplayUtil.switchStage(root, 300, 150, false, "Dodavanje biljke", true);
			System.out.println(region.getBasisId());
		}
		regions.put(p, region);
		outlines.put(p, pl);
		newPolygons.add(p);
	}

	// TODO pokusati uopstiti
	private Double[] coordinates;
	private Group elements;
	private List<Polygon> newPolygons;

	@Override
	public void invalidate() {
		if (newPolygons.size() > 0) {
			if (DisplayUtil.showConfirmationDialog("Zelite li sacuvai promjene?").equals(ButtonType.YES)) {
				for (Polygon polygon : newPolygons) {
					DAOFactory.getInstance().getRegionDAO().insert(regions.get(polygon));
				}
			} else {
				for (Polygon polygon : newPolygons) {
					elements.getChildren().remove(polygon);
					elements.getChildren().remove(outlines.get(polygon));
				}
			}
		}
	}
}

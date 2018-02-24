package org.unibl.etf.gui.plants.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dto.Region;
import org.unibl.etf.dto.ReproductionCutting;
import org.unibl.etf.gui.util.DisplayUtil;

import javafx.scene.Group;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;

public class PolygonTool extends CanvasEditor {

	public PolygonTool() {
	}

	public PolygonTool(Map<Polygon, Region> regions, Map<Polygon, Polyline> outlines, Group elements,
			Stack<Command> undoCommands, Stack<Command> redoCommands) {
		super(regions, outlines, undoCommands, redoCommands);
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
		if (!(this.coordinates[0].equals(this.coordinates[6]) && this.coordinates[1].equals(this.coordinates[7]))) {
			Command command = new DrawCommand(coordinates, elements, regions, outlines, newPolygons, cuttings);
			command.execute();
			undoCommands.push(command);
		}
	}
	private Double[] coordinates;
	private Group elements;
	private List<Polygon> newPolygons;
	private Map<Polygon, ReproductionCutting> cuttings = new HashMap<Polygon, ReproductionCutting>();

	@Override
	public void invalidate() {
		if (newPolygons.size() > 0) {
			if (DisplayUtil.showConfirmationDialog("Zelite li sacuvati promjene?").equals(ButtonType.YES)) {
				for (Polygon polygon : newPolygons) {
					DAOFactory.getInstance().getRegionDAO().insert(regions.get(polygon));
					System.out.println(cuttings.get(polygon));
					if (cuttings.get(polygon).getBasis() != null) {
								DAOFactory.getInstance().getReproductionCuttingDAO().insert(cuttings.get(polygon));
					}
				}
			} else {
				for (Polygon polygon : newPolygons) {
					elements.getChildren().remove(polygon);
					elements.getChildren().remove(outlines.get(polygon));
				}
			}
		}
		undoCommands.clear();
		redoCommands.clear();
	}

	@Override
	public void undo() {
		if (!undoCommands.isEmpty()) {
			Command command = undoCommands.pop();
			command.unexecute();
			redoCommands.push(command);
		}
	}

	@Override
	public void redo() {
		if (!redoCommands.isEmpty()) {
			Command command = redoCommands.pop();
			command.execute();
			undoCommands.push(command);
		}
	}
}

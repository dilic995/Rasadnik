package org.unibl.etf.gui.plants.controller;

import java.util.Map;

import org.unibl.etf.dto.Region;

import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
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
	}

	@Override
	public void click(MouseEvent event) {
	}

	@Override
	public void startDrawing(MouseEvent event) {
		coordinates[0] = coordinates[6] = event.getSceneX();
		coordinates[1] = coordinates[3] = event.getSceneY() - 40;
	}

	@Override
	public void finishDrawing(MouseEvent event) {
		coordinates[2] = coordinates[4] = event.getSceneX();
		coordinates[5] = coordinates[7] = event.getSceneY() - 40;
		Polygon p = new Polygon();
		Polyline pl = new Polyline();
		p.getPoints().addAll(coordinates);
		pl.getPoints().addAll(coordinates);
		pl.getPoints().addAll(coordinates[0], coordinates[1]);
		p.getStyleClass().clear();
		p.getStyleClass().add("transparent-brown");
		elements.getChildren().add(pl);
		elements.getChildren().add(p);
		Region region = new Region(rb++, 0, null, null, coordinates);
		regions.put(p, region);
		outlines.put(p, pl);
	}

	// TODO pokusati uopstiti
	private Double[] coordinates;
	private Group elements;
	private static int rb = 0;

	@Override
	public void invalidate() {
		
	}
}

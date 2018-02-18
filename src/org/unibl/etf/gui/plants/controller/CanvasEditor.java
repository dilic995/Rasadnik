package org.unibl.etf.gui.plants.controller;

import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import org.unibl.etf.dto.Region;

import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;

public abstract class CanvasEditor {

	public CanvasEditor() {

	}

	public CanvasEditor(Map<Polygon, Region> regions, Map<Polygon, Polyline> outlines, Stack<Command> undoCommands,
			Stack<Command> redoCommands) {
		for (Iterator<Polygon> it = regions.keySet().iterator(); it.hasNext();) {
			Polygon p = it.next();
			p.setOnMouseClicked(null);
		}
		this.regions = regions;
		this.outlines = outlines;
		this.undoCommands = undoCommands;
		this.redoCommands = redoCommands;
	}

	public abstract void click(MouseEvent event);

	public abstract void startDrawing(MouseEvent event);

	public abstract void finishDrawing(MouseEvent event);
	
	public abstract void undo();
	public abstract void redo();
	
	public abstract void invalidate();

	protected Map<Polygon, Region> regions;
	protected Map<Polygon, Polyline> outlines;
	protected Stack<Command> undoCommands;
	protected Stack<Command> redoCommands;
}

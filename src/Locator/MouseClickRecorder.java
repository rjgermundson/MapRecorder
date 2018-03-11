package Locator;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JViewport;

import PointModel.ImageGrid;
import PointModel.MapPoint;

/**
 * Utility class that determines what to do on click. Prompts the user 
 * for the abbreviated and full name of the location that they clicked on
 * and records it in the proper format in an external file
 * @author Riley
 *
 */
public class MouseClickRecorder implements MouseListener {
	private static final int START_LEEWAY = 50;
	private static final int PATH_LEEWAY = 20;
	private static final InputDialogBox DIALOG = new InputDialogBox("Input Names");
	private PrintWriter pathWriter;
	
	private MapPoint pathStart;
	private boolean pathing = false;
	
	private JViewport port;
	private MapImagePanel panel;
	private MapLabel label;
	
	private MapLines lines = new MapLines();
	private ImageGrid grid;

	/**
	 * Constructor for the mouse listener
	 * @param pane The scroll pane which this listens in
	 * @throws IOException If the I/O error occurs
	 */
	public MouseClickRecorder(MapScrollPane pane, File outBuildings, File outPath) throws IOException {
		PrintWriter outputBuildings = null;
		try {
			outputBuildings = new PrintWriter(outBuildings);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		DIALOG.setWriter(outputBuildings);
		
		pathWriter = null;
		try {
			pathWriter = new PrintWriter(outPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		this.port = pane.getViewport();
		this.panel = (MapImagePanel) port.getView();
		this.label = panel.getLabel();
		
		grid = new ImageGrid(panel.getImage(), 50);
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		Point viewPos = port.getViewPosition();
		double clickX = (arg0.getX() + viewPos.getX()) / panel.zoomScalar;
		double clickY = (arg0.getY() + viewPos.getY()) / panel.zoomScalar;
		MapPoint currPoint = new MapPoint(clickX, clickY);
		if (arg0.getButton() == MouseEvent.BUTTON1) {
			if (pathing) {
				currPoint = determinePoint(currPoint, PATH_LEEWAY);
				lines.associateLine(pathStart, currPoint);
				pathWriter.println("\t" + clickX + "," + clickY + ":\t");
				pathWriter.flush();
			} else {
				DIALOG.setVisible(true);
				DIALOG.setCoordinate(clickX, clickY);
				lines.addStart(currPoint);
			}
		} else if (arg0.getButton() == MouseEvent.BUTTON3) {
			if (pathing) {
				pathing = false;
			} else {
				pathing = true;
				System.out.println("PATHING ENABLED");
				currPoint = determinePoint(currPoint, START_LEEWAY);
				pathStart = currPoint;
				lines.addStart(currPoint);
				label.setCurrentLines(currPoint, lines.getDestination(currPoint));
				pathWriter.println(pathStart.getX() + "," + pathStart.getY());
				pathWriter.flush();
			}
		}
		label.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Determines whether the given point is too close to another point that has already been recorded 
	 * @param point
	 * @modifies grid
	 * @effects Adds the given point if it is not possibly mistaken for another
	 * @return Return point, or the closest point in the grid if there is a point < leeway away
	 */
	private MapPoint determinePoint(MapPoint point, int leeway) {
		MapPoint closestPoint = grid.getClosest(point, 1);
		if (closestPoint != null && point.getDistance(closestPoint) <= leeway) {
			return closestPoint;
		}
		grid.addPoint(point);
		return point;
	}

}

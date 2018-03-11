package Locator;

import java.awt.Point;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JViewport;

/**
 * Class that takes a viewport and allows it to zoom in and out on the given map
 * 
 * @specfield SCROLL_RATE The speed the pane zooms in and out
 * @author Riley
 *
 */
public class MouseZoom implements MouseWheelListener {
	private static final double SCROLL_RATE = 1.02;
	private MapScrollPane pane;
	private JViewport port;

	public MouseZoom(MapScrollPane pane) {
		this.pane = pane;
		this.port = pane.getViewport();
	}
	
	/**
	 * Zooms in the view of the image in the current pane's view. If
	 * the mouse wheel is scrolled forward, zoom in, otherwise zoom out
	 * 
	 * @param e Mouse wheel event
	 */
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		MapImagePanel panel = (MapImagePanel) pane.getViewport().getView();
		if (e.getWheelRotation() != 0) {
			Point viewPos = port.getViewPosition();
			int currWidth = panel.getMapWidth();
			int currHeight = panel.getMapHeight();
			double xNew = 0;
			double yNew = 0;
			double xCoord = viewPos.getX();
			double yCoord = viewPos.getY();
			if (e.getWheelRotation() > 0 && panel.zoomScalar > 0.4) {
				// Scroll out
				panel.zoom = true;
				panel.zoomScalar /= SCROLL_RATE;
				xNew = xCoord / SCROLL_RATE;
				yNew = yCoord / SCROLL_RATE;
			} else if (e.getWheelRotation() < 0 && panel.zoomScalar * SCROLL_RATE < 1.0){
				// Scroll in
				panel.zoom = true;
				panel.zoomScalar *= SCROLL_RATE;
				xNew = xCoord * SCROLL_RATE;
				yNew = yCoord * SCROLL_RATE;
			}
			if (panel.zoom) {
				double xDiff = xNew - xCoord;
				double yDiff = yNew - yCoord;
				xNew = xDiff / 2 + xNew;
				yNew = yDiff / 2 + yNew;
				Point newPos = checkBounds(xNew, yNew, currWidth, currHeight);
				panel.repaint();
				port.setViewPosition(newPos);
			}
		}
		panel.repaint();
	}
	
	/**
	 * Verifies that the viewport is not viewing a region outside of the viewport's view's image
	 * @param xCoord Potential x coordinate of the viewport
	 * @param yCoord Potential y coordinate of the viewport
	 * @param currWidth The current width of the image in the viewport
	 * @param currHeight The current height of the image in the viewport
	 * @return The desired location for the viewport. No change if the viewport was alread in a valid area
	 */
	private Point checkBounds(double xCoord, double yCoord, int currWidth, int currHeight) {
		if (xCoord < 0) {
			xCoord = 0;
		} else if (xCoord + port.getWidth() > currWidth / SCROLL_RATE) {
			xCoord = currWidth / SCROLL_RATE - port.getWidth();
		}
		if (yCoord < 0) {
			yCoord = 0;
		} else if (yCoord + port.getHeight() > currHeight / SCROLL_RATE) {
			yCoord = currHeight / SCROLL_RATE - port.getHeight();
		}
		Point result = new Point();
		result.setLocation(xCoord, yCoord);
		return result;
	}
}

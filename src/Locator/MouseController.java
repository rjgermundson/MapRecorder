package Locator;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JViewport;

/**
 * Mouse listener for a MapScrollPane that allows it to zoom in and out as well
 * as scroll based on mouse dragging. Limits the movement to the size of the image
 * in the scroll panes viewport
 * 
 * @specfield BORDER_DISPLACEMENT The amount of extra space accounted for by the border
 * around the viewports view
 * @specfield pane The MapScrollPane which uses this listener
 * @specfield port The viewport of the pane
 * @specfield imagePanel The image panel in the viewport's view
 * @specfield lastX The last x coordinate of the mouse
 * @specfield lastY The last y coordinate of the mouse
 * @author Riley
 *
 */
public class MouseController implements MouseMotionListener {
	private static final int BORDER_DISPLACEMENT = 5;
	private MapScrollPane pane;
	private JViewport port;
	private MapImagePanel imagePanel;
	private int lastX = 0;
	private int lastY = 0;
	
	/**
	 * Constructor for the mouse listener
	 * @param pane The scroll pane which this listens in
	 */
	public MouseController(MapScrollPane pane) {
		this.pane = pane;
		this.port = pane.getViewport();
		this.imagePanel = (MapImagePanel) port.getView();
	}
	
	/**
	 * Scrolls pane's view on mouse drag. Direction is opposite
	 * of how the mouse is dragged. Does not allow the view to go
	 * outside of the view's image
	 * 
	 * @param arg0 Mouse event
	 */
	@Override
	public void mouseDragged(MouseEvent arg0) {
			int nextX = arg0.getX();
			int nextY = arg0.getY();
			int newHorizontal = getChange(pane.getHorizontalScrollBar().getValue(), nextX, lastX);
			int newVertical = getChange(pane.getVerticalScrollBar().getValue(), nextY, lastY);
			if (newHorizontal + port.getWidth() <= imagePanel.getMapWidth() + BORDER_DISPLACEMENT) {
				pane.getHorizontalScrollBar().setValue(newHorizontal);
			}
			if (newVertical + port.getHeight() <= imagePanel.getMapHeight() + BORDER_DISPLACEMENT) {
				pane.getVerticalScrollBar().setValue(newVertical);
			}
			lastX = nextX;
			lastY = nextY;
	}
	
	/**
	 * Calculates the new value for a scroll bar
	 * 
	 * @param init Initial value of the scroll bar
	 * @param next Where the mouse has been dragged to
	 * @param prev Where the mouse was initially located on drag initiation
	 * @return Value of what the scroll bar should be set to
	 */
	private int getChange(int init, int next, int prev) {
		int currDiff = next - prev;
		return init - currDiff;
	}
	
	
	/**
	 * Calculates the current location of the mouse in the pane
	 * 
	 * @param arg0 Mouse event
	 */
	@Override
	public void mouseMoved(MouseEvent arg0) {
		lastX = arg0.getX();
		lastY = arg0.getY();
	}
}
package Locator;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;

/**
 * Container for the map panel
 * @author Riley
 *
 */
public class MapFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final Dimension MIN_SIZE = new Dimension(600, 600);
	private static final Dimension MAX_SIZE = new Dimension(800, 800);
	private static final String MAP_NAME = "SampleMap.png";
	private MapScrollPane pane;
	
	/**
	 * Constructor for the map frame object
	 * @param title Title of the frame
	 */
	public MapFrame(String title) {
		super(title);
		this.setMinimumSize(MIN_SIZE);
		this.setMaximumSize(MAX_SIZE);
		addComponentListener(new ResizeListener());
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints con = new GridBagConstraints();	
		MapImagePanel mapPanel = new MapImagePanel(MAP_NAME);
		
		MapScrollPane pane = new MapScrollPane(mapPanel, MAP_NAME);
		this.pane = pane;
		con.ipadx = this.getWidth();
		con.ipady = this.getHeight();
		con.weightx = 1;
		con.gridx = 0;
		con.gridy = 0;
		add(pane, con);
		pack();
		setVisible(true);
	}
	
	/**
	 * Utility class that resets the view of the map to a regular state on resize
	 * @author Riley
	 */
	private class ResizeListener implements ComponentListener {
		
		/**
		 * Sets the view to the upper left corner on resize
		 */
		@Override
		public void componentResized(ComponentEvent e) {
			pane.getViewport().setViewPosition(new Point());
		}

		@Override
		public void componentMoved(ComponentEvent e) {			}

		@Override
		public void componentShown(ComponentEvent e) {			}

		@Override
		public void componentHidden(ComponentEvent e) {			}
		
	}

}

package Locator;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import PointModel.MapPoint;

/**
 * Component that stores a map image and allocated points as a grid
 * 
 * @author Riley
 *
 */
public class MapLabel extends JLabel {
	private static final long serialVersionUID = 1L;
	private static final int BORDER_DISPLACEMENT = 8;
	private final BufferedImage original;
	private final BufferedImage paths;
	private ImageIcon workingIcon;
	private double startX = -1;
	private double startY = -1;
	private Set<MapPoint> destinations = new HashSet<MapPoint>();
	private boolean isOriginal = true;
	
	/**
	 * Constructor for the MapLabel
	 * 
	 * @param original The image that will be stored in the label
	 * @param scalar Size multiplier of image
	 */
	public MapLabel(BufferedImage original, int scalar) {
		this.original = copyImage(original, scalar);
		this.paths = copyImage(original, scalar);
		this.workingIcon = new ImageIcon(this.original);
		setIcon(workingIcon);
	}
	
	
	/**
	 * Toggles the image in this label between the pathing image and the original
	 * @param isOriginal Determines whether the original is presented or not
	 */
	public void togglePath(boolean isOriginal) {
		if (isOriginal) {
			this.workingIcon = new ImageIcon(original);
		} else {
			this.workingIcon = new ImageIcon(paths);
		}
		setIcon(workingIcon);
		this.isOriginal = isOriginal;
	}
	
	/**
	 * Return a copy of a buffered image
	 * @param image The image to copy
	 * @param scalar The size by which the image will be scaled
	 * @throws IllegalArgumentException
	 * 		   image == null || scalar < 0
	 * @return Return a copy of the image
	 */
	public static BufferedImage copyImage(BufferedImage image, int scalar) {
		if (image == null || scalar < 0) {
			throw new IllegalArgumentException();
		}
	    BufferedImage copy = new BufferedImage(image.getWidth() * scalar, image.getHeight() * scalar, image.getType());
	    Graphics g = copy.getGraphics();
	    
	    g.drawImage(image.getScaledInstance(image.getWidth() * scalar, image.getHeight() * scalar, BufferedImage.SCALE_SMOOTH), 0, 0, null);
	    g.dispose();
	    return copy;
	}
	
	/**
	 * Sets the start of the line in the
	 * @param start Start point for the lines
	 * @param destinations All points this point directly connects to
	 * @throws IllegalArgumentException
	 * 		   start == null || destinations == null
	 */
	public void setCurrentLines(MapPoint start, Set<MapPoint> destinations) {
		this.startX = start.getX();
		this.startY = start.getY();
		this.destinations = destinations;
	}
	
	/**
	 * Paints the current paths that are recorded onto the image
	 * @param g Graphics object for this label
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (isOriginal && startX >= 0 && startY >= 0) {
			g.drawOval((int) startX - 5 / 2 - BORDER_DISPLACEMENT, (int) startY - 5 / 2 - BORDER_DISPLACEMENT, 5, 5);
			for (MapPoint point : destinations) {
				g.drawLine((int) startX - BORDER_DISPLACEMENT, (int) startY - BORDER_DISPLACEMENT, (int) point.getX() - BORDER_DISPLACEMENT, (int) point.getY() - BORDER_DISPLACEMENT);
			}
		}
	}
}

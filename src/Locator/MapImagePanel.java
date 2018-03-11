package Locator;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * A MapImagePanel is a panel containing a single image designated by
 * the file
 * 
 * @specfield DIRECTORY The directory the image file should be in
 * @specfield mapLabel The label in the panel
 * @specfield imageWidth The width of the image in the panel
 * @specfield imageHeight The height of the image in the panel
 * @specfield original The original image displayed in the panel
 * @specfield transform The transformation to be applied to the image
 * @specfield zoom Whether the panel will be zoomed in
 * @specfield zoomScalar The current level of magnification 
 * 
 * @author Riley
 *
 */
public class MapImagePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final int ORIGINAL_SCALAR = 2;
	private static final String DIRECTORY = "src/data/";
	private final MapLabel mapLabel;
	private int imageWidth;
	private int imageHeight;
	private BufferedImage original;
	private AffineTransform transform = new AffineTransform();
	boolean zoom;
	double zoomScalar;
	
	/**
	 * Constructor for a map image panel. Takes the given file and displays 
	 * it in the panel
	 * 
	 * @requires file is a properly formatted image file in the correct directory
	 * 			 buildings are associated buildings in the given file
	 * @param file The file to be displayed in the panel
	 * @throws IOException if file is not a valid file
	 */
	public MapImagePanel(String file) {
		try {
			original = ImageIO.read(new File(DIRECTORY + file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		zoom = false;
		zoomScalar = 1.0;
		mapLabel = new MapLabel(original, ORIGINAL_SCALAR);
		imageWidth = original.getWidth() * ORIGINAL_SCALAR;
		imageHeight = original.getHeight() * ORIGINAL_SCALAR;
		add(mapLabel);
	}
	
	/**
	 * Repaints the panel. Potentially scaling if necessary
	 * 
	 * @param g Graphics for this component
	 * @Overrides paintComponent in class JComponent
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics = (Graphics2D) g;
		if (zoom) {
			transform = new AffineTransform();
			transform.scale(zoomScalar, zoomScalar);
			imageWidth = (int) (original.getWidth() * ORIGINAL_SCALAR * zoomScalar);
			imageHeight = (int) (original.getHeight() * ORIGINAL_SCALAR * zoomScalar);
			zoom = false;
		}
		graphics.transform(transform);
	}
	
	/**
	 * Returns the width of the map displayed in the panel
	 * @return The width of the map in the panel
	 */
	public int getMapWidth() {
		return imageWidth;
	}
	
	/**
	 * Returns the height of the map displayed in the panel
	 * @return The height of the map in the panel
	 */
	public int getMapHeight() {
		return imageHeight;
	}
	
	/**
	 * Returns the image that the panel is viewing
	 * @return The image the panel is viewing
	 */
	public BufferedImage getImage() {
	    BufferedImage copy = new BufferedImage(original.getWidth() * ORIGINAL_SCALAR, original.getHeight() * ORIGINAL_SCALAR, original.getType());
	    Graphics g = copy.getGraphics();
	    g.drawImage(copy, 0, 0, null);
	    g.dispose();
	    return copy;
	}
	
	/**
	 * Returns the MapLabel in this panel
	 * @return The MapLabel in this panel
	 */
	public MapLabel getLabel() {
		return (MapLabel) getComponent(0);
	}
}

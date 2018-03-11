package Locator;

import java.io.File;
import java.io.IOException;

import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 * Scroll pane over an image that allows the user to scroll using mouse drag
 * and zoom
 * 
 * @author Riley
 *
 */
public class MapScrollPane extends JScrollPane {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for a MapScrollPane
	 * 
	 * @param view The MapImagePanel that will be used as the view of
	 * 		  this scrollpane
	 */
	public MapScrollPane(MapImagePanel view, String mapName) {
		setViewport(new JViewport());
		getViewport().setView(view);
		addMouseMotionListener(new MouseController(this));
		addMouseWheelListener(new MouseZoom(this));
		try {
			addMouseListener(new MouseClickRecorder(this, new File("src/data/" + mapName + ".txt"), new File("src/data/" + mapName.substring(0, mapName.length() - 4) + "Paths.txt")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_NEVER);
		setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
	}
}

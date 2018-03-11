package Locator;

import javax.swing.JFrame;

/**
 * Launcher for the Map
 * @author Riley
 *
 */
public class MapLauncher {
	
	public static void main(String[] args) {
		MapFrame frame = new MapFrame("Map");
		frame.setSize(frame.getMaximumSize());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
}

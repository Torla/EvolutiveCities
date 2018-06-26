import graphics.JGameCanvas;
import graphics.Tile;
import graphics.Window;

import javax.swing.*;

public class Test {
	public static void main(String[] args) {
		Window window = new Window();
		window.setTile(Tile.KEEP,1,1);
		window.setTile(Tile.KEEP,5,5);
		window.setTile(Tile.HILL,5,5);
		window.setTile(Tile.FARM4,10,10);

		window.repaint();
	}
}

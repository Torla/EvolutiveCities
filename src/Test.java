import graphics.JGameCanvas;
import graphics.Tile;
import graphics.Window;

import javax.swing.*;

public class Test {
	public static void main(String[] args) {
		Window window = new Window();
		window.setTile(Tile.TREE,1,1);

		window.repaint();
	}
}

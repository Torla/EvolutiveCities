package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import javax.swing.JPanel;
import javax.swing.text.html.Option;

public class JGameCanvas extends JPanel{
	private static final int tW = 16; // tile width
	private static final int tH = 16; // tile height
	private static final Tile map[][] = new Tile[Options.screenTilesH][Options.screenTilesW];

	private Image tileset;

	JGameCanvas() {
		tileset = Toolkit.getDefaultToolkit().getImage("tileset1.png");
		for(int i=0;i<Options.screenTilesH;i++) {
			for (int j = 0; j < Options.screenTilesW; j++) {
				map[i][j]=Tile.GRASS;
			}
		}

	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		for(int i=0;i<Options.screenTilesH;i++) {
			for (int j = 0; j < Options.screenTilesW; j++) {
				drawTile(g, Tile.GRASS, i * Options.tileW, j * Options.tileH);
				drawTile(g, map[j][i], i * Options.tileW, j * Options.tileH);
			}
		}
	}

	void drawTile(Graphics g, Tile t, int x, int y){
		// map Tile from the tileset
		int mx = t.ordinal()%7;
		int my = t.ordinal()/7;
		g.drawImage(tileset, x, y, x+Options.tileW, y+Options.tileH,
				mx*tW, my*tH,  mx*tW+tW, my*tH+tH, this);
	}
	void setTile(Tile t, int x,int y){
		map[x][y]=t;
	}
}
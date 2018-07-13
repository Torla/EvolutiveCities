package graphics;

import java.awt.*;
import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import javax.swing.*;
import javax.swing.text.html.Option;

public class JGameCanvas extends JPanel{
	private static final int tW = 16; // tile width
	private static final int tH = 16; // tile height
	private static final Tile map[][] = new Tile[1000][1000];
	private static final boolean mapChange[][] = new boolean[1000][1000];

	private Image tileset;

	JGameCanvas() {
		tileset = Toolkit.getDefaultToolkit().getImage("tileset1.png");
		for(int i=0;i<map.length;i++) {
			for (int j = 0; j < map.length; j++) {
				map[i][j]=Tile.GRASS;
				mapChange[i][j]=false;
			}
		}

	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(0, 0, getWidth(), getHeight());
		for(int i=0;i<mapChange.length;i++) {
			for (int j = 0; j < mapChange[i].length; j++) {
				if(mapChange[i][j]) {
					drawTile(g, map[i][j], i, j);
					mapChange[i][j]=false;
				}
			}
		}
	}


	private void drawTile(Graphics g, Tile t, int x, int y){
		// map Tile from the tileset
		mapChange[x][y]=true;
		int mx = t.ordinal()%7;
		int my = t.ordinal()/7;
		g.drawImage(tileset, x* Options.tileW, y* Options.tileH, x* Options.tileW+Options.tileW, y* Options.tileH+Options.tileH,
				mx*tW, my*tH,  mx*tW+tW, my*tH+tH, this);
	}
	void setTile(Tile t, int x,int y){
		try {
			map[x][y] = t;
			mapChange[x][y]=true;
		}catch (ArrayIndexOutOfBoundsException e){ //todo caused by concurrency
			e.printStackTrace();
		}

	}
}
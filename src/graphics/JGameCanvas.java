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

public class JGameCanvas extends JPanel{
	private static final int tW = 16; // tile width
	private static final int tH = 16; // tile height
	private static final Tile map[][] =
			{{Tile.TREE,Tile.TREE, Tile.TREE, Tile.ROAD_V, Tile.GRASS, Tile.TREE, Tile.TREE_DEAD, Tile.GRASS_STONE, Tile.TREE, Tile.TREE},
					{Tile.WALL, Tile.WALL_POSTER, Tile.WALL_END_RIGHT , Tile.ROAD_V, Tile.WALL_END_LEFT, Tile.WALL, Tile.WALL_END_RIGHT, Tile.TREE_CHOMP, Tile.GRASS_STONE, Tile.GRASS_STONE},
					{Tile.GRASS,Tile.GRASS, Tile.GRASS_STONE, Tile.ROAD_V, Tile.GRASS, Tile.GRASS, Tile.GRASS, Tile.GRASS, Tile.GRASS, Tile.GRASS},
					{Tile.PIZZ_1,Tile.PIZZ_2, Tile.GRASS, Tile.ROAD_V, Tile.GRASS, Tile.GRASS, Tile.GRASS, Tile.GRASS, Tile.GRASS, Tile.GRASS},
					{Tile.PIZZ_3,Tile.PIZZ_4, Tile.GRASS, Tile.ROAD_V, Tile.GRASS, Tile.GRASS, Tile.MARK_1, Tile.MARK_2, Tile.HOSP_1, Tile.HOSP_2},
					{Tile.ROAD_H,Tile.ROAD_H, Tile.ROAD_H, Tile.ROAD_VH_LEFT, Tile.TREE, Tile.TREE_DEAD, Tile.MARK_3, Tile.MARK_4, Tile.HOSP_3, Tile.HOSP_4},
					{Tile.GRASS,Tile.BUSS_1, Tile.BUSS_2, Tile.ROAD_V, Tile.TREE, Tile.NEWS, Tile.MARK_5, Tile.MARK_6, Tile.HOSP_5, Tile.HOSP_6},
					{Tile.GRASS,Tile.BUSS_3, Tile.BUSS_4, Tile.ROAD_VH_RIGHT, Tile.ROAD_H, Tile.ROAD_H, Tile.ROAD_H, Tile.ROAD_H, Tile.ROAD_H, Tile.ROAD_H},
					{Tile.GRASS,Tile.BUSS_5, Tile.BUSS_6, Tile.ROAD_V, Tile.GRASS, Tile.GRASS, Tile.GRASS, Tile.GRASS, Tile.GRASS, Tile.GRASS},
					{Tile.GRASS,Tile.GRASS, Tile.GRASS, Tile.ROAD_V, Tile.GRASS, Tile.GRASS, Tile.GRASS, Tile.GRASS, Tile.GRASS, Tile.GRASS}
			};

	private Image tileset;

	JGameCanvas() {
		try {
			new FileInputStream("tileset.png");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		tileset = Toolkit.getDefaultToolkit().getImage("tileset1.png");
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());

		for(int i=0;i<10;i++)
		for(int j=0;j<10;j++)
		drawTile(g, map[j][i], i*tW,j*tH);
	}

	void drawTile(Graphics g, Tile t, int x, int y){
		// map Tile from the tileset
		int mx = t.ordinal()%10;
		int my = t.ordinal()/10;
		g.drawImage(tileset, x, y, x+tW, y+tH,
				mx*tW, my*tH,  mx*tW+tW, my*tH+tH, this);
	}
	void setTile(Tile t, int x,int y){
		map[x][y]=t;
	}
}
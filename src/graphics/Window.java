package graphics;

import javax.swing.*;
import java.awt.*;

public class Window extends Frame {
	private final JGameCanvas canvas;
	public Window() throws HeadlessException {
		super("prova");
		canvas=new JGameCanvas();
		add(canvas);
		setVisible(true);
		setSize(Options.screenTilesW*Options.tileW,Options.screenTilesH*Options.tileH);
	}

	public void setTile(Tile t, int x, int y){
		canvas.setTile(t,x,y);

	}

}

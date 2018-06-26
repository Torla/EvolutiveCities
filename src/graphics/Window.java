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
		setSize(1000,1000);
	}

	public void setTile(Tile t, int x, int y){
		canvas.setTile(t,x,y);

	}

}

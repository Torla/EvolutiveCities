package graphics;



import game.Match;

import java.awt.*;
import java.awt.event.*;

public class Window extends Frame implements KeyListener,WindowListener{
	private final JGameCanvas canvas;
	public Window() throws HeadlessException {
		super("Map");
		addWindowListener(this);
		addKeyListener(this);
		canvas=new JGameCanvas();
		//canvas.setDoubleBuffered(true);
		add(canvas);
		setVisible(true);
		setSize(Options.screenTilesW*Options.tileW,Options.screenTilesH*Options.tileH);
	}

	public void setTile(Tile t, int x, int y){
		canvas.setTile(t,x,y);

	}

	@Override
	public void repaint(){
		canvas.repaint();
	}

	public int getSizeX(){
		return this.getBounds().getSize().width;
	}
	public int getSizeY(){
		return canvas.getBounds().getSize().height;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();

		switch( keyCode ) {
			case KeyEvent.VK_UP:
				Graphics.moveScreen(0,-1);
				break;
			case KeyEvent.VK_DOWN:
				Graphics.moveScreen(0,1);
				break;
			case KeyEvent.VK_LEFT:
				Graphics.moveScreen(-1,0);
				break;
			case KeyEvent.VK_RIGHT :
				Graphics.moveScreen(1,0);
				break;
			case KeyEvent.VK_PAGE_UP:
				Match.setSleep(Match.getSleep()+50);
				break;
			case  KeyEvent.VK_PAGE_DOWN:
				Match.setSleep(Match.getSleep()-50);
				break;
			case KeyEvent.VK_SPACE:
				Graphics.resetScreenPosition();
				break;
			case KeyEvent.VK_MINUS:
				if(Options.tileW==1 || Options.tileH==1) break;
				Options.tileW--;
				Options.tileH--;
				break;
			case KeyEvent.VK_PLUS:
				Options.tileW++;
				Options.tileH++;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void windowOpened(WindowEvent e) {

	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}

	@Override
	public void windowClosed(WindowEvent e) {
		System.exit(0);
	}

	@Override
	public void windowIconified(WindowEvent e) {

	}

	@Override
	public void windowDeiconified(WindowEvent e) {

	}

	@Override
	public void windowActivated(WindowEvent e) {

	}

	@Override
	public void windowDeactivated(WindowEvent e) {

	}


}

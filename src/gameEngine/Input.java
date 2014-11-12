/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameEngine;

import gameEngine.View;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author Sebastian Galli
 */
public class Input implements MouseListener, KeyListener {

	private int x = 1000;
	private int y = 1000;
	private View view;

	Input(View view) {
		this.view = view;
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		setX(me.getX());
		setY(me.getY());

		view.update();
	}

	@Override
	public void mousePressed(MouseEvent me) {

	}

	@Override
	public void mouseReleased(MouseEvent me) {

	}

	@Override
	public void mouseEntered(MouseEvent me) {

	}

	@Override
	public void mouseExited(MouseEvent me) {

	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the view
	 */
	public View getView() {
		return view;
	}

	/**
	 * @param view
	 *            the view to set
	 */
	public void setView(View view) {
		this.view = view;
	}

	@Override
	public void keyTyped(KeyEvent ke) {
		if (ke.getKeyChar() != 'r') {
			throw new UnsupportedOperationException("Not supported yet.");
		}
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		if (ke.getKeyChar() != 'r') {
			throw new UnsupportedOperationException("Not supported yet.");
		}
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		if (ke.getKeyChar() == 'r') {
			System.out.println("check");
			view.getGameLogic().setup();
			view.repaint();
		}
	}

}

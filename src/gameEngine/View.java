/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameEngine;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 *
 * @author Sebastian
 */
public class View extends JPanel {

	private Input input;
	private GameLogic gameLogic;
	private Tile tile;

	private TileStatus tileStatus;

	public View() {

		input = new Input(this);
		gameLogic = new GameLogic(getInput());

		// listen to key presses
		setFocusable(true);
		this.addMouseListener(input);
		this.addKeyListener(input);

		gameLogic.step();
	}

	// update() führt step() einmal aus und zeichnet danach das veränderte
	// Spielfeld neu.
	public void update() {
		getGameLogic().step();
		repaint();
	}

	// paint() zeichnet das Spielfeld.
	@Override
	public void paint(Graphics g) {
		for (Tile tile : getGameLogic().getTiles()) {

			if (tile.getStatus() == TileStatus.PLAYER1) {
				g.setColor(Color.RED);
				g.fillRect(tile.getX() * GameLogic.getTileSize(), tile.getY()
						* GameLogic.getTileSize(), GameLogic.getTileSize(),
						GameLogic.getTileSize());
			}

			if (tile.getStatus() == TileStatus.PLAYER2) {
				g.setColor(Color.BLUE);
				g.fillRect(tile.getX() * GameLogic.getTileSize(), tile.getY()
						* GameLogic.getTileSize(), GameLogic.getTileSize(),
						GameLogic.getTileSize());
			}

			if (tile.getStatus() == TileStatus.PLAYER1SELECTED) {
				g.setColor(Color.MAGENTA);
				g.fillRect(tile.getX() * GameLogic.getTileSize(), tile.getY()
						* GameLogic.getTileSize() + 1, GameLogic.getTileSize(),
						GameLogic.getTileSize());
			}

			if (tile.getStatus() == TileStatus.PLAYER2SELECTED) {
				g.setColor(Color.CYAN);
				g.fillRect(tile.getX() * GameLogic.getTileSize(), tile.getY()
						* GameLogic.getTileSize(), GameLogic.getTileSize(),
						GameLogic.getTileSize());
			}

			if (tile.getStatus() == TileStatus.EMPTY) {
				g.setColor(Color.WHITE);
				g.fillRect(tile.getX() * GameLogic.getTileSize(), tile.getY()
						* GameLogic.getTileSize(), GameLogic.getTileSize(),
						GameLogic.getTileSize());
			}

			if (tile.getStatus() == TileStatus.LOCKED) {
				g.setColor(Color.BLACK);
				g.fillRect(tile.getX() * GameLogic.getTileSize(), tile.getY()
						* GameLogic.getTileSize(), GameLogic.getTileSize(),
						GameLogic.getTileSize());
			}
			g.setColor(Color.BLACK);
			g.drawRect(tile.getX() * GameLogic.getTileSize(), tile.getY()
					* GameLogic.getTileSize(), GameLogic.getTileSize(),
					GameLogic.getTileSize());
		}
	}

	// Nachfolgende Funktionen ind automatisch generierte getter & setter

	/**
	 * @return the input
	 */
	public Input getInput() {
		return input;
	}

	/**
	 * @param input
	 *            the input to set
	 */
	public void setInput(Input input) {
		this.input = input;
	}

	/**
	 * @return the gameLogic
	 */
	public GameLogic getGameLogic() {
		return gameLogic;
	}

	/**
	 * @param gameLogic
	 *            the gameLogic to set
	 */
	public void setGameLogic(GameLogic gameLogic) {
		this.gameLogic = gameLogic;
	}

	/**
	 * @return the tile
	 */
	public Tile getTile() {
		return tile;
	}

	/**
	 * @param tile
	 *            the tile to set
	 */
	public void setTile(Tile tile) {
		this.tile = tile;
	}

	/**
	 * @return the tileStatus
	 */
	public TileStatus getTileStatus() {
		return tileStatus;
	}

	/**
	 * @param tileStatus
	 *            the tileStatus to set
	 */
	public void setTileStatus(TileStatus tileStatus) {
		this.tileStatus = tileStatus;
	}
}

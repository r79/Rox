/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameEngine;

import gameEngine.Input;
import gameEngine.Tile;
import gameEngine.TileStatus;

import java.util.ArrayList;

/**
 *
 * @author Sebastian Galli
 */
public class GameLogic {

	private static int tileSize = 50;

	private Input input;
	private ArrayList<Tile> tiles;

	private int fieldHeight = 8;
	private int fieldWidth = 8;

	private boolean setup = true;
	private boolean movingTiles = false;
	private Tile selectedTile;

	private int player1Points = 0;
	private int player1Tiles = 0;
	private int player2Points = 0;
	private int player2Tiles = 0;

	public GameLogic(Input input) {

		this.input = input;

		tiles = new ArrayList<Tile>();

		for (int i = 0; i <= fieldWidth; i++) {
			for (int j = 0; j <= fieldHeight; j++) {
				tiles.add(new Tile(i, j));
			}
		}
	}

	// setup() bereitet das Spiel vor.
	public void setup() {
		int counter = 0;
		for (Tile tile : getTiles()) {
			tile.setStatus(TileStatus.EMPTY);
			if (counter % 2 == 0) {
				if (tile.getY() <= 1) {
					tile.setStatus(TileStatus.PLAYER1);
				}

				if (tile.getY() > getFieldHeight() - 2) {
					tile.setStatus(TileStatus.PLAYER2);
				}
			}

			counter++;
		}

		setSetup(false);

	}

	// checkIfScored() setz den Status von Steinen, die sich auf der
	// gegenüberliegend Grundlinie befinden uf Locked.
	// Diese werden schwarz und können nicht mehr bwegt werden.
	public void checkIfScored() {
		for (Tile tile : getTiles()) {
			if (getTiles().indexOf(tile) % 9 == 0
					&& tile.getStatus() == TileStatus.PLAYER2) {
				tile.setStatus(TileStatus.LOCKED);
			} else if ((getTiles().indexOf(tile) + 1) % 9 == 0
					&& tile.getStatus() == TileStatus.PLAYER1) {
				tile.setStatus(TileStatus.LOCKED);
			}

			if (tile.getStatus() == TileStatus.PLAYER1) {
			} else if (tile.getStatus() == TileStatus.PLAYER2) {
			}
		}
	}

	// step() wird aufgerufen sobald ein Spieler mit der Maus klickt.
	// step() lauft dann einmal durch die Logik des Spiels. In ihr sind alle
	// möglichen Spielzüge definiert.
	public void step() throws IndexOutOfBoundsException {

		if (isSetup()) {
			setup();
		}

		for (Tile tile : getTiles()) {
			if (getInput().getX() > tile.getX() * getTileSize()
					&& getInput().getX() < tile.getX() * getTileSize()
							+ getTileSize()) {
				if (getInput().getY() > tile.getY() * getTileSize()
						&& getInput().getY() < tile.getY() * getTileSize()
								+ getTileSize()) {

					if (tile == selectedTile) {
						if (tile.getStatus() == TileStatus.PLAYER1SELECTED) {
							tile.setStatus(TileStatus.PLAYER1);
						} else if (tile.getStatus() == TileStatus.PLAYER2SELECTED) {
							tile.setStatus(TileStatus.PLAYER2);
						}
					}

					if (tile.getStatus() == TileStatus.PLAYER1 && !movingTiles) {
						setSelectedTile(tile);
						setMovingTiles(true);
						tile.setStatus(TileStatus.PLAYER1SELECTED);

					} else if (tile.getStatus() == TileStatus.PLAYER2
							&& !movingTiles) {
						setSelectedTile(tile);
						setMovingTiles(true);
						tile.setStatus(TileStatus.PLAYER2SELECTED);

					} else if (tile.getStatus() == TileStatus.EMPTY
							&& isMovingTiles()) {
						if ((tiles.indexOf(selectedTile) - tiles.indexOf(tile) <= 10 && tiles
								.indexOf(selectedTile) - tiles.indexOf(tile) >= 8)
								|| (tiles.indexOf(tile)
										- tiles.indexOf(selectedTile) >= 8 && tiles
										.indexOf(tile)
										- tiles.indexOf(selectedTile) <= 10)
								|| (tiles.indexOf(tile)
										- tiles.indexOf(selectedTile) >= 8 && tiles
										.indexOf(tile)
										- tiles.indexOf(selectedTile) <= 10)
								|| (tiles.indexOf(selectedTile)
										- tiles.indexOf(tile) == 1 || tiles
										.indexOf(selectedTile)
										- tiles.indexOf(tile) == -1)) {
							if (getSelectedTile().getStatus() == TileStatus.PLAYER1SELECTED) {
								tile.setStatus(TileStatus.PLAYER1);
								getSelectedTile().setStatus(TileStatus.EMPTY);
							}

							if (getSelectedTile().getStatus() == TileStatus.PLAYER2SELECTED) {
								tile.setStatus(TileStatus.PLAYER2);
								getSelectedTile().setStatus(TileStatus.EMPTY);
							}
						} else if (selectedTile.getStatus() == TileStatus.PLAYER1SELECTED) {
							selectedTile.setStatus(TileStatus.PLAYER1);
						} else if (selectedTile.getStatus() == TileStatus.PLAYER2SELECTED) {
							selectedTile.setStatus(TileStatus.PLAYER2);
						}

						movingTiles = false;

					} else if (tile.getStatus() == TileStatus.PLAYER1
							&& movingTiles) {
						if (selectedTile.getStatus() == TileStatus.PLAYER1SELECTED) {
							if (tiles.indexOf(tile) % 9 != 0) {
								if (tiles.indexOf(selectedTile)
										- tiles.indexOf(tile) == 8
										&& tiles.get(tiles.indexOf(tile) - 8)
												.getStatus() == TileStatus.EMPTY) {
									tiles.get(tiles.indexOf(tile) - 8)
											.setStatus(TileStatus.PLAYER1);
									selectedTile.setStatus(TileStatus.EMPTY);

								} else if (tiles.indexOf(tile)
										- tiles.indexOf(selectedTile) == 10
										&& tiles.get(tiles.indexOf(tile) + 10)
												.getStatus() == TileStatus.EMPTY) {
									tiles.get(tiles.indexOf(tile) + 10)
											.setStatus(TileStatus.PLAYER1);
									selectedTile.setStatus(TileStatus.EMPTY);

								} else if (tiles.indexOf(tile)
										- tiles.indexOf(selectedTile) == -10
										&& tiles.get(tiles.indexOf(tile) - 10)
												.getStatus() == TileStatus.EMPTY) {
									tiles.get(tiles.indexOf(tile) - 10)
											.setStatus(TileStatus.PLAYER1);
									selectedTile.setStatus(TileStatus.EMPTY);
								} else if (tiles.indexOf(selectedTile)
										- tiles.indexOf(tile) == -8
										&& tiles.get(tiles.indexOf(tile) + 8)
												.getStatus() == TileStatus.EMPTY) {
									tiles.get(tiles.indexOf(tile) + 8)
											.setStatus(TileStatus.PLAYER1);
									selectedTile.setStatus(TileStatus.EMPTY);
								} else {
									selectedTile.setStatus(TileStatus.PLAYER1);
								}
							} else {
								selectedTile.setStatus(TileStatus.PLAYER1);
							}

						} else if (selectedTile.getStatus() == TileStatus.PLAYER2SELECTED) {
							if (tiles.indexOf(tile) % 9 != 0) {
								if (tiles.indexOf(selectedTile)
										- tiles.indexOf(tile) == 8
										&& tiles.get(tiles.indexOf(tile) - 8)
												.getStatus() == TileStatus.EMPTY) {
									tiles.get(tiles.indexOf(tile) - 8)
											.setStatus(TileStatus.PLAYER2);
									tile.setStatus(TileStatus.EMPTY);
									selectedTile.setStatus(TileStatus.EMPTY);

								} else if (tiles.indexOf(tile)
										- tiles.indexOf(selectedTile) == 10
										&& tiles.get(tiles.indexOf(tile) + 10)
												.getStatus() == TileStatus.EMPTY) {
									tiles.get(tiles.indexOf(tile) + 10)
											.setStatus(TileStatus.PLAYER2);
									tile.setStatus(TileStatus.EMPTY);
									selectedTile.setStatus(TileStatus.EMPTY);

								} else if (tiles.indexOf(selectedTile)
										- tiles.indexOf(tile) == -8
										&& tiles.get(tiles.indexOf(tile) + 8)
												.getStatus() == TileStatus.EMPTY) {
									tiles.get(tiles.indexOf(tile) + 8)
											.setStatus(TileStatus.PLAYER2);
									tile.setStatus(TileStatus.EMPTY);
									selectedTile.setStatus(TileStatus.EMPTY);

								} else if (tiles.indexOf(tile)
										- tiles.indexOf(selectedTile) == -10
										&& tiles.get(tiles.indexOf(tile) - 10)
												.getStatus() == TileStatus.EMPTY) {
									tiles.get(tiles.indexOf(tile) - 10)
											.setStatus(TileStatus.PLAYER2);
									tile.setStatus(TileStatus.EMPTY);
									selectedTile.setStatus(TileStatus.EMPTY);
								} else {
									selectedTile.setStatus(TileStatus.PLAYER2);
								}
							} else {
								selectedTile.setStatus(TileStatus.PLAYER2);
							}
						}

						movingTiles = false;

					} else if (tile.getStatus() == TileStatus.PLAYER2
							&& movingTiles) {
						if (selectedTile.getStatus() == TileStatus.PLAYER2SELECTED) {
							if (tiles.indexOf(selectedTile)
									- tiles.indexOf(tile) == 8
									&& tiles.get(tiles.indexOf(tile) - 8)
											.getStatus() == TileStatus.EMPTY) {
								tiles.get(tiles.indexOf(tile) - 8).setStatus(
										TileStatus.PLAYER2);
								selectedTile.setStatus(TileStatus.EMPTY);

							} else if (tiles.indexOf(tile)
									- tiles.indexOf(selectedTile) == 10
									&& tiles.get(tiles.indexOf(tile) + 10)
											.getStatus() == TileStatus.EMPTY) {
								tiles.get(tiles.indexOf(tile) + 10).setStatus(
										TileStatus.PLAYER2);
								selectedTile.setStatus(TileStatus.EMPTY);

							} else if (tiles.indexOf(tile)
									- tiles.indexOf(selectedTile) == -10
									&& tiles.get(tiles.indexOf(tile) - 10)
											.getStatus() == TileStatus.EMPTY) {
								tiles.get(tiles.indexOf(tile) - 10).setStatus(
										TileStatus.PLAYER2);
								selectedTile.setStatus(TileStatus.EMPTY);
							} else if (tiles.indexOf(selectedTile)
									- tiles.indexOf(tile) == -8
									&& tiles.get(tiles.indexOf(tile) + 8)
											.getStatus() == TileStatus.EMPTY) {
								tiles.get(tiles.indexOf(tile) + 8).setStatus(
										TileStatus.PLAYER2);
								selectedTile.setStatus(TileStatus.EMPTY);

							} else {
								selectedTile.setStatus(TileStatus.PLAYER2);
							}

						} else if (selectedTile.getStatus() == TileStatus.PLAYER1SELECTED) {
							if (tiles.indexOf(selectedTile)
									- tiles.indexOf(tile) == 8
									&& tiles.get(tiles.indexOf(tile) - 8)
											.getStatus() == TileStatus.EMPTY) {
								tiles.get(tiles.indexOf(tile) - 8).setStatus(
										TileStatus.PLAYER1);
								tile.setStatus(TileStatus.EMPTY);
								selectedTile.setStatus(TileStatus.EMPTY);

							} else if (tiles.indexOf(tile)
									- tiles.indexOf(selectedTile) == 10
									&& tiles.get(tiles.indexOf(tile) + 10)
											.getStatus() == TileStatus.EMPTY) {
								tiles.get(tiles.indexOf(tile) + 10).setStatus(
										TileStatus.PLAYER1);
								tile.setStatus(TileStatus.EMPTY);
								selectedTile.setStatus(TileStatus.EMPTY);
							} else if (tiles.indexOf(selectedTile)
									- tiles.indexOf(tile) == -8
									&& tiles.get(tiles.indexOf(tile) + 8)
											.getStatus() == TileStatus.EMPTY) {
								tiles.get(tiles.indexOf(tile) + 8).setStatus(
										TileStatus.PLAYER1);
								tile.setStatus(TileStatus.EMPTY);
								selectedTile.setStatus(TileStatus.EMPTY);

							} else if (tiles.indexOf(tile)
									- tiles.indexOf(selectedTile) == -10
									&& tiles.get(tiles.indexOf(tile) - 10)
											.getStatus() == TileStatus.EMPTY) {
								tiles.get(tiles.indexOf(tile) - 10).setStatus(
										TileStatus.PLAYER1);
								tile.setStatus(TileStatus.EMPTY);
								selectedTile.setStatus(TileStatus.EMPTY);
							} else {
								selectedTile.setStatus(TileStatus.PLAYER1);
							}
						}

						movingTiles = false;
					}
				}
			}
		}

		checkIfScored();
	}

	// Nachfolgende funktionen sind automatisch generierte getter & setter

	/**
	 * @return the tileSize
	 */
	public static int getTileSize() {
		return tileSize;
	}

	/**
	 * @param aTileSize
	 *            the tileSize to set
	 */
	public static void setTileSize(int aTileSize) {
		tileSize = aTileSize;
	}

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
	 * @return the tiles
	 */
	public ArrayList<Tile> getTiles() {
		return tiles;
	}

	/**
	 * @param tiles
	 *            the tiles to set
	 */
	public void setTiles(ArrayList<Tile> tiles) {
		this.tiles = tiles;
	}

	/**
	 * @return the fieldHeight
	 */
	public int getFieldHeight() {
		return fieldHeight;
	}

	/**
	 * @param fieldHeight
	 *            the fieldHeight to set
	 */
	public void setFieldHeight(int fieldHeight) {
		this.fieldHeight = fieldHeight;
	}

	/**
	 * @return the fieldWidth
	 */
	public int getFieldWidth() {
		return fieldWidth;
	}

	/**
	 * @param fieldWidth
	 *            the fieldWidth to set
	 */
	public void setFieldWidth(int fieldWidth) {
		this.fieldWidth = fieldWidth;
	}

	/**
	 * @return the setup
	 */
	public boolean isSetup() {
		return setup;
	}

	/**
	 * @param setup
	 *            the setup to set
	 */
	public void setSetup(boolean setup) {
		this.setup = setup;
	}

	/**
	 * @return the movingTiles
	 */
	public boolean isMovingTiles() {
		return movingTiles;
	}

	/**
	 * @param movingTiles
	 *            the movingTiles to set
	 */
	public void setMovingTiles(boolean movingTiles) {
		this.movingTiles = movingTiles;
	}

	/**
	 * @return the selectedTile
	 */
	public Tile getSelectedTile() {
		return selectedTile;
	}

	/**
	 * @param selectedTile
	 *            the selectedTile to set
	 */
	public void setSelectedTile(Tile selectedTile) {
		this.selectedTile = selectedTile;
	}
}

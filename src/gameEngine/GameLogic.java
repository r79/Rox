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

	public GameLogic(Input input) {

		this.input = input;

		tiles = new ArrayList<Tile>();

		for (int i = 0; i <= fieldWidth; i++) {
			for (int j = 0; j <= fieldHeight; j++) {
				tiles.add(new Tile(i, j));
			}
		}

		setup();
	}

	// setup() bereitet das Spiel vor.
	public void setup() {
		int counter = 0;
		for (Tile tile : getTiles()) {
			tile.setPlayer(0);
			if (counter % 2 == 0) {
				if (tile.getY() <= 1) {
					tile.setPlayer(1);
				}

				if (tile.getY() > getFieldHeight() - 2) {
					tile.setPlayer(2);
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
				tile.lock();
			} else if ((getTiles().indexOf(tile) + 1) % 9 == 0
					&& tile.getStatus() == TileStatus.PLAYER1) {
				tile.lock();
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

		for (Tile tile : getTiles()) {
			if (tile.isOnTile(input.getX(), input.getY(), tileSize)) {
				if (tile == selectedTile) {
					tile.toggleSelect();
				}

				if (tile.isPlayerTile()&&!movingTiles)
					setSelectedTile(tile);
					setMovingTiles(true);
					tile.toggleSelect();

				} else if (!tile.isPlayerTile()
						&& isMovingTiles()) {
					if ((tiles.indexOf(selectedTile) - tiles.indexOf(tile) <= 10 && tiles.indexOf(selectedTile) - tiles.indexOf(tile) >= 8)
							|| (tiles.indexOf(tile)- tiles.indexOf(selectedTile) >= 8 && tiles.indexOf(tile)
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
							tile.setPlayer(1);
							selectedTile.setPlayer(0);
						}

						if (getSelectedTile().getStatus() == TileStatus.PLAYER2SELECTED) {
							tile.setPlayer(2);
							selectedTile.setPlayer(0);
						}
					} else if (selectedTile.getStatus() == TileStatus.PLAYER1SELECTED) {
						selectedTile.setPlayer(1);
					} else if (selectedTile.getStatus() == TileStatus.PLAYER2SELECTED) {
						selectedTile.setPlayer(2);
					}

					movingTiles = false;

				} else if (tile.getStatus() == TileStatus.PLAYER1
						&& movingTiles) {
					if (selectedTile.getStatus() == TileStatus.PLAYER1SELECTED) {
						if (tiles.indexOf(tile) % 9 != 0) {
							if (tiles.indexOf(selectedTile) - tiles.indexOf(tile) == 8
									&& !tiles.get(tiles.indexOf(tile) - 8).isPlayerTile()) {
								tiles.get(tiles.indexOf(tile) - 8).setPlayer(1);
								selectedTile.setPlayer(0);

							} else if (tiles.indexOf(tile)
									- tiles.indexOf(selectedTile) == 10
									&& tiles.get(tiles.indexOf(tile) + 10)
											.getStatus() == TileStatus.EMPTY) {
								tiles.get(tiles.indexOf(tile) + 10).setPlayer(1);;
								selectedTile.setPlayer(0);

							} else if (tiles.indexOf(tile)
									- tiles.indexOf(selectedTile) == -10
									&& tiles.get(tiles.indexOf(tile) - 10)
											.getStatus() == TileStatus.EMPTY) {
								tiles.get(tiles.indexOf(tile) - 10).setPlayer(1);;
								selectedTile.setPlayer(0);
							} else if (tiles.indexOf(selectedTile)
									- tiles.indexOf(tile) == -8
									&& tiles.get(tiles.indexOf(tile) + 8)
											.getStatus() == TileStatus.EMPTY) {
								tiles.get(tiles.indexOf(tile) + 8).setPlayer(1);
								selectedTile.setPlayer(0);
							} else {
								selectedTile.setPlayer(1);
							}
						} else {
							selectedTile.setPlayer(1);
						}

					} else if (selectedTile.getStatus() == TileStatus.PLAYER2SELECTED) {
						if (tiles.indexOf(tile) % 9 != 0) {
							if (tiles.indexOf(selectedTile)
									- tiles.indexOf(tile) == 8
									&& tiles.get(tiles.indexOf(tile) - 8)
											.getStatus() == TileStatus.EMPTY) {
								tiles.get(tiles.indexOf(tile) - 8).setPlayer(2);
								tile.setPlayer(0);
								selectedTile.setPlayer(0);

							} else if (tiles.indexOf(tile)
									- tiles.indexOf(selectedTile) == 10
									&& tiles.get(tiles.indexOf(tile) + 10)
											.getStatus() == TileStatus.EMPTY) {
								tiles.get(tiles.indexOf(tile) + 10).setPlayer(2);
								tile.setPlayer(0);
								selectedTile.setPlayer(0);

							} else if (tiles.indexOf(selectedTile)
									- tiles.indexOf(tile) == -8
									&& tiles.get(tiles.indexOf(tile) + 8)
											.getStatus() == TileStatus.EMPTY) {
								tiles.get(tiles.indexOf(tile) + 8).setPlayer(2);
								tile.setPlayer(0);
								selectedTile.setPlayer(0);

							} else if (tiles.indexOf(tile)
									- tiles.indexOf(selectedTile) == -10
									&& tiles.get(tiles.indexOf(tile) - 10)
											.getStatus() == TileStatus.EMPTY) {
								tiles.get(tiles.indexOf(tile) - 10).setPlayer(2);
								tile.setPlayer(0);
								selectedTile.setPlayer(0);
							} else {
								selectedTile.setPlayer(0);
							}
						} else {
							selectedTile.setPlayer(0);
						}
					}

					movingTiles = false;

				} else if (tile.getStatus() == TileStatus.PLAYER2
						&& movingTiles) {
					if (selectedTile.getStatus() == TileStatus.PLAYER2SELECTED) {
						if (tiles.indexOf(selectedTile) - tiles.indexOf(tile) == 8
								&& tiles.get(tiles.indexOf(tile) - 8)
										.getStatus() == TileStatus.EMPTY) {
							tiles.get(tiles.indexOf(tile) - 8).setPlayer(2);
							selectedTile.setPlayer(0);

						} else if (tiles.indexOf(tile)
								- tiles.indexOf(selectedTile) == 10
								&& tiles.get(tiles.indexOf(tile) + 10)
										.getStatus() == TileStatus.EMPTY) {
							tiles.get(tiles.indexOf(tile) + 10).setPlayer(2);
							selectedTile.setPlayer(0);

						} else if (tiles.indexOf(tile)
								- tiles.indexOf(selectedTile) == -10
								&& tiles.get(tiles.indexOf(tile) - 10)
										.getStatus() == TileStatus.EMPTY) {
							tiles.get(tiles.indexOf(tile) - 10).setPlayer(2);
							selectedTile.setPlayer(0);
						} else if (tiles.indexOf(selectedTile)
								- tiles.indexOf(tile) == -8
								&& tiles.get(tiles.indexOf(tile) + 8)
										.getStatus() == TileStatus.EMPTY) {
							tiles.get(tiles.indexOf(tile) + 8).setPlayer(2);
							selectedTile.setPlayer(0);

						} else {
							selectedTile.setPlayer(2);
						}

					} else if (selectedTile.getStatus() == TileStatus.PLAYER1SELECTED) {
						if (tiles.indexOf(selectedTile) - tiles.indexOf(tile) == 8
								&& tiles.get(tiles.indexOf(tile) - 8)
										.getStatus() == TileStatus.EMPTY) {
							tiles.get(tiles.indexOf(tile) - 8).setPlayer(1);
							tile.setPlayer(0);
							selectedTile.setPlayer(0);

						} else if (tiles.indexOf(tile)
								- tiles.indexOf(selectedTile) == 10
								&& tiles.get(tiles.indexOf(tile) + 10)
										.getStatus() == TileStatus.EMPTY) {
							tiles.get(tiles.indexOf(tile) + 10).setPlayer(1);
							tile.setPlayer(0);
							selectedTile.setPlayer(0);
						} else if (tiles.indexOf(selectedTile)
								- tiles.indexOf(tile) == -8
								&& tiles.get(tiles.indexOf(tile) + 8)
										.getStatus() == TileStatus.EMPTY) {
							tiles.get(tiles.indexOf(tile) + 8).setPlayer(1);
							tile.setPlayer(0);
							selectedTile.setPlayer(0);

						} else if (tiles.indexOf(tile)
								- tiles.indexOf(selectedTile) == -10
								&& tiles.get(tiles.indexOf(tile) - 10)
										.getStatus() == TileStatus.EMPTY) {
							tiles.get(tiles.indexOf(tile) - 10).setPlayer(1);
							tile.setPlayer(0);
							selectedTile.setPlayer(0);
						} else {
							selectedTile.setPlayer(1);
						}
					}

					movingTiles = false;
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

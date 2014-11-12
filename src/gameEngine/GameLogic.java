/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameEngine;

import java.util.ArrayList;

/**
 * @author Sebastian Galli
 */
public class GameLogic {

    private static int tileSize = 50;

    private Input input;
    private ArrayList<Tile> tiles;

    private int fieldHeight = 8;
    private int fieldWidth = 8;

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
        for (Tile tile : tiles) {
            tile.setPlayer(0);
            if (counter % 2 == 0) {
                if (tile.getY() <= 1) {
                    tile.setPlayer(1);
                }

                if (tile.getY() > fieldHeight - 2) {
                    tile.setPlayer(2);
                }
            }

            counter++;
        }

    }

    // checkIfScored() setz den Status von Steinen, die sich auf der
    // gegenüberliegend Grundlinie befinden uf Locked.
    // Diese werden schwarz und können nicht mehr bwegt werden.
    public void checkIfScored() {
        for (Tile tile : tiles) {
            if (tiles.indexOf(tile) % 9 == 0
                    && tile.getStatus() == TileStatus.PLAYER2) {
                tile.lock();
            } else if ((tiles.indexOf(tile) + 1) % 9 == 0
                    && tile.getStatus() == TileStatus.PLAYER1) {
                tile.lock();
            }

            if (tile.getStatus() == TileStatus.PLAYER1) {
            } else if (tile.getStatus() == TileStatus.PLAYER2) {
            }
        }
    }


    private Tile previousSelected;
    private boolean secondSelection = false;

    // step() wird aufgerufen sobald ein Spieler mit der Maus klickt.
    // step() lauft dann einmal durch die Logik des Spiels. In ihr sind alle
    // möglichen Spielzüge definiert.
    public void step() throws IndexOutOfBoundsException {

        for (Tile selectedTile : tiles) {
            if (selectedTile.isOnTile(input.getX(), input.getY(), tileSize)) {
                if (secondSelection) {
                    if (selectedTile.isPlayerTile()) {
                        previousSelected = selectedTile;
                        secondSelection = true;
                        selectedTile.select();
                    }
                } else {

                    if (selectedTile == previousSelected) {
                        selectedTile.unselect();
                        secondSelection = false;
                    }

                    if (!selectedTile.isPlayerTile()) {
                        if ((tiles.indexOf(previousSelected) - tiles.indexOf(selectedTile) <= 10 && tiles.indexOf(previousSelected) - tiles.indexOf(selectedTile) >= 8)
                                || (tiles.indexOf(selectedTile) - tiles.indexOf(previousSelected) >= 8 && tiles.indexOf(selectedTile) - tiles.indexOf(previousSelected) <= 10)
                                || (tiles.indexOf(selectedTile) - tiles.indexOf(previousSelected) >= 8 && tiles.indexOf(selectedTile) - tiles.indexOf(previousSelected) <= 10)
                                || (tiles.indexOf(previousSelected) - tiles.indexOf(selectedTile) == 1
                                || tiles.indexOf(previousSelected) - tiles.indexOf(selectedTile) == -1)) {
                            if (previousSelected.isPlayerTile()) {
                                selectedTile.unselect();
                                previousSelected.setPlayer(0);
                            }
                        } else {
                            previousSelected.unselect();
                        }

                        secondSelection = false;

                    }

                    if (selectedTile.isPlayerTile()) {
                        if (previousSelected.isSelected()) {
                            if (tiles.indexOf(selectedTile) % 9 != 0) {
                                if (tiles.indexOf(previousSelected) - tiles.indexOf(selectedTile) == 8
                                        && !tiles.get(tiles.indexOf(selectedTile) - 8).isPlayerTile()) {

                                    tiles.get(tiles.indexOf(selectedTile) - 8).unselect();
                                    previousSelected.setPlayer(0);

                                } else if (tiles.indexOf(selectedTile)
                                        - tiles.indexOf(previousSelected) == 10
                                        && !tiles.get(tiles.indexOf(selectedTile) + 10).isPlayerTile()) {

                                    tiles.get(tiles.indexOf(selectedTile) + 10).unselect();
                                    previousSelected.setPlayer(0);

                                } else if (tiles.indexOf(selectedTile)
                                        - tiles.indexOf(previousSelected) == -10
                                        && !tiles.get(tiles.indexOf(selectedTile) - 10).isPlayerTile()) {
                                    tiles.get(tiles.indexOf(selectedTile) - 10).unselect();
                                    previousSelected.setPlayer(0);
                                } else if (tiles.indexOf(previousSelected)
                                        - tiles.indexOf(selectedTile) == -8
                                        && !tiles.get(tiles.indexOf(selectedTile) + 8).isPlayerTile()) {
                                    tiles.get(tiles.indexOf(selectedTile) + 8).unselect();
                                    previousSelected.setPlayer(0);
                                } else {
                                    previousSelected.unselect();
                                }
                            } else {
                                previousSelected.unselect();
                            }

                        }
                        secondSelection = false;
                    }
                }
                checkIfScored();
            }
        }
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public static int getTileSize() {
        return tileSize;
    }
}

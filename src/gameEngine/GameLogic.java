/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameEngine;

import java.util.ArrayList;

/**
 * @author Sebastian Galli
 * @author Raphael Büchler
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

            //TODO: End the game
//
//            if (tile.getStatus() == TileStatus.PLAYER1) {
//            } else if (tile.getStatus() == TileStatus.PLAYER2) {
//            }
        }
    }


    private Tile previousSelection;
    private boolean secondClick = false;

    // step() wird aufgerufen sobald ein Spieler mit der Maus klickt.
    // step() lauft dann einmal durch die Logik des Spiels. In ihr sind alle
    // möglichen Spielzüge definiert.
    public void step() throws IndexOutOfBoundsException {

        for (Tile currentSelection : tiles) {
            if (currentSelection.isOnTile(input.getX(), input.getY(), tileSize) && !currentSelection.isLocked()) {
                if (!secondClick) {
                    if (currentSelection.isPlayerTile()) {
                        previousSelection = currentSelection;
                        secondClick = true;
                        currentSelection.select();
                    }
                } else {
                    if (currentSelection == previousSelection) {
                        currentSelection.unselect();
                    }

                    if (!currentSelection.isPlayerTile()) {
                        if ((tiles.indexOf(previousSelection) - tiles.indexOf(currentSelection) <= 10 && tiles.indexOf(previousSelection) - tiles.indexOf(currentSelection) >= 8)
                                || (tiles.indexOf(currentSelection) - tiles.indexOf(previousSelection) >= 8 && tiles.indexOf(currentSelection) - tiles.indexOf(previousSelection) <= 10)
                                || (tiles.indexOf(currentSelection) - tiles.indexOf(previousSelection) >= 8 && tiles.indexOf(currentSelection) - tiles.indexOf(previousSelection) <= 10)
                                || (tiles.indexOf(previousSelection) - tiles.indexOf(currentSelection) == 1
                                || tiles.indexOf(previousSelection) - tiles.indexOf(currentSelection) == -1)) {
                            currentSelection.setPlayer(previousSelection.getPlayer());
                            previousSelection.setPlayer(0);
                        } else {
                            previousSelection.unselect();
                        }
                    }

                    if (currentSelection.isPlayerTile()) {
                        if (previousSelection.isSelected()) {
                            if (tiles.indexOf(currentSelection) % 9 != 0) {
                                if (tiles.indexOf(previousSelection) - tiles.indexOf(currentSelection) == 8
                                        && !tiles.get(tiles.indexOf(currentSelection) - 8).isPlayerTile()) {

                                    if(currentSelection.getPlayer() != previousSelection.getPlayer()) {
                                        currentSelection.setPlayer(0);
                                    }

                                    tiles.get(tiles.indexOf(currentSelection) - 8).setPlayer(previousSelection.getPlayer());
                                    previousSelection.setPlayer(0);

                                } else if (tiles.indexOf(currentSelection)
                                        - tiles.indexOf(previousSelection) == 10
                                        && !tiles.get(tiles.indexOf(currentSelection) + 10).isPlayerTile()) {

                                    if(currentSelection.getPlayer() != previousSelection.getPlayer()) {
                                        currentSelection.setPlayer(0);
                                    }

                                    tiles.get(tiles.indexOf(currentSelection) + 10).setPlayer(previousSelection.getPlayer());
                                    previousSelection.setPlayer(0);

                                } else if (tiles.indexOf(currentSelection)
                                        - tiles.indexOf(previousSelection) == -10
                                        && !tiles.get(tiles.indexOf(currentSelection) - 10).isPlayerTile()) {

                                    if(currentSelection.getPlayer() != previousSelection.getPlayer()) {
                                        currentSelection.setPlayer(0);
                                    }

                                    tiles.get(tiles.indexOf(currentSelection) - 10).setPlayer(previousSelection.getPlayer());
                                    previousSelection.setPlayer(0);

                                } else if (tiles.indexOf(previousSelection)
                                        - tiles.indexOf(currentSelection) == -8
                                        && !tiles.get(tiles.indexOf(currentSelection) + 8).isPlayerTile()) {

                                    if(currentSelection.getPlayer() != previousSelection.getPlayer()) {
                                        currentSelection.setPlayer(0);
                                    }

                                    tiles.get(tiles.indexOf(currentSelection) + 8).setPlayer(previousSelection.getPlayer());
                                    previousSelection.setPlayer(0);

                                } else {
                                    previousSelection.unselect();
                                }
                            } else {
                                previousSelection.unselect();
                            }

                        }
                    }
                    secondClick = false;
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

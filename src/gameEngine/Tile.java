package gameEngine;

/**
 *
 * @author Sebastian Galli
 */
public class Tile {
    private int x;
    private int y;
    private TileStatus status;

    public Tile(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
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
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the status
     */
    public TileStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(TileStatus status) {
        this.status = status;
    }
}

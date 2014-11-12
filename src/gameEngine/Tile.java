package gameEngine;

/**
 *
 * @author Sebastian Galli
 */
public class Tile {
	private int x;
	private int y;
	private TileStatus status;
	private boolean selected = false;
	private int player;
    boolean locked = false;
	
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	private void updateStatus() {
		switch (player) {
		case 0:
			this.status = TileStatus.EMPTY;
            this.selected = false;
			break;
		
		case 1:
			if(selected) {
				this.status = TileStatus.PLAYER1SELECTED;
			} else {
				this.status = TileStatus.PLAYER1;
			}
			break;
			
		case 2:
			if(selected) {
				this.status = TileStatus.PLAYER2SELECTED;
			} else {
				this.status = TileStatus.PLAYER2;
			}
			
			break;
		}
	}
	
	public void setPlayer (int player) {
		this.player = player;
		updateStatus();
	}

	public boolean isOnTile(int inputX, int inputY, int tilesize) {
		return inputX > x * tilesize && inputX < (x+1) * tilesize
				&& inputY > y * tilesize && inputY < (y+1) * tilesize;
	}
	
	public void select() {
		selected = true;
		updateStatus();
	}

    public void unselect() {
        selected = false;
        updateStatus();
    }

    public int getPlayer() {
        return player;
    }

    public boolean isSelected() {
        return selected;
    }
	
	public boolean isPlayerTile() {
		return player!=0;
	}
	
	public void lock() {
		this.status = TileStatus.LOCKED;
        locked = true;
	}

    public boolean isLocked() {
        return locked;
    }

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	/**
	 * @return the status
	 */
	public TileStatus getStatus() {
		return status;
	}
}

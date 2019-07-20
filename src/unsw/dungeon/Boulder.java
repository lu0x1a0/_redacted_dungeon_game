package unsw.dungeon;

import java.util.ArrayList;

import javafx.scene.image.ImageView;

public class Boulder extends Movable {

	public Boulder(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean checkPositionAvail() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
    public void moveUp() {
        if (getY() > 0 ) { 
			if(dungeon.ispassable(getX(), getY() - 1) == true) {
	    		int oldY = getY();
	        	y().set(oldY - 1);
	        	notifyObservers(this,new Coord(getX(),oldY));
			}
        }
    }
    @Override
    public void moveDown() {
        if (getY() < dungeon.getHeight() - 1) {
	        if(dungeon.ispassable(getX(), getY() + 1) == true) {
        		int oldY = getY();
	        	y().set(oldY + 1);
	        	notifyObservers(this,new Coord(getX(),oldY));
	        }
        }
    }
    @Override
    public void moveLeft() {
    	if (getX() > 0) { 
			if(dungeon.ispassable(getX() - 1, getY()) == true) {
	    		int oldx = getX();
		    	x().set(oldx - 1);
		    	notifyObservers(this,new Coord(oldx,getY()));
			}
    	}
    }
    @Override
    public void moveRight() {
        if (getX() < dungeon.getWidth() - 1) { 
			if(dungeon.ispassable(getX() + 1, getY()) == true) {
	        	int oldx = getX();
	        	x().set(oldx + 1);
	        	notifyObservers(this,new Coord(oldx,getY()));
			}
        }
        
    }

	@Override
	public boolean ispassable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void react(Entity e) {
		if(e instanceof Player) {
			if( ((Player) e).getX()<getX() ) {
				moveRight();
			}else if(((Player) e).getX()>getX()) {
				moveLeft();
			}else if(((Player) e).getY()<getY()) {
				moveDown();
			}else if(((Player) e).getY()>getY()) {
				moveUp();
			}
		}
		else if(e instanceof Bomb) {
			
		}
	}

}

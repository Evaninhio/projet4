package game.components;

import java.util.*;

public class Path{
	
	
	/*attributes*/
	private int w = Layout.w;
	private int h = Layout.h;
	private int offset = Layout.offset;
	static HashMap<Integer, BoardSquare> path;
	
	/*Constructor*/
	
	public Path(){
		Path.path = new HashMap<Integer, BoardSquare>();
		this.Initializer();
	}
	
	/*Methods*/
	
	public void printThePathInConsole() {
		for(int i=0; i<52; i++){
			System.out.println(this.getPathMap().get(i).ToString());
		}
	}
	
	
	public void Initializer() {
		for(int i=0; i<6; i++) {
			this.getPathMap().put(i+1, new BoardSquare(offset+w*i, offset+6*h, i+1));
			this.getPathMap().put(i+20, new BoardSquare(offset+w*(9+i), offset+6*h, i+20));
			this.getPathMap().put(51-i, new BoardSquare(offset+w*i, offset+8*h, 51-i));
			this.getPathMap().put(32-i, new BoardSquare(offset+w*(9+i), offset+8*h, 32-i));
		}
		
		for(int i=0; i<6; i++) {
			this.getPathMap().put(12-i, new BoardSquare(offset+w*6, offset+h*i, 12-i));
			this.getPathMap().put(45-i, new BoardSquare(offset+w*6, offset+h*(9+i), 45-i));
			this.getPathMap().put(14+i, new BoardSquare(offset+w*8, offset+h*i, 14+i));
			this.getPathMap().put(33+i, new BoardSquare(offset+w*8, offset+h*(9+i), 33+i));
		}
		
		this.getPathMap().put(0, new BoardSquare(offset, offset+7*h, 0));
		this.getPathMap().put(13, new BoardSquare(offset+7*w, offset, 13));
		this.getPathMap().put(26, new BoardSquare(offset+14*w, offset+7*h, 26));
		this.getPathMap().put(39, new BoardSquare(offset+7*w, offset+14*h, 39));
		
	}

	public HashMap<Integer, BoardSquare> getPathMap() {
		return path;
	}
}


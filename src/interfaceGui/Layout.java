package interfaceGui;

import java.awt.Color;
import java.awt.Graphics;

public class Layout{
		
	static int w, h, offset;
	
	public Layout(int width, int height, int offset){
		Layout.w = width;
		Layout.h = height;
		Layout.offset = offset;
		
	}
	
	public void draw(Graphics g) {
			

		int[] xBluePolygon = {offset+6*w, offset+6*w, offset+(15*w)/2};
		int[] yBluePolygon = {offset+6*h, offset+9*h, offset+(15*h)/2};

		int[] xYellowPolygon = {offset+6*w, offset+9*w, offset+(15*w)/2};
		int[] yYellowPolygon = {offset+6*h, offset+6*h, offset+(15*h)/2};

		int[] xRedPolygon = {offset+9*w, offset+9*w, offset+(15*w)/2};
		int[] yRedPolygon = {offset+6*h, offset+9*h, offset+(15*h)/2};

		int[] xGreenPolygon = {offset+9*w, offset+6*w, offset+(15*w)/2};
		int[] yGreenPolygon = {offset+9*h, offset+9*h, offset+(15*h)/2};
		
				
		/*Border adjustment*/
		
		g.setColor(Color.BLACK);
		g.drawRect(offset, offset, 15*w, 15*h);
		g.drawRect(offset+w, offset+6*h, w, h);
		g.drawRect(offset+8*w, offset+h, w, h);
		g.drawRect(offset+13*w, offset+8*h, w, h);
		g.drawRect(offset+6*w, offset+13*h, w, h);
		g.drawRect(offset+w, offset+h, 4*w, 4*h);
		g.drawRect(offset+10*w, offset+h, 4*w, 4*h);
		g.drawRect(offset+10*w, offset+10*h, 4*w, 4*h);
		g.drawRect(offset+w, offset+10*h, 4*w, 4*h);
		
		g.drawPolygon(xBluePolygon,yBluePolygon,3);
		g.drawPolygon(xYellowPolygon,yYellowPolygon,3);
		g.drawPolygon(xRedPolygon,yRedPolygon,3);
		g.drawPolygon(xGreenPolygon,yGreenPolygon,3);
	}
}

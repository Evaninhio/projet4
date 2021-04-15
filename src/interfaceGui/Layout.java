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
			
		/* Font grid */
		
		for(int i=0;i<15;i++) {
			for(int j=0;j<15;j++) {
				g.setColor(Color.WHITE);
				g.fillRect(w*i+offset, j*h+offset, w, h);
				g.setColor(Color.BLACK);
				g.drawRect(w*i+offset, j*h+offset, w, h);
			}
		}

		/*Layout of BLUE Player*/
		
		g.setColor(Color.BLUE);
		g.fillRect(offset, offset, 6*w, 6*h);
		g.fillRect(offset+w, offset+6*h, w, h);
		
		g.clearRect(offset+w, offset+h, 4*w, 4*h);
		
						
		int[] xBluePolygon = {offset+6*w, offset+6*w, offset+(15*w)/2};
		int[] yBluePolygon = {offset+6*h, offset+9*h, offset+(15*h)/2};
		g.fillPolygon(xBluePolygon,yBluePolygon,3);
		
		for(int i=1;i<6;i++) {
			g.fillRect(offset+w*i, offset+7*h, w, h);
		}
		
		/*Layout of YELLOW Player*/
		
		g.setColor(Color.YELLOW);
		g.fillRect(offset+9*w+1, offset, 6*w, 6*h);
		g.fillRect(offset+8*w+1, offset+h, w, h);
		
		g.clearRect(offset+10*w, offset+h, 4*w, 4*h);

		int[] xYellowPolygon = {offset+6*w, offset+9*w, offset+(15*w)/2};
		int[] yYellowPolygon = {offset+6*h, offset+6*h, offset+(15*h)/2};
		g.fillPolygon(xYellowPolygon,yYellowPolygon,3);
		
		for(int i=1;i<6;i++) {
			g.fillRect(offset+7*w+1, offset+i*h, w, h);
		}
		
		/*Layout of RED Player*/
		
		g.setColor(Color.RED);
		g.fillRect(offset+9*w+1, offset+9*h+1, 6*w, 6*h);
		g.fillRect(offset+13*w+1, offset+8*h+1, w, h);
		
		g.clearRect(offset+10*w, offset+10*h, 4*w, 4*h);
		
		int[] xRedPolygon = {offset+9*w, offset+9*w, offset+(15*w)/2};
		int[] yRedPolygon = {offset+6*h, offset+9*h, offset+(15*h)/2};
		g.fillPolygon(xRedPolygon,yRedPolygon,3);
		
		for(int i=1;i<6;i++) {
			g.fillRect(offset+8*w+i*w+1, offset+7*h+1, w, h);
		}
		
		/*Layout of GREEN Player*/
		
		g.setColor(Color.GREEN);
		g.fillRect(offset, offset+9*h+1, 6*w, 6*h);
		g.fillRect(offset+6*w, offset+13*h+1, w, h);
		
		g.clearRect(offset+w, offset+10*h, 4*w, 4*h);
		
		int[] xGreenPolygon = {offset+9*w, offset+6*w, offset+(15*w)/2};
		int[] yGreenPolygon = {offset+9*h, offset+9*h, offset+(15*h)/2};
		g.fillPolygon(xGreenPolygon,yGreenPolygon,3);
		
		for(int i=1;i<6;i++) {
			g.fillRect(offset+7*w, offset+8*h+i*h+1, w, h);
		}
		
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
		
		for(int i=0;i<2;i++) {
			g.drawRect(offset+(3*w)/2+9*w*i, offset+(3*h)/2, w, h);
			g.drawRect(offset+(7*w)/2+9*w*i, offset+(3*h)/2, w, h);
			g.drawRect(offset+(3*w)/2+9*w*i, offset+(7*h)/2, w, h);
			g.drawRect(offset+(7*w)/2+9*w*i, offset+(7*h)/2, w, h);
		}
		
		for(int j=0;j<2;j++) {
			int i=1;
			g.drawRect(offset+(3*w)/2+9*w*j, offset+(3*h)/2+9*h*i, w, h);
			g.drawRect(offset+(7*w)/2+9*w*j, offset+(3*h)/2+9*h*i, w, h);
			g.drawRect(offset+(3*w)/2+9*w*j, offset+(7*h)/2+9*h*i, w, h);
			g.drawRect(offset+(7*w)/2+9*w*j, offset+(7*h)/2+9*h*i, w, h);
		}
				
		for(int i=1;i<6;i++) {
			g.drawRect(offset+w*i, offset+7*h, w, h);
			g.drawRect(offset+7*w, offset+i*h, w, h);
			g.drawRect(offset+8*w+i*w, offset+7*h, w, h);
			g.drawRect(offset+7*w, offset+8*h+i*h, w, h);
		}
	}
}

package lineSegment;

import java.util.ArrayList;

public class LineSegment {
	// Segment Ingredient class --> represent ingredient of line segment (gradient, x_intercept, y_intercept)
	static class SegmentIngredient {
		// constructor for "ax+by+c = 0"
		SegmentIngredient(int x_coeff, int y_coeff, int c_coeff) {
			this.x_coeff = x_coeff;
			this.y_coeff = y_coeff;
			this.c_coeff = c_coeff;
		}
		
		// ax+by+c=0
		private int x_coeff;	// a
		private int y_coeff;	// b
		private int c_coeff;	// c
		
		// gradient --> only use "y=ax+b"
		public int getXCoeff() {
			return x_coeff;
		}
		public int getYCoeff() {
			return y_coeff;
		}
		public int getCCoeff() {
			return c_coeff;
		}
		
		/*
		 * static method to make line ingredient with point p1, p2
		 * - calculate x_coeff, y_coeff, c_coeff
		 */
		public static SegmentIngredient makeIngredient(Point p1, Point p2) {
			int x_coeff = p2.y - p1.y;
			int y_coeff = p1.x - p2.x;
			int c_coeff = (p2.x - p1.x) * p1.y - (p2.y - p1.y)*p1.x;
			return new SegmentIngredient(x_coeff, y_coeff, c_coeff);
		}
	}
	
	ArrayList<Point> points = new ArrayList<Point>();	// points contained in line segment.
	SegmentIngredient ingredient;
	
	// make line with 2 points
	public LineSegment(Point p1, Point p2) {
		points.add(p1); points.add(p2);
		ingredient = SegmentIngredient.makeIngredient(p1, p2);
	}
	
	// check that some point is on line segment. 
	public Boolean isOnLineSegment(Point p) throws Exception {
		int resultValue = ingredient.getXCoeff() * p.x + ingredient.getYCoeff() * p.y + ingredient.getCCoeff();
		return resultValue == 0;
	}
	
	public void addPoint(Point p) {
		points.add(p);
	}
	
	// fill out points that which line they are contained
	public void setPointsLineIndex(int index) {
		for(int i=0; i<points.size(); i++) {
			points.get(i).addSegmentIndex(index);
		}
	}
}

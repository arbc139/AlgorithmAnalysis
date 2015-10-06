package lineSegment;

import java.util.ArrayList;
import java.util.Comparator;

public class PointVerHash {
	int x, y;	// coordinates
	ArrayList<Integer> assignedSegmentIndexList = new ArrayList<Integer>();	// lineSegments index which line they are contained
	
	PointVerHash() {
		x = -1;		y = -1;
	}
	public PointVerHash(int x, int y) {
		this.x = x;	this.y = y;
	}
	
	public int getX() { return x; }
	public int getY() { return y; }
	
	public void addSegmentIndex(int index) {
		assignedSegmentIndexList.add(index);
	}
	public ArrayList<Integer> getSegmentList() { return assignedSegmentIndexList; }
	public Boolean isCrossingPoint() {
		return assignedSegmentIndexList.size() >= 2;
	}
	public Boolean isHavingSameLineSegment(Point p) {
		for(int i=0; i<assignedSegmentIndexList.size(); i++) {
			int myIndex = assignedSegmentIndexList.get(i);
			for(int j=0; j<p.assignedSegmentIndexList.size(); j++) {
				int compareIndex = p.assignedSegmentIndexList.get(j);
				if(myIndex == compareIndex) return true;
			}
		}
		return false;
	}
	public String showAssignedLineSegment() {
		if(this.assignedSegmentIndexList.isEmpty()) return "isEmpty";
		String returnValue = "";
		for(int i=0; i<this.assignedSegmentIndexList.size(); i++) {
			returnValue += assignedSegmentIndexList.get(i) + " ";
		}
		return returnValue;
	}
	
	public static class lexicographicCompare implements Comparator<Point> {
		// sorting point to lexicographic
		@Override
		public int compare(Point p1, Point p2) {
			// TODO Auto-generated method stub
			if(p1.x < p2.x) return -1;
			else if(p1.x > p2.x) return 1;
			else if(p1.y < p2.y) return -1;
			else if(p1.y > p2.y) return 1;
			else return 0;
		}
 
	}
}

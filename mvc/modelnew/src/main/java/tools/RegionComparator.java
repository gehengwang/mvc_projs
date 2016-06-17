package tools;

import java.util.Comparator;


public class RegionComparator implements Comparator<int []> {
	private int sort = 1;
	public static final int SORT_ASC = 1;
	public static final int SORT_DESC = -1;
	public RegionComparator(int sort) {
		this.sort = sort;
	}
	
	@SuppressWarnings("rawtypes")
	public static Comparator getRegionComparator(int sort){
		return new RegionComparator(sort);
	}
	
	@Override
	public int compare(int[] o1, int[] o2) {
		return sort == 1?compareAsc(o1, o2):compareDesc(o1, o2);
	}
	public int compareDesc(int[] o1, int[] o2){
		return -1*compareAsc(o1, o2);
	}
	public int compareAsc(int[] o1, int[] o2) {
		if (o1[0] < o2[0]) return -1;
		if (o1[0] == o2[0] && o1[1] <= o2[1]) return -1;
		return 1;
	}
};

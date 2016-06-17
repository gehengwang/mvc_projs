package tools;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.util.StringUtils;

public class CommonUtil {
	
	/**
	 * 根据区间字符串，生成区间（二维数组）。带最大值约束（最小值约束为0）
	 * @param regionsStr 区间字符串，形如：8,12;9,12;23,25
	 * @param maxIndex 区间坐标最大值约束
	 * @return 返回区间 [[8,12],[9,12],[23,25]]
	 */
	public static int[][] getRegionFromStrWithConstraint (String regionsStr,int maxIndex){
		return getRegionFromStrWithConstraint(regionsStr, 0, maxIndex);
	}
	
	/**
	 * 根据区间字符串，生成区间（二维数组）。带最大值约束，和最小值约束
	 * @param regionsStr 区间字符串，形如：8,12;9,12;23,25
	 * @param minIndex 区间坐标最小值约束
	 * @param maxIndex 区间坐标最大值约束
	 * @return 返回区间 [[8,12],[9,12],[23,25]]
	 */
	public static int[][] getRegionFromStrWithConstraint (String regionsStr,int minIndex,int maxIndex){
		if(StringUtils.isEmpty(regionsStr)){
			return new int[0][0];
		}
		String[] regionArray = regionsStr.split(";");
		int[][] regions = new int[regionArray.length][2];
		for (int i=0;i<regionArray.length;i++) {
			String[] regionStr = regionArray[i].split(",");
			int beginIndex = Integer.parseInt(regionStr[0]);
			int endIndex = Integer.parseInt(regionStr[1]);
			//如果区间内坐标前大后小，调换两个坐标位置
			if(beginIndex > endIndex){
				beginIndex += endIndex;
				endIndex = beginIndex-endIndex;
				beginIndex -= endIndex;
			}
			//约束区间不能越界[minIndex,maxIndex]
			if(beginIndex > maxIndex){
				beginIndex = maxIndex;
				endIndex = maxIndex;
			}else if(endIndex < minIndex){
				beginIndex = minIndex;
				endIndex = minIndex;
			}else{
				beginIndex = Integer.max(beginIndex, minIndex);
				endIndex = Integer.min(endIndex, maxIndex);
			}
			
			regions[i][0] = (beginIndex);
			regions[i][1] = (endIndex);
		};
		return regions;
	}
	
	/**
	 * 根据区间字符串，生成区间（二维数组）
	 * @param regionsStr 区间字符串，形如：8,12;9,12;23,25
	 * @return 返回区间 [[8,12],[9,12],[23,25]]
	 */
	public static int[][] getRegionFromStr (String regionsStr){
		if(StringUtils.isEmpty(regionsStr)){
			return new int[0][0];
		}
		String[] regionArray = regionsStr.split(";");
		int[][] regions = new int[regionArray.length][2];
		for (int i=0;i<regionArray.length;i++) {
			String[] regionStr = regionArray[i].split(",");
			regions[i][0] = Integer.parseInt(regionStr[0]);
			regions[i][1] = Integer.parseInt(regionStr[1]);
		};
		return regions;
	}
	
	/**
	 * 合并传入的区间，并倒序排列区间
	 * @param regions 区间字符串，形如： [[8,12],[9,12],[23,25]]
	 * @return 返回区间 [[23,25],[8,12]]
	 */
	@SuppressWarnings({ "unchecked" })
	public static int[][] megerRegions (int[][] regions){
		if(regions.length==0||regions[0].length != 2){
			return regions;
		}
		Arrays.sort(regions, RegionComparator.getRegionComparator(RegionComparator.SORT_ASC));
		ArrayList<int []> ret = new ArrayList<int[]>();
		int start = regions[0][0];
		int end = regions[0][1];
		for (int i = 1; i < regions.length; i++) {
		    if (regions[i][0] <= end) {
		        end = Math.max(end, regions[i][1]);
		    } else {
		    	int[] region = {start,end};
		        ret.add(region);
		        start = regions[i][0];
		        end = regions[i][1];
		    }
		}
		int[] region = {start,end};
		ret.add(region);
		int[][] regionsRs = new int[ret.size()][2];
		ret.toArray(regionsRs);
		Arrays.sort(regionsRs,RegionComparator.getRegionComparator(RegionComparator.SORT_DESC));;
		return regionsRs;
	}
	
	public static void main(String[] args) {
		int[][] rs = CommonUtil.megerRegions(getRegionFromStr("11,16;2,6;16,28"));
		for (int[] is : rs) {
			for (int i : is) {
				System.out.print(i+" ");
			}
			System.out.println();
		}
	}
}

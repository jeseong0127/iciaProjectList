package back;

import java.text.SimpleDateFormat;
import java.util.Date;

import data.DataAccessObject;

public class Reserve { //날짜선택 , 예약
	DataAccessObject dao;

	public String[][] entrance(String serviceCode , String request) {
		String[][] reserveDate = null;
		switch (serviceCode) {
		case "날짜선택":
			reserveDate = this.getDate(request);
			break;
		case "예약":
			this.getReserve(request);
			break;
		default:
			break;
		}return reserveDate;
	}
	/*특정 상점에 대한 예약 가능한 날짜 검색*/
	private String[][] getDate(String request) {
		dao = new DataAccessObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date ="";
		//현재 날짜 구하기
		Date today = new Date();
		int first = 0;
		for(int i=0; i<7; i++) {
			String compareDate = sdf.format(today);
			if(dao.getDate(request, compareDate)) {
				first++;
				date = (first==1)? compareDate : date + "," + compareDate;

			}
			today.setDate(today.getDate()+1);
		}
		// "2021-05-21, 2021-05-05"
		String[] dateList = date.split(","); 
		String[][] reserveDate = new String[dateList.length][1];
		for(int i=0; i<dateList.length; i++) {
			reserveDate[i][0] = dateList[i];

		}
		return reserveDate;
	}


	private void getReserve(String request) {
		System.out.println(request);
	}
}

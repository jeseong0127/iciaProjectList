package back;

import data.DataAccessObject;

public class Search {
	DataAccessObject dao;
	public Search() {

	}
	public String[][] entrance(int serviceCode, String request) {
		String[][] result = null;
		switch (serviceCode) {
		case 1: {
			result = this.compare(request);
			break;
		}
		case 2: {
			result = this.getMovie(request);
			break;
		}
		case 3: {
			result = this.getDate(request);
			break;
		}
		case 4:{
			result = this.getTime(request);
			break;
		}
		case 5:{
			result = this.getSeat(request);
			break;
		}
		default:
			break;
		}
		return result;
	}
	
	private String[][] compare(String request) {
		dao = new DataAccessObject();
		String[][] cinema = new String[25][4];
		dao.getCinema(request,cinema);
		return cinema;
	}
	
	private String[][] getMovie(String request) {
		dao = new DataAccessObject();
		String[][] movies = new String[110][3];
		dao.getMovie(request,movies);
		return movies;
	}
	
	private String[][] getDate(String request) {
		dao = new DataAccessObject();
		String[][] Date = new String[100][3];
	 	dao.getDate(request,Date);
		return Date;
	}
	
	private String[][] getTime(String request){
		dao = new DataAccessObject();
		String[][] times = new String[100][18];
		dao.getTime(request,times);
		return times;
	}
	private String[][] getSeat(String request){
		dao = new DataAccessObject();
		String[][] seat = new String[100][18];
		dao.getSeat(request,seat);
		return seat;
	}
}

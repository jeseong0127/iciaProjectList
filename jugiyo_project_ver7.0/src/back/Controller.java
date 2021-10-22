package back;
/* 1. 클라이언트로부터의 요청을 받아 해당 서비스로 분기
 * 2. 해당 서비스의 응답을 클라이언트에게 전달
 * 
 * */
public class Controller {

	public Controller() {

	}
	public String[][] entrance/*진입 메소드*/(String serviceCode, String request) {	
		Search search;
		Reserve reserve;
		String[][] menu = null;
		/* 1 : 비교검색
		 * 2 : 특정 상점의 메뉴 검색
		 * 3 : 특정 상점의 예약가능 날짜 검색
		 * 서비스 코드값 몇개로 할건지 정하기 / 검색이나 예약클래스 각자 어디로 갈건지
		 * */
		switch(serviceCode) {
		case "비교검색"://search
			search = new Search();
			menu = search.entrance(serviceCode, request);
			break;
		case "상점선택"://search
			search = new Search();
			menu = search.entrance(serviceCode, request);
			break;
		case "상점메뉴선택"://search
			search = new Search();
			menu = search.entrance(serviceCode, request);
			break;
		case "날짜선택"://reserve
			reserve = new Reserve();
			menu = reserve.entrance(serviceCode, request);
			break;
		case "예약"://reserve
			reserve = new Reserve();
			menu = reserve.entrance(serviceCode, request);
			break;
		default:
		}return menu;
	}
}

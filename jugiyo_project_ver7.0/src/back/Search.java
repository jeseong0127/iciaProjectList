package back;

import data.DataAccessObject;

public class Search {//비교검색 , 상점선택 , 상점메뉴선택

	DataAccessObject dao;

	public Search() {

	}

	public String[][] entrance(String serviceCode, String request) {
		String[][] menu = null;
		switch (serviceCode) {
		case "비교검색":
			menu = this.compare(request);	
			break;
		case "상점선택":
			this.storeSelect(request);
			break;
		case "상점메뉴선택":
			menu = this.getMenuList(request);
		default:
		}

		return menu;
	}
	
	
	private String[][] getMenuList(String storeCode){
		String[][] menuList = new String[300][4];
		DataAccessObject dao = new DataAccessObject();
		
		this.menuList(storeCode, menuList, dao);
		this.appendValue(menuList, dao);
		
		return menuList;
	}
	
	private void menuList(String storeCode, String[][] menuList, DataAccessObject dao) {
		dao.getMenuList(storeCode, menuList);
	}
	
	private void appendValue(String[][] menuList, DataAccessObject dao) {
		dao.appendValue(menuList);
	}
	/* 비교검색 
	 *   1 >> menu.txt >> 메뉴 + 가격  >> String[][] menu
	 *   2 >> store.txt >> 상점명 + 위치 >> menu
	 *   3 >> orders >> 평점 + 주문 >> menu
	 * */

	private String[][] compare(String request) {
		/* 비교검색 Control 메서드
		1 >> menu.txt >> 메뉴 + 가격  >> String[][] menu :: getMenu(String)
		2 >> store.txt >> 상점명 + 위치 >> menu :: getStore(String)
		3 >> orders >> 평점 + 주문 >> menu :: getValue(String, String)
		 */
		dao = new DataAccessObject();
		String[][] menu = new String[300][7];//실제 방의 개수값을 나타내기 때문에 [99],[6]이 아니다. 
		this.getMenu(request, menu, dao);
		this.getStore(menu, dao);
		//this.getValue(menu, dao);

		return menu;
	}



	public void getMenu(String req, String[][] m, DataAccessObject d) {
		d.getMenu(req,m);
	}

	public void getStore(String[][] menu, DataAccessObject dao) {
		dao.getStore(menu);
	}
	
	private void storeSelect(String request) {
		dao = new DataAccessObject();
		dao.getDate(request, "");
	}
//	private void storeMenu(String request) {
//		System.out.println();
//	}
}



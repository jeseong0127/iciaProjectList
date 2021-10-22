package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

//파일관리:read,write 기능 실행


public class DataAccessObject {
	FileReader reader;
	BufferedReader bReader;
	FileWriter writer;
	BufferedWriter bWriter;
	String[] fileInfo= {"C:\\coding\\database\\menu.txt",
			"C:\\coding\\database\\restaurant.txt",
	"C:\\coding\\database\\orders.txt"};



	public DataAccessObject() {

	}//▼해당 파일의 전체를 불러온다.
	private String[] fileReading(int index, int roomSize){
		String[] source = new String[roomSize];
		try {
			reader = new FileReader(this.fileInfo[index]);
			bReader = new BufferedReader(reader);
			int record = 0;
			while((source[record] = bReader.readLine())!=null) { // \n가 안나올때까지 반복한다 
				record++;
			}
			/*배열변수에 데이터 배치(index : 0,2,3), 패턴 파악이 핵심
				System.out.println(line.length); 1,김치찌개,13000  3개
				System.out.println(menu.length); 메뉴는 100개? //이중배열*/
			//1번 레코드에 전체 길이를 출력한다

		}
		catch (Exception e) {
			e.printStackTrace();
		}finally{ //StringBuffer 공간 효율을 위해...?
			try{
				bReader.close(); 
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		return source;
	}

	public void getMenu( String word, String[][] menu) { // 가게코드 , 메뉴명 , 가격
		String[] source = this.fileReading(0, menu.length); //fileReading메서드에 해당 파일의 전체 내용 수집 요청
		int menuIndex = -1;
		for(int record=0; record<source.length; record++) {
			if(source[record] !=null) { /*null이 아닐 때까지*/
				if(source[record].contains(word)) {
					//split
					menuIndex++;
					menu[menuIndex][0] = source[record].split(",")[0];
					menu[menuIndex][2] = source[record].split(",")[1];
					menu[menuIndex][3] = source[record].split(",")[2];
				}
			}
		}
	}

	public void getStore(String[][] menu) { // 명동찌개 강북
		String[] source = this.fileReading(1, menu.length);
		// 1 명동찌개 강북 010-2111-6304
		for(int record=0; record<menu.length; record++){
			if(menu[record][0]!=null) {
				for(int index=0; index<source.length; index++) {
					if(source[index]!=null) {
						String[] line = source[index].split(",");
						if(menu[record][0].equals(line[0])) {
							menu[record][1]=line[1];
							menu[record][4]=line[2];
							break;
						}
					}
				}
			}
		}
	}
	public void getValue() {

	}
	
	
	// 날짜
	public boolean getDate(String storecode, String compareDate) {
		//같은 날짜면 false   없으면 true
		boolean result = true;
		String[] reserved = this.fileReading(2, 300);

		for(String line : reserved) {
			if(line != null) {
				String[] reservedInfo = line.split(",");
				if(reservedInfo[0].equals(storecode)) {
					if(reservedInfo[2].equals(compareDate)) {
						result = false;
					}
				}
			}else {
				break;
			}
		}
		return result;
	}
	
	// 사용자가 요청한  상점에 대한 메뉴 불러오기
	public void getMenuList(String storeCode, String[][] menuList) { /*파라미터 안에있는것들은 쓸수 있는거*/
		String[] list = this.fileReading(0, menuList.length); /*파일리딩 메소드에 호출하면서 값을 요청한다*/
		int index = -1;
		for(String item : list) {
			if(item != null) {
				String[] it = item.split(",");
				if(it[0].equals(storeCode)) {
					index++;
					for(int col = 0; col<it.length; col++) {
						menuList[index][col] = it[col];

					}
				}
			}else {break;}
		}
	}
	
	// 평점
	public void appendValue(String[][] menuList) {
		String[] list = this.fileReading(2, menuList.length);
		int index = -1;
		for(String[] item : menuList) {
			if(item[0]!=null) {
				index++;
				for(String line : list) {
					if(line!=null) {
						String[] record = line.split(",");
						if(item[0].equals(record[0])) {
							if(item[1].equals(record[3])) {
								if(!record[5].equals("0.0")) {		
									menuList[index][3] =
											(menuList[index][3]==null)? record[5]:
												(Math.round(((Double.parseDouble(menuList[index][3])+
														Double.parseDouble(record[5]))/ 2)*10) / 10.0) + "";
								}
							}
						}
					}else {break;}      
				}
			}
		}
	}

}

/*Java IO
 *  BufferedReader Class
 *   + FlieReader(file_name)--> 음절단위로 읽음.
 *   + InputStream(file_name)--> byte단위로 읽음.
 *  BufferedWriter Class
 *   + FlieReader(file_name)--> 음절단위로 기록.
 *   + OutputStream
 * */
/*Variable--> 파괴성, 한번에 하나의 데이터만 저장(새로운 데이터 들어오면 기존 데이터 지워진다)
 * Array --> 여러개의 데이터를 하나의 참조변수로 접근 가능하게 하는 기술
 * index
 * */


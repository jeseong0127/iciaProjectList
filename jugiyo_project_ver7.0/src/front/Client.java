package front;

import java.util.Scanner;

import back.Controller;
import data.DataAccessObject;

public class Client {
	private Scanner scanner;
	//클래스     변수명
	/*constructor의 역할
	 * 1. 클래스 로딩 
	 *   + 클래스의 멤버(필드, 메소드 로딩)
	 * 2. 필드의 초기화 ->반드시 해야하는 것은 아님 (생성된 필드가 생성자에서 초기화할수있다)
	 * 3. 메모리에 로딩할 때 한 번만 올라간다 
	 * 4. 생성자는 초기값을 설정하기 위해 만들어야한다*/
	public Client() {
		this.scanner  = new Scanner(System.in);
		this.controller();
	}
	/** Data Flow Control : 데이터 흐름의 제어
	 * @restrict 	private
	 * @return 		void
	 * @method 	controller
	 * @param 		
	 * @author 	hoonzzang
	 */
	//Data Flow Management
	private void controller() { //this. controller();를 뜻함
		String title; // = new String (String은 =new 생략 가능)
		String word , storeSelect;
		Controller ctl = new Controller();//controller class를 호출
		String[][] menu;
		String[][] reserveDate;

		int storeIndex;
		int dateIndex;

		while(true) {

			while(true) {
				String test;

				while(true) {
					//타이틀 생성
					title = this.setTitle();
					/* Job 1-1 : front-end : 비교검색단어 수집 */
					word = this.pro1(title);
					/* Job 1-2 : back-end : 백엔드에 검색 요청 */ 
					menu= ctl.entrance("비교검색", word);
					/* Job 1-3 : front-end : 백엔드로부터 전달받은 응답개체 출력 */
						if(menu[0][1] == null) {
							continue;
						}else {
							break;
						}
					
				}
				while(true) {
					this.pro2(title, word, menu);
					/* job 2 : 계속 진행 질의*/
					//			if(this.userInput("예약하시겠습니까?(y/n) : ").equals("y")) break;
					/*job3 : 특정 상점의 예약가능날짜 조회*/
					/*job3-1 : 예약 예정인 상점코드 선택*/
					storeIndex = Integer.parseInt(userInput("__________store : "))-1;

					/*job3-2 : 사용자가 선택한 순번에 해당하는 상점코드를 조회*/
					/*job3-3 : 상점코드를 백엔드에 전달하여 예약가능 날짜 가져오기*/
					reserveDate = ctl.entrance("날짜선택",menu[storeIndex][0]);

					/*job3-4 : 예약가능 날짜 출력*/
					pro3(title, menu[storeIndex][1], reserveDate);
					//job4 예약자가 선택한 날짜의 인덱스
					dateIndex = Integer.parseInt(userInput("\n__________________________Date Select : "))-1;

					test = userInput("Y_다음단계로 / N_  전 단계로 / R_  초기화면  :  _______  ").toUpperCase();
					if(test.equals("Y")) {
						break;
					}else if(test.equals("N")) {
						continue;
					}else {
						break;
					}			
				}
				if(test.equals("Y")) {
					break;
				}else if(test.equals("R")) {
					continue;
				}

			}
			String[][] storeMenuList = ctl.entrance("상점메뉴선택", menu[storeIndex][0]);
			String[][] menuPro4;
			String[][] reserveInfo;
			String test;

			while(true) {
				String test1;
				while(true) {
					menuPro4 = this.pro4(title,menu[storeIndex][1], reserveDate[dateIndex][0], storeMenuList);

					//job5 메뉴 및 수량 선택 + 추가 / 예약정보 저장하기
					reserveInfo = pro5(title, menu[storeIndex][1], reserveDate[dateIndex][0], storeMenuList);

					test1 = userInput("Y_다음단계로 / N_  전 단계로  ").toUpperCase();
					if(test1.equals("Y")) {
						break;
					}else if(test1.equals("N")) {
						continue;
					}		
				}
				pro6(title, reserveInfo, menu[storeIndex][1], storeMenuList);

				this.display("위 정보로 예약하시겠습니까 ?\n");
				test= this.userInput("Y_다음단계로 / N_  메뉴재선택 / R_  초기화면  :  _______ ").toUpperCase();
				if(test.equals("Y")) {
					break;
				}else if(test.equals("N")) {
					continue;
				}else if(test.equals("R")) {
					break;
				}
			}
			if(test.equals("Y")) {
				break;
			}else if(test.equals("R")) {
				continue;
			}
		}//while
		System.out.println("끝이에용");
	}


	// 예약자 정보확인
	private void pro6(String title , String[][] reserveInfo , String storeName ,String[][] storeMenuList) {
		//		String[] sum = null;
		int sum = 0;
		this.display(title);
		this.display("[예약 정보를 확인해주세요!]\n");
		this.display("[" +storeName +"]\n");
		this.display("예약일자 : " + reserveInfo[0][3] +"\n");
		this.display("[ 선택 메뉴]\n");
		for(int i=0; i < reserveInfo.length; i++) {
			this.display(" " +(i+1) + ". "+ reserveInfo[i][4] +": "+ reserveInfo[i][5] +"개\n");
		}
		this.display("[ 예약 정보 ]\n");
		this.display("예약자 : "+ reserveInfo[0][1]+ "\n" + "연락처: "+ reserveInfo[0][2] + "\n");
		this.display("총 결제금액 : ");

		for(int i=0; i< reserveInfo.length; i++) {
			for(int j=0; j < storeMenuList.length; j++) {
				if(reserveInfo[i][4].equals(storeMenuList[j][1])){
					sum = sum + (Integer.parseInt(storeMenuList[j][2]) * Integer.parseInt(reserveInfo[i][5])) ;
				}
			}
		}
		this.display(sum+"원\n");

	}

	private String[][] pro5(String title, String storeCode, String reserveDate, String[][] storeMenuList){
		String[][] reserveInfo = null;
		StringBuffer info = new StringBuffer();
		//1,2 (김치찌개, 수량)   1, 훈이네, 01021116304, 2021-04-26, 김치찌개, 2, 2.0
		while(true) {
			info.append(storeCode + ",,," + reserveDate);
			info.append(("," + storeMenuList[Integer.parseInt(this.userInput("   ___메뉴선택 : "))-1][1]));
			info.append(("," + this.userInput("    ___수량 : ")));
			info.append(",0.0");

			if(this.userInput("       ___Continue(y/n)?").equals("n")) {break;}
			else { info.append(":");
			}
		}
		reserveInfo = new String[info.toString().split(":").length][];
		for(int index = 0; index<reserveInfo.length; index++) {
			reserveInfo[index] = (info.toString().split(":")[index].split(","));
		}

		this.display(title);
		this.display("[예약 정보를 입력해주세요!]\n");
		this.display("[ 예약 정보 ]\n");
		this.display("예약한 날짜 : " + reserveDate + "\n");
		this.display("[ 선택 메뉴] \n");
		for(int i=0; i < reserveInfo.length; i++) {
			this.display(" " +(i+1) + ". "+ reserveInfo[i][4] +": "+ reserveInfo[i][5] +"개\n"); // 김치찌개 2
		}

		reserveInfo[0][1] = userInput("예약자 : " );

		//		reserveInfo[0][2] = userInput("연락처 :  ");

		while(true) {
			reserveInfo[0][2]  = userInput("연락처 :  ");
			if(isPhone(reserveInfo[0][2])) {break;}
			display("연락처 형식에 맞게 입력해주세요(ex)01011112222)\n");
		}

		return reserveInfo;
	}

	// 메뉴 선택
	//타이틀        가게명           예약한 날짜        선택한 가게의 메뉴
	private String[][] pro4(String title, String storeName, String date, String[][] storeMenuList) {
		int index = 0;
		this.display(title);
		this.display("[ " +  storeName + " ]  예약일자 : " + date + "\n");
		this.display(" -----------------------------------\n");
		this.display("   순번      메뉴      가격     평점        \n"); // 상점코드 , 메뉴 , 가격 , 평점
		this.display(" -----------------------------------\n");
		for(String[] record : storeMenuList) {
			index++;
			int colIndex = 0;
			if(record[0] != null) {
				for(String item : record) {
					colIndex++;
					if(colIndex==1) {
						this.display("   ");
						this.display(index+" ");
						this.display("\t");
					}else if(colIndex==2 || colIndex == 3) {
						this.display(""+item);
						this.display((item.length()>=6)?"\t":(item.length()<=2)? "\t\t" : "\t\t");
					}else if(colIndex==4) {
						if(item == null) {item = "0.0";}
						this.display(item);
						this.display("\n");

					}
				}
			}
		}
		this.display(" -----------------------------------\n");
		return storeMenuList;
	}



	/*예약가능 날짜 출력 및 예약날짜 선택*/
	private void pro3(String title, String store, String[][] reseverDate) {
		this.display(title);
		this.display("[ " +  store + " ] _____\n");
		this.display(" -----------------------------------\n");
		this.display("             예약 가능 날짜           \n");
		this.display(" -----------------------------------\n");
		for(int i = 0; i<reseverDate.length; i++) {
			System.out.println(("         ")+(i+1)+(". ") + reseverDate[i][0]);
		}

	}




	private String pro1(String title) {
		//타이틀 출력
		this.display(title);
		//검색데이터 입력(입력항목 출력 + 사용자 입력) ->검색명은 한번밖에 사용 안하기 때문에 저장할 이유x
		return this.userInput(" 비교검색 상품명 : ");
	}

	private void pro2(String title, String word, String[][] menu) {
		String goods;
		//타이틀 출력
		this.display(title);
		//사용자 검색 요청 단어 출력
		this.display("상품명 :" + word + "\n");
		//메뉴리스트 타이틀 출력
		this.display("               [예약하실 번호를 선택해주세요!]\n ");
		this.display("----------------------------------------------------------\n");
		this.display(" 순번     상호      상품     가격    위치     평점     주문 \n");
		this.display("----------------------------------------------------------\n");
		//메뉴리스트 출력

		for(int i=0; i<menu.length; i++) {//menu[record]
			if(menu[i][1] == null) {
				break;
			}
			for(int menuIndex=0; menuIndex<menu[i].length; menuIndex++) {//menu[record][index]
				goods=menu[i][menuIndex];
				goods=(menuIndex==0)?" "+(i+1):goods;//int에서 String으로 만들어준다(+띄어쓰기 기능 포함)

				if(menuIndex==1 || menuIndex ==2) {
					if(goods.length()>4){
						goods = goods.substring(0,4);
					}
				}
				if(menuIndex==3) {
					//문자의 길이가 5미만이면 공백을 채워 5자리로 만들기
					if(goods.length()<5) {
						for(int space=0; space<5-goods.length(); space++) {
							goods = " "+goods;
						}
					}
				}
				if(menuIndex==5||menuIndex==6) {
					if(goods==null) {
						goods="0";
					}
				}

				this.display(goods);
				if(menuIndex==menu[i].length-1) {
					this.display("\n");
				}else {
					this.display("\t");
				}
			}
		}
	}




	private String setTitle() {
		StringBuffer title= new StringBuffer();
		title.append("┌──────────────────────────────────────────────┐\n");
		title.append("│                                              │\n");
		title.append("│                    jugiyo                    │\n");
		title.append("│              reservation_service             │\n");
		title.append("│                                              │\n");
		title.append("└──────────────────────────────────────────────┘\n");
		return title.toString();
	}

	/** 입력: 사용자로부터 발생하는 데이터 처리
	 * @restrict 	private
	 * @return 		String
	 * @method 	userInput
	 * @param 		text
	 * @author 	hoonzzang
	 */
	private String userInput(String text) {
		this.display(text);
		return scanner.next();
	}



	/** 화면출력 : 내용을 전달 받아 화면 출력
	 * @restrict 	private
	 * @return 		void
	 * @method 	display
	 * @param 		String text
	 * @author 	hoonzzang
	 */
	private void display(String text) {
		System.out.print(text);
	}

	/** 숫자 여부 판단 유효성 : 사용자로부터 발생하는 데이터 전달
	 * @restrict 	private
	 * @return 		boolean result
	 * @method 	isValidate
	 * @param 	String value
	 * @author 	hoonzzang
	 */
	private boolean isValidate(String value) {
		boolean result = false;

		try {
			Double.parseDouble(value);
			result = true;
		}catch(Exception ex) {
			result = false;
		}
		return result;
	}

	/** 숫자의 범위 유효성 : 사용자로부터 발생하는 데이터 전달
	 * @restrict 	private
	 * @return 		boolean result
	 * @method 	isRange
	 * @param   Double value, Double min, Double max
	 * @author 	hoonzzang
	 */
	private boolean isRange(Double value, Double min, Double max) {
		boolean result = false;
		if (value>=min && value<=max) {result = true;
		}else {
			result = false;
		}
		return result;

	}


	/** 연락처 유효성 : 연락처 데이터의 유효성 판단
	 * @restrict 	private
	 * @return 		boolean result
	 * @method 	isPhone
	 * @param 		String phone
	 * @author 	hoonzzang
	 */
	private boolean isPhone(String phone) {
		boolean result = false;
		/*문자의 길이는 11 && 010으로 시작
		String data type에서 length()를 지원 (길이를 리턴한다)
		=11은 11이라는 숫자를 집어넣게 되는거다!
		String data type에서 substring()를 지원(시작위치, 종료위치) (전체문자열에서 원하는 문자만 꺼내쓴다)
		012345678910 각 인덱스 자리 (length-1=max index)
		phone = "01021116304"; phone.substring(0,3);
	    자바는 종료위치가 그 앞단계까지 설정되기 때문에 010을 기본으로 설정하고 싶으면 0~3까지 해야함
		& 면 앞에가 false여도 두 조건 다 검사, && 면 앞쪽이 false면 뒤쪽은 검사 안하고 넘어감*/
		if(phone.length()==11 && phone.substring(0,3).equals("010")) {
			result = true;
		}

		return result;
	}

}

/*
 * 
 */

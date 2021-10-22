package front;

import java.util.Scanner;

import back.Controller;

public class Client {
	Scanner scanner;
	public Client() {
		scanner = new Scanner(System.in);
		this.controller();
	}
	public void controller() {
		String title = this.setTitle1();
		String title2 = this.setTitle2();
		String title3 = this.setTitle3();
		String[][] cinema;
		String[][] movies;
		String[][] date;
		String[][] time;
		String[][] seat;
		Controller ctl = new Controller();
		//job1
		String select1 = job1(title);
		ctl.entrance(0, select1);
		String select2 = job1_1(title2); //영화관검색
		cinema = ctl.entrance(1, select2);
		int select3 = Integer.parseInt(job1_2(title3,cinema)); // 영화관선택 
		movies = ctl.entrance(2, cinema[select3-1][0]); // O01
		//job2
		int select4 = Integer.parseInt(job2(movies));
		date = ctl.entrance(3, movies[select4-1][0]+","+movies[select4-1][1]); // O01,극한직업
		//코드 , 영화 , 날짜 
		//job3
		int select5 = Integer.parseInt(job3(date));
		time = ctl.entrance(4, movies[select4-1][0]+","+ movies[select4-1][1]); // O01,극한직업
		//job4
		int select6 = Integer.parseInt(job4(time));
		seat = ctl.entrance(5, date[select5-1][0]+","+ date[select5-1][1]+","+date[select5-1][2]); // O01,극한직업,2021-07-27
		//job5
		job5(seat);
		//job6
		String reserve = job6();
		//job7

		//		for(int record=0; record < seat.length; record++) {
		//			for(int index=0; index < seat[record].length; index++) {
		//				System.out.println(seat[record][index]);
		//			}
		//		}


		job7(seat,reserve,cinema[select3-1][1]+" "+cinema[select3-1][2],movies[select4-1][1]);
		
		

	}

	// 총 예약한 정보 + 예약자 정보 , 예약자 번호
	private void job7(String[][] seat , String reserve, String cinema,String movies) {
		int price = 0;
		StringBuffer sb = new StringBuffer();

		//		sb.append("["+cinema+"]");//애관극장 부산
		//		sb.append("["+movies+"]"); //영화이름
		//		sb.append("["+movies+"]")

		sb.append("["+cinema+"]\n");//애관극장 부산
		sb.append("["+seat[0][1]+"]\n"); //영화이름
		sb.append("["+seat[0][2].substring(0,10)+"]\n");
		sb.append("["+seat[0][2].substring(11,16)+"]\n");
		sb.append("["+reserve+"]\n"); // 1.청소년-A32.성인-A4

		for(String line:reserve.split(":")) { 
			if(reserve.contains("청소년")) {
				price += 9000;
			}else if(reserve.contains("성인")){
				price += 11000;
			}else if(reserve.contains("우대")) {
				price += 5000;
			}
		}
		sb.append("["+price+"]"); // 가격

		System.out.println(sb.toString());
	}

	private String job6() {
		String sPlus;
		StringBuffer sb = new StringBuffer();

		while(true) {
			String age = this.userInput("\n" + "▶▶▶▶ 연령 : "+ "\n" + "1.청소년 2.성인 3.우대" + "\n" + "▶▶▶▶ 연령 선택 : ");
			if(age.equals("1")) {
				age += ".청소년";
			}else if(age.equals("2")) {
				age += ".성인";
			}else {
				age += ".우대";
			}
			String seat = this.userInput("▶▶▶▶ 좌석 선택 : ");
			this.display("선택된 좌석 : " + age +"-"+"["+seat+"]"+"\n");
			sb.append(age+"-"+seat);


			if((sPlus = this.userInput("추가 선택하시겠습니까?(Y/N) : ")).equals("Y")) {
				sb.append(":");

			}else{
				break;
			}

		}
		System.out.println(sb.toString());

		return sb.toString();
	}

	private void job5(String[][] seat) {

		for(int record=0; record < seat.length; record++) {
			if(seat[record][0] != null) {
				for(int index=3; index < seat[record].length; index++) {
					if(index >= 3 &&  index <= 7) {
						System.out.print("\t" + "["+seat[record][index]+"]");
					}
					else if(index == 8 ) {
						System.out.print("\n");
						System.out.print("\t" + "["+seat[record][index]+"]");
					}
					else if(index >= 9 && index <= 12) {
						System.out.print("\t" + "["+seat[record][index]+"]");
					}
					else if(index == 13) {
						System.out.print("\n");
						System.out.print("\t" + "["+seat[record][index]+"]");
					}
					else if(index >= 14 && index <= 17) {
						System.out.print("\t" + "["+seat[record][index]+"]");
					}


					//					System.out.print("\t" + "["+seat[record][index]+"]");
				}
			}
		}
	}

	private String job4(String[][] times) {
		int count = 1;
		for(int record=0; record < times.length; record++) {
			if(times[record][0] != null) {
				System.out.print("\t" + count);
				for(int index=2; index < times[record].length; index++) {
					if(times[record][index] != null) {
						if(index ==2) {
							System.out.print("\t" + times[record][index].substring(11,16)+ "\t");

						}else {
							System.out.print("\t" + times[record][index]+ "\t");
						}

					}
				}

				System.out.print("\n");
				count++;
			}
		}

		return this.userInput("▶▶▶▶ select time : ");
	}

	private String job3(String[][] getDate) { // 순번 , 날짜  :: server 코드 영화 날짜 2021-05-05-15-30
		int count = 1;
		for(int record=0; record < getDate.length; record++) {
			if(getDate[record][0] != null) {
				System.out.print("\t" + count);
				for(int index=2; index < getDate[record].length; index++) {
					System.out.print("\t" + getDate[record][index].substring(0,10)+ "\t");
				}
				System.out.print("\n");
				count++;
			}
		}
		return this.userInput("▶▶▶▶ select date : ");
	}

	private String job2(String[][] movies) {
		int count = 1;
		for(int record=0; record < movies.length; record++) {
			if(movies[record][0] != null) {
				System.out.print("\t" + count);
				for(int index=1; index < movies[record].length; index++) {
					System.out.print("\t" + movies[record][index] + "\t");
				}
				System.out.print("\n");
				count++;
			}
		}
		return this.userInput(" ▶▶▶▶ selcet movie : ");
	}

	private String job1_2(String title,String[][] cinema) {
		this.display(title);
		int count = 1;
		for(int record=0; record < cinema.length; record++) {
			if(cinema[record][0] != null) {
				System.out.print("\t" + count);
				for(int index=1; index < cinema[record].length -1; index++) {
					System.out.print("\t" + cinema[record][index] + "\t");
				}
				System.out.print("\n");
				count++;
			}
		}
		return this.userInput("\n▶▶▶▶ search movie theater : ");
	}

	private String job1_1(String title) {
		this.display(title);
		return this.userInput("\n▶▶▶▶ cinema name : ");
	}

	private String job1(String title) {
		this.display(title);

		return this.userInput("\n▶▶▶▶Select : ");
	}

	private String setTitle3() {
		StringBuffer title = new StringBuffer();
		title.append("┌──────────────────────────────────────────────┐\n");
		title.append("│                                              │\n");
		title.append("│                Movie Theater                 │\n");
		title.append("│                                              │\n");
		title.append("└──────────────────────────────────────────────┘\n");
		return title.toString();
	}

	private String setTitle2() {
		StringBuffer title = new StringBuffer();
		title.append("┌──────────────────────────────────────────────┐\n");
		title.append("│                                              │\n");
		title.append("│                Search Cinema                 │\n");
		title.append("│                                              │\n");
		title.append("└──────────────────────────────────────────────┘\n");
		return title.toString();
	}

	private String setTitle1() {
		StringBuffer title = new StringBuffer();
		title.append("┌──────────────────────────────────────────────┐\n");
		title.append("│                                              │\n");
		title.append("│                    Select                    │\n");
		title.append("│                                              │\n");
		title.append("└──────────────────────────────────────────────┘\n");
		title.append("\t 1.영화별 예매\t\t2.극장별 예매 \n");
		return title.toString();
	}

	private String userInput(String text) {
		this.display(text);
		return scanner.next();
	}

	private void display(String text) {
		System.out.print(text);
	}
}

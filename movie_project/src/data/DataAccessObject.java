package data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DataAccessObject {
	FileReader reader;
	BufferedReader bReader;
	String[] fileInfo = {"C:\\coding\\database\\movie\\cinema.txt"
			,"C:\\coding\\database\\movie\\movie.txt"
			,"C:\\coding\\database\\movie\\movie_time.txt"
	};

	public String[] fileReading(int index , int roomSize) {

		String[] source = new String[roomSize];
		try {
			reader = new FileReader(fileInfo[index]);
			bReader = new BufferedReader(reader);
			int record = 0;
			while((source[record] = bReader.readLine()) != null) {
				record++;
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return source;
	}

	public void getCinema(String word , String[][] cinema){
		String[] source;
		source = fileReading(0, cinema.length); // source[0] = O01,애관극장,부산,990327  source[1] = O01,애관극장,부산,990327 
		int record = 0;
		//		for(String result1 : source) {
		//			System.out.println(result1);
		//		}


		for(String result : source) { // O01,애관극장,부산,990327
			if(result != null && result.contains(word)){
				cinema[record][0] = result.split(",")[0];
				cinema[record][1] = result.split(",")[1];
				cinema[record][2] = result.split(",")[2];
				cinema[record][3] = result.split(",")[3];
				record++;
			}
		}


	}

	public void getMovie(String request, String[][] movies) {
		String[] source;
		source = fileReading(1, movies.length);

		int record = 0;
		for(String movie : source) {
			if(movie != null) {
				if(movie.split(",")[0].equals(request)) {
					movies[record][0] = movie.split(",")[0];
					movies[record][1] = movie.split(",")[1];
					movies[record][2] = movie.split(",")[2];
					record++;
				}
			}
		}
	}

	public void getDate(String request,String[][] date) { // O01,극한직업
		String[] source;
		source = fileReading(2, date.length);

		int record = 0;

		for(String line : source) {
			if(line != null) {
				if(line.substring(0,line.indexOf(",",line.indexOf(",")+1)).equals(request)) { //O01,극한직,
					date[record][0] = line.split(",")[0];
					date[record][1] = line.split(",")[1];
					date[record][2] = line.split(",")[2]; // .substring(0,10)
					record++;
				}
			}
		}

	}
	public void getTime(String request , String[][] times) {
		
		String [] source = fileReading(2,times.length);

		int record = 0;
		int count = 0;
		
		for(String line : source) {
			if(line != null) {
				String[] test = line.split(","); // O01 극한직업 날짜 
				if(line.substring(0,line.indexOf(",",line.indexOf(",")+1)).equals(request)) { //O01.극한직업 // 순번 , 시간 , 좌석수 
					times[record][0] = line.split(",")[0];
					times[record][0] = line.split(",")[1];
					times[record][2] = line.split(",")[2];
					
					for(int index = 3; index < test.length; index++) {
						if(test[index].contains("N"))
							count++;
					}
					times[record][3] = count+"/15석";
					count = 0;
					record++;
					
				}
			}
		}		
		
	}
	
	public void getSeat(String request , String[][] seat) {
		
		String[] source = fileReading(2, seat.length);
		
		int record = 0;
		for(String line : source) {
			//O01,분노의 질주,2021-09-01-14-10,A1Y,
			if(line != null) {
				String[] test2 = line.split(",");
				if(line.substring(0, line.indexOf(",", line.indexOf(",", line.indexOf(",")+1)+1)).equals(request)){ //O01,극한직업,날짜(2021-07-27-05-05
					
					seat[record][0] = line.split(",")[0];
					seat[record][1] = line.split(",")[1];
					seat[record][2] = line.split(",")[2];
					
					for(int index = 3; index < test2.length; index++) {
						if(test2[index].contains("N")) {
							seat[record][index] = line.split(",")[index].substring(0,2);
						}else {
							seat[record][index] = "X";
						}
						
					}
					record++;
				}
			}
			
			
			
		}
		
	}

}

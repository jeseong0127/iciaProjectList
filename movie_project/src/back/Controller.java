package back;

public class Controller {

	public Controller() {
		
	}
	
	public String[][] entrance(int serviceCode,String request) {
		Search search;
		String[][] result = null;
		
		switch (serviceCode) {
		case 1: {
			search = new Search();
			result =search.entrance(serviceCode, request);
			break;
		}
		case 2:{
			search = new Search();
			result = search.entrance(serviceCode, request);
			break;
		}
		case 3:{
			search = new Search();
			result = search.entrance(serviceCode, request);
			break;
		}
		case 4:{
			search = new Search();
			result = search.entrance(serviceCode,request);
			break;
			
		}
		case 5:{
			search = new Search();
			result = search.entrance(serviceCode,request);
			break;
		}
		default:
			break;
		}
		return result;
		
	}
}

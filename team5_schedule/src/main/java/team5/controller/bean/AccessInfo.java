package team5.controller.bean;

import java.util.Date;

import lombok.Data;

@Data
public class AccessInfo {
	String uCode;
	String aCode;
	Date date;
	int method;
	String publicIp;
	String privateIp;
	String browser;
	
}

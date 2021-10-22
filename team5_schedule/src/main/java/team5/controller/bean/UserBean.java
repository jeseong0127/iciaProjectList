package team5.controller.bean;


import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UserBean {
	private String uCode;
	private String aCode;
	private String uName;
	private String uMail;
	
	private String stickerPath;
	private MultipartFile file1;
}

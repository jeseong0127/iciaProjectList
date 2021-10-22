package team5.controller.bean;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ScheduleBean {
	private String tid;
	private String tname;
	private int num;
	private String mbid;
	private String mbname;
	private String title;
	private String date; //
	private String location;
	private String contents;
	private String process; //
	private String pname;
	private String open; //
	private String oname;
	private String loop; //
	private String lname;
	
	private String stickerPath;
	private List<MultipartFile> files;
	
}
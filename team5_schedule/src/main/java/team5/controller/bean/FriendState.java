package team5.controller.bean;

import java.util.List;

import lombok.Data;

@Data
public class FriendState {
	private String mbid1;
	private String mbid2;
	private List<UserBean> mbid3; 
}

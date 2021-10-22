package team5.controller.bean;

import java.util.List;

import lombok.Data;

@Data
public class TeamBean {
	private String tid;
	private String tname;
	private String mbid;
	private List<TeamDetailBean> tdetails;
}

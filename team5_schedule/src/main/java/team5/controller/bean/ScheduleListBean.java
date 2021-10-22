package team5.controller.bean;

import java.util.List;

import lombok.Data;

@Data
public class ScheduleListBean {
	private List<ScheduleListBean> tid;
	private List<ScheduleListBean> tname;
	private List<ScheduleListBean> num;
	private List<ScheduleListBean> mbid;
	private List<ScheduleListBean> mbname;
	private List<ScheduleListBean> title;
	private List<ScheduleListBean> sdDate; //
	private List<ScheduleListBean> sdLocation;
	private List<ScheduleListBean> sdContents;
	private List<ScheduleListBean> sdProcess; //
	private List<ScheduleListBean> pname;
	private List<ScheduleListBean> sdOpen; //
	private List<ScheduleListBean> oname;
	private List<ScheduleListBean> sdLoop; //
	private List<ScheduleListBean> lname;
}

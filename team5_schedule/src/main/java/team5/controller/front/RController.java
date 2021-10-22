package team5.controller.front;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import team5.controller.bean.FriendState;
import team5.controller.bean.ScheduleBean;
import team5.controller.bean.TeamBean;
import team5.controller.bean.TeamDetailBean;
import team5.controller.bean.UserBean;
import team5.services.friends.friendsRelation;
import team5.services.schedule.ScheduleManagement;

@RestController
@RequestMapping("/schedule")
public class RController {
	
	@Autowired
	friendsRelation fr;
	
	@Autowired
	ScheduleManagement sm;
	
	@PostMapping("/teamList")
	public List<TeamBean> getTeamList(@RequestBody List<TeamBean> tl) {
		return fr.getTeam(tl.get(0));
	}
	
	@PostMapping("/memberList")
	public List<TeamDetailBean> getMemberList(@RequestBody List<TeamDetailBean> tl){
		return fr.getMember(tl.get(0));
	}
		
	@PostMapping("/addTeam")
	public List<TeamBean> addTeam(@RequestBody List<TeamBean> tl){
		return fr.addTeam(tl.get(0));
	}
	
	@PostMapping("/addFriends")
	public List<TeamDetailBean> addFriends(@RequestBody List<TeamDetailBean> tl){
		return fr.addFriends(tl.get(0));
	}
	
	//팀원 초대
	@PostMapping("/addFriendlist")
	public List<TeamDetailBean> addFriendlist(@RequestBody List<TeamBean> tl){
		return fr.addFriendsList(tl.get(0));
	}
	
	@PostMapping("/searchFriends")
	public List<TeamDetailBean> searchFriends(@RequestBody List<TeamDetailBean> tl){
		return fr.searchFriends(tl.get(0));
	}
	
	@PostMapping("/sendFriendEmail")
	public Map<String, String> sendFriendEmail(@RequestBody List<UserBean> tl){
		//System.out.println(tl.get(0).getUMail()); 성공
		return fr.sendFriendEmail(tl.get(0));
	}
	
	@PostMapping("/addFriendState")
	public List<FriendState> addFriendState(@RequestBody List<FriendState> tl){
		return fr.addFriendState(tl.get(0));
	}
	
	// 친구 초대
	@PostMapping("/inviteFriends")
	public Map<String, String> inviteFriends(@RequestBody List<FriendState> tl){
		return fr.inviteFriends(tl.get(0));
	}
	
	
	@PostMapping("/getSchedule")
	public List<ScheduleBean> getSchedule(@RequestBody List<ScheduleBean> sdb){
		//System.out.println(sdb.get(0).getDate()); 2021-08-14
		return sm.getSchedule(sdb.get(0));
	}
}

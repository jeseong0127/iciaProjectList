package team5.services.friends;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import team5.controller.bean.FriendState;
import team5.controller.bean.ScheduleBean;
import team5.controller.bean.TeamBean;
import team5.controller.bean.TeamDetailBean;
import team5.controller.bean.UserBean;
import team5.database.mapper.friendsMapper;
import team5.services.util.Encryption;
import team5.services.util.ProjectUtils;

@Service
public class friendsRelation implements friendsMapper{
	
	@Autowired
	SqlSessionTemplate sql;
	
	@Autowired
	Encryption enc;
	
	@Autowired
	ProjectUtils pu;
	
	@Autowired
	DataSourceTransactionManager tx;
	private DefaultTransactionDefinition def;
	private TransactionStatus status;
	
	@Autowired
	JavaMailSenderImpl javaMail;
	
	public friendsRelation() {
	}
	
	public List<TeamBean> getTeam(TeamBean tb) {
		List<TeamBean> teamList;
		teamList = sql.selectList("getTeam",tb);
		return teamList;
	}

	public List<TeamDetailBean> getMember(TeamDetailBean tdb) {
		List<TeamDetailBean> memberList;
		memberList = sql.selectList("getMember",tdb);
		try {
			for(int i=0; i < memberList.size(); i++) {
				memberList.get(i).setMbname(enc.aesDecode(memberList.get(i).getMbname(), memberList.get(i).getMbid()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return memberList;
	}

	@Transactional(rollbackFor = Exception.class)
	public List<TeamBean> addTeam(TeamBean tb) {
		try {
			tb.setMbid((String)pu.getAttribute("uCode"));
			System.out.println("뭐야"+(String)pu.getAttribute("uCode"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 명시적 transaction
		setTransactionConf(TransactionDefinition.PROPAGATION_REQUIRED,TransactionDefinition.ISOLATION_READ_COMMITTED,false);
		
		String number = "";
		//String teamNumber;
		if(sql.selectOne("getTeamNumber") == null) {
			number = "001";
		}else {
			// "004"
			//3 - number.length(); // 2
			String result = (Integer.parseInt(sql.selectOne("getTeamNumber"))+1) + "";
			for(int i=0; i < 3-result.length(); i++) {
				number += "0"; // 00
			}
			number += result;
		}
		
		System.out.println(number); //005
		tb.setTid(number);
		
		try {
			if(this.convertData(sql.insert("addTeam",tb))){
				this.convertData(sql.insert("addTeamDetail",tb));
				setTransactionResult(true);
			}
		}catch(Exception e){
			setTransactionResult(false);
			e.printStackTrace();
		}

		return this.getTeam(tb);
	}
	
	private boolean convertData(int data) {
		return data > 0?true:false;
	}
	
	private void setTransactionConf(int propagation,int isolationLevel,boolean isRead) {
		def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(propagation);
		def.setIsolationLevel(isolationLevel);
		def.setReadOnly(isRead);
		status = tx.getTransaction(def);
	}
	
	private void setTransactionResult(boolean isCheck) {
		if(isCheck) {
			tx.commit(status);
		}else {
			tx.rollback(status);
		}
	}

	public List<TeamDetailBean> addFriends(TeamDetailBean tb) {
		try {
			tb.setMbid((String)pu.getAttribute("uCode"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(tb.getMbid());
		List<TeamDetailBean> friendsList;
		friendsList = sql.selectList("getFriends",tb);
		
		System.out.println(friendsList);
		
		try {
			for(int i=0; i < friendsList.size(); i++) {
				friendsList.get(i).setMbname(enc.aesDecode(friendsList.get(i).getMbname(), friendsList.get(i).getMbid()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return friendsList;
	}
	
	
	public List<TeamDetailBean> addFriendsList(TeamBean tb) {
		//tb.getTdetails().get(0).setEmail("je_seong2@naver.com");
		//tb.getTdetails().get(1).setEmail("mywptjd@naver.com");
		// 고정값 X 수정필요 메일 전송
		//this.friendsAuth(tb.getTdetails(),tb.getTid());
		
		
		
		return tb.getTdetails();
	}
	
	
	// 메일 전송
	private void friendsAuth(List<TeamDetailBean> list, String tid) {
		String subject = "Invitation";
		String contents = "<a href='http://192.168.1.112/mailAuth?tid="+tid+"'>"+"인증해주세요"+"</a>";
		String from = "mywptjd0127@naver.com";
		
		String[] to = new String[list.size()];
		for(int index=0; index < list.size(); index++) {
			to[index] = list.get(index).getEmail();
		}
		
		MimeMessage mail = javaMail.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mail,"UTF-8");
			
		try {
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(contents,true);
			javaMail.send(mail);
		} catch (MessagingException e) {
			e.printStackTrace();
		}					
	}
	
	//초대장 전송 (회원가입 -> 다시 친구)
	private Map<String, String> friendsAuth(UserBean ub) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("message", ub.getUMail() + "님에게 친구요청 메일을 보냈습니다.");
		String subject = ub.getUCode() + "님의 친구요청";
		String contents = "<a href='http://211.222.245.154/joinForm'>"+ub.getUCode()+"님의 친구수락을 받아주세요."+"</a>";
		String from = "mywptjd0127@naver.com";
		
		String to = ub.getUMail();
	
		MimeMessage mail = javaMail.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mail,"UTF-8");
			
		try {
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(contents,true);
			javaMail.send(mail);
		} catch (MessagingException e) {
			map.put("message", "메일을 보내지 못했습니다.");
			e.printStackTrace();
		}
		
		return map;
	}
	
	public ArrayList<TeamDetailBean> searchFriends(TeamDetailBean tdb) {
		List<TeamDetailBean> list;
		ArrayList<TeamDetailBean> list2= new ArrayList<TeamDetailBean>();
		
		try {
			tdb.setMbid((String)pu.getAttribute("uCode"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}  
		
		list = sql.selectList("getFriendsList",tdb); // mb 추출
		
		for(int i = 0; i < list.size(); i++) { // 복호화
			try {
				list.get(i).setMbname(enc.aesDecode(list.get(i).getMbname(), list.get(i).getMbid()));
				list.get(i).setEmail(enc.aesDecode(list.get(i).getEmail(), list.get(i).getMbid()));
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		
		System.out.println(tdb.getWord());
		System.out.println(list);

		for(int i=0; i < list.size(); i++) { // 비교해서 담기
			if(list.get(i).getMbid().contains(tdb.getWord())||
					list.get(i).getMbname().contains(tdb.getWord())|| 
					list.get(i).getEmail().contains(tdb.getWord())) {
					list2.add(list.get(i));
					System.out.print(list2.size());
			}
		}
		
		System.out.println(list2);
		return list2;
	}

	
	// 새로운 친구요청 이메일 전송
	public Map<String, String> sendFriendEmail(UserBean ub) { // ub.uamil 보낼 이메일 담겨있음
		try {
			ub.setUCode((String)pu.getAttribute("uCode"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// insSendfr 추가하기 대기상태 0
		return this.friendsAuth(ub);
	}
	
	// 친구 받은 요청
	public List<FriendState> addFriendState(FriendState fs) {
		List<FriendState> list;
		try {
			fs.setMbid1((String)pu.getAttribute("uCode"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		list = sql.selectList("friendState", fs);
		
		if(list.get(0).getMbid2().equals("0")) {
			list.get(0).setMbid2("받은 요청이 없습니다");
		}
		
		return list;
	}

	public Map<String, String> inviteFriends(FriendState fs) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			fs.setMbid1((String)pu.getAttribute("uCode"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(this.convertData(sql.insert("inviteFriends",fs))) {
			map.put("mbid2", fs.getMbid2() + "님에게 초대를 보냈습니다");
		}else{
			map.put("mbid2", fs.getMbid2() + "님에게 초대를 보내지 못했습니다");
		}
		
		return map;
	}
	
}

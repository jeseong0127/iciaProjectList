package team5.services.auth;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import team5.controller.bean.AccessInfo;
import team5.controller.bean.MailBean;
import team5.controller.bean.ScheduleBean;
import team5.controller.bean.UserBean;

@Repository
public class AuthDAO {

	@Autowired
	SqlSessionTemplate sqlSession;
   

	 boolean isUserId(AccessInfo ai) {
		return convertBoolean(sqlSession.selectOne("isUserId",ai));
	}

	 String getUserPwInfo(AccessInfo ai) {
		return sqlSession.selectOne("getUserPwInfo",ai);
	}

	 boolean insAccessHistory(AccessInfo ai) {
		return convertBoolean(sqlSession.insert("insAccessHistory",ai));
	}
	
	 boolean insMembers(UserBean ub) {
		return convertBoolean(sqlSession.insert("insMembers",ub));
	}
	
	List<UserBean> selMemberInfo(AccessInfo ai){
		return sqlSession.selectList("selMemberInfo",ai);
	}
	
	// util
	private boolean convertBoolean(int value) {
		return value > 0?true:false;
	}

	boolean isLogout(AccessInfo ai) {
		return convertBoolean(sqlSession.insert("insAccessHistory",ai));
	}
	
	boolean logOutCheck(AccessInfo ai) {
		return sqlSession.selectOne("logOutCheck",ai);
	}

	String browserLogOut(AccessInfo ai) {
		return sqlSession.selectOne("browserselect", ai);
	}
	
	boolean browserInsert(AccessInfo ai) {
		return convertBoolean(sqlSession.insert("insAccessHistory",ai));
	}

	boolean browserCheck(AccessInfo ai) {
		return convertBoolean(sqlSession.selectOne("logOutCheck",ai));
	}

	boolean insTeamDetail(MailBean mb) {
		return convertBoolean(sqlSession.insert("insTeamDetail",mb));
	}

}

package team5.database.mapper;

import java.util.List;

import team5.controller.bean.AccessInfo;
import team5.controller.bean.UserBean;

public interface MybatisMapper {

	boolean isUserId(AccessInfo ai);
	boolean isAccess(AccessInfo ai);
	boolean insAccessHistory(AccessInfo ai);
	List<UserBean> selMemberInfo(AccessInfo ai);
	void insMembers(UserBean ub);
	boolean isLogout(AccessInfo ai);
	boolean logOutCheck(AccessInfo ai);
}

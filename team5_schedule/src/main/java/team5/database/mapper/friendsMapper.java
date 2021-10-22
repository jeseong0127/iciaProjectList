package team5.database.mapper;

import java.util.List;

import team5.controller.bean.TeamBean;
import team5.controller.bean.TeamDetailBean;

public interface friendsMapper {
	public List<TeamBean> getTeam(TeamBean tb);
	public List<TeamDetailBean> getMember(TeamDetailBean tdb);
	public List<TeamBean> addTeam(TeamBean tb);
}

package team5.services.schedule;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import team5.controller.bean.ScheduleBean;
import team5.database.mapper.scheduleMapper;
import team5.services.util.ProjectUtils;

@Service
public class ScheduleManagement implements scheduleMapper{
	
	@Autowired
	ProjectUtils pu;
	
	@Autowired
	SqlSessionTemplate sqlSession;
	
	public ScheduleManagement() {}

	// ALBUM
	public void pictures(ScheduleBean sdb) {
		for(int i=0; i< sdb.getFiles().size(); i++) {
			sdb.setStickerPath(pu.savingFile(sdb.getFiles().get(i)));
			sqlSession.insert("insAlbum",sdb);
		}
	}

	
	// SD + ALBUM
	public ModelAndView addSchedule(ScheduleBean sdb) {
		ModelAndView mav = new ModelAndView();
		try {
			sdb.setMbid((String)pu.getAttribute("uCode"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sdb.setNum(sqlSession.selectOne("selSeq")); // Seq
		
		sqlSession.insert("insSchedule",sdb); // SD
		
		if(sdb.getFiles() != null) {
			pictures(sdb); // ALBUM
		}
		
		mav.setViewName("newDashboard");
		
		return mav;
	}
	
	public List<ScheduleBean> getSchedule(ScheduleBean sdb) {
		List<ScheduleBean> list;
		//ArrayList<ScheduleBean> list2 = new ArrayList<ScheduleBean>();
		try {
			sdb.setMbid((String)pu.getAttribute("uCode"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		sdb.setDate(sdb.getDate().replace("-", ""));
		
		System.out.println("날짜야"+sdb.getDate());
		//list.addAll(sqlSession.selectList("getSchedule",sdb));
		
		list = sqlSession.selectList("getSchedule",sdb);
		
		System.out.println(list);
		return list;
	}
}

package team5.controller.front;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import lombok.Data;
import team5.controller.bean.AccessInfo;
import team5.controller.bean.MailBean;
import team5.controller.bean.ScheduleBean;
import team5.controller.bean.TeamDetailBean;
import team5.controller.bean.UserBean;
import team5.services.auth.Authentication;
import team5.services.schedule.ScheduleManagement;
import team5.services.util.Encryption;
import team5.services.util.ProjectUtils;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private Authentication auth;
	
	private ModelAndView mav;
	
	@Autowired
	private ProjectUtils pu;
	
	@Autowired
	private Encryption enc;
	
	@Autowired
	private ScheduleManagement smg;
	
	@RequestMapping(value = "/", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView home(@ModelAttribute AccessInfo ai) {
//		try {
//			if(pu.getAttribute("uCode") != null) {
//				mav.setViewName("dashboard");
//				mav.addObject("umail",enc.aesDecode((String)pu.getAttribute("umail"), (String)pu.getAttribute("uCode")));
//			}else {
//				mav.setViewName("login");
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return auth.rootCtl(ai);
	}
	
	@GetMapping(value = "/joinForm")
	public String joinForm() {
		return "join";
	}
	
	@PostMapping(value = "/login")
	public ModelAndView login2(@ModelAttribute AccessInfo ai, HttpServletRequest req) {
		mav = new ModelAndView();
		mav = auth.accessCtl(ai,req);
		return mav;
	}
	
	@PostMapping(value = "/join")
	public ModelAndView join(@ModelAttribute UserBean ub) {
		mav = new ModelAndView();
		mav = auth.joinCtl(ub);
		return mav;
	}
	
	@PostMapping(value = "/IsDup")
	@ResponseBody
	public String isDuplicateCheck(@ModelAttribute AccessInfo ai) {
		System.out.println(ai.getUCode());
		return auth.isDuplicateId(ai);
	}
	
	@RequestMapping(value = "/logOut")
	public ModelAndView logOut(@ModelAttribute AccessInfo ai,HttpServletRequest req){
		mav = new ModelAndView();
		mav = auth.logOutCtl(ai,req);
		return mav;
	}
	
	@GetMapping(value = "/dashboard")
	public ModelAndView dashboard() {
		mav = new ModelAndView();
		mav.setViewName("dashboard");
		return mav;
	}
	
	@GetMapping(value = "/calendar")
	public ModelAndView calendar() {
		mav = new ModelAndView();
		mav.setViewName("calendar");
		return mav;
	}
	
	@GetMapping(value = "/teamAdd")
	public ModelAndView teamAdd() {
		mav = new ModelAndView();
		mav.setViewName("teamAdd");
		return mav;
	}
	
	@GetMapping(value = "/mailAuth")
	public ModelAndView mailAuth() {
		mav = new ModelAndView();
		mav.setViewName("emailAuth");
		return mav;
	}
	
	@PostMapping(value = "/cerMailAuth")
	public ModelAndView cerMailAuth(@ModelAttribute MailBean mb) {
		mav = auth.cerMainAuth(mb);
		return mav;
	}
	
	@PostMapping("/upload")
	public String upload(Model model,@RequestParam("file1") MultipartFile file) {
		auth.restore(file);
		return "redirect:/";
	}
	
//	@PostMapping(value = "/pictures")
//	public void pictures(@ModelAttribute ScheduleBean sdb) {
//		smg.pictures(sdb);
//		//return mav;
//	}
	
	@PostMapping(value = "/sendSchedule")
	public ModelAndView sendSchedule(@ModelAttribute ScheduleBean sdb) {
//		System.out.println(sdb.getTid());
//		System.out.println(sdb.getTitle());
//		System.out.println(sdb.getDate());
//		System.out.println(sdb.getLocation());
//		System.out.println(sdb.getContents());
//		System.out.println(sdb.getOpen());
//		System.out.println(sdb.getLoop());
		
		mav = smg.addSchedule(sdb);
		return mav;
	}
	
	@GetMapping(value = "/newDashboard")
	public ModelAndView newDashboard() {
		mav = new ModelAndView();
		mav.setViewName("newDashboard");
		return mav;
	}
	
}

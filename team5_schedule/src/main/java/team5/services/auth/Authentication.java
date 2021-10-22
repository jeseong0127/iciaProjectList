package team5.services.auth;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import team5.controller.bean.AccessInfo;
import team5.controller.bean.MailBean;
import team5.controller.bean.ScheduleBean;
import team5.controller.bean.UserBean;
import team5.services.util.Encryption;
import team5.services.util.ProjectUtils;


@Service
public class Authentication{

	@Autowired
	AuthDAO dao;
	
	private ModelAndView mav;
	@Autowired
	Gson gson;
	@Autowired
	Encryption enc;
	@Autowired
	ProjectUtils pu;

	public ModelAndView accessCtl(AccessInfo ai , HttpServletRequest req) {
		boolean check = false;
		String encPw;
		encPw = dao.getUserPwInfo(ai);
		//ai.setBrowser(getBrowserInfo(req,"others"));
		mav = new ModelAndView();
		
		String test1 = dao.browserLogOut(ai); // 로그인 되어 있는 곳
		ai.setBrowser(test1);
		//String test2 = ai.getBrowser(); // 내가 지금 로그인하는 곳
		
		
		if(test1 != null && dao.browserCheck(ai)) {
			ai.setBrowser(test1);
			ai.setMethod(-1);
			dao.browserInsert(ai);
		}
		
		try {
			//			if(pu.getAttribute("logOut") != null && (boolean)pu.getAttribute("logOut")) {
			//				pu.setAttribute("logOut", false);
			//				mav.setViewName("login");
			//				mav.addObject("message","이미 로그아웃 되었습니다 다시 접속해주세요");
			//			}else {
					
				ai.setBrowser(getBrowserInfo(req,"others"));
				ai.setMethod(1);
			if(check=(encPw != null)) {	
				if(check = enc.matches(ai.getACode(), encPw)) {
					if(check = dao.insAccessHistory(ai)) {
						mav.addObject("publicIp",ai.getPublicIp());
						mav.addObject("privateIp",ai.getPrivateIp());

						ArrayList<UserBean> test = (ArrayList)dao.selMemberInfo(ai);
						//System.out.print("진실 " + enc.aesDecode(test.get(0).getUMail(),ai.getUCode()));
						//mav.setViewName("dashboard");
						mav.setViewName("redirect:/");
						
						pu.setAttribute("uCode", ai.getUCode());
						pu.setAttribute("Check", test1);
						pu.setAttribute("umail", test.get(0).getUMail());
						pu.setAttribute("img",test.get(0).getStickerPath());
						mav.addObject("umail", enc.aesDecode(test.get(0).getUMail(),ai.getUCode()));

					}
				}
			}
				
//			if(test1 != null && !(test1.equals(test2))) {
//				//else {
////					ai.setBrowser(test1);
////					ai.setMethod(-1);
////					dao.browserInsert(ai);
////				}
//				ai.setBrowser(test1);
//				ai.setMethod(-1);
//				dao.browserInsert(ai);
//				
//			}
				
			if(!check) {
				mav.setViewName("login");
				mav.addObject("message","로그인 정보를 다시 확인 해 주시거나 잠시 후 시도해 주세요");
			}
			//}
			
			
			
			pu.setAttribute("browser",test1);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			//mav.setViewName(view);
			mav.addObject("uCode",ai.getUCode());
			mav.addObject("publicIp",ai.getPublicIp());
			mav.addObject("privateIp",ai.getPrivateIp());
			mav.addObject("browser", ai.getBrowser());
			
			//mav.addObject("file",pu.getAttribute(test1));
		}
		return mav;
	}

	public ModelAndView joinCtl(UserBean ub) {
		mav = new ModelAndView();
		ub.setACode(enc.encode(ub.getACode()));
		
		try {
			ub.setUName(enc.aesEncode(ub.getUName(), ub.getUCode()));
			ub.setUMail(enc.aesEncode(ub.getUMail(), ub.getUCode()));
			
			//System.out.println(ub.getFile1().getOriginalFilename());
			//System.out.println(ub.getFile1().getName());
			//System.out.println(ub.getFile1().getContentType());
			//System.out.println(ub.getFile1().getSize());
			// file1   application/octet-stream   0
			
			if(ub.getFile1().getSize() != 0) {
				ub.setStickerPath(pu.savingFile(ub.getFile1()));  // 파일저장
			}else {
				ub.setStickerPath("");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(dao.insMembers(ub)) {
			System.out.println("회원가입 성공");
			
			mav.setViewName("login");
		}else {
			System.out.println("회원가입 실패");
			mav.setViewName("join");
			mav.addObject("message","잠시 후 다시 시도해 주세요");
		}

		return mav;
	}


	public String isDuplicateId(AccessInfo ai) {
		boolean message = false;
		if(!dao.isUserId(ai)) {
			message = true;
		}

		return gson.toJson(message);
	}

	public ModelAndView logOutCtl(AccessInfo ai, HttpServletRequest req) {
		boolean check = false;
		ai.setBrowser(getBrowserInfo(req,"others"));
		mav = new ModelAndView();
		try {
			//ai.setUCode(enc.aesDecode(ai.getUCode(), "logOut"));
			if(pu.getAttribute("uCode") != null) {

				while(!check) {
					check = dao.isLogout(ai);
				}
				pu.removeAttribute("uCode");
				//pu.setAttribute("logOut", true);
				mav.setViewName("redirect:/");
				//mav.addObject("message", "정상적으로 로그아웃 성공");
				pu.setAttribute("message", "정상적으로 로그아웃 성공");

			}else {
				mav.setViewName("redirect:/");
				//mav.addObject("message", "이미 로그아웃 되었습니다"); param. 써야함(jsp에서)
				System.out.print("여기왔음");
				pu.setAttribute("message", "이미 로그아웃 되었습니다");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	public ModelAndView rootCtl(AccessInfo ai) {
		mav = new ModelAndView();
		try {
//			if(pu.getAttribute("uCode") != null) {
//				mav.setViewName("dashboard");
//				mav.addObject("umail",enc.aesDecode((String)pu.getAttribute("umail"), (String)pu.getAttribute("uCode")));
//			}else {
//				mav.setViewName("login");
//			}
			
			if(pu.getAttribute("uCode") != null && ai.getUCode() != null) {
				
				mav.setViewName("dashboard");
				mav.addObject("umail",enc.aesDecode((String)pu.getAttribute("umail"), (String)pu.getAttribute("uCode")));
//				if(dao.browserCheck((String)pu.getAttribute("uCode"),(String)pu.getAttribute("browser"))== 0) {
//					pu.removeAttribute("uCode");
//					mav.setViewName("login");
//				}
				
				if(!dao.browserCheck(ai)) {
					pu.removeAttribute("uCode");
					mav.setViewName("login");
				}
				
			}else if(pu.getAttribute("uCode") != null) {
				mav.setViewName("dashboard");
				mav.addObject("umail",enc.aesDecode((String)pu.getAttribute("umail"), (String)pu.getAttribute("uCode")));
			}
			
			else {
				mav.setViewName("login");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	protected String getBrowserInfo(HttpServletRequest req, String browser) {
		try {
			String browserInfo = req.getHeader("User-Agent"); // 사용자 User-Agent 값 얻기
			if (browserInfo != null) {
				if (browserInfo.indexOf("Trident") > -1) {
					browser = "MSIE";
				} else if (browserInfo.indexOf("Opera") > -1) {
					browser = "Opera";
				} else if (browserInfo.indexOf("iPhone") > -1
						&& browserInfo.indexOf("Mobile") > -1) {
					browser = "iPhone";
				} else if (browserInfo.indexOf("Android") > -1
						&& browserInfo.indexOf("Mobile") > -1) {
					browser = "Android";
				} else if (browserInfo.indexOf("Whale") > -1){
					browser = "Whale";
				} else if (browserInfo.indexOf("Edg") > -1){
					browser = "Edg";
				} else if (browserInfo.indexOf("Chrome") > -1) {
					browser = "Chrome";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return browser;
	}

	public ModelAndView cerMainAuth(MailBean mb) {
		ModelAndView mav = new ModelAndView();
		
		System.out.println(mb.getUCode());
		System.out.println(mb.getTid());
		
		if(dao.insTeamDetail(mb)) {
			mav.setViewName("dashboard");
		}else {
			mav.setViewName("emailAuth");
		}
		
		return mav;
	}
	
	String SAVE_PATH = "C:\\coding\\upload";
	
	public void restore(MultipartFile file) {
		
		try {
		String originFilename = file.getOriginalFilename();
		String extName = originFilename.substring(originFilename.lastIndexOf("."),originFilename.length());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		Calendar cal = Calendar.getInstance();
		String saveFileName = "upload"+sdf.format(cal.getTime())+extName;
		byte[] data = file.getBytes();
		FileOutputStream fos = new FileOutputStream(SAVE_PATH + "/" + saveFileName);
		fos.write(data);
		fos.close();
		System.out.println("업로드성공");
		}
		catch(Exception e) {
			System.out.println("error");
		}
	}

	//	public boolean isUserId(AccessInfo ai) {
	//		return false;
	//	}
	//	public boolean isAccess(AccessInfo ai) {
	//		return false;
	//	}
	//	public boolean insAccessHistory(AccessInfo ai) {
	//		return false;
	//	}
	//	public boolean insMembers(UserBean ub) {
	//		return false;
	//	}

}

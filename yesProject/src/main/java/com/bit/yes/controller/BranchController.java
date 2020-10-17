package com.bit.yes.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bit.yes.model.entity.BranchVo;
import com.bit.yes.model.entity.ReserveListVo;
import com.bit.yes.model.entity.ReviewVo;
import com.bit.yes.model.entity.UserVo;
import com.bit.yes.service.BranchService;
import com.bit.yes.service.ReserveListService;

@Controller
public class BranchController {

	@Autowired
	private BranchService branchService;
	@Autowired
	private ReserveListService reserveListService;
	
	private final Logger logger = LoggerFactory.getLogger(BranchController.class);

	@RequestMapping("/insert")
	public String insert() {
		return "branch/insert";
	}

	@ResponseBody
	@RequestMapping(value = "/insertstep1", method = RequestMethod.POST)
	public void insertStep1 (@RequestBody Map<String, String> map, HttpSession httpSession){
		String id = ((UserVo)httpSession.getAttribute("member")).getId();
		map.put("id", id);
		branchService.insertBranchInfo(map);
		branchService.insertBranchAddress(map);
	}
	@ResponseBody
	@RequestMapping(value = "/insertstep2", method = RequestMethod.POST)
	public void insertStep2 (@RequestBody Map<String, Object> map, HttpSession httpSession){
		String id = ((UserVo)httpSession.getAttribute("member")).getId();
		map.put("id",id);
		branchService.insertBranchMenu(map);
	}
	@ResponseBody
	@RequestMapping(value = "/insertstep3", method = RequestMethod.POST)
	public void insertStep3 (@RequestBody String markerImage, HttpSession httpSession){
		String id = ((UserVo)httpSession.getAttribute("member")).getId();
		Map<String, String> imageMap = new HashMap<>();
		imageMap.put("id", id);
		imageMap.put("markerImage", markerImage.substring(0,markerImage.length()-1));
		imageMap.put("mainImage", "mainImage.jpg");
		for(int i = 1; i<9; i++){
			String safeFile = "food_" + id + "_" + i + ".jpg";
			imageMap.put("image"+i, safeFile);
		}
		branchService.insertImageNames(imageMap);
	}

	@RequestMapping("/list")
	public String list(Model model) throws Exception {
		List<BranchVo> articleList = branchService.selectAll();
		model.addAttribute("alist", articleList);
		return "branch/list";
	}
	@ResponseBody
	@RequestMapping(value = "/popup", method = RequestMethod.POST)
	public List<BranchVo> markerMenuLoad(@RequestBody String branchID, Model model){
        String[] id = branchID.split("=");
        List<BranchVo> menuList = branchService.menuLoad(id[0]);
        return menuList;
    }

    @ResponseBody
    @RequestMapping(value = "/branchdetail", method = RequestMethod.POST)
	public List<BranchVo> branchDetail(@RequestBody String branchID, Model model){
		List<BranchVo> allMenuList = branchService.allMenuLoad(branchID.substring(0, branchID.length()-1));

		return allMenuList;
	}
    @ResponseBody
    @RequestMapping(value = "/mybranchdetail", method = RequestMethod.POST)
	public List<BranchVo> myBranchDetail(HttpSession httpSession){
		String branchId = ((UserVo)httpSession.getAttribute("member")).getId();
		List<BranchVo> myAllMenuList = branchService.myAllMenuLoad(branchId);

		return myAllMenuList;
	}

	@ResponseBody
	@RequestMapping(value = "/waitingList", method = RequestMethod.POST)
	public int waitingList(@RequestBody String branchId){
		return branchService.waitingList(branchId.substring(0, branchId.length()-1));
	}


	@ResponseBody
	@RequestMapping(value = "/updatelatlng", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public void updateLatLng(@RequestBody Map<String, Object> updateLatLng){
		branchService.updateLatLng(updateLatLng);
	}

	@ResponseBody
	@RequestMapping(value = "/search", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public List<BranchVo> search(@RequestBody Map<String, Object> searchMap){
		List<BranchVo> searchResult = branchService.searchResult(searchMap);
		return searchResult;
	}

	@RequestMapping(value = "requestupload2")
    public String requestupload2(MultipartHttpServletRequest mtfRequest, HttpSession httpSession) {
		String id = ((UserVo)httpSession.getAttribute("member")).getId();
		String uploadResult = branchService.imageUpload(mtfRequest, id);
        return uploadResult;
	}

	@ResponseBody
	@RequestMapping(value = "reservepreview", method = RequestMethod.POST)
	public List<String> reservePreviewDate(String id, String date){//@RequestBody Map<String, Object> map){
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("date", date);

		List<ReserveListVo> reserveList = reserveListService.reserveDatePreview(map);
		List<BranchVo> branchTimeList = branchService.reserveInfoPreview((map.get("id")).toString());

		String opTime = branchTimeList.get(0).getOpTime();
		String[] opTimes = opTime.split("~");

		String[] openTimes = opTimes[0].split(":");
		int openTimeHour = Integer.parseInt(openTimes[0]);
		int openTimeMin = Integer.parseInt(openTimes[1]);

		String[] closeTimes = opTimes[1].split(":");
		int closeTimeHour = Integer.parseInt(closeTimes[0]);
		int closeTimeMin = Integer.parseInt(closeTimes[1]);

		List<String> resultTimeArr = new ArrayList<String>();
		int maxMin = 60;
		// 영업시간에서 예약 가능한 시간을 구하는 로직. 10분단위
		for (int j = openTimeHour; j <= closeTimeHour; j++) {
			for (int i = (openTimeMin+9)/10*10; i < maxMin ; i = i + 10) {

				if(j < 10){
					if(i == 0) resultTimeArr.add("0"+j+":"+"00");
					else resultTimeArr.add("0"+j+":"+i);
				}else{
					if(i == 0) resultTimeArr.add(j+":"+"00");
					else resultTimeArr.add(j+":"+i);
				}



			}
			if(j == closeTimeHour-1) maxMin = closeTimeMin;
			else openTimeMin = 0;
		}



		if(reserveList.size() == 0){
			return (List<String>) resultTimeArr;
		}else{
			for (int i = 0; i < reserveList.size(); i++) {
				Map<String, Object> temp = (Map<String, Object>) reserveList.get(i);
				String reserveTime = String.valueOf(temp.get("reserveTime"));
				String reserved = reserveTime.substring(11, 16);
				resultTimeArr.remove(reserved);
			}


			return (List<String>) resultTimeArr;
		}

	}

	@ResponseBody
    @RequestMapping(value = "/ticketingStart", method = RequestMethod.POST)
	public void ticketingStart(@RequestBody String branchID, HttpSession httpSession){
		String clientId = ((UserVo)httpSession.getAttribute("member")).getId();
		branchService.ticketingStart(branchID.substring(0, branchID.length()-1), clientId);
	}
	@ResponseBody
    @RequestMapping(value = "/ticketingCheck", method = RequestMethod.POST)
	public int ticketingCheck(@RequestBody String branchID, HttpSession httpSession){
		if(httpSession.getAttribute("member") == null) return 1001;
		String clientId = ((UserVo)httpSession.getAttribute("member")).getId();
		return branchService.ticketingCheck(branchID.substring(0, branchID.length()-1), clientId);
	}

	@ResponseBody
	@RequestMapping(value = "/branchReview", method = RequestMethod.POST)
	public List<ReviewVo> branchReview(@RequestBody String branchId){
		return branchService.branchReview(branchId);
	}
	
}
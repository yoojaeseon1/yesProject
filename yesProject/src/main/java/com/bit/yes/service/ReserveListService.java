package com.bit.yes.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.bit.yes.model.ReserveListDAO;
import com.bit.yes.model.entity.BranchVo;
import com.bit.yes.model.entity.ReserveListVo;
import com.bit.yes.model.entity.ReviewVo;
import com.bit.yes.model.paging.SearchCriteria;

@Service
public class ReserveListService {

   @Autowired
   ReserveListDAO reserveDAO;


   public List<ReserveListVo> listPage(String id) throws SQLException{
//      List<ReserveListVo> list=reserveDAO.reserveList(id);
//      model.addAttribute("rlist",list);
      return reserveDAO.reserveList(id);
   }
   public List<ReserveListVo> listPage(SearchCriteria cri) throws Exception{
//      List<ReserveListVo> list=reserveDAO.reserveList(id);
//      model.addAttribute("rlist",list);
	   return reserveDAO.reserveList(cri);
   }
   
   public int selectTotalReservation(SearchCriteria cri) throws Exception{
	   return reserveDAO.selectTotalReservation(cri);
   }
   
   
   public BranchVo selectOne(String branchID) throws SQLException{
      return reserveDAO.selectOne(branchID);
   }
   public void deleteOne(ReserveListVo bean) throws SQLException{
      reserveDAO.deleteOne(bean);
   }
   
   public List<ReserveListVo> reserveAll(String id) throws SQLException {
//      List<ReserveListVo> list=reserveDAO.b_reserveList(id);
//      model.addAttribute("alist",reserveDAO.b_reserveList(id));
//      System.out.println("reserveList : " + list);
      return reserveDAO.b_reserveList(id);
   }
   
   public BranchVo selectBranch(String id) {
      return reserveDAO.selectBranch(id);
   }
   public void updateState(BranchVo bean) {
      reserveDAO.updateState(bean);
   }
   public int loadTicket(String id) {
      return reserveDAO.countTicket(id);
   }
   public void updateWaiting(BranchVo bean) {
      reserveDAO.updateWaiting(bean);
   }
   public void deleteTicket(int entry) {
      reserveDAO.deleteTicket(entry);
   }
   public void end(String id) {
      reserveDAO.end(id);
   }
   public int getNum(String id) {
      return reserveDAO.getNum(id);
   }
   public int getState(String id) {
      return reserveDAO.getState(id);
   }

	public List<ReserveListVo> reserveDatePreview(Map<String,Object> map) {
       return reserveDAO.reserveDatePreview(map);
    }

    public void insertReserve(Map<String, Object> map, String id){
      reserveDAO.insertReserve(map, id);
    }
//	public int updateUseState(ReserveListVo bean) {
//		return reserveDAO.updateUseState(bean);
//	}
	public int updateUseState(Map<String, Object> reverveMap) {
		return reserveDAO.updateUseState(reverveMap);
	}
	
	public List<ReviewVo> writeList(SearchCriteria cri) throws SQLException {
	      List<ReviewVo> list=reserveDAO.writeList(cri);
	      return list;
	}
	
	public List<ReviewVo> selectAll(Model model, String id) throws SQLException {
		List<ReviewVo>	list=reserveDAO.selectAll(id);
		model.addAttribute("page",reserveDAO.selectAll(id));
		System.out.println(list);
		return list;
	}
	
	public List<BranchVo> selectOneBranch(String id) {
      return reserveDAO.selectOneBranch(id);
	}
	public int deleteReview(String idx) throws NumberFormatException, SQLException {
		return reserveDAO.deleteReview(Integer.parseInt(idx));
	}
	
	public String selectUseState(ReserveListVo bean) throws SQLException{
		return reserveDAO.selectUseState(bean);
	}
	
	public String selectBranchName(String branchID) throws Exception{
		return reserveDAO.selectBranchName(branchID);
	}
}

package com.bit.yes.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.bit.yes.model.entity.BranchVo;
import com.bit.yes.model.entity.ReserveListVo;
import com.bit.yes.model.entity.ReviewVo;
import com.bit.yes.model.paging.SearchCriteria;

public interface ReserveListDAO {
   //고객 예약 현황 리스트
   public List<ReserveListVo> reserveList(String id) throws SQLException;
   public List<ReserveListVo> reserveList(SearchCriteria cri) throws Exception;
   
   public int selectTotalReservation(SearchCriteria cri) throws Exception;
   
   
   public BranchVo selectOne(String branchID) throws SQLException;
   public String selectUseState(ReserveListVo bean) throws SQLException;
   public void deleteOne(ReserveListVo bean) throws SQLException;
   public List<ReserveListVo> b_reserveList(String id) throws SQLException;
   public BranchVo selectBranch(String id);
   public void updateState(BranchVo bean);
   public int countTicket(String id);
   public void updateWaiting(BranchVo bean);
   public int deleteTicket(int entry);
   public void end(String id);
   public int getNum(String id);
   public int getState(String id);

   public List<ReserveListVo> reserveDatePreview(Map<String,Object> map);

   void insertReserve(Map<String,Object> map, String id);
//   public int updateUseState(ReserveListVo bean);
   public int updateUseState(Map<String, Object> reserveMap);
   
   
   public List<ReviewVo> writeList(SearchCriteria cri) throws SQLException;
   public int myReviewsearchCount(SearchCriteria cri) throws Exception;
   
   
   public List<ReviewVo> selectAll(String id) throws SQLException; //리뷰리스트 가져오기
   List<BranchVo> selectOneBranch(String id);
   public int deleteReview(int parseInt)throws SQLException; //작성글 삭제
   
   
   public String selectBranchName(String branchID) throws Exception;
}

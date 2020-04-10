package com.bit.yes.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bit.yes.model.entity.BranchVo;
import com.bit.yes.model.entity.ReserveListVo;
import com.bit.yes.model.entity.ReviewVo;
import com.bit.yes.model.paging.SearchCriteria;

@Repository
public class ReserveListDaoImpl implements ReserveListDao {

	@Autowired
	SqlSession sqlSession;

	@Override
	public List<ReserveListVo> reserveList(String id) throws SQLException {
		return sqlSession.selectList("reserve.listClientReservation", id);
	}

	@Override
	public BranchVo selectOne(String branchID) throws SQLException {
		return sqlSession.selectOne("reserve.branchInfo", branchID);
	}

	@Override
	public void deleteOne(ReserveListVo bean) throws SQLException {
		sqlSession.delete("reserve.deleteReserve", bean);
	}

	// 사업자 예약 현황 리스트 가져오기
	@Override
	public List<ReserveListVo> b_reserveList(String id) throws SQLException {
		return sqlSession.selectList("reserve.listBranchReservation", id);
	}

	@Override
	public BranchVo selectBranch(String id) {
		return sqlSession.selectOne("reserve.selectBranch", id);
	}

	@Override
	public void updateState(BranchVo bean) {
		sqlSession.update("reserve.updateState", bean);
	}

	@Override
	public int countTicket(String id) {
		return sqlSession.selectOne("reserve.countTicket", id);
	}

	@Override
	public void updateWaiting(BranchVo bean) {
		sqlSession.update("reserve.updateWaiting", bean);
	}

	@Override
	public int deleteTicket(int entry) {
		return sqlSession.delete("reserve.deleteTicket", entry);
	}

	@Override
	public void end(String id) {
		sqlSession.delete("reserve.end", id);
	}

	@Override
	public int getNum(String id) {
		if (sqlSession.selectOne("reserve.getNum", id) != null)
			return sqlSession.selectOne("reserve.getNum", id);
		return 0;
	}

	@Override
	public int getState(String id) {
		return sqlSession.selectOne("reserve.getState", id);
	}

	@Override
	public List<ReserveListVo> reserveDatePreview(Map<String, Object> map) {
		return sqlSession.selectList("reserve.reserveDatePreview", map);

	}

	@Override
	public void insertReserve(Map<String, Object> map, String id) {
		map.put("id", id);
		sqlSession.insert("reserve.insertReserve", map);
	}

//   @Override
//	public int updateUseState(ReserveListVo bean) {
//	   return sqlSession.update("reserveList.updateUseState", bean);
//	}
	@Override
	public int updateUseState(Map<String, Object> reserveMap) {
		return sqlSession.update("reserve.updateUseState", reserveMap);
	}

	@Override
	public List<ReviewVo> writeList(SearchCriteria cri) throws SQLException {
		return sqlSession.selectList("review.listReviewSearch", cri);
	}

	@Override
	public int myReviewsearchCount(SearchCriteria cri) throws Exception {
		return sqlSession.selectOne("review.listReviewSearchCount", cri);
	}

	@Override
	public List<ReviewVo> selectAll(String id) throws SQLException {
		return sqlSession.selectList("reserve.selectAll", id);
	}

	@Override
	public List<BranchVo> selectOneBranch(String id) {
		return sqlSession.selectList("reserve.selectOneBranch", id);
	}

	@Override
	public int deleteReview(int idx) throws SQLException {
		return sqlSession.delete("reserve.deleteReview", idx);
	}

	@Override
	public String selectUseState(ReserveListVo bean) throws SQLException {

		return sqlSession.selectOne("reserve.selectUseState", bean);
	}

	@Override
	public String selectBranchName(String branchID) throws Exception {

		return sqlSession.selectOne("reserve.selectBranchName", branchID);
	}

}

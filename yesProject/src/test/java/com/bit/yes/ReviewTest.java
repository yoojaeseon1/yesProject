package com.bit.yes;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bit.yes.model.entity.CommentVo;
import com.bit.yes.model.paging.Criteria;
import com.bit.yes.service.ReviewService;

public class ReviewTest {

	
	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	ReviewService reviewService;
	
	private Logger logger = LoggerFactory.getLogger(ReviewTest.class);
	
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//	}
//
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//	}
//
//	@Before(value = "")
//	public void setUp() throws Exception {
//	}
//
//	@After(value = "")
//	public void tearDown() throws Exception {
//	}

	@Test
	public void test() throws Exception {
		
		Criteria cri = new Criteria();
		
		cri.setPage(2);
		
		cri.setPerPageNum(20);
		
		cri.setReviewIndex(71);
		
		List<CommentVo> list = reviewService.listCommentCriteria(cri);
		
		for(CommentVo commentVo : list) {
			
			logger.info(commentVo.getCommentIndex() + " : " + commentVo.getComment());
			
			
		}
		
		
	}

}

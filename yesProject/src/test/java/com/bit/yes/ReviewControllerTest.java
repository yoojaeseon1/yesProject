package com.bit.yes;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bit.yes.model.entity.ReviewVo;
import com.bit.yes.model.paging.SearchCriteria;
import com.bit.yes.service.ReviewService;


@RunWith(SpringJUnit4ClassRunner.class)
public class ReviewControllerTest {

	
	@Autowired
	SqlSession sqlSession;
	
//	ReviewService reviewService = new ReviewService();
	
	@Autowired
	ReviewService reviewService;
	
	private Logger logger = LoggerFactory.getLogger(ReviewControllerTest.class);
	
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
		
		SearchCriteria cri = new SearchCriteria();
		
		cri.setPage(1);
		cri.setBranchID("user44");
//		logger.info("haha");
		
		logger.info("cri : " + cri);
		
		List<ReviewVo> branchReviews = reviewService.listBranchReview(cri);
		int totalCount = reviewService.countBranchReview(cri);
		
		logger.info("totalCount : " + totalCount);
		
		for(ReviewVo review : branchReviews) {
			
			logger.info("branchReview : " + review);
			
		}
		
		
	}

}

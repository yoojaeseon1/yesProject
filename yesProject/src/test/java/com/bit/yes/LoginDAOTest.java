package com.bit.yes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bit.yes.model.LoginDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
//@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/**/servlet-context.xml"})
public class LoginDAOTest {

	@Autowired
	private LoginDAO loginDAO;
	
	
	@Test
	public void testPrint() throws Exception {
	
		System.out.println("loginDAOTest");
	}
	
}

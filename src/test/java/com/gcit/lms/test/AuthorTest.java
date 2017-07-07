package com.gcit.lms.test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.gcit.lms.LMSConfig;
import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.service.AdminService;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes=LMSConfig.class, loader=AnnotationConfigContextLoader.class)
public class AuthorTest {

	@InjectMocks
	AdminService adminService = new AdminService();
	
	@Mock
	AuthorDAO adao;
	
	public static Integer authorId = null;
	
	@Before
	public void testMockCreation(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void test1(){
		assertNotNull(adminService);
		assertNotNull(adao);
	}
	
	@Test
	public void test2() throws SQLException{
		Author author = new Author();
		when(adao.addAuthorWithID(author)).thenReturn(new Integer(1));
		author.setAuthorName("Junit Integ Test Author");
		authorId = adminService.saveAuthorWithID(author);
		//System.out.println("Test Author ID: " +authorId);
		assertNotNull(authorId);
		assertEquals(new Integer(1), authorId);
		assertNotEquals(new Integer(2), authorId);
	}
	
//	@Test
//	public void test3() throws SQLException{
//		Author author = new Author();
//		author.setAuthorName("Junit Integ Test Author - Updated");
//		author.setAuthorId(authorId);
//		adminService.saveAuthor(author);
//	}
//	
//	@Test
//	public void test4() throws SQLException{
//		Author author = new Author();
//		author.setAuthorId(authorId);
//		adminService.deleteAuthor(author);
//	}
}

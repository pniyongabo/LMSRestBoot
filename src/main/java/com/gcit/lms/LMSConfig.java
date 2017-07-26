package com.gcit.lms;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookCopyDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoanDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.BranchDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.service.AdminService;
import com.gcit.lms.service.BorrowerService;
import com.gcit.lms.service.LibrarianService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class LMSConfig extends WebMvcConfigurerAdapter{
	
	static Logger logger  = LoggerFactory.getLogger(LMSConfig.class);
	
	public String driver = "com.mysql.jdbc.Driver";
	public String url = "jdbc:mysql://localhost/library";
	public String username = "root";
	public String password = "root";
	
//	public String driver = "com.mysql.jdbc.Driver";
//	public String awsDbEndpoint = "testrds.c4uwpmj5ivko.us-east-1.rds.amazonaws.com:3306/";
//	public String dbName = "library";
//	public String url = "jdbc:mysql://" + awsDbEndpoint + dbName;
//	public String username = "rootroot";
//	public String password = "rootroot";
	
	@Value("${spring.profiles.active}")
	private String profile;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//Logger logger = LoggerFactory.getLogger(LMSConfig.class);
		logger.info("Hello World, This is the App Config File!");
		if(!profile.equalsIgnoreCase("LOCAL")) {
			registry.addInterceptor(new AuthenticationInterceptor())
			.addPathPatterns("/**")	
					.excludePathPatterns("/public/**", "/*swagger*/**", "/v2/api-docs");
		}
	}
	
	@Bean
	//@Scope(value="Prototype")
	public BasicDataSource dataSource(){
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(driver);
		ds.setUrl(url);
		ds.setUsername(username);
		ds.setPassword(password);
		
		return ds;
	}
	
	@Bean(name="MySQL")
	public JdbcTemplate template(){
		return new JdbcTemplate(dataSource());
	}
	
	@Bean
	public AdminService adminService(){
		return new AdminService();
	}
	
	@Bean
	public BorrowerService borrowerService(){
		return new BorrowerService();
	}
	
	@Bean
	public LibrarianService librarianService(){
		return new LibrarianService();
	}
	
	@Bean
	public AuthorDAO adao(){
		return new AuthorDAO();
	}
	
	@Bean
	public BookDAO bdao(){
		return new BookDAO();
	}
	
	@Bean
	public BookCopyDAO bcdao(){
		return new BookCopyDAO();
	}
	
	@Bean
	public BookLoanDAO bldao(){
		return new BookLoanDAO();
	}
	
	@Bean
	public BorrowerDAO bodao(){
		return new BorrowerDAO();
	}
	
	@Bean
	public BranchDAO brdao(){
		return new BranchDAO();
	}
	
	@Bean
	public GenreDAO gdao(){
		return new GenreDAO();
	}
	
	@Bean
	public PublisherDAO pdao(){
		return new PublisherDAO();
	}
	
	@Bean
	public PlatformTransactionManager txManager(){
		return new DataSourceTransactionManager(dataSource());
	}
}
 
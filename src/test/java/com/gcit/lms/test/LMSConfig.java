package com.gcit.lms.test;

import java.net.UnknownHostException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.service.AdminService;
import com.mongodb.MongoClient;

@Configuration
public class LMSConfig {
	
	public String driver = "com.mysql.jdbc.Driver";
	public String url = "jdbc:mysql://localhost/library";
	public String username = "root";
	public String password = "root";
	
	@Bean
	public BasicDataSource dataSource(){
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(driver);
		ds.setUrl(url);
		ds.setUsername(username);
		ds.setPassword(password);
		
		return ds;
	}
	
	@Bean
	public JdbcTemplate template(){
		return new JdbcTemplate(dataSource());
	}
	
	@Bean
	public AdminService adminService(){
		return new AdminService();
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
	public PlatformTransactionManager txManager(){
		return new DataSourceTransactionManager(dataSource());
	}
	
	@Bean
	public SimpleMongoDbFactory mongoDbFactory() throws UnknownHostException{
		return new SimpleMongoDbFactory(new MongoClient(), "local");
	}
	
	@Bean
	public MongoTemplate mongoTemplate() throws UnknownHostException{
		return new MongoTemplate(mongoDbFactory());
	}
}

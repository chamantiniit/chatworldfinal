package com.nik.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.nik.model.AnswerForum;
import com.nik.model.Blog;
import com.nik.model.BlogComment;
import com.nik.model.Forum;
import com.nik.model.Friend;
import com.nik.model.Jobs;
import com.nik.model.Users;


 
@Configuration  
@ComponentScan("com")
@EnableTransactionManagement
public class ApplicationConfiguration {
	
	@Autowired
	@Bean(name="dataSource")
	public DataSource getDataSource()
	{
		System.out.println("get datasource method called");
		DriverManagerDataSource dataSource=new DriverManagerDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUsername("sai");
		dataSource.setPassword("sai");
		dataSource.setUrl("jdbc:h2:tcp://localhost/~/Chatworld1");
		return dataSource;
	}
	private  Properties getHibernateProperties()
	 {
		  Properties properties=new Properties();
		  properties.setProperty("hibernate.show_sql", "true");
			properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
			properties.setProperty("hibernate.hbm2ddl.auto", "create");
			return properties;
				  
	 }
	@Autowired
	@Bean(name="sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource)
	{
		LocalSessionFactoryBuilder localSessionFactoryBuilder=new LocalSessionFactoryBuilder(dataSource);
		localSessionFactoryBuilder.addProperties(getHibernateProperties());
		localSessionFactoryBuilder.addAnnotatedClass(Users.class);
		localSessionFactoryBuilder.addAnnotatedClass(Blog.class);
		localSessionFactoryBuilder.addAnnotatedClass(Jobs.class);
		localSessionFactoryBuilder.addAnnotatedClass(Forum.class);
		localSessionFactoryBuilder.addAnnotatedClass(Friend.class);
		localSessionFactoryBuilder.addAnnotatedClass(BlogComment.class);
		localSessionFactoryBuilder.addAnnotatedClass(AnswerForum.class);
		return localSessionFactoryBuilder.buildSessionFactory();
	}
	@Autowired
	@Bean(name="transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory)
	{
		HibernateTransactionManager hibernateTransactionManager=new HibernateTransactionManager(sessionFactory);
		return hibernateTransactionManager;
		
	}
	@Bean
	public CommonsMultipartResolver multipartResolver() {
	    CommonsMultipartResolver resolver=new CommonsMultipartResolver();
	    resolver.setDefaultEncoding("utf-8");
	    return resolver;
	}

}

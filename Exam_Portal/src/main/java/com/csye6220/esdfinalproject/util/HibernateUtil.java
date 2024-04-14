package com.csye6220.esdfinalproject.util;

import com.csye6220.esdfinalproject.model.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.util.HashMap;
import java.util.Map;

public class HibernateUtil {

    public static SessionFactory buildSessionFactory(){
        Map<String, Object> settings = new HashMap<>();
        settings.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        settings.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/examportal");
        settings.put("hibernate.connection.username", "root");
        settings.put("hibernate.connection.password","Prashu@123");

        settings.put("hibernate.hbm2ddl.auto", "update");
        settings.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        settings.put("hibernate.dialect.storage_engine", "innodb");
        settings.put("hibernate.show-sql", "true");
        settings.put("hibernate.format_sql", "true");


        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(settings).build();

        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        metadataSources.addPackage("com.csye6220.esdfinalproject.model");
        metadataSources.addAnnotatedClasses( User.class, UserRole.class, Category.class, CategoryTypes.class, Exam.class, Question.class, Authority.class);

        Metadata metadata = metadataSources.buildMetadata();

        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

        return sessionFactory;
    }
}

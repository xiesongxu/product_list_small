package com.storems.admin.config;

import org.apache.ibatis.session.SqlSessionFactory;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;

/**
 * 项目配置类
 */
@Configuration()
@ComponentScan("com.storems.admin")
@EnableTransactionManagement
@MapperScan("com.storems.admin.dao")
public class ProductConfig {

    /**
     * 配置数据源然后吧对象存入spring
     * @return
     */
    @Bean
    public DataSource dataSource() {
        //创建数据源
        DriverManagerDataSource dataSource1 = new DriverManagerDataSource();
        dataSource1.setUsername("root");
        dataSource1.setPassword("990111");
        //配置编码，allowMultiQueries打开批量执行多条sql开关
        dataSource1.setUrl("jdbc:mysql://localhost:3306/world?characterEncoding=utf-8&allowMultiQueries=true");
        dataSource1.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return dataSource1;
    }

    /**
     * 创建mybatis会话管理工厂
     * @param data
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource data, ApplicationContext applicationContext) throws Exception {
        //初始化mybatis的数据库链接
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        //配置数据源
        factoryBean.setDataSource(data);
        //配置mapper.xml的扫描位置
        factoryBean.setMapperLocations(
                applicationContext.getResources("classpath*:com/storems/admin/dao/mapper/*Mapper.xml"));
        //mapperLocations
        return factoryBean.getObject();
    }

    /**
     * 创建事务管理器
     * @param data
     * @return
     */
    @Bean
    public TransactionManager transactionManager(DataSource data) {
        //创建事务管理器
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(data);
        return transactionManager;
    }

//    @Bean
//    public ViewResolver setView() {
//        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setPrefix("/WEB-INF/pages/");
//        viewResolver.setSuffix(".jsp");
//        return viewResolver;
//    }

    @Bean("multipartResolver")
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        resolver.setMaxUploadSize(100000);
        return resolver;
    }

}

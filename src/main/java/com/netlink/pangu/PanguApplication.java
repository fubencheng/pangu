package com.netlink.pangu;

import com.aliyun.openservices.ClientConfiguration;
import com.netlink.pangu.util.OssUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.servlet.MultipartConfigElement;
import javax.sql.DataSource;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 应用启动入口
 *
 * @author fubencheng
 * @version 0.0.1 2018-01-06 16:19 fubencheng
 */
@Configuration
@EnableTransactionManagement
@SpringBootApplication
@MapperScan({ "com.netlink.pangu.dao" })
public class PanguApplication implements TransactionManagementConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(PanguApplication.class, args);
	}

    @Value("${oss.accessId}")
    private String accessId;

    @Value("${oss.accessKey}")
    private String accessKey;

    @Value("${oss.endpoint}")
    private String endpoint;

    @Value("${oss.bucketName}")
    private String bucketName;

    @Autowired
    private DataSource dataSource;

    /**
     * 阿里云服务配置
     * @return OssUtil
     */
//    @Bean
//    public OssUtil ossUtil() {
//        OssUtil ossUtil = new OssUtil();
//        ossUtil.setAccessId(accessId);
//        ossUtil.setAccessKey(accessKey);
//        ossUtil.setBucketName(bucketName);
//        ossUtil.setEndpoint(endpoint);
//        ossUtil.setBucketPublicReadable();
//        return ossUtil;
//    }

    /**
     * 阿里云服务客户端配置
     * @return ClientConfiguration
     */
//    @Bean
//    public ClientConfiguration printOssClientConfig() {
//        ClientConfiguration printOssClientConfig = new ClientConfiguration();
//        printOssClientConfig.setMaxConnections(100);
//        printOssClientConfig.setSocketTimeout(5000);
//        printOssClientConfig.setConnectionTimeout(5000);
//        printOssClientConfig.setMaxErrorRetry(3);
//        return printOssClientConfig;
//    }

    /**
     * 事物配置
     * @return DataSourceTransactionManager
     */
    @Override
    @Bean
    public DataSourceTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * HTTP请求参数验证配置
     * @return MethodValidationPostProcessor
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor(){
        return new MethodValidationPostProcessor();
    }

    /**
     * 线程池配置
     * @return ThreadPoolTaskExecutor
     */
    @Bean
    public ThreadPoolTaskExecutor threadPool() {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        threadPool.setCorePoolSize(5);
        threadPool.setMaxPoolSize(20);
        threadPool.setQueueCapacity(50);
        threadPool.setKeepAliveSeconds(120);
        threadPool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return threadPool;
    }

    /**
     * 文件上传配置
     * @return MultipartConfigElement
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory configFactory = new MultipartConfigFactory();
        // 上传文件临时路径
        configFactory.setLocation("/alidata1/admin/upload/temp");
        // 最大文件大小，100MB
        configFactory.setMaxFileSize("100MB");
        // 最大请求大小，300MB
        configFactory.setMaxRequestSize("300MB");
        // 最大文件个数，3个
        configFactory.setFileSizeThreshold(3);

        return configFactory.createMultipartConfig();
    }

}

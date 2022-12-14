package com.internship.service;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class ServiceApplication {

    public static ConfigurableApplicationContext context;
    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

//    public static void restart() {
//        ApplicationArguments args = context.getBean(ApplicationArguments.class);
//
//        Thread thread = new Thread(() -> {
//            context.close();
//            context = SpringApplication.run(ServiceApplication.class, args.getSourceArgs());
//        });
//        thread.setDaemon(false);
//        thread.start();
//    }

}

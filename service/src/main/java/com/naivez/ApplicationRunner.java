package com.naivez;

import com.naivez.config.RepositoryConfig;
import com.naivez.repository.UserRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationRunner {
    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext()) {
            context.register(RepositoryConfig.class);
            context.refresh();

            var bean = context.getBean(UserRepository.class);
            System.out.println(bean);
        }
    }
}
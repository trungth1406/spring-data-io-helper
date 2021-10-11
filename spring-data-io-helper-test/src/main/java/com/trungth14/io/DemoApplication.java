package com.trungth14.io;

import com.github.javafaker.Faker;
import com.trungth14.io.entity.User;
import com.trungth14.io.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        Faker faker = new Faker();
//        List<User> users = IntStream.range(1, 1_000_000).mapToObj(i -> {
//            User user = new User();
//            user.setUserName(faker.funnyName().name());
//            user.setAge(faker.programmingLanguage().name());
//            user.setUserRole(faker.dog().memePhrase());
//            return user;
//        }).collect(Collectors.toList());
//
//        userRepository.saveAll(users);

    }
}

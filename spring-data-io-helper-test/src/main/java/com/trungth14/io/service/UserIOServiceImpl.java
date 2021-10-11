package com.trungth14.io.service;

import com.trungth14.io.entity.User;
import com.trungth14.io.repository.UserRepository;
import com.trungth14.io.schemaprovider.metamodel.MappedSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;


@Service
public class UserIOServiceImpl implements UserIOService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public void export(HttpServletResponse servletResponse) {
        try{
            List<User> allUser = userRepository.findAll();
            MappedSchema schema = MappedSchema.from(User.class);
            for(User user: allUser){

            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

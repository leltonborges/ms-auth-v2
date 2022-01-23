package org.ms.auth.auth.configs;

import org.modelmapper.ModelMapper;
import org.ms.auth.auth.dtos.UserDTO;
import org.ms.auth.auth.entities.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper instanceModelMapper(){
        ModelMapper mapper = new ModelMapper();
        mapper.createTypeMap(UserDTO.class, User.class);

        mapper.createTypeMap(User.class, UserDTO.class);

        return mapper;
    }
}

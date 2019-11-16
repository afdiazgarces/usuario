package com.p2p.usuario.api;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DozierMappingUsuario {
    @Bean
    public Mapper mapper() {
        return new DozerBeanMapper();
    }
}

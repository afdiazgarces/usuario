package com.p2p.usuario.services;

import com.p2p.usuario.entities.EntitiUsuario;
import com.p2p.usuario.dto.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import com.p2p.usuario.dao.RepositoryUsuario;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Service
public class ServicesUsuario {
    @Autowired
    RepositoryUsuario repositoryUsuario;
    public void save(EntitiUsuario entitiUsuario){
        repositoryUsuario.save(entitiUsuario);
    }
    
    public List<EntitiUsuario> getAll(){
        return repositoryUsuario.findAll();
    }

    public List<EntitiUsuario> getAllEstado(String nombre) {
        //https://www.baeldung.com/spring-data-query-by-example
        EntitiUsuario enm = new EntitiUsuario();
        enm.setNombre(nombre); 
        ExampleMatcher customExampleMatcher = ExampleMatcher.matchingAny()
                .withMatcher("nombre", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        Example<EntitiUsuario> example = Example.of(enm, customExampleMatcher);
        List<EntitiUsuario> passengers = repositoryUsuario.findAll(example);

        return passengers;//To change body of generated methods, choose Tools | Templates.
    }
    
    public List<EntitiUsuario> getUsuarioLogin(String correo, String password) {
        
        EntitiUsuario usu = new EntitiUsuario();
        
        usu.setCorreo(correo); 
        usu.setPassword(password); 

        ExampleMatcher customExampleMatcher = ExampleMatcher.matching().withIgnorePaths("id"); 
        
        Example<EntitiUsuario> example = Example.of(usu,customExampleMatcher);

        return repositoryUsuario.findAll(example);
    }
    
}

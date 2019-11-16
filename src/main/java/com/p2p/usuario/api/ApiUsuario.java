package com.p2p.usuario.api;

import com.p2p.usuario.dto.Usuario;
import com.p2p.usuario.dto.Login;
import com.p2p.usuario.entities.EntitiUsuario;
import com.p2p.usuario.services.ServicesUsuario;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.LinkedList;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class ApiUsuario {
    @Autowired
    ServicesUsuario servicesEmpresa;
    @Autowired
    Mapper mapper; //Objeto para mapear Usuario a EntitiUsuario y realizar la persistencia

    @RequestMapping(value = "/usuario", method = RequestMethod.GET)
    public List<Usuario> getUsuario(){
        List<EntitiUsuario> all =  servicesEmpresa.getAll();
        List<Usuario> list = new LinkedList<>();
        for (EntitiUsuario dto:all){
            Usuario map = mapper.map(dto,Usuario.class);
            list.add(map);
        }
        return list;
    }
    
    @RequestMapping(value = "/usuario/{nombre}", method = RequestMethod.GET)
    public List<Usuario> getUsuarioNombre(@PathVariable("nombre") String estado){
        List<EntitiUsuario> all =  servicesEmpresa.getAllEstado(estado);
        List<Usuario> list = new LinkedList<>();
        for (EntitiUsuario dto:all){
            Usuario map = mapper.map(dto,Usuario.class);
            list.add(map);
        }
        return list;
    }

    @RequestMapping(value = "/usuario", method = RequestMethod.POST)
    public void saveUsuario(@RequestBody Usuario usuario){
        EntitiUsuario entitiEmpresa =mapper.map(usuario,EntitiUsuario.class);
        servicesEmpresa.save(entitiEmpresa);
    }
    
    @RequestMapping(value = "/usuario/{id}", method = RequestMethod.PUT)
    public void updateUsuario(@RequestBody Usuario empresa){
        EntitiUsuario entitiEmpresa =mapper.map(empresa,EntitiUsuario.class);
        servicesEmpresa.save(entitiEmpresa);
    }
    
    @RequestMapping(value = "/usuario/{correo}/{password}", method = RequestMethod.GET)
    public List<Usuario> getUsuarioLogin(@PathVariable("correo") String correo,@PathVariable("password") String password){
        List<EntitiUsuario> all =  servicesEmpresa.getUsuarioLogin(correo, password);
        List<Usuario> list = new LinkedList<>();
        for (EntitiUsuario dto:all){
            Usuario map = mapper.map(dto,Usuario.class);
            list.add(map);
        }
        return list;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public List<Usuario> getLogin(@RequestBody Login login){
        List<EntitiUsuario> all =  servicesEmpresa.getUsuarioLogin(login.getCorreo(),login.getPassword());
        //List<Usuario> list = new LinkedList<>();
        LinkedList list = new LinkedList();
        for (EntitiUsuario dto:all){
            Usuario map = mapper.map(dto,Usuario.class);
            list.add(map);
        }
        if(list.size()>0){
            list.add(0,true);
        }else{
           list.add(0,false); 
        }
        return list;
    }
    
}

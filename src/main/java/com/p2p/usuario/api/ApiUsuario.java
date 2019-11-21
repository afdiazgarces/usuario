package com.p2p.usuario.api;

import com.p2p.usuario.dto.Usuario;
import com.p2p.usuario.dto.Login;
import com.p2p.usuario.entities.EntitiUsuario;
import com.p2p.usuario.services.ServicesUsuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

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
    
   /* @RequestMapping(value = "/usuario/{correo}/{password}", method = RequestMethod.GET)
    public List<Usuario> getUsuarioLogin(@PathVariable("correo") String correo,@PathVariable("password") String password){
        List<EntitiUsuario> all =  servicesEmpresa.getUsuarioLogin(correo, password);
        List<Usuario> list = new LinkedList<>();
        for (EntitiUsuario dto:all){
            Usuario map = mapper.map(dto,Usuario.class);
            list.add(map);
        }
        return list;
    }*/
    
    @PostMapping("user")
    public Usuario getUsuarioLogin(@RequestParam("user") String correo, @RequestParam("password") String pwd){
        List<EntitiUsuario> all =  servicesEmpresa.getUsuarioLogin(correo, pwd);
        Usuario u = new Usuario();
        for (EntitiUsuario dto:all){
            u = mapper.map(dto,Usuario.class);
        }
        
       // System.out.println(u.toString());
        String token = getJWTToken(u);
	u.setToken(token);
        //System.out.println(u.toString());
        
        return u;
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
    
    private String getJWTToken(Usuario usuario) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList(usuario.getRoll());
		
		String token = Jwts
				.builder()
				.setId("softtekJWT")
				.setSubject(usuario.getCorreo())
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return "Bearer " + token;
	}
}

package com.p2p.usuario.dao;

import com.p2p.usuario.entities.EntitiUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryUsuario extends JpaRepository<EntitiUsuario,String> {
}

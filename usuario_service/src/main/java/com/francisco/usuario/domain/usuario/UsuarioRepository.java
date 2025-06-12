package com.francisco.usuario.domain.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByEmail(String subject);
    Usuario getReferenceByEmail(String email);

    Page<Usuario> findByRollAndActivoTrue(ROLL promotor, Pageable pageable);
}

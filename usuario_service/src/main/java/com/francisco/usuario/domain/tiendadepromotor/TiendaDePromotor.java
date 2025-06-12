package com.francisco.usuario.domain.tiendadepromotor;

import com.francisco.usuario.domain.usuario.Usuario;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity(name = "tienda_de_promotor")
@Table(name = "tienda_de_promotor")
public class TiendaDePromotor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "promotor_id", nullable = false)
    private Usuario promotor;

    private Long tiendaId;

    @CreationTimestamp
    private LocalDateTime fechaDeAsignacion;
}

CREATE TABLE usuario_tienda(
    usuario_id BIGINT NOT NULL PRIMARY KEY,
    promotor_id BIGINT NOT NULL,
    tienda_id BIGINT NOT NULL,
    fecha_de_asignacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_usuario_tienda_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_usuario_tienda_promotor FOREIGN KEY (promotor_id) REFERENCES usuarios(id) ON DELETE CASCADE
)
CREATE TABLE tienda_de_promotor(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    promotor_id BIGINT NOT NULL,
    tienda_id BIGINT NOT NULL,
    fecha_de_asignacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_tienda_de_promotor_usuarios FOREIGN KEY (promotor_id) REFERENCES usuarios(id)
)
CREATE TABLE categorias(
                           id SERIAL PRIMARY KEY,
                           categoria TEXT NOT NULL,
                           iof_percentual DECIMAL(4, 2),
                           pis_percentual DECIMAL(4, 2),
                           cofins_percentual DECIMAL(4, 2)
);
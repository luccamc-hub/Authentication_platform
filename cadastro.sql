CREATE DATABASE criando_api;

USE criando_api;

CREATE TABLE usuario(
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    nome_completo VARCHAR(200) NOT NULL,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(50) NOT NULL UNIQUE,
    senha TEXT NOT NULL,
    telefone VARCHAR(15) NOT NULL
);


SELECT * FROM usuario;
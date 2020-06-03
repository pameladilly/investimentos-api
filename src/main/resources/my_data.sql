CREATE TABLE USUARIO (
	IDUSUARIO INT NOT NULL PRIMARY KEY auto_increment ,
    NOME VARCHAR(80) NOT NULL,
    LOGIN VARCHAR(80) NOT NULL,
    SENHA VARCHAR(40) NOT NULL,
    EMAIL VARCHAR(80) NOT NULL,
    DATACADASTRO DATETIME

);

CREATE TABLE ATIVO (
	IDATIVO INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    TICKER VARCHAR(40) NOT NULL,
    DESCRICAO VARCHAR(80) NOT NULL,
    VENCIMENTO DATE,
    TIPO VARCHAR(20),
    IDUSUARIO INT NOT NULL
);

ALTER TABLE ATIVO
ADD CONSTRAINT FK_ATIVO_USUARIO FOREIGN KEY(IDUSUARIO)
REFERENCES USUARIO(IDUSUARIO);

CREATE TABLE CARTEIRA (
	IDCARTEIRA INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    DESCRICAO VARCHAR(80) NOT NULL,
    RENTABILIDADE DECIMAL(8,2),
    ULTIMAATUALIZACAO DATETIME NOT NULL,
    IDUSUARIO INT NOT NULL
);

ALTER TABLE CARTEIRA
ADD CONSTRAINT FK_CARTEIRA_USUARIO FOREIGN KEY(IDUSUARIO)
REFERENCES USUARIO(IDUSUARIO);

CREATE TABLE TRANSACAO (
	IDTRANSACAO INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    DATA DATETIME NOT NULL,
    TOTAL DECIMAL(18,2),
    VALORUNITARIO DECIMAL(18,2) NOT NULL,
    QUANTIDADE DECIMAL(8,2) NOT NULL,
    IDUSUARIO INT NOT NULL
);

ALTER TABLE TRANSACAO
ADD CONSTRAINT FK_TRANSACAO_USUARIO FOREIGN KEY(IDUSUARIO)
REFERENCES USUARIO(IDUSUARIO);

CREATE TABLE TRANSACAO_CARTEIRA (
	IDTRANSACAO_CARTEIRA INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    IDCARTEIRA INT NOT NULL,
    IDTRANSACAO INT NOT NULL,
    DATAVINCULO DATETIME NOT NULL,
    IDUSUARIO INT NOT NULL
);

ALTER TABLE TRANSACAO_CARTEIRA
ADD CONSTRAINT FK_TRANSACAO_CARTEIRA_CARTEIRA FOREIGN KEY(IDCARTEIRA)
REFERENCES CARTEIRA(IDCARTEIRA);

ALTER TABLE TRANSACAO_CARTEIRA
ADD CONSTRAINT FK_TRANSACAO_CARTEIRA_TRANSACAO FOREIGN KEY(IDTRANSACAO)
REFERENCES TRANSACAO(IDTRANSACAO);

ALTER TABLE TRANSACAO_CARTEIRA
ADD CONSTRAINT FK_TRANSACAO_CARTEIRA_USUARIO FOREIGN KEY(IDUSUARIO)
REFERENCES USUARIO(IDUSUARIO);

CREATE TABLE PATRIMONIO (
	IDPATRIMONIO INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    RENTABILIDADE DECIMAL(8,2),
    ULTIMAATUALIZACAO DATETIME NOT NULL,
    DESCRICAO VARCHAR(40) NOT NULL,
    DATACADASTRO DATETIME NOT NULL,
    IDUSUARIO INT NOT NULL
);

ALTER TABLE PATRIMONIO
ADD CONSTRAINT FK_PATRIMONIO_USUARIO FOREIGN KEY(IDUSUARIO)
REFERENCES USUARIO(IDUSUARIO);

ALTER TABLE CARTEIRA
ADD COLUMN IDPATRIMONIO INT NOT NULL;

ALTER TABLE CARTEIRA
ADD CONSTRAINT FK_CARTEIRA_PATRIMONIO FOREIGN KEY(IDPATRIMONIO)
REFERENCES PATRIMONIO(IDPATRIMONIO);
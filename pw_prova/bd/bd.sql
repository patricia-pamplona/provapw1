Drop table Pessoa;
CREATE TABLE Pessoa(
	id_pessoa int NOT NULL PRIMARY KEY generated always as identity,
	nome varchar (60) NOT NULL,
	email varchar (60) NOT NULL,
	senha varchar(60) NOT NULL,
	categoria boolean not null
);
Drop table Arte;
CREATE TABLE Arte(
	id_produtos int NOT NULL PRIMARY KEY generated always as identity,
	preco real NOT NULL,
	nome varchar (60) NOT NULL,
	descricao varchar (300) NOT NULL,
	artista varchar (60) NOT Null,
	ano int	NOT NULL
);

INSERT INTO Pessoa (nome, email, senha, categoria) values ('Maria', 'maria@gmail.com', '123456', 'true');
INSERT INTO Pessoa (nome, email, senha, categoria) values ('Pedro', 'pedro@gmail.com', '123456', 'false');

select * from Pessoa;

INSERT INTO Arte (preco, nome, descricao, artista, ano) values ('20000', 'A Noite Estrelada', 'A tela faz parte da coleção permanente do Museu de Arte Moderna de Nova Iorque desde 1941', 'Vincent van Gogh', '1889');
select * from Arte;

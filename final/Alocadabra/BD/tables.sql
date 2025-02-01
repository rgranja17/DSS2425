CREATE SCHEMA IF NOT EXISTS Alocadabra;
ALTER USER 'root'@'localhost' IDENTIFIED BY '12345678';
GRANT ALL PRIVILEGES ON *.* TO "root"@localhost;
FLUSH PRIVILEGES;

USE Alocadabra;

CREATE TABLE Uc (
    codUC INT PRIMARY KEY NOT NULL,
    nomeUC VARCHAR(50) NOT NULL
);

CREATE TABLE Horario (
    codHorario VARCHAR(10) PRIMARY KEY NOT NULL
);

CREATE TABLE Aluno (
    codAluno VARCHAR(10) PRIMARY KEY NOT NULL,
    nome VARCHAR(100) NOT NULL,
    genero VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    estatuto BOOLEAN NOT NULL,
    codHorario VARCHAR(10),
    FOREIGN KEY (codHorario) REFERENCES Horario(codHorario)
);

CREATE TABLE Aluno_UC (
	codUC INT NOT NULL,
	codAluno VARCHAR(10) NOT NULL,
	PRIMARY KEY (codUC, codAluno),
	FOREIGN KEY (codUC) REFERENCES Uc(codUC),
	FOREIGN KEY (codAluno) REFERENCES Aluno(codAluno)
);

CREATE TABLE User (
    email VARCHAR(50) PRIMARY KEY NOT NULL,
    password VARCHAR(100) NOT NULL,
    role ENUM('Diretor', 'Aluno') NOT NULL
);


CREATE TABLE Sala (
	idSala VARCHAR(20) PRIMARY KEY NOT NULL,
	capacidade INT NOT NULL
);

CREATE TABLE Turno (
	codTurno VARCHAR(10) PRIMARY KEY NOT NULL,
	diaDaSemana VARCHAR(15) NOT NULL,
	horaInicial TIME NOT NULL,
	horaFinal TIME NOT NULL,
	duracao VARCHAR(20) NOT NULL,
	ocupacao VARCHAR(20) NOT NULL,
	codUC INT NOT NULL,
	idSala VARCHAR(20) NOT NULL,
    limite INT NOT NULL,
	FOREIGN KEY (idSala) REFERENCES Sala(idSala),
	FOREIGN KEY (codUC) REFERENCES Uc(codUC)
);

CREATE TABLE Horario_Turno (
    codHorario VARCHAR(10) NOT NULL,
    codTurno VARCHAR(10) NOT NULL,
    PRIMARY KEY (codHorario, codTurno),
    FOREIGN KEY (codHorario) REFERENCES Horario(codHorario),
    FOREIGN KEY (codTurno) REFERENCES Turno(codTurno)
);
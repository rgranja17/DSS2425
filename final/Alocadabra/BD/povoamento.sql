USE Alocadabra;

INSERT INTO Aluno (codAluno, nome, genero, email, estatuto)
VALUES
('A1', 'João Silva', 'Masculino', 'A1@alunos.uminho.pt', TRUE),
('A2', 'Maria Santos', 'Feminino', 'A2@alunos.uminho.pt', FALSE),
('A3', 'Pedro Costa', 'Masculino', 'A3@alunos.uminho.pt', TRUE),
('A4', 'Ana Martins', 'Feminino', 'A4@alunos.uminho.pt', FALSE),
('A5', 'Rui Oliveira', 'Masculino', 'A5@alunos.uminho.pt', TRUE),
('A6', 'Carla Ferreira', 'Feminino', 'A6@alunos.uminho.pt', FALSE),
('A7', 'Tiago Moreira', 'Masculino', 'A7@alunos.uminho.pt', TRUE),
('A8', 'Sofia Almeida', 'Feminino', 'A8@alunos.uminho.pt', FALSE),
('A9', 'Miguel Lopes', 'Masculino', 'A9@alunos.uminho.pt', TRUE),
('A10', 'Beatriz Ribeiro', 'Feminino', 'A10@alunos.uminho.pt', FALSE),
('A11', 'Ricardo Fonseca', 'Masculino', 'A11@alunos.uminho.pt', TRUE),
('A12', 'Helena Monteiro', 'Feminino', 'A12@alunos.uminho.pt', FALSE),
('A13', 'André Lima', 'Masculino', 'A13@alunos.uminho.pt', TRUE),
('A14', 'Catarina Dias', 'Feminino', 'A14@alunos.uminho.pt', FALSE),
('A15', 'Luís Pereira', 'Masculino', 'A15@alunos.uminho.pt', TRUE),
('A16', 'Marta Gomes', 'Feminino', 'A16@alunos.uminho.pt', FALSE),
('A17', 'Fábio Nunes', 'Masculino', 'A17@alunos.uminho.pt', TRUE),
('A18', 'Joana Figueiredo', 'Feminino', 'A18@alunos.uminho.pt', FALSE),
('A19', 'Bruno Rodrigues', 'Masculino', 'A19@alunos.uminho.pt', TRUE),
('A20', 'Inês Matos', 'Feminino', 'A20@alunos.uminho.pt', FALSE);


-- Tabela Uc
INSERT INTO Uc (codUC, nomeUC)
VALUES
(1, 'CP'),
(2, 'DSS'),
(3, 'LI4'),
(4, 'CC'),
(5, 'IA'),
(6, 'SD');

-- Tabela Sala
INSERT INTO Sala (idSala, capacidade)
VALUES
('2.18', 20),
('2.09', 10),
('1.27', 15),
('2.11', 10),
('2.20', 10),
('1.22', 25),
('1.04', 15),
('2.03', 10),
('1.21', 15),
('1.13', 15);

-- Tabela Turno
INSERT INTO Turno (codTurno, diaDaSemana, horaInicial, horaFinal, duracao, ocupacao, codUC, idSala, limite)
VALUES
('T1-CP', 'Segunda', '09:00', '11:00', '2 horas', 0, 1, '2.18', 20),
('T1-DSS', 'Segunda', '09:00', '11:00', '2 horas', 0, 2, '2.09', 10),
('T1-LI4', 'Segunda', '11:00', '13:00', '2 horas', 0, 3, '1.27', 15),
('T1-CC', 'Terça', '09:00', '11:00', '2 horas', 0, 4, '2.11', 10),
('T1-IA', 'Quarta', '11:00', '13:00', '2 horas', 0, 5, '2.20', 10),
('T1-SD', 'Quinta', '16:00', '18:00', '2 horas', 0, 6, '1.22', 25),
('TP1-CP', 'Segunda', '09:00', '11:00', '2 horas', 0, 1, '1.13', 10),
('TP1-DSS', 'Terça', '09:00', '11:00', '2 horas', 0, 2, '1.21', 10),
('TP1-LI4', 'Segunda', '16:00', '18:00', '2 horas', 0, 3, '2.03', 8),
('TP1-CC', 'Quinta', '11:00', '13:00', '2 horas', 0, 4, '1.04', 9),
('TP1-IA', 'Quarta', '09:00', '11:00', '2 horas', 0, 5, '2.11', 10),
('TP1-SD', 'Sexta', '16:00', '18:00', '2 horas', 0, 6, '2.20', 8),
('T2-CP', 'Terça', '11:00', '13:00', '2 horas', 0, 1, '2.18', 20),
('T2-DSS', 'Quarta', '16:00', '18:00', '2 horas', 0, 2, '2.09', 10),
('T2-LI4', 'Sexta', '09:00', '11:00', '2 horas', 0, 3, '1.27', 15),
('T2-CC', 'Sexta', '11:00', '13:00', '2 horas', 0, 4, '2.11', 10),
('T2-IA', 'Quarta', '14:00', '16:00', '2 horas', 0, 5, '2.20', 10),
('T2-SD', 'Segunda', '14:00', '16:00', '2 horas', 0, 6, '1.22', 25),
('TP2-CP', 'Terça', '14:00', '16:00', '2 horas', 0, 1, '1.13', 10),
('TP2-DSS', 'Quarta', '09:00', '11:00', '2 horas', 0, 2, '1.21', 10),
('TP2-LI4', 'Sexta', '14:00', '16:00', '2 horas', 0, 3, '2.09', 8),
('TP2-CC', 'Quinta', '14:00', '16:00', '2 horas', 0, 4, '1.04', 9),
('TP2-IA', 'Terça', '16:00', '18:00', '2 horas', 0, 5, '2.20', 10),
('TP2-SD', 'Sexta', '09:00', '11:00', '2 horas', 0, 6, '2.20', 8),
('T3-CP', 'Quarta', '09:00', '11:00', '2 horas', 0, 1, '2.18', 20),
('T3-DSS', 'Quinta', '14:00', '16:00', '2 horas', 0, 2, '1.27', 15),
('T3-LI4', 'Sexta', '14:00', '16:00', '2 horas', 0, 3, '2.18', 20),
('T3-CC', 'Terça', '14:00', '16:00', '2 horas', 0, 4, '1.21', 15),
('T3-IA', 'Quinta', '09:00', '11:00', '2 horas', 0, 5, '2.11', 10),
('T3-SD', 'Segunda', '11:00', '13:00', '2 horas', 0, 6, '1.22', 25),
('TP3-CP', 'Segunda', '14:00', '16:00', '2 horas', 0, 1, '2.09', 10),
('TP3-DSS', 'Sexta', '14:00', '16:00', '2 horas', 0, 2, '1.27', 10),
('TP3-LI4', 'Quinta', '16:00', '18:00', '2 horas', 0, 3, '2.03', 8),
('TP3-CC', 'Terça', '09:00', '11:00', '2 horas', 0, 4, '1.04', 9),
('TP3-IA', 'Sexta', '11:00', '13:00', '2 horas', 0, 5, '1.22', 10),
('TP3-SD', 'Quarta', '14:00', '16:00', '2 horas', 0, 6, '1.27', 8),
('T4-CP', 'Sexta', '09:00', '11:00', '2 horas', 0, 1, '1.22', 25),
('T4-DSS', 'Segunda', '14:00', '16:00', '2 horas', 0, 2, '2.20', 10),
('T4-LI4', 'Quinta', '11:00', '13:00', '2 horas', 0, 3, '1.27', 15),
('T4-CC', 'Quarta', '14:00', '16:00', '2 horas', 0, 4, '2.20', 10),
('T4-IA', 'Segunda', '16:00', '18:00', '2 horas', 0, 5, '2.11', 10),
('T4-SD', 'Quinta', '09:00', '11:00', '2 horas', 0, 6, '1.22', 25),
('TP4-CP', 'Terça', '14:00', '16:00', '2 horas', 0, 1, '1.04', 10),
('TP4-DSS', 'Quinta', '14:00', '16:00', '2 horas', 0, 2, '2.18', 10),
('TP4-LI4', 'Quarta', '16:00', '18:00', '2 horas', 0, 3, '2.18', 8),
('TP4-CC', 'Segunda', '16:00', '18:00', '2 horas', 0, 4, '2.18', 9),
('TP4-IA', 'Quinta', '14:00', '16:00', '2 horas', 0, 5, '1.27', 10),
('TP4-SD', 'Sexta', '11:00', '13:00', '2 horas', 0, 6, '2.18', 8);

-- Tabela User
INSERT INTO User (email, password, role)
VALUES
('d1@uminho.pt', 'lei', 'Diretor');


INSERT INTO Aluno_UC (codUC, codAluno)
VALUES
(1, 'A1'),
(2, 'A1'),

(1, 'A2'),
(3, 'A2'),

(4, 'A3'),
(5, 'A3'),

(6, 'A4'),

(1, 'A5'),
(2, 'A5'),
(3, 'A5'),

(4, 'A6'),

(1, 'A7'),
(2, 'A7'),

(5, 'A8'),
(6, 'A8'),

(1, 'A9'),
(2, 'A9'),
(3, 'A9'),
(4, 'A9'),
(5, 'A9'),
(6, 'A9'),

(1, 'A10'),

(4, 'A11'),
(6, 'A11'),

(2, 'A12'),
(5, 'A12'),

(3, 'A13'),
(6, 'A13'),

(1, 'A14'),
(4, 'A14'),

(5, 'A15'),

(2, 'A16'),
(6, 'A16'),

(1, 'A17'),
(3, 'A17'),
(4, 'A17'),

(2, 'A18'),
(5, 'A18'),

(1, 'A19'),
(4, 'A19'),
(6, 'A19'),

(2, 'A20'),
(3, 'A20'),
(5, 'A20');


FLUSH TABLES;
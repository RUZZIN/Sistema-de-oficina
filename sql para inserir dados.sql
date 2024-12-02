-- Inserindo dados na tabela peca
INSERT INTO public.peca (codigo, nome, preco_unitario, quantidade) VALUES
('PEC123', 'Filtro de óleo', 50.00, 10),
('PEC124', 'Pastilha de freio', 30.00, 15),
('PEC125', 'Amortecedor', 80.00, 5),
('PEC126', 'Bateria', 120.00, 7),
('PEC127', 'Correia dentada', 60.00, 12),
('PEC128', 'Velas de ignição', 25.00, 20),
('PEC129', 'Óleo lubrificante', 40.00, 8);

-- Inserindo dados na tabela acessorio
INSERT INTO public.acessorio (nome) VALUES
('GPS'),
('Som automotivo'),
('Câmera de ré'),
('Airbag lateral'),
('Ar-condicionado'),
('Sensor de estacionamento'),
('Alarme');

-- Inserindo dados na tabela cliente
INSERT INTO public.cliente (nome, logradouro, numero, complemento, ddi1, ddd1, numero1, email, tipo_cliente) VALUES
('Carlos Silva', 'Rua A', '123', 'Apto 1', 55, 11, 912345678, 'carlos@email.com', 'PessoaFisica'),
('Joana Almeida', 'Av. B', '456', 'Casa', 55, 21, 987654321, 'joana@email.com', 'PessoaFisica'),
('Mecânica Lopes LTDA', 'Rua C', '789', 'Galpão', 55, 31, 33335555, 'lopes@email.com', 'PessoaJuridica'),
('Paula Souza', 'Rua D', '101', NULL, 55, 41, 44446666, 'paula@email.com', 'PessoaFisica'),
('João Pedro', 'Av. E', '202', 'Bloco 2', 55, 51, 55557777, 'joao@email.com', 'PessoaFisica'),
('Pedro Gomes', 'Rua F', '303', NULL, 55, 61, 66668888, 'pedro@email.com', 'PessoaFisica'),
('Transporte Rápido', 'Rua G', '404', 'Galpão 2', 55, 71, 77779999, 'transporte@email.com', 'PessoaJuridica');

-- Inserindo dados na tabela funcionario
INSERT INTO public.funcionario (nome, salario, data_nascimento, data_admissao, cargo, telefone, email, cpf) VALUES
('José Carlos', 2500.00, '1985-10-10', '2020-01-15', 'Mecânico', '11987654321', 'jose.carlos@email.com', '111.111.111-11'),
('Mariana Lopes', 3000.00, '1990-06-20', '2019-03-20', 'Administradora', '21987654321', 'mariana.lopes@email.com', '222.222.222-22'),
('Carlos Henrique', 2000.00, '1988-12-25', '2021-06-01', 'Atendente', '31987654321', 'carlos.henrique@email.com', '333.333.333-33'),
('Joana Silva', 2800.00, '1995-02-14', '2022-08-15', 'Auxiliar', '41987654321', 'joana.silva@email.com', '444.444.444-44'),
('Rafael Souza', 2700.00, '1992-04-18', '2023-02-05', 'Mecânico', '51987654321', 'rafael.souza@email.com', '555.555.555-55'),
('Ana Paula', 3500.00, '1993-08-30', '2021-10-10', 'Gerente', '61987654321', 'ana.paula@email.com', '666.666.666-66'),
('Renato Gomes', 2200.00, '1990-09-12', '2020-12-01', 'Auxiliar', '71987654321', 'renato.gomes@email.com', '777.777.777-77');

-- Inserindo dados na tabela marca
INSERT INTO public.marca (nome) VALUES
('Toyota'),
('Honda'),
('Chevrolet'),
('Volkswagen'),
('Ford'),
('Fiat'),
('Nissan');

-- Inserindo dados na tabela modelo
INSERT INTO public.modelo (nome, id_marca) VALUES
('Corolla', 1),
('Civic', 2),
('Onix', 3),
('Gol', 4),
('Ka', 5),
('Uno', 6),
('Versa', 7);

-- Inserindo dados na tabela veiculo
INSERT INTO public.veiculo (placa, quilometragem, chassi, ano_modelo, ano_fabricacao, id_modelo) VALUES
('ABC1234', 50000, '9BWZZZ377VT004251', 2020, 2019, 1),
('DEF5678', 60000, '9BWZZZ377VT004252', 2019, 2018, 2),
('GHI9101', 70000, '9BWZZZ377VT004253', 2021, 2020, 3),
('JKL2345', 80000, '9BWZZZ377VT004254', 2020, 2019, 4),
('MNO6789', 40000, '9BWZZZ377VT004255', 2022, 2021, 5),
('PQR0123', 30000, '9BWZZZ377VT004256', 2021, 2020, 6),
('STU4567', 20000, '9BWZZZ377VT004257', 2023, 2022, 7);

-- Inserindo dados na tabela ordem_servico
INSERT INTO public.ordem_servico (numero, data, preco_final, status, placa_veiculo, id_cliente) VALUES
(1, '2023-11-01', 1200.50, 'Orçamento', 'ABC1234', 1),
(2, '2023-11-02', 800.00, 'Execução', 'DEF5678', 2),
(3, '2023-11-03', 500.00, 'Finalizada', 'GHI9101', 3),
(4, '2023-11-04', 1500.75, 'Paga', 'JKL2345', 4),
(5, '2023-11-05', 2000.00, 'Aprovação', 'MNO6789', 5),
(6, '2023-11-06', 1750.00, 'Execução', 'PQR0123', 6),
(7, '2023-11-07', 2200.30, 'Finalizada', 'STU4567', 7);

-- Inserindo dados na tabela servico
INSERT INTO public.servico (nome, preco_unitario) VALUES
('Troca de óleo', 150.00),
('Troca de pastilhas de freio', 120.00),
('Troca de amortecedores', 200.00),
('Troca de bateria', 250.00),
('Troca de correia dentada', 180.00),
('Troca de velas de ignição', 90.00),
('Troca de óleo lubrificante', 80.00);

-- Inserindo dados na tabela itens_peca
INSERT INTO public.itens_peca (preco_total, numero_os, quantidade, id_peca) VALUES
(250.00, 1, 2, 1),
(150.00, 2, 1, 2),
(300.00, 3, 3, 3),
(100.00, 4, 1, 4),
(200.00, 5, 2, 5),
(175.00, 6, 1, 6),
(400.00, 7, 4, 7);

-- Inserindo dados na tabela itens_servico
INSERT INTO public.itens_servico (horario_inicio, horario_fim, quantidade, preco_total, id_funcionario, id_servico, numero_os) VALUES
('08:00:00', '10:00:00', 1, 1200.00, 1, 1, 1),
('09:00:00', '12:00:00', 1, 800.00, 2, 2, 2),
('13:00:00', '15:00:00', 1, 500.00, 3, 3, 3),
('10:00:00', '14:00:00', 1, 1500.00, 4, 4, 4),
('11:00:00', '15:00:00', 1, 2000.00, 5, 5, 5),
('12:00:00', '16:00:00', 1, 1750.00, 6, 6, 6),
('14:00:00', '18:00:00', 1, 2200.00, 7, 7, 7);

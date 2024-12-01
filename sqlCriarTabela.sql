CREATE SEQUENCE acessorio_id_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE;
CREATE SEQUENCE cliente_id_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE;
CREATE SEQUENCE funcionario_id_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE;
CREATE SEQUENCE itens_peca_id_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE;
CREATE SEQUENCE itens_servico_id_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE;
CREATE SEQUENCE marca_id_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE;
CREATE SEQUENCE modelo_id_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE;
CREATE SEQUENCE oficina_id_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE;
CREATE SEQUENCE ordem_servico_numero_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE;
CREATE SEQUENCE peca_id_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE;
CREATE SEQUENCE propriedade_id_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE;
CREATE SEQUENCE servico_id_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE;

CREATE TABLE acessorio (
    id serial PRIMARY KEY,
    nome varchar(80) NOT NULL
);

CREATE TABLE cliente (
    id bigserial PRIMARY KEY,
    nome varchar(255) NOT NULL,
    logradouro varchar(255) NOT NULL,
    numero varchar(50) NOT NULL,
    complemento varchar(255),
    ddi1 int NOT NULL,
    ddd1 int NOT NULL,
    numero1 int NOT NULL,
    ddi2 int,
    ddd2 int,
    numero2 int,
    email varchar(255) NOT NULL,
    cpf varchar(14) UNIQUE,
    cnpj varchar(18),
    inscricao_estadual varchar(20),
    contato varchar(255),
    tipo_cliente varchar(50) NOT NULL CHECK (tipo_cliente IN ('PessoaFisica', 'PessoaJuridica'))
);

CREATE TABLE funcionario (
    id bigserial PRIMARY KEY,
    nome varchar(255) NOT NULL,
    salario numeric(10, 2),
    data_nascimento date,
    data_admissao date,
    data_demissao date,
    cargo varchar(100),
    endereco varchar(255),
    telefone varchar(20),
    email varchar(255),
    cpf varchar(14) NOT NULL UNIQUE,
    rg varchar(20),
    situacao varchar(50)
);

CREATE TABLE marca (
    id serial PRIMARY KEY,
    nome varchar(80) NOT NULL
);

CREATE TABLE oficina (
    id serial PRIMARY KEY,
    nome varchar(80) NOT NULL,
    logradouro varchar(60) NOT NULL,
    complemento varchar(60) NOT NULL,
    numero varchar(60) NOT NULL,
    ddi1 int NOT NULL,
    ddd1 int NOT NULL,
    numero1 int NOT NULL,
    ddi2 int,
    ddd2 int,
    numero2 int,
    email varchar(50) NOT NULL UNIQUE
);

CREATE TABLE peca (
    id serial PRIMARY KEY,
    codigo varchar(50) NOT NULL,
    nome varchar(80) NOT NULL,
    preco_unitario float NOT NULL,
    quantidade int NOT NULL
);

CREATE TABLE servico (
    id serial PRIMARY KEY,
    nome varchar(80) NOT NULL,
    preco_unitario float NOT NULL
);

CREATE TABLE modelo (
    id serial PRIMARY KEY,
    nome varchar(80) NOT NULL,
    id_marca int NOT NULL REFERENCES marca(id)
);

CREATE TABLE veiculo (
    placa varchar(17) PRIMARY KEY,
    quilometragem int NOT NULL,
    chassi varchar(17),
    patrimonio varchar(50),
    ano_modelo int NOT NULL,
    ano_fabricacao int NOT NULL,
    id_modelo int NOT NULL REFERENCES modelo(id)
);

CREATE TABLE acessorio_veiculo (
    id_acessorio int NOT NULL REFERENCES acessorio(id),
    placa_veiculo varchar(17) NOT NULL REFERENCES veiculo(placa)
);

CREATE TABLE ordem_servico (
    numero serial PRIMARY KEY,
    "data" date NOT NULL,
    preco_final float NOT NULL,
    status varchar(30) NOT NULL CHECK (status IN ('Orçamento', 'Aprovação', 'Execução', 'Finalizada', 'Paga')),
    placa_veiculo varchar(17) NOT NULL REFERENCES veiculo(placa),
    id_cliente int REFERENCES cliente(id)
);

CREATE TABLE propriedade (
    id serial PRIMARY KEY,
    data_inicio date NOT NULL,
    data_fim date NOT NULL,
    id_cliente int NOT NULL REFERENCES cliente(id),
    placa_veiculo varchar(17) NOT NULL REFERENCES veiculo(placa)
);

CREATE TABLE itens_peca (
    id serial PRIMARY KEY,
    preco_total float NOT NULL,
    numero_os int NOT NULL REFERENCES ordem_servico(numero),
    quantidade int NOT NULL,
    id_peca int NOT NULL REFERENCES peca(id)
);

CREATE TABLE itens_servico (
    id serial PRIMARY KEY,
    horario_inicio time NOT NULL,
    horario_fim time NOT NULL,
    quantidade int NOT NULL,
    preco_total float NOT NULL,
    id_funcionario int NOT NULL REFERENCES funcionario(id),
    id_servico int NOT NULL REFERENCES servico(id),
    numero_os int NOT NULL REFERENCES ordem_servico(numero)
);


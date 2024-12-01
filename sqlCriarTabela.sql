-- DROP SCHEMA public;

CREATE SCHEMA public AUTHORIZATION postgres;

-- DROP SEQUENCE public.acessorio_id_seq;

CREATE SEQUENCE public.acessorio_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE public.acessorio_id_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE public.acessorio_id_seq TO postgres;

-- DROP SEQUENCE public.cliente_id_seq;

CREATE SEQUENCE public.cliente_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE public.cliente_id_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE public.cliente_id_seq TO postgres;

-- DROP SEQUENCE public.funcionario_id_seq;

CREATE SEQUENCE public.funcionario_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE public.funcionario_id_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE public.funcionario_id_seq TO postgres;

-- DROP SEQUENCE public.itens_peca_id_seq;

CREATE SEQUENCE public.itens_peca_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE public.itens_peca_id_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE public.itens_peca_id_seq TO postgres;

-- DROP SEQUENCE public.itens_servico_id_seq;

CREATE SEQUENCE public.itens_servico_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE public.itens_servico_id_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE public.itens_servico_id_seq TO postgres;

-- DROP SEQUENCE public.marca_id_seq;

CREATE SEQUENCE public.marca_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE public.marca_id_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE public.marca_id_seq TO postgres;

-- DROP SEQUENCE public.modelo_id_seq;

CREATE SEQUENCE public.modelo_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE public.modelo_id_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE public.modelo_id_seq TO postgres;

-- DROP SEQUENCE public.oficina_id_seq;

CREATE SEQUENCE public.oficina_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE public.oficina_id_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE public.oficina_id_seq TO postgres;

-- DROP SEQUENCE public.ordem_servico_numero_seq;

CREATE SEQUENCE public.ordem_servico_numero_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE public.ordem_servico_numero_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE public.ordem_servico_numero_seq TO postgres;

-- DROP SEQUENCE public.peca_id_seq;

CREATE SEQUENCE public.peca_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE public.peca_id_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE public.peca_id_seq TO postgres;

-- DROP SEQUENCE public.propriedade_id_seq;

CREATE SEQUENCE public.propriedade_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE public.propriedade_id_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE public.propriedade_id_seq TO postgres;

-- DROP SEQUENCE public.servico_id_seq;

CREATE SEQUENCE public.servico_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE public.servico_id_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE public.servico_id_seq TO postgres;
-- public.acessorio definition

-- Drop table

-- DROP TABLE public.acessorio;

CREATE TABLE public.acessorio (
	id serial4 NOT NULL,
	nome varchar(80) NOT NULL,
	CONSTRAINT acessorio_pkey PRIMARY KEY (id)
);

-- Permissions

ALTER TABLE public.acessorio OWNER TO postgres;
GRANT ALL ON TABLE public.acessorio TO postgres;


-- public.cliente definition

-- Drop table

-- DROP TABLE public.cliente;

CREATE TABLE public.cliente (
	id bigserial NOT NULL,
	nome varchar(255) NOT NULL,
	logradouro varchar(255) NOT NULL,
	numero varchar(50) NOT NULL,
	complemento varchar(255) NULL,
	ddi1 int4 NOT NULL,
	ddd1 int4 NOT NULL,
	numero1 int4 NOT NULL,
	ddi2 int4 NULL,
	ddd2 int4 NULL,
	numero2 int4 NULL,
	email varchar(255) NOT NULL,
	cpf varchar(14) NULL,
	cnpj varchar(18) NULL,
	inscricao_estadual varchar(20) NULL,
	contato varchar(255) NULL,
	tipo_cliente varchar(50) NOT NULL,
	CONSTRAINT cliente_cpf_key UNIQUE (cpf),
	CONSTRAINT cliente_pkey PRIMARY KEY (id),
	CONSTRAINT cliente_tipo_cliente_check CHECK (((tipo_cliente)::text = ANY ((ARRAY['PessoaFisica'::character varying, 'PessoaJuridica'::character varying])::text[])))
);

-- Permissions

ALTER TABLE public.cliente OWNER TO postgres;
GRANT ALL ON TABLE public.cliente TO postgres;


-- public.funcionario definition

-- Drop table

-- DROP TABLE public.funcionario;

CREATE TABLE public.funcionario (
	id bigserial NOT NULL,
	nome varchar(255) NOT NULL,
	salario numeric(10, 2) NULL,
	data_nascimento date NULL,
	data_admissao date NULL,
	data_demissao date NULL,
	cargo varchar(100) NULL,
	endereco varchar(255) NULL,
	telefone varchar(20) NULL,
	email varchar(255) NULL,
	cpf varchar(14) NOT NULL,
	rg varchar(20) NULL,
	situacao varchar(50) NULL,
	CONSTRAINT funcionario_cpf_key UNIQUE (cpf),
	CONSTRAINT funcionario_pkey PRIMARY KEY (id)
);

-- Permissions

ALTER TABLE public.funcionario OWNER TO postgres;
GRANT ALL ON TABLE public.funcionario TO postgres;


-- public.marca definition

-- Drop table

-- DROP TABLE public.marca;

CREATE TABLE public.marca (
	id serial4 NOT NULL,
	nome varchar(80) NOT NULL,
	CONSTRAINT marca_pkey PRIMARY KEY (id)
);

-- Permissions

ALTER TABLE public.marca OWNER TO postgres;
GRANT ALL ON TABLE public.marca TO postgres;


-- public.oficina definition

-- Drop table

-- DROP TABLE public.oficina;

CREATE TABLE public.oficina (
	id serial4 NOT NULL,
	nome varchar(80) NOT NULL,
	logradouro varchar(60) NOT NULL,
	complemento varchar(60) NOT NULL,
	numero varchar(60) NOT NULL,
	ddi1 int4 NOT NULL,
	ddd1 int4 NOT NULL,
	numero1 int4 NOT NULL,
	ddi2 int4 NULL,
	ddd2 int4 NULL,
	numero2 int4 NULL,
	email varchar(50) NOT NULL,
	CONSTRAINT oficina_ddi1_ddd1_numero1_key UNIQUE (ddi1, ddd1, numero1),
	CONSTRAINT oficina_ddi2_ddd2_numero2_key UNIQUE (ddi2, ddd2, numero2),
	CONSTRAINT oficina_email_key UNIQUE (email),
	CONSTRAINT oficina_pkey PRIMARY KEY (id)
);

-- Permissions

ALTER TABLE public.oficina OWNER TO postgres;
GRANT ALL ON TABLE public.oficina TO postgres;


-- public.peca definition

-- Drop table

-- DROP TABLE public.peca;

CREATE TABLE public.peca (
	id serial4 NOT NULL,
	codigo varchar(50) NOT NULL,
	nome varchar(80) NOT NULL,
	preco_unitario float4 NOT NULL,
	quantidade int4 NOT NULL,
	CONSTRAINT peca_pkey PRIMARY KEY (id)
);

-- Permissions

ALTER TABLE public.peca OWNER TO postgres;
GRANT ALL ON TABLE public.peca TO postgres;


-- public.servico definition

-- Drop table

-- DROP TABLE public.servico;

CREATE TABLE public.servico (
	id serial4 NOT NULL,
	nome varchar(80) NOT NULL,
	preco_unitario float4 NOT NULL,
	CONSTRAINT servico_pkey PRIMARY KEY (id)
);

-- Permissions

ALTER TABLE public.servico OWNER TO postgres;
GRANT ALL ON TABLE public.servico TO postgres;


-- public.modelo definition

-- Drop table

-- DROP TABLE public.modelo;

CREATE TABLE public.modelo (
	id serial4 NOT NULL,
	nome varchar(80) NOT NULL,
	id_marca int4 NOT NULL,
	CONSTRAINT modelo_pkey PRIMARY KEY (id),
	CONSTRAINT modelo_id_marca_fkey FOREIGN KEY (id_marca) REFERENCES public.marca(id)
);

-- Permissions

ALTER TABLE public.modelo OWNER TO postgres;
GRANT ALL ON TABLE public.modelo TO postgres;


-- public.veiculo definition

-- Drop table

-- DROP TABLE public.veiculo;

CREATE TABLE public.veiculo (
	placa varchar(17) NOT NULL,
	quilometragem int4 NOT NULL,
	chassi varchar(17) NULL,
	patrimonio varchar(50) NULL,
	ano_modelo int4 NOT NULL,
	ano_fabricacao int4 NOT NULL,
	id_modelo int4 NOT NULL,
	CONSTRAINT veiculo_pkey PRIMARY KEY (placa),
	CONSTRAINT veiculo_id_modelo_fkey FOREIGN KEY (id_modelo) REFERENCES public.modelo(id)
);

-- Permissions

ALTER TABLE public.veiculo OWNER TO postgres;
GRANT ALL ON TABLE public.veiculo TO postgres;


-- public.acessorio_veiculo definition

-- Drop table

-- DROP TABLE public.acessorio_veiculo;

CREATE TABLE public.acessorio_veiculo (
	id_acessorio int4 NOT NULL,
	placa_veiculo varchar(17) NOT NULL,
	CONSTRAINT acessorio_veiculo_id_acessorio_fkey FOREIGN KEY (id_acessorio) REFERENCES public.acessorio(id),
	CONSTRAINT acessorio_veiculo_placa_veiculo_fkey FOREIGN KEY (placa_veiculo) REFERENCES public.veiculo(placa)
);

-- Permissions

ALTER TABLE public.acessorio_veiculo OWNER TO postgres;
GRANT ALL ON TABLE public.acessorio_veiculo TO postgres;


-- public.ordem_servico definition

-- Drop table

-- DROP TABLE public.ordem_servico;

CREATE TABLE public.ordem_servico (
	numero serial4 NOT NULL,
	"data" date NOT NULL,
	preco_final float4 NOT NULL,
	status varchar(30) NOT NULL,
	placa_veiculo varchar(17) NOT NULL,
	cliente_id int4 NULL,
	CONSTRAINT ordem_servico_pkey PRIMARY KEY (numero),
	CONSTRAINT ordem_servico_status_check CHECK (((status)::text = ANY ((ARRAY['Orçamento'::character varying, 'Aprovação'::character varying, 'Execução'::character varying, 'Finalizada'::character varying, 'Paga'::character varying])::text[]))),
	CONSTRAINT ordem_servico_placa_veiculo_fkey FOREIGN KEY (placa_veiculo) REFERENCES public.veiculo(placa)
);

-- Permissions

ALTER TABLE public.ordem_servico OWNER TO postgres;
GRANT ALL ON TABLE public.ordem_servico TO postgres;


-- public.propriedade definition

-- Drop table

-- DROP TABLE public.propriedade;

CREATE TABLE public.propriedade (
	id serial4 NOT NULL,
	data_inicio date NOT NULL,
	data_fim date NOT NULL,
	id_cliente int4 NOT NULL,
	placa_veiculo varchar(17) NOT NULL,
	CONSTRAINT propriedade_pkey PRIMARY KEY (id),
	CONSTRAINT propriedade_placa_veiculo_fkey FOREIGN KEY (placa_veiculo) REFERENCES public.veiculo(placa)
);

-- Permissions

ALTER TABLE public.propriedade OWNER TO postgres;
GRANT ALL ON TABLE public.propriedade TO postgres;


-- public.itens_peca definition

-- Drop table

-- DROP TABLE public.itens_peca;

CREATE TABLE public.itens_peca (
	id serial4 NOT NULL,
	preco_total float4 NOT NULL,
	numero_os int4 NOT NULL,
	quantidade int4 NOT NULL,
	id_peca int4 NOT NULL,
	CONSTRAINT itens_peca_pkey PRIMARY KEY (id),
	CONSTRAINT fk_id_peca FOREIGN KEY (id_peca) REFERENCES public.peca(id),
	CONSTRAINT itens_peca_id_peca_fkey FOREIGN KEY (id_peca) REFERENCES public.peca(id),
	CONSTRAINT itens_peca_numero_os_fkey FOREIGN KEY (numero_os) REFERENCES public.ordem_servico(numero)
);

-- Permissions

ALTER TABLE public.itens_peca OWNER TO postgres;
GRANT ALL ON TABLE public.itens_peca TO postgres;


-- public.itens_servico definition

-- Drop table

-- DROP TABLE public.itens_servico;

CREATE TABLE public.itens_servico (
	id serial4 NOT NULL,
	horario_inicio time NOT NULL,
	horario_fim time NOT NULL,
	quantidade int4 NOT NULL,
	preco_total float4 NOT NULL,
	id_funcionario int4 NOT NULL,
	id_servico int4 NOT NULL,
	numero_os int4 NOT NULL,
	CONSTRAINT itens_servico_pkey PRIMARY KEY (id),
	CONSTRAINT fk_numero_os FOREIGN KEY (numero_os) REFERENCES public.ordem_servico(numero),
	CONSTRAINT itens_servico_id_servico_fkey FOREIGN KEY (id_servico) REFERENCES public.servico(id),
	CONSTRAINT itens_servico_numero_os_fkey FOREIGN KEY (numero_os) REFERENCES public.ordem_servico(numero)
);

-- Permissions

ALTER TABLE public.itens_servico OWNER TO postgres;
GRANT ALL ON TABLE public.itens_servico TO postgres;




-- Permissions

GRANT ALL ON SCHEMA public TO postgres;
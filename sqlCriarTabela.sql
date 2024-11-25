CREATE TABLE acessorio (
	id serial4 NOT NULL,
	nome varchar(80) NOT NULL,
	CONSTRAINT acessorio_pkey PRIMARY KEY (id)
);

CREATE TABLE cliente (
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
	cpf varchar(11) NULL,
	cnpj varchar(14) NULL,
	inscricao_estadual varchar(50) NULL,
	contato varchar(60) NOT NULL,
	tipo_cliente varchar(30) NOT NULL,
	CONSTRAINT cliente_cnpj_key UNIQUE (cnpj),
	CONSTRAINT cliente_cpf_key UNIQUE (cpf),
	CONSTRAINT cliente_ddi1_ddd1_numero1_key UNIQUE (ddi1, ddd1, numero1),
	CONSTRAINT cliente_ddi2_ddd2_numero2_key UNIQUE (ddi2, ddd2, numero2),
	CONSTRAINT cliente_email_key UNIQUE (email),
	CONSTRAINT cliente_inscricao_estadual_key UNIQUE (inscricao_estadual),
	CONSTRAINT cliente_pkey PRIMARY KEY (id)
);


CREATE TABLE funcionario (
	id serial4 NOT NULL,
	nome varchar(80) NOT NULL,
	CONSTRAINT funcionario_pkey PRIMARY KEY (id)
);

CREATE TABLE marca (
	id serial4 NOT NULL,
	nome varchar(80) NOT NULL,
	CONSTRAINT marca_pkey PRIMARY KEY (id)
);


CREATE TABLE oficina (
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


CREATE TABLE peca (
	id serial4 NOT NULL,
	codigo varchar(50) NOT NULL,
	nome varchar(80) NOT NULL,
	preco_unitario float4 NOT NULL,
	quantidade int4 NOT NULL,
	CONSTRAINT peca_pkey PRIMARY KEY (id)
);

CREATE TABLE servico (
	id serial4 NOT NULL,
	nome varchar(80) NOT NULL,
	preco_unitario float4 NOT NULL,
	CONSTRAINT servico_pkey PRIMARY KEY (id)
);


CREATE TABLE modelo (
	id serial4 NOT NULL,
	nome varchar(80) NOT NULL,
	id_marca int4 NOT NULL,
	CONSTRAINT modelo_pkey PRIMARY KEY (id),
	CONSTRAINT modelo_id_marca_fkey FOREIGN KEY (id_marca) REFERENCES marca(id)
);

CREATE TABLE veiculo (
	placa varchar(17) NOT NULL,
	quilometragem int4 NOT NULL,
	chassi varchar(17) NULL,
	patrimonio varchar(50) NULL,
	ano_modelo int4 NOT NULL,
	ano_fabricacao int4 NOT NULL,
	id_modelo int4 NOT NULL,
	CONSTRAINT veiculo_pkey PRIMARY KEY (placa),
	CONSTRAINT veiculo_id_modelo_fkey FOREIGN KEY (id_modelo) REFERENCES modelo(id)
);


CREATE TABLE acessorio_veiculo (
	id_acessorio int4 NOT NULL,
	placa_veiculo varchar(17) NOT NULL,
	CONSTRAINT acessorio_veiculo_id_acessorio_fkey FOREIGN KEY (id_acessorio) REFERENCES acessorio(id),
	CONSTRAINT acessorio_veiculo_placa_veiculo_fkey FOREIGN KEY (placa_veiculo) REFERENCES veiculo(placa)
);


CREATE TABLE ordem_servico (
	numero serial4 NOT NULL,
	"data" date NOT NULL,
	preco_final float4 NOT NULL,
	status varchar(30) NOT NULL,
	placa_veiculo varchar(17) NOT NULL,
	CONSTRAINT ordem_servico_pkey PRIMARY KEY (numero),
	CONSTRAINT ordem_servico_status_check CHECK (((status)::text = ANY ((ARRAY['Orçamento'::character varying, 'Aprovação'::character varying, 'Execução'::character varying, 'Finalizada'::character varying, 'Paga'::character varying])::text[]))),
	CONSTRAINT ordem_servico_placa_veiculo_fkey FOREIGN KEY (placa_veiculo) REFERENCES veiculo(placa)
);

CREATE TABLE propriedade (
	id serial4 NOT NULL,
	data_inicio date NOT NULL,
	data_fim date NOT NULL,
	id_cliente int4 NOT NULL,
	placa_veiculo varchar(17) NOT NULL,
	CONSTRAINT propriedade_pkey PRIMARY KEY (id),
	CONSTRAINT propriedade_id_cliente_fkey FOREIGN KEY (id_cliente) REFERENCES cliente(id),
	CONSTRAINT propriedade_placa_veiculo_fkey FOREIGN KEY (placa_veiculo) REFERENCES veiculo(placa)
);

CREATE TABLE itens_peca (
	id serial4 NOT NULL,
	preco_total float4 NOT NULL,
	numero_os int4 NOT NULL,
	quantidade int4 NOT NULL,
	id_peca int4 NOT NULL,
	CONSTRAINT itens_peca_pkey PRIMARY KEY (id),
	CONSTRAINT fk_id_peca FOREIGN KEY (id_peca) REFERENCES peca(id),
	CONSTRAINT itens_peca_id_peca_fkey FOREIGN KEY (id_peca) REFERENCES peca(id),
	CONSTRAINT itens_peca_numero_os_fkey FOREIGN KEY (numero_os) REFERENCES ordem_servico(numero)
);

CREATE TABLE itens_servico (
	id serial4 NOT NULL,
	horario_inicio time NOT NULL,
	horario_fim time NOT NULL,
	quantidade int4 NOT NULL,
	preco_total float4 NOT NULL,
	id_funcionario int4 NOT NULL,
	id_servico int4 NOT NULL,
	numero_os int4 NOT NULL,
	CONSTRAINT itens_servico_pkey PRIMARY KEY (id),
	CONSTRAINT fk_numero_os FOREIGN KEY (numero_os) REFERENCES ordem_servico(numero),
	CONSTRAINT itens_servico_id_funcionario_fkey FOREIGN KEY (id_funcionario) REFERENCES funcionario(id),
	CONSTRAINT itens_servico_id_servico_fkey FOREIGN KEY (id_servico) REFERENCES servico(id),
	CONSTRAINT itens_servico_numero_os_fkey FOREIGN KEY (numero_os) REFERENCES ordem_servico(numero)
);
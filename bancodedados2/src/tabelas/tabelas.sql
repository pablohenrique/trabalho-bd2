SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

DROP SCHEMA IF EXISTS cia CASCADE;
CREATE SCHEMA cia;

SET search_path TO cia;

SET default_tablespace = '';

SET default_with_oids = false;




CREATE TABLE departamento (
    numero integer NOT NULL,
    nome character varying(15) NOT NULL,
    gerssn character(9) NOT NULL,
    gerdatainicio date NOT NULL
);

CREATE TABLE dept_localizacao (
    dlocalizacao character varying(15) NOT NULL,
    departamento_numero integer NOT NULL
);

CREATE TABLE dependentes (
    nome_dependente character varying(255) NOT NULL,
    essn character(9) NOT NULL,
    sexo character(1),
    datanasc date,
    parentesco character varying(8)
);

CREATE TABLE empregado (
    ssn character(9) NOT NULL,
    nome character varying(60) NOT NULL, -- colocar o nome completo do empregado
    sexo varchar(1),
    endereco character varying(45),
    salario numeric(10,2),
    datanasc date,
    dno integer NOT NULL,
    superssn character(9) NOT NULL,
    senha character(15) NOT NULL
);


CREATE TABLE auditoria(
    gerssn character(9) NOT NULL,
    essn character(9) NOT NULL,
    asalario numeric(10,2),
    nsalario numeric(10,2),
    datamodificado date
);


CREATE TABLE projeto (
    pnumero integer NOT NULL,
    pjnome character varying(15) NOT NULL,
    plocalizacao character varying(15),
    dnum integer NOT NULL
);

CREATE TABLE trabalha_em (
    essn character(9) NOT NULL,
    pjnumero integer NOT NULL,
    horas numeric(3,1) NOT NULL
);

CREATE TABLE propaganda (
    id integer NOT NULL,
    projeto INTEGER NOT NULL,
    dataInicio DATE NOT NULL,
    dataFinal DATE,
    agencia VARCHAR(15),
    tarifa FLOAT
);

--
-- criando sequencias 
--
CREATE SEQUENCE departamento_seq INCREMENT 1; 
CREATE SEQUENCE projeto_seq INCREMENT 1; 
CREATE SEQUENCE propaganda_seq INCREMENT 1;

INSERT INTO departamento VALUES(1,'FACOM','11011','17-04-2013');
INSERT INTO empregado VALUES('11011','YURI CAMPOS','M','RUA X',1000,'25-08-1992',1,'11011','123');

ALTER TABLE departamento ALTER COLUMN numero SET DEFAULT nextval('departamento_seq');
ALTER TABLE projeto ALTER COLUMN pnumero SET DEFAULT nextval('projeto_seq');
ALTER TABLE propaganda ALTER COLUMN id SET DEFAULT nextval('propaganda_seq');


ALTER TABLE ONLY departamento
    ADD CONSTRAINT pk_departamento PRIMARY KEY (numero);

ALTER TABLE ONLY dependentes
    ADD CONSTRAINT pk_dependentes PRIMARY KEY (nome_dependente, essn);

ALTER TABLE ONLY empregado
    ADD CONSTRAINT pk_empregado PRIMARY KEY (ssn);

ALTER TABLE ONLY projeto
    ADD CONSTRAINT pk_projeto PRIMARY KEY (pnumero);

ALTER TABLE ONLY trabalha_em
    ADD CONSTRAINT pk_trabalha_em PRIMARY KEY (essn, pjnumero);

ALTER TABLE ONLY dept_localizacao
    ADD CONSTRAINT pk_localizacao PRIMARY KEY (dlocalizacao, departamento_numero);

ALTER TABLE ONLY propaganda 
    ADD CONSTRAINT pkey_id PRIMARY KEY (id);

--
-- RESTRICOES UNIQUE
--

ALTER TABLE ONLY departamento
    ADD CONSTRAINT uq_dnome UNIQUE (nome);


ALTER TABLE ONLY projeto
    ADD CONSTRAINT uq_pnome UNIQUE (pjnome);


--
-- RESTRICOES FOREIGN KEY
--

ALTER TABLE ONLY departamento ADD CONSTRAINT fk_empregado_gerencia_dpto
    FOREIGN KEY (gerssn)
    REFERENCES empregado(ssn)
    ON DELETE NO ACTION
    ON UPDATE CASCADE;

ALTER TABLE dept_localizacao ADD CONSTRAINT fk_departamento_local
    FOREIGN KEY(departamento_numero)
    REFERENCES departamento(numero)
    ON DELETE NO ACTION
    ON UPDATE CASCADE;

ALTER TABLE empregado ADD CONSTRAINT fk_empregado_trabalha_departamento 
    FOREIGN KEY (dno)
    REFERENCES departamento (numero)
    ON DELETE NO ACTION--NAO PERMITE REMOVER DEPARTAMENTO
    ON UPDATE CASCADE;--CASO ATUALIZE CHAVE DEPARTAMENTO, ATUALIZA EM TODO BANCO AS CHAVES QUE REFERENCIAM
    
ALTER TABLE empregado ADD CONSTRAINT fk_empregado_supervisiona_empregado
    FOREIGN KEY (superssn)
    REFERENCES empregado (ssn)
    ON DELETE SET NULL--se remover o o empregado genrente os que referenciam vao ficar NULL
    ON UPDATE SET NULL;

ALTER TABLE projeto ADD CONSTRAINT fk_projeto_controlado_departamento
    FOREIGN KEY (dnum)
    REFERENCES Departamento (numero)
    ON DELETE NO ACTION--um projeto nao pode ficar sem departamento
    ON UPDATE CASCADE;--se alterar a chave departamento altera em projeto

ALTER TABLE dependentes ADD CONSTRAINT fk_dependentes_do_empregado
    FOREIGN KEY (essn)
    REFERENCES empregado (ssn)
    ON DELETE CASCADE --as atualizacoes de delete/update dependentes e empregados devem ocorrer em conjuntos
    ON UPDATE CASCADE;

ALTER TABLE dept_localizacao ADD CONSTRAINT fk_dept_localizacao_departamento
    FOREIGN KEY (Departamento_numero)
    REFERENCES Departamento (numero)
    ON DELETE CASCADE --caso remova um departamento pode remover a localizacao
    ON UPDATE CASCADE;--caso atualize o numero do departamento deve atualizar a localizacao

ALTER TABLE trabalha_em ADD CONSTRAINT fk_trabalha_em_empregado
    FOREIGN KEY (essn)
    REFERENCES empregado (ssn)
    ON DELETE CASCADE --caso remova um empregado deve remover os projetos que ele trabalha
    ON UPDATE CASCADE;--caso ocorra uma atualizacao no SSN deve fazer update na tabela trabalha_em

ALTER TABLE trabalha_em ADD CONSTRAINT  fk_trabalha_em_projeto
    FOREIGN KEY (pjnumero)
    REFERENCES projeto (pnumero)
    ON DELETE NO ACTION --caso remova um projeto nao pode remover o trabalha em, pois tem trabalhadores
    ON UPDATE CASCADE;--caso atualize chave primaria de projeto posso atualizar em trabalha em

ALTER TABLE propaganda ADD CONSTRAINT fkey_projeto 
    FOREIGN KEY(projeto) REFERENCES projeto(pnumero)
    ON DELETE CASCADE --quando remover um projeto remove todas propagadas
    ON UPDATE CASCADE;--quando alterar um projeto update todas propagadas
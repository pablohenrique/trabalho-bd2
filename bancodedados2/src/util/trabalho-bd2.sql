--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- Name: cia; Type: SCHEMA; Schema: -; Owner: -
--
drop schema if exists cia cascade;
CREATE SCHEMA cia;


SET search_path = cia, pg_catalog;
SET default_tablespace = '';
SET default_with_oids = false;

--
-- Name: departamento; Type: TABLE; Schema: cia; Owner: -; Tablespace: 
--

CREATE TABLE departamento (
    numero integer NOT NULL,
    nome character varying(15) NOT NULL,
    gerssn character(9) NOT NULL,
    gerdatainicio date
);


--
-- Name: dependentes; Type: TABLE; Schema: cia; Owner: -; Tablespace: 
--

CREATE TABLE dependentes (
    nome_dependente character varying(255) NOT NULL,
    essn character(9) NOT NULL,
    sexo character(1),
    datanasc date,
    parentesco character varying(8)
);


--
-- Name: dept_localizacao; Type: TABLE; Schema: cia; Owner: -; Tablespace: 
--

CREATE TABLE dept_localizacao (
    dlocalizacao character varying(15) NOT NULL,
    departamento_numero integer NOT NULL
);


--
-- Name: empregado; Type: TABLE; Schema: cia; Owner: -; Tablespace: 
--

CREATE TABLE empregado (
    ssn character(9) NOT NULL,
    nome character varying(60) NOT NULL, -- colocar o nome completo do empregado
    sexo varchar(1),
    endereco character varying(45),
    salario numeric(10,2),
    datanasc date,
    dno integer NOT NULL,
    superssn character(9),
    senha character(15) NOT NULL
);


CREATE TABLE auditoria(
    gerssn character(9) NOT NULL,
    essn character(9) NOT NULL,
    asalario numeric(10,2),
    nsalario numeric(10,2),
    datamodificado date
);


--
-- Name: projeto; Type: TABLE; Schema: cia; Owner: -; Tablespace: 
--

CREATE TABLE projeto (
    pnumero integer NOT NULL,
    pjnome character varying(15) NOT NULL,
    plocalizacao character varying(15),
    dnum integer NOT NULL
);


--
-- Name: trabalha_em; Type: TABLE; Schema: cia; Owner: -; Tablespace: 
--

CREATE TABLE trabalha_em (
    essn character(9) NOT NULL,
    pjnumero integer NOT NULL,
    horas numeric(3,1) NOT NULL
);


--
-- Name: pk_departamento; Type: CONSTRAINT; Schema: cia; Owner: -; Tablespace: 
--

ALTER TABLE ONLY departamento
    ADD CONSTRAINT pk_departamento PRIMARY KEY (numero);


--
-- Name: pk_dependentes; Type: CONSTRAINT; Schema: cia; Owner: -; Tablespace: 
--

ALTER TABLE ONLY dependentes
    ADD CONSTRAINT pk_dependentes PRIMARY KEY (nome_dependente, essn);


--
-- Name: pk_dept_localizacao; Type: CONSTRAINT; Schema: cia; Owner: -; Tablespace: 
--

ALTER TABLE ONLY dept_localizacao
    ADD CONSTRAINT pk_dept_localizacao PRIMARY KEY (dlocalizacao, departamento_numero);


--
-- Name: pk_empregado; Type: CONSTRAINT; Schema: cia; Owner: -; Tablespace: 
--

ALTER TABLE ONLY empregado
    ADD CONSTRAINT pk_empregado PRIMARY KEY (ssn);


--
-- Name: pk_projeto; Type: CONSTRAINT; Schema: cia; Owner: -; Tablespace: 
--

ALTER TABLE ONLY projeto
    ADD CONSTRAINT pk_projeto PRIMARY KEY (pnumero);


--
-- Name: pk_trabalha_em; Type: CONSTRAINT; Schema: cia; Owner: -; Tablespace: 
--

ALTER TABLE ONLY trabalha_em
    ADD CONSTRAINT pk_trabalha_em PRIMARY KEY (essn, projeto_pnumero);


--
-- Name: uq_dnome; Type: CONSTRAINT; Schema: cia; Owner: -; Tablespace: 
--

ALTER TABLE ONLY departamento
    ADD CONSTRAINT uq_dnome UNIQUE (nome);


--
-- Name: uq_pnome; Type: CONSTRAINT; Schema: cia; Owner: -; Tablespace: 
--

ALTER TABLE ONLY projeto
    ADD CONSTRAINT uq_pnome UNIQUE (pjnome);


--
-- Name: fk_dependentes_do_empregado; Type: FK CONSTRAINT; Schema: cia; Owner: -
--

ALTER TABLE ONLY dependentes
    ADD CONSTRAINT fk_dependentes_do_empregado FOREIGN KEY (essn) REFERENCES empregado(ssn);


--
-- Name: fk_dept_localizacao_departamento; Type: FK CONSTRAINT; Schema: cia; Owner: -
--

ALTER TABLE ONLY dept_localizacao
    ADD CONSTRAINT fk_dept_localizacao_departamento FOREIGN KEY (departamento_numero) REFERENCES departamento(numero);


--
-- Name: fk_empregado_gerencia_dpto; Type: FK CONSTRAINT; Schema: cia; Owner: -
--

ALTER TABLE ONLY departamento
    ADD CONSTRAINT fk_empregado_gerencia_dpto FOREIGN KEY (gerssn) REFERENCES empregado(ssn);


--
-- Name: fk_empregado_supervisiona_empregado; Type: FK CONSTRAINT; Schema: cia; Owner: -
--

ALTER TABLE ONLY empregado
    ADD CONSTRAINT fk_empregado_supervisiona_empregado FOREIGN KEY (superssn) REFERENCES empregado(ssn);


--
-- Name: fk_empregado_trabalha_departamento; Type: FK CONSTRAINT; Schema: cia; Owner: -
--

ALTER TABLE ONLY empregado
    ADD CONSTRAINT fk_empregado_trabalha_departamento FOREIGN KEY (dno) REFERENCES departamento(numero);


--
-- Name: fk_projeto_controlado_departamento; Type: FK CONSTRAINT; Schema: cia; Owner: -
--

ALTER TABLE ONLY projeto
    ADD CONSTRAINT fk_projeto_controlado_departamento FOREIGN KEY (dnum) REFERENCES departamento(numero);


--
-- Name: fk_trabalha_em_empregado; Type: FK CONSTRAINT; Schema: cia; Owner: -
--

ALTER TABLE ONLY trabalha_em
    ADD CONSTRAINT fk_trabalha_em_empregado FOREIGN KEY (essn) REFERENCES empregado(ssn);


--
-- Name: fk_trabalha_em_projeto; Type: FK CONSTRAINT; Schema: cia; Owner: -
--

ALTER TABLE ONLY trabalha_em
    ADD CONSTRAINT fk_trabalha_em_projeto FOREIGN KEY (projeto_pnumero) REFERENCES projeto(pnumero);


--
-- PostgreSQL database dump complete
--


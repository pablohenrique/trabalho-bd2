/*
 * SCRIPT SQL - SISTEMA DE GESTAO
 *
 * Grupo:
 * Caio Thomas
 * Yuri Campos
 * Pablo Henrique
 */
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
    gerdatainicio date
);

CREATE TABLE dependentes (
    nome_dependente character varying(255) NOT NULL,
    essn character(9) NOT NULL,
    sexo character(1),
    datanasc date,
    parentesco character varying(8)
);


CREATE TABLE dept_localizacao (
    dlocalizacao character varying(15) NOT NULL,
    departamento_numero integer NOT NULL
);


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



--
-- REMOVER RESTRICOES DE INTEGRIDADE
--
/*
ALTER TABLE departamento DROP CONSTRAINT fk_empregado_gerencia_dpto;
ALTER TABLE dependentes DROP CONSTRAINT fk_dependentes_do_empregado;
ALTER TABLE dept_localizacao DROP CONSTRAINT fk_dept_localizacao_departamento;
ALTER TABLE empregado DROP CONSTRAINT fk_empregado_supervisiona_empregado;
ALTER TABLE empregado DROP CONSTRAINT fk_empregado_trabalha_departamento;
ALTER TABLE projeto DROP CONSTRAINT fk_projeto_controlado_departamento;
ALTER TABLE trabalha_em DROP CONSTRAINT fk_trabalha_em_empregado;
ALTER TABLE trabalha_em DROP CONSTRAINT fk_trabalha_em_projeto;

ALTER TABLE departamento DROP CONSTRAINT uq_dnome;
ALTER TABLE projeto DROP CONSTRAINT uq_pnome;

ALTER TABLE projeto DROP CONSTRAINT pk_projeto;
ALTER TABLE departamento DROP CONSTRAINT pk_departamento;
ALTER TABLE dependentes DROP CONSTRAINT pk_dependentes;
ALTER TABLE dept_localizacao DROP CONSTRAINT pk_dept_localizacao;
ALTER TABLE empregado DROP CONSTRAINT pk_empregado CASCADE;
ALTER TABLE trabalha_em DROP CONSTRAINT pk_trabalha_em;
*/

--
-- INSERTS BASICOS
--
    
INSERT INTO departamento VALUES('1', 'PESQUISA', '11013', '2013-02-01');
INSERT INTO departamento VALUES('2', 'TESTES', '11012', '2013-02-01');

INSERT INTO empregado VALUES('11011', 'CAIO THOMAS OLIVEIRA', 'M', 'RUA DA ALEGRIA', 1234.09, '1991-11-21', 1, NULL, 123);
INSERT INTO empregado VALUES('11012', 'RICARDO DA SILVA', 'M', 'RUA DA FELICIADE', 934.09, '1990-02-12', 1, '11011', 123);
INSERT INTO empregado VALUES('11013', 'LUCIANA FERREIRA', 'F', 'RUA DA FELICIADE', 934.09, '1990-04-29', 1, '11011', 123);
INSERT INTO empregado VALUES('11014', 'MARIANA DA SILVA', 'F', 'RUA DA FELICIADE', 934.09, '1990-02-12', 1, '11011', 123);

INSERT INTO projeto VALUES(1, 'XML', 'FACOM', 1);
INSERT INTO projeto VALUES(2, 'MINERACAO', 'FACOM', 1);

INSERT INTO trabalha_em VALUES('11011', 1, 8);
INSERT INTO trabalha_em VALUES('11011', 2, 8);
INSERT INTO trabalha_em VALUES('11012', 1, 8);
INSERT INTO trabalha_em VALUES('11013', 2, 8);
INSERT INTO trabalha_em VALUES('11014', 1, 8);
INSERT INTO trabalha_em VALUES('11014', 2, 8);


--
-- RESTRICOES PRIMARY KEY
--

ALTER TABLE ONLY departamento
    ADD CONSTRAINT pk_departamento PRIMARY KEY (numero);

ALTER TABLE ONLY dependentes
    ADD CONSTRAINT pk_dependentes PRIMARY KEY (nome_dependente, essn);

ALTER TABLE ONLY dept_localizacao
    ADD CONSTRAINT pk_dept_localizacao PRIMARY KEY (dlocalizacao, departamento_numero);

ALTER TABLE ONLY empregado
    ADD CONSTRAINT pk_empregado PRIMARY KEY (ssn);

ALTER TABLE ONLY projeto
    ADD CONSTRAINT pk_projeto PRIMARY KEY (pnumero);

ALTER TABLE ONLY trabalha_em
    ADD CONSTRAINT pk_trabalha_em PRIMARY KEY (essn, pjnumero);

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


--
-- FUNCOES
--

CREATE OR REPLACE VIEW login AS 
SELECT e.ssn, e.superssn, e.senha, d.gerssn, d1.gerssn AS gerente
  FROM empregado e
  LEFT JOIN departamento d ON e.ssn = d.gerssn
  JOIN departamento d1 ON d1.numero = e.dno;


DROP FUNCTION IF EXISTS login(varchar(9),varchar(15));

CREATE OR REPLACE FUNCTION login (argssn VARCHAR(9),argsenha VARCHAR(15))
RETURNS int4 AS $$
DECLARE 
    userInfo record;
BEGIN
    SELECT COUNT(DISTINCT ssn) as qtdusuario, ssn, senha, superssn, gerssn
    INTO userInfo 
    FROM login 
    WHERE ssn = argssn AND senha = argsenha
    GROUP BY ssn,senha,superssn,gerssn;
    
    IF ( userInfo.senha != argsenha OR userInfo.qtdusuario = 0 OR userInfo.qtdusuario IS NULL)
    THEN 
	RETURN -1;
    ELSE
        IF (argssn = userInfo.gerssn AND argssn = userInfo.superssn)
        THEN
	    RETURN 3;
        ELSE
            IF (argssn = userInfo.gerssn)
            THEN
		RETURN 2;
            ELSE
                IF (argssn = userInfo.superssn)
                THEN
		    RETURN 1;
                ELSE
		    RETURN 0;
                END IF;
            END IF;
        END IF;
    END IF;
END;
$$ language 'plpgsql';

SELECT login('11024','senha4');

--
-- FUNCAO SEXO 
--


CREATE OR REPLACE FUNCTION sexo (sexo VARCHAR(1))
RETURNS VARCHAR AS $$
    BEGIN
        IF(lower(sexo) = 'm') THEN            
            RETURN 'Masculino';
        ELSEIF(lower(sexo) = 'f') THEN            
            RETURN 'Feminino';
        ELSE 
		RETURN '';
        END IF;
    END;
$$ language 'plpgsql';

--
--CONSULTAS BASICAS
--

--Recuperar todos empregados com seu dependente e supervisor

SELECT *, cia.sexo(e.sexo) AS sexoEmp, cia.sexo(ger.sexo) AS sexoGer, cia.sexo(s.sexo) AS sexoSuper
    FROM (((cia.empregado AS e LEFT JOIN cia.departamento
		AS d ON e.dno = d.numero) LEFT JOIN cia.empregado AS ger
		ON d.gerssn = ger.ssn) LEFT JOIN cia.empregado AS s 
			ON e.superssn = s.ssn)
    ORDER BY e.nome ASC;





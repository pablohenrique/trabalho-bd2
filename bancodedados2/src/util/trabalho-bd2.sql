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
    gerdatainicio date NOT NULL,
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

--
-- criando sequencias 
--
CREATE SEQUENCE departamento_seq INCREMENT 1; 
CREATE SEQUENCE projeto_seq INCREMENT 1; 

ALTER TABLE departamento ALTER COLUMN numero SET DEFAULT nextval('departamento_seq');
ALTER TABLE projeto ALTER COLUMN pnumero SET DEFAULT nextval('projeto_seq');


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
INSERT INTO empregado VALUES('11015', 'SUELEN GIMENEZ', 'F', 'RUA JOHEN CARNEIRO', 900.00, '1989-02-12', 2, '11011', 123);

INSERT INTO projeto VALUES(1, 'XML', 'FACOM', 1);
INSERT INTO projeto VALUES(2, 'MINERACAO', 'FACOM', 1);

INSERT INTO trabalha_em VALUES('11011', 1, 8);
INSERT INTO trabalha_em VALUES('11011', 2, 8);
INSERT INTO trabalha_em VALUES('11012', 1, 8);
INSERT INTO trabalha_em VALUES('11013', 2, 8);
INSERT INTO trabalha_em VALUES('11014', 1, 8);
INSERT INTO trabalha_em VALUES('11014', 2, 8);

INSERT INTO dept_localizacao VALUES('BLOCO A', 1);
INSERT INTO dept_localizacao VALUES('BLOCO B', 2);
INSERT INTO dept_localizacao VALUES('BLOCO C', 3);
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

ALTER TABLE ONLY dept_localizacao
    ADD CONSTRAINT pk_localizacao PRIMARY KEY (dlocalizacao, departamento_numero);


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
    FROM cia.login 
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

CREATE OR REPLACE FUNCTION sexoToBd (sexo VARCHAR)
RETURNS VARCHAR AS $$
    BEGIN
        IF(lower(sexo) = 'masculino') THEN            
            RETURN 'M';
        ELSEIF(lower(sexo) = 'feminino') THEN            
            RETURN 'F';
        END IF;
    END;
$$ language 'plpgsql';



---
-- TRIGGERS
---

---
-- Confere salario
---

CREATE OR REPLACE FUNCTION trigger_emp_salario()
  RETURNS trigger AS
$BODY$
BEGIN
    IF NEW.salario < 100 THEN
	RAISE EXCEPTION 'Nao aceitamos escravos nesta companhia, usuario %', NEW.superssn;
    ELSEIF(TG_OP = 'UPDATE')
        IF NEW.salario > OLD.salario THEN
            INSERT INTO cia.auditoria VALUES(NEW.superssn,NEW.ssn,OLD.salario,NEW.salario,current_date);
        ELSE
            RAISE EXCEPTION 'Valor de salario nao pode ser persistido. Nao se pode reduzir o salario de um empregado, senhor %', OLD.superssn;
        END IF;
    END IF;
    
    RETURN NEW;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION cia.trigger_emp_salario()
  OWNER TO postgres;

CREATE TRIGGER trigger_emp_salario BEFORE INSERT OR UPDATE ON cia.empregado
FOR EACH ROW EXECUTE PROCEDURE trigger_emp_salario();

--
--
--

CREATE OR REPLACE FUNCTION dependeteConjugue()
RETURNS trigger AS 
$$
DECLARE
dep INTEGER;
BEGIN
    IF(NEW.parentesco = 'conjugue') THEN 
        SELECT COUNT(d.essn) 
        FROM cia.dependentes AS d 
        WHERE d.essn = NEW.essn AND parentesco = 'conjugue' INTO dep;	

        IF (dep = 1) THEN 
            RAISE EXCEPTION 'NO BRASIL VC SOH PODE TER 1 CONJUGUE';
        END IF;
    END IF;	

    RETURN NEW;
END;
$$ language 'plpgsql';


CREATE TRIGGER verificaConjugue
BEFORE INSERT OR UPDATE
ON cia.dependentes FOR EACH ROW
EXECUTE PROCEDURE dependeteConjugue();

--
--
--

CREATE OR REPLACE FUNCTION projetoDep()
RETURNS trigger AS 
$$
DECLARE dep_emp empregado.dno%TYPE;
DECLARE dep_proj projeto.dnum%TYPE;

BEGIN
    SELECT e.dno 
    FROM cia.empregado AS e
    WHERE e.ssn = NEW.essn
    INTO dep_emp;

    SELECT p.dnum
    FROM cia.projeto AS p
    WHERE p.pnumero = NEW.projeto_pnumero
    INTO dep_proj;

    IF(dep_emp <> dep_proj) THEN 
        RAISE EXCEPTION 'O empregado so pode trablhar em projetos de seu departamento.';
    END IF;

RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER verificaDep
BEFORE INSERT OR UPDATE
ON cia.trabalha_em FOR EACH ROW
EXECUTE PROCEDURE projetoDep();


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





SELECT e.ssn AS E_ssn, e.nome AS E_nome, cia.sexo(e.sexo) AS E_sexo, e.endereco AS E_endereco, e.salario AS E_salario, e.datanasc AS E_datanasc, e.dno AS E_dno, 
       e.superssn AS E_superssn, e.senha AS E_senha, s.ssn AS S_ssn, s.nome AS S_nome, cia.sexo(s.sexo) AS S_sexo, s.endereco AS S_endereco, s.salario AS S_salario,
       s.datanasc AS S_datanasc, s.dno AS S_dno, s.superssn AS S_superssn, s.senha AS S_senha, d.numero AS D_numero, d.nome AS D_nome, d.gerssn AS D_gerssn, d.gerdatainicio AS D_gerdatainicio
    FROM (((cia.empregado AS e LEFT JOIN cia.departamento
		AS d ON e.dno = d.numero) LEFT JOIN cia.empregado AS ger
		ON d.gerssn = ger.ssn) LEFT JOIN cia.empregado AS s 
			ON e.superssn = s.ssn)
    ORDER BY e.nome ASC;


--SELECIONAR TODOS PROJETOS E O DEPARTAMENTO DE UM EMPREGADO       
SELECT p.pnumero AS p_numero, p.pjnome AS p_nome, p.plocalizacao AS p_localizacao, d.numero AS d_numero,
       d.nome AS d_nome, d.gerssn AS d_gerssn, d.gerdatainicio AS d_dataInicio
       FROM empregado AS e, projeto AS p, departamento AS d, trabalha_em AS t
       WHERE e.ssn = t.essn AND
	     t.pjnumero = p.pnumero AND
	     p.dnum = d.numero AND
	     e.ssn = '11014';
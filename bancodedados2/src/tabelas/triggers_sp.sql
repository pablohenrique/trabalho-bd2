---
-- Confere salario
---

CREATE OR REPLACE FUNCTION cia.trigger_emp_salario()
RETURNS TRIGGER AS
$BODY$
BEGIN
    IF (TG_OP = 'INSERT') THEN
        IF (NEW.salario <= 600) THEN
            RAISE EXCEPTION 'Nao aceitamos escravos nesta companhia, usuario % . Salario deve ser maior que 600.', NEW.superssn;
        ELSE
            INSERT INTO cia.auditoria VALUES(NEW.superssn,NEW.ssn,NEW.salario,NEW.salario,current_date);
        END IF;
    ELSEIF(TG_OP = 'UPDATE' AND NEW.salario <> OLD.salario) THEN
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

CREATE TRIGGER trigger_emp_salario 
   BEFORE INSERT OR UPDATE ON cia.empregado
   FOR EACH ROW EXECUTE PROCEDURE trigger_emp_salario();

--
--
--

CREATE OR REPLACE FUNCTION cia.dependeteConjugue()
RETURNS trigger AS 
$$
DECLARE
dep INTEGER;
BEGIN
    IF(NEW.parentesco = 'Conjugue') THEN 
        SELECT COUNT(d.essn) 
        FROM cia.dependentes AS d 
        WHERE d.essn = NEW.essn AND parentesco = 'Conjugue' INTO dep;   

        IF (dep = 1) THEN 
            RAISE EXCEPTION 'No Brasil vc soh pode casar com uma pessoa!';
        END IF;
    END IF; 

    RETURN NEW;
END;
$$ language 'plpgsql';


CREATE TRIGGER verificaConjugue
    BEFORE INSERT OR UPDATE ON cia.dependentes 
    FOR EACH ROW EXECUTE PROCEDURE dependeteConjugue();

--
--
--

CREATE OR REPLACE FUNCTION cia.projetoDep()
RETURNS trigger AS 
$$
    DECLARE dep_emp cia.empregado.dno%TYPE;
    DECLARE dep_proj cia.projeto.dnum%TYPE;

    BEGIN
        SELECT e.dno 
        FROM cia.empregado AS e
        WHERE e.ssn = NEW.essn
        INTO dep_emp;

        SELECT p.dnum
        FROM cia.projeto AS p
        WHERE p.pnumero = NEW.pjnumero
        INTO dep_proj;

        IF(dep_emp <> dep_proj) THEN 
            RAISE EXCEPTION 'O empregado so pode trablhar em projetos de seu departamento.';
        END IF;

    RETURN NEW;
    END;
$$ language 'plpgsql';

CREATE TRIGGER verificaDep
    BEFORE INSERT OR UPDATE ON cia.trabalha_em 
    FOR EACH ROW EXECUTE PROCEDURE projetoDep();
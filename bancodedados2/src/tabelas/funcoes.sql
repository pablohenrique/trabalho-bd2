CREATE OR REPLACE FUNCTION cia.login (argssn VARCHAR(9),argsenha VARCHAR(15))
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

--SELECT login('11024','senha4');

--
-- FUNCAO SEXO: responsavel por converter para o banco e o software
--


CREATE OR REPLACE FUNCTION cia.sexo (sexo VARCHAR(1))
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


--
-- Funcao responsavel por contar a tarifa de propagandas de um determinado projeto
--

CREATE OR REPLACE FUNCTION cia.gera_tarifa(projetoID cia.projeto.pnumero%TYPE)
RETURNS FLOAT AS
$gera_tarifa$
    DECLARE acumulador cia.propaganda.tarifa%TYPE;
    DECLARE contador cia.propaganda.tarifa%TYPE;
    DECLARE propaganda RECORD;
    DECLARE dias INTEGER;

    BEGIN
        acumulador := 0;
        FOR propaganda IN SELECT * FROM cia.propaganda as pp WHERE pp.projeto = projetoID
        LOOP
            SELECT (propaganda.dataFinal - propaganda.dataInicio)  INTO dias;
            acumulador := acumulador + (propaganda.tarifa * dias);
        END LOOP;
        RETURN acumulador;
    END;

$gera_tarifa$
LANGUAGE 'plpgsql';
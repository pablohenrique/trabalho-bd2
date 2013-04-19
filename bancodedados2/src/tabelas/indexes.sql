--
-- INDEXES
--

SET SEARCH_PATH TO cia;

CREATE INDEX index_empregado_salario
    ON empregado(salario);

CREATE INDEX index_salario_departamento
    ON empregado(dno);

CREATE INDEX index_departamento_gerente
    ON departamento(gerssn);

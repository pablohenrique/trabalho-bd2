/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

/**
 *
 * @author pablohenrique
 */
public class FactoryDAO {
    
    public static IObjectDAO getFactory(String dao){
        switch(dao){
            case("Departamento"):
                return new DepartamentoDAO();
            case("Dependente"):
                return new DependenteDAO();
            case("Empregado"):
                return new EmpregadoDAO();
            case("NewDao"):
                return new NovoDao();                
            case("Projeto"):
                return new ProjetoDAO();
            case("Trabalha"):
                return new TrabalhaDAO();
            case("Localizacao"):
                return new LocalizacaoDAO();
            default:
                return null;
        }
    }
    
}

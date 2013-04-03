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
    private static String factoryInUse = "";
    private static IObjectDAO instance;
    public static IObjectDAO getFactory(String dao){
        if(!dao.equals(factoryInUse) || factoryInUse.equals(""))
            switch(dao){
                case("Departamento"):
                    factoryInUse = "Departamento";
                    instance = new DepartamentoDAO();
                    break;
                case("Dependente"):
                    factoryInUse = "Dependente";
                    instance = new DependenteDAO();
                    break;
                case("Empregado"):
                    factoryInUse = "Empregado";
                    instance = new EmpregadoDAO();
                    break;
                case("Projeto"):
                    factoryInUse = "Projeto";
                    instance = new ProjetoDAO();
                    break;
                case("Trabalha"):
                    factoryInUse = "Trabalha";
                    instance = new TrabalhaDAO();
                    break;
                case("Localizacao"):
                    factoryInUse = "Localizacao";
                    instance = new LocalizacaoDAO();
                    break;
                default:
                    factoryInUse = "";
                    instance = null;
                    break;
            }
        return instance;
    }
    
}

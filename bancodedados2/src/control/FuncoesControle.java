/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;


import DAO.FactoryDAO;
import DAO.IObjectDAO;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author yuricampos
 */
public class FuncoesControle {
    
    public boolean verificarExistenciaProjeto(int projetonumero)
    {
        try
        {
            FactoryDAO.getFactory("Projeto").get(projetonumero);
            return true;
        }
        catch(Exception e)
        {
            return false;            
        }
    }
    
    public boolean verificarExistenciaLocalizacao(String localizacao){
        try{
           FactoryDAO.getFactory("Localizacao").get(localizacao);
           return true;
        }
        catch(Exception e)
        {
            return false;            
        }
    }
    
        public boolean verificarExistenciaTrabalha(String essn){
        try{
           FactoryDAO.getFactory("Trabalha").get(essn);
           return true;
        }
        catch(Exception e)
        {
            return false;            
        }
    }
    
    public boolean verificarExistenciaDependente(String nome_dependenete){
        try{
            FactoryDAO.getFactory("Dependente").get(nome_dependenete);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
        
    public boolean verificarExistenciaDepartamento(int departamentonumero)
    {
        try
        {
            FactoryDAO.getFactory("Departamento").get(departamentonumero);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }                  

    public boolean verificarExistenciaEmpregado(String ssn)
    {
        try
        {
            FactoryDAO.getFactory("Empregado").get(ssn);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    
    public static String converteData(Date d)
    {  
        return new SimpleDateFormat("dd/MM/yyyy").format(d);
    }      
    
   public static Date coverteStringData(String data)
   {   
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");         
        try  
        {  
            return new java.sql.Date(formatador.parse(data).getTime());    
        }  
        catch(ParseException ex)  
        {   
           System.out.println("Erro conversao de Date!");
           return null;
        }  
    }      
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import DAO.DepartamentoDAO;
import DAO.EmpregadoDAO;
import DAO.ProjetoDAO;
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
        ProjetoDAO pdao = new ProjetoDAO();
        try
        {
            pdao.get(projetonumero);
            return true;
        }
        catch(Exception e)
        {
            return false;            
        }
    }
        
    public boolean verificarExistenciaDepartamento(int departamentonumero)
    {
        DepartamentoDAO ddao = new DepartamentoDAO();
        try
        {
            ddao.get(departamentonumero);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }                  

    public boolean verificarExistenciaEmpregado(String ssn)
    {
        EmpregadoDAO edao = new EmpregadoDAO();
        
        try
        {
            edao.get(ssn);
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
    
    public static String getSexoView(String sexo)
    {
        if(sexo.toLowerCase().equals('m'))
            return "Masculino";
        return "Feminino";
    }
    
    public static String getSexoChar(String sexo)
    {
        if(sexo.toLowerCase().equals("masculino"))
            return "M";
        return "F";
    }     
}

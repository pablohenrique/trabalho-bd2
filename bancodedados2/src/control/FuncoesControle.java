/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import DAO.DepartamentoDAO;
import DAO.EmpregadoDAO;
import DAO.ProjetoDAO;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
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
    
    public static String converteData(java.util.Date dtData)  throws Exception
    {  
       SimpleDateFormat formatBra;     
       formatBra = new SimpleDateFormat("dd/MM/yyyy");  
       
       try
       {  
          java.util.Date newData = formatBra.parse(dtData.toString());  
          return (formatBra.format(newData));  
       }
       catch (ParseException ex)
       {  
           throw ex;  
       }         
    }      
    
   public static Date coverteStringData(String data) throws Exception
   {   
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy"); 
        
        try  
        {  
            return new java.sql.Date(formatador.parse(data).getTime());    
        }  
        catch(ParseException ex)  
        {   
            throw ex;  
        }  
    }      
    
    public static int getSexoView(String sexo)
    {
        if(sexo.toLowerCase().equals('m'))
            return 0;
        return 1;
    }    
    
    public static String getSexoChar(String sexo)
    {
        if(sexo.toLowerCase().equals("masculino"))
            return "M";
        return "F";
    }     
}

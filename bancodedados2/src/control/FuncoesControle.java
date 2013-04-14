/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;



import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author yuricampos
 */
public class FuncoesControle {
    
    
    public static String converteData(Date d)
    {  
        if(d == null)
            return new String("");
        return new SimpleDateFormat("dd/MM/yyyy").format(d);
    }      
    
   public static Date coverteStringData(String data){   
        if(data.equals(""))
            return null;
            
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");         
        try  {  
            return new java.sql.Date(formatador.parse(data).getTime());    
        }  
        catch(ParseException ex)  {   
           System.out.println("Erro conversao de Date!");
           return null;
        }  
    }      
    
}

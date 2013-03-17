/**
 * Mascara
 * --------------
 * 
 * Classe responsavel por inserir mascara em visoes
 * 
 * @author Caio Thomas
 */
package view.formularios;

import javax.swing.text.MaskFormatter;

public class Mascara
{ 
    public MaskFormatter Mascara(String m)
    {           
       MaskFormatter formatter = new MaskFormatter();  
       
       try
       {  
           formatter.setMask(m);
           formatter.setPlaceholderCharacter(' ');
       }  
       catch (Exception e)
       {  
           System.out.println("Erro Mascara: " + e);
       }   
       
       return formatter;  
    }   
}

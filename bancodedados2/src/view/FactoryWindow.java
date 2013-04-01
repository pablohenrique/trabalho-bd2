/**
 * Factory Window
 * -----------------------------
 * 
 * - Classe responsavel por criar uma janela especifica ao usuario
 */
package view;

import javax.swing.JFrame;

public class FactoryWindow
{
    FactoryWindow(){}
    
    /**
     * Metodo que cria uma janela especifica ao usuario
     * @param login JFrame 
     * @param value integer tipo de usuario
     * @return retorna uma janela JFrame ou nulo como usuario nao especificado
     */
    public JFrame execute(JFrame login, int value, String user) throws Exception
    {
        if(value == -1)
            return null;
         
        this.sessao(user, value);                    
        
        if(value == 0)//usuario normal
        {            
            login.dispose();
            return new WindowFuncionario();                                                                        
        }
        else if(value == 1)//supervisor
        {
            login.dispose();
            return new Window();                                                                        
        }
        else if(value == 2)//gerente
        {
            login.dispose();
            return new Window();                                                                        
        }
        else if(value == 3)//supervisor e gerente
        {
            login.dispose();
            return new WindowLoginSelect();                                                                        
        }
        
        return null;
    }
    
    public void sessao(String essn, int value) throws Exception
    {
        Principal.user = Principal.cf.getEmpregadoBySsn(essn);//recupera usuario e cria sessao
        Principal.user.setTipoLogin(value);        
    }

}

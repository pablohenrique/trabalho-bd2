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
    public void execute(JFrame login, int value, String user) throws Exception
    {
        if(value == -1)
            return;
         
        this.sessao(user, value);                    
        
        if(value == 0)//usuario normal
        {            
            login.dispose();
            Principal.janela = new WindowFuncionario();                                                                        
        }
        else if(value == 1 || value == 2 || value == 3)//cria um selected e define qual tela
        {
            login.dispose();            
            new WindowLoginSelect(value);                                                                                
        } 
        System.gc();
    }   
    
    public void sessao(String essn, int value) throws Exception
    {
        Principal.user = Principal.cf.getEmpregadoBySsn(essn);//recupera usuario e cria sessao
        Principal.user.setTipoLogin(value);        
    }

}

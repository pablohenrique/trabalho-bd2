package view;
import Model.Empregado;
import control.ControlFacade;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Principal{
    final static String osName = System.getProperty("os.name").toLowerCase();
    public static JFrame login;
    public static JFrame janela;
    public static Empregado user;
    public static ControlFacade cf = new ControlFacade();

    public static void main(String[] args) throws Exception{
         Principal.initLookAndFeel();
         //String ssn = "11014";
         //if((Object) FactoryDAO.getFactory("Empregado").get(ssn) != null)
         //    System.out.println("foi");
         
         /*
         ProjetoDAO prodao = (ProjetoDAO) FactoryDAO.getFactory("Projeto");
         
         for(Object aux : prodao.getAll()){
             Projeto pro = (Projeto) aux;
             System.out.println(pro.getNome());
         }
         ///*
         for(Object aux : prodao.getAllDep(1)){
             Projeto pro = (Projeto) aux;
             System.out.println(pro.getNome());
         }
         
         for(Object aux : prodao.getAllDep("PESQUISA")){
             Projeto pro = (Projeto) aux;
             System.out.println(pro.getNome());
         }
         
         for(Object aux : prodao.getAllEmp("11011")){
             Projeto pro = (Projeto) aux;
             System.out.println(pro.getNome());
         }
         
         Projeto update = (Projeto) FactoryDAO.getFactory("Projeto").get(1);
         update.setLocalizacao("NOVALOC");
         prodao.update(update);
         
         update = (Projeto) FactoryDAO.getFactory("Projeto").read("MINERACAO");
         System.out.println(update.getLocalizacao());
         */
         ViewObjectPool.set("todosEmpregados", Principal.cf.listarEmpregados());
         ViewObjectPool.set("todosDapartamentos", Principal.cf.listarDepartamentos());         
         login = new WindowLogin();
//         janela = new WindowFuncionario();
    }

    private static void initLookAndFeel(){		 
        String lookAndFeel = null;

        if (osName != null){
            if (osName.equals("Metal"))
                lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
            else if (osName.equals("Motif"))
                lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
            else if (isUnix())
                lookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
            else if (isWindows())
                lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";   
            else if (isMac())
                lookAndFeel = "javax.swing.plaf.mac.MacLookAndFeel";	            
            else
                lookAndFeel = UIManager.getSystemLookAndFeelClassName();	                

            try{
                UIManager.setLookAndFeel(lookAndFeel);
            } 	            
            catch (ClassNotFoundException e){
                System.err.println("Couldn't find class for specified look and feel:"  + lookAndFeel);                
            } 	            
            catch (UnsupportedLookAndFeelException e){
                System.err.println("Couldn't find class for specified look and feel:"  + lookAndFeel);	            
            } 	            
            catch (Exception e){
                System.err.println("Couldn't find class for specified look and feel:"  + lookAndFeel);
            }
        }
    }

    public static boolean isWindows(){		
            return (osName.indexOf("win") >= 0); 
    }

    public static boolean isMac(){ 
            return (osName.indexOf("mac") >= 0); 
    }

    public static boolean isUnix(){
            return (osName.indexOf("nix") >= 0 || osName.indexOf("nux") >= 0 || osName.indexOf("aix") > 0 ); 
    }	 
}

package view;
import DAO.DepartamentoDAO;
import DAO.FactoryDAO;
import DAO.IObjectDAO;
import Model.Departamento;
import Model.Empregado;
import control.ControlFacade;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Principal
{
    final static String osName = System.getProperty("os.name").toLowerCase();
    public static Window_Login login;
    public static JFrame janela;
    public static Empregado user;
    public static ControlFacade cf = new ControlFacade();

    public static void main(String[] args)
    {
         Principal.initLookAndFeel();
         /*
         Departamento p = new Departamento();
         FactoryDAO.getFactory("Departamento");
         ///*
         p.setNome("TI");
         p.setGerenteSsn("11021");
         p.setNumero(1);
         p.setGerenteDataInicio(java.sql.Date.valueOf("2013-03-03"));
         FactoryDAO.getFactory("Departamento").post(p);

         Departamento a = (Departamento) FactoryDAO.getFactory("Departamento").get(1);
         System.out.println(a.getNumero());

         Departamento b = (Departamento) FactoryDAO.getFactory("Departamento").read("TI");
         System.out.println(b.getNome());

         p.setNome("T.I.");
         p.setGerenteSsn("11021bsi");
         p.setNumero(1);
         p.setGerenteDataInicio(java.sql.Date.valueOf("2013-05-03"));

         FactoryDAO.getFactory("Departamento").update(p);

         for(Object aux : FactoryDAO.getFactory("Departamento").getAll()){
             Departamento d = (Departamento) aux;
             System.out.println(d.getNome());
         }
         //*/
         //FactoryDAO.getFactory("Departamento").delete(1);


         //login = new Window_Login();
         janela = new Window();
    }


    private static void initLookAndFeel()
    {		 
        String lookAndFeel = null;

        if (osName != null)
        {
            if (osName.equals("Metal"))
            {
                lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
            }	                     
            else if (osName.equals("Motif"))
            {
                lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
            } 	            
            else if (isUnix())
            { 
                lookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
            }
            else if (isWindows())
            { 
                lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
            } 	 	            
            else if (isMac())
            { 
                lookAndFeel = "javax.swing.plaf.mac.MacLookAndFeel";
            }	            	            
            else
            {
                lookAndFeel = UIManager.getSystemLookAndFeelClassName();	                
            }

            try
            {
                UIManager.setLookAndFeel(lookAndFeel);
            } 	            
            catch (ClassNotFoundException e)
            {
                System.err.println("Couldn't find class for specified look and feel:"  + lookAndFeel);                
            } 	            
            catch (UnsupportedLookAndFeelException e)
            {
                System.err.println("Couldn't find class for specified look and feel:"  + lookAndFeel);	            
            } 	            
            catch (Exception e)
            {
                System.err.println("Couldn't find class for specified look and feel:"  + lookAndFeel);
            }
        }
    }

    public static boolean isWindows()
    {		
            return (osName.indexOf("win") >= 0); 
    }

    public static boolean isMac()
    { 
            return (osName.indexOf("mac") >= 0); 
    }

    public static boolean isUnix()
    {
            return (osName.indexOf("nix") >= 0 || osName.indexOf("nux") >= 0 || osName.indexOf("aix") > 0 ); 
    }	 
}

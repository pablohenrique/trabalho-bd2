package view;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;

public class Principal
{
    final static String osName = System.getProperty("os.name").toLowerCase();
	public static Window_Login login;
	public static Window janela;
	
	public static void main(String[] args)
	{
		
		 Principal.initLookAndFeel();
		
		 login = new Window_Login();
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

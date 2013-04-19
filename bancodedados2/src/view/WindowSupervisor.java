/**
 * Window extens JFrame
 * ---------------------------------------
 * 
 * Esta classe representa a visao principal de todo programa.
 * Implementa o Action Listerner.
 * 
 */
package view;

import Model.Empregado;
import Model.Projeto;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import view.panel.PainelFuncionarios;
import view.panel.PainelInit;
import view.panel.PainelProjetos;

public class WindowSupervisor extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    private static JPanel painelCentral;
    
    public static Empregado usuario = null;
    
    private JMenuItem menuProjetosListar;	
    private JMenuItem menuFuncionariosListar;
    private JMenuItem menuFuncionariosEditar;
    
    private JMenuItem menuExit;
    private JMenuItem menuInit;
    private JMenuItem menuAbout;
    private JMenuItem menuFuncionario = new JMenuItem("Logar Funcionário");
    private JMenuItem menuGerente = new JMenuItem("Logar Gerente");
    
    private JButton btnFuncionarios; 
    private JButton btnProjetos;
    private static CardLayout card;
    
    private JPanel painel_funcionarios = null;
    private JPanel painel_projetos = null;

    public WindowSupervisor(){
            super("Sistema de Gerenciamento - Supervisor");
            //definir todos painel central
            card = new CardLayout();
            painelCentral = new JPanel(card);
            painelCentral.add(new PainelInit(), "inicio");                           
            
            //menu
            JMenu arquivo = new JMenu("Arquivo");		
            
            menuExit = new JMenuItem("Sair");
            menuInit = new JMenuItem("Inicio");
            arquivo.add(menuInit);
            if(Principal.value == 3){
                arquivo.add(new JSeparator());
                arquivo.add(menuGerente);   
                arquivo.add(menuFuncionario);
            } else if (Principal.value == 2){
                arquivo.add(new JSeparator()); 
                arquivo.add(menuFuncionario);
            }                        
            arquivo.add(new JSeparator());
            arquivo.add(menuExit);		
            
            JMenu funcionarios = new JMenu("Funcionários");		
            menuFuncionariosListar = new  JMenuItem("Listar todos Funcionarios");
            menuFuncionariosEditar =  new  JMenuItem("Editar Meu Cadastro");
           
            funcionarios.add(menuFuncionariosListar);
            
            
            JMenu projetos = new JMenu("Projetos");		
            menuProjetosListar = new  JMenuItem("Listar todos Funcionarios");
            projetos.add(menuProjetosListar);

            JMenu ajuda = new JMenu("Ajuda");
            menuAbout = new JMenuItem("Sobre");
            ajuda.add(menuAbout);

            JMenuBar menubar = new JMenuBar();
            menubar.add(arquivo);	
            menubar.add(funcionarios);
            menubar.add(projetos);
            menubar.add(ajuda);

            //actions listerns
            menuFuncionariosListar.addActionListener(this);
            menuFuncionariosEditar.addActionListener(this);
            menuInit.addActionListener(this);
            menuAbout.addActionListener(this);
            menuExit.addActionListener(this);	  
            menuProjetosListar.addActionListener(this);
            menuFuncionario.addActionListener(this);
            menuGerente.addActionListener(this);


            JToolBar barraFerramentas = new JToolBar();
            ArrayList<JButton> botoes = new ArrayList<JButton>();

            btnFuncionarios = new JButton("Funcionários");
            btnFuncionarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/funcionarios.png")));
            botoes.add(btnFuncionarios);
            
            btnProjetos = new JButton("Projetos");
            btnProjetos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/projetos.png")));
            botoes.add(btnProjetos); 
            
            for (JButton botao : botoes){
                    botao.setVerticalTextPosition(JButton.BOTTOM);
                    botao.setHorizontalTextPosition(JButton.CENTER);
                    botao.addActionListener(this);
                    botao.setBorderPainted(false);
                    botao.setFocusable(false);
            }

            barraFerramentas.add(btnFuncionarios);
            barraFerramentas.add(btnProjetos);
            
            barraFerramentas.setFloatable(false);
            barraFerramentas.setOpaque(false);		

            this.add(painelCentral, BorderLayout.CENTER);
            this.add(barraFerramentas, BorderLayout.NORTH);
            this.setJMenuBar(menubar);
            this.pack();
            this.setSize(980, 600);
            this.setLocation((java.awt.Toolkit.getDefaultToolkit()
                                            .getScreenSize().width / 2)
                                            - (this.getWidth() / 2), (java.awt.Toolkit
                                            .getDefaultToolkit().getScreenSize().height / 2)
                                            - (this.getHeight() / 2) - 25);
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.setVisible(true);		
    }

    @Override
    public void actionPerformed(ActionEvent e){
            Object origem = e.getSource();
            int nivel = Principal.user.getTipoLogin();
            String ssn = Principal.user.getSsn();
            Empregado em = null;
            
            if(origem == menuAbout){
                    JOptionPane.showMessageDialog(this,"Grupo:\nCaio Thomás\nPablo Henrique\nYuri Campos","Sobre", JOptionPane.PLAIN_MESSAGE);
            }		
            else if(origem == menuExit){
                    System.exit(0);
            }
            else if (origem == menuInit){
                    WindowSupervisor.card.show(WindowSupervisor.painelCentral, "inicio");
            }		
            else if (origem == btnFuncionarios || origem == menuFuncionariosListar){
                painelCentral.add(painelFuncionarios(), "funcionario");                     
                WindowSupervisor.card.show(WindowSupervisor.painelCentral, "funcionario");
            }
            else if( origem == btnProjetos || origem == menuProjetosListar){
                painelCentral.add(painelProjetos(), "projeto"); 
                WindowSupervisor.card.show(WindowSupervisor.painelCentral, "projeto");
            } else if(menuFuncionario == origem){
                Principal.user.setTipoLogin(0);                
                Principal.janela = new WindowFuncionario();            
                this.dispose();                            
            } else if (menuGerente == origem) {
                Principal.user.setTipoLogin(2);
                Principal.janela = new WindowGerente();              
                this.dispose();                            
            }                   
    }   
    
    public JPanel painelFuncionarios(){
        if(painel_funcionarios == null)
            painel_funcionarios = new PainelFuncionarios();
        
        PainelFuncionarios.setDataTable();        
        return painel_funcionarios;
    } 
    
    public JPanel painelProjetos(){
        if(painel_projetos == null){  
            try {                
                if(Principal.user.getTipoLogin() != 0)//supervisor
                    ViewObjectPool.set("todosProj", (Vector<Projeto>) Principal.cf.listarProjetos());
                else if(Principal.user.getTipoLogin() == 0)//funcionario
                    ViewObjectPool.set("todosProj", (Vector<Projeto>) Principal.cf.listarProjetosByEmp(Principal.user.getSsn()));
                
                painel_projetos = new PainelProjetos();    
            } catch (Exception ex) {
                 JOptionPane.showMessageDialog(null,"Erro Listar Projetos: " + ex, "Atenção", JOptionPane.ERROR_MESSAGE);                                                                                    }
        }
        PainelProjetos.setDataTable();
        return painel_projetos;
    }     
        
}

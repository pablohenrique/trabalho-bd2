package view;

import Model.Empregado;
import view.panel.*;
import view.formularios.FormFuncionario;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import view.panel.PainelInit;

public class WindowFuncionario extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    private static JPanel painelCentral;

    private static JMenuItem menuEditarFuncionario;
    private static JMenuItem menuProjetosListar;   
    private static JMenuItem menuDependentesListar;
    
    private static JMenuItem menuExit;
    private static JMenuItem menuInit;
    private static JMenuItem menuAbout;

    private static JButton btnFuncionarios; 
    private static JButton btnDependentes;
    private static JButton btnDepartamentos;
    private static JButton btnProjetos;
    private static CardLayout card;

    public WindowFuncionario(){
            super("Sistema de Gerenciamento - Funcionario");
            
            //definir todos painel central
            card = new CardLayout();
            painelCentral = new JPanel(card);
            painelCentral.add(new PainelInit(), "inicio");           
            painelCentral.add(new PainelProjetos(), "projeto");            
            painelCentral.add(new PainelDependentes(), "dependente");
            
            //menu
            JMenu arquivo = new JMenu("Arquivo");		
            
            menuExit = new JMenuItem("Sair");
            menuInit = new JMenuItem("Inicio");
            arquivo.add(menuInit);
            arquivo.add(new JSeparator());
            arquivo.add(menuExit);		
            
            JMenu funcionarios = new JMenu("Funcionario");		
            menuEditarFuncionario = new JMenuItem("Editar dados");            
            menuDependentesListar = new  JMenuItem("Listar todos Dependentes");
            
            funcionarios.add(menuEditarFuncionario);
            funcionarios.add(new JSeparator());
            funcionarios.add(menuDependentesListar);
            
            JMenu projetos = new JMenu("Projetos");
            
            menuProjetosListar = new JMenuItem("Informacoes de Projetos");
            
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
            menuEditarFuncionario.addActionListener(this);
            menuDependentesListar.addActionListener(this);
            menuInit.addActionListener(this);
            menuAbout.addActionListener(this);
            menuExit.addActionListener(this);	            


            JToolBar barraFerramentas = new JToolBar();
            ArrayList<JButton> botoes = new ArrayList<JButton>();

            btnFuncionarios = new JButton("Editar Cadastro");
            btnFuncionarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/funcionarios.png")));
            botoes.add(btnFuncionarios);
            
            btnDependentes = new JButton("Meus Dependentes");
            btnDependentes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/dependentes.png")));
            botoes.add(btnDependentes);	                

            btnProjetos = new JButton("Informacoes Projetos");
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
            barraFerramentas.add(btnDependentes);
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
                    WindowFuncionario.card.show(WindowFuncionario.painelCentral, "inicio");
            }		
            else if (origem == menuDependentesListar || origem == btnDependentes){
                    WindowFuncionario.card.show(WindowFuncionario.painelCentral, "dependente");
            }
            else if( origem == btnProjetos || origem == menuProjetosListar){
                    WindowFuncionario.card.show(WindowFuncionario.painelCentral, "projeto");
            }            
            else if (origem == menuEditarFuncionario || origem == btnFuncionarios){                
                new FormFuncionario(Principal.user);
                
                try {
                    Principal.user = Principal.cf.getEmpregadoBySsn(ssn);
                    Principal.user.setTipoLogin(0);       
                } catch (Exception ex) {
                    System.err.println("Erro: " + ex);
                }                                 
            }		
    }
}
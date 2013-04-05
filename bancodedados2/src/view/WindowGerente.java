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
import view.panel.PainelProjetos;
import view.panel.PainelFuncionarios;
import view.panel.PainelDependentes;
import view.panel.PainelDepartamento;
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
import view.formularios.FormDepartamento;
import view.formularios.FormDependente;
import view.formularios.FormProjetos;

public class WindowGerente extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    private static JPanel painelCentral;
    
    public static Empregado usuario = null;
    
    private static JMenuItem menuFuncionarioCadastro;
    private static JMenuItem menuDepartamentoCadastro;
    private static JMenuItem menuDepartamentoListar;
    private static JMenuItem menuDependentesCadastro;   
    private static JMenuItem menuProjetosCadastro;	
    private static JMenuItem menuProjetosListar;	
    private static JMenuItem menuFuncionariosListar;
    private static JMenuItem menuDependentesListar;
    
    private static JMenuItem menuExit;
    private static JMenuItem menuInit;
    private static JMenuItem menuAbout;

    private static JButton btnFunc; 
    private static JButton btnDep;
    private static JButton btnDepartamentos;
    private static JButton btnProjetos;
    private static JButton btnPropagandas;
    private static JButton btnFinancas;
    private static JButton btnCargaHoraria;
    private static JButton btnCalculadora;
    private static CardLayout card;

    public WindowGerente(){
            super("Sistema de Gerenciamento");
            //definir todos painel central
            card = new CardLayout();
            painelCentral = new JPanel(card);
            //painelCentral.add(new PainelInit(), "inicio");           
            painelCentral.add(new PainelProjetos(), "projeto");            
            painelCentral.add(new PainelDepartamento(), "departamento");
            painelCentral.add(new PainelDependentes(), "dependente");
            painelCentral.add(new PainelFuncionarios(), "funcionario");                        

            //menu
            JMenu arquivo = new JMenu("Arquivo");		
            
            menuExit = new JMenuItem("Sair");
            menuInit = new JMenuItem("Inicio");
            arquivo.add(menuInit);
            arquivo.add(new JSeparator());
            arquivo.add(menuExit);		
            
            JMenu funcionarios = new JMenu("Funcionarios");		
            menuFuncionarioCadastro = new JMenuItem("Cadastro de Funcionarios");            
            menuDependentesCadastro = new JMenuItem("Cadastro de Dependentes");
            menuFuncionariosListar = new JMenuItem("Listar todos Funcionarios");
            menuDependentesListar =  new JMenuItem("Listar todos Dependentes");
            
            funcionarios.add(menuFuncionarioCadastro);
            funcionarios.add(menuDependentesCadastro);
            funcionarios.add(new JSeparator());
            funcionarios.add(menuFuncionariosListar);
            funcionarios.add(menuDependentesListar);
            
            JMenu dep = new JMenu("Departamentos");		
            menuDepartamentoCadastro = new JMenuItem("Cadastro de Departamentos");
            menuDepartamentoListar = new JMenuItem("Listar Departamentos");
            
            dep.add(menuDepartamentoCadastro);
            dep.add(menuDepartamentoListar);
            
            JMenu projetos = new JMenu("Projetos");		
            menuProjetosCadastro = new JMenuItem("Cadastro de Projetos");           
            menuProjetosListar = new JMenuItem("Listar Projetos");           
            
            projetos.add(menuProjetosCadastro);
            projetos.add(menuProjetosListar);
            
            JMenu ajuda = new JMenu("Ajuda");
            menuAbout = new JMenuItem("Sobre");
            ajuda.add(menuAbout);

            JMenuBar menubar = new JMenuBar();
            menubar.add(arquivo);	
            menubar.add(funcionarios);	
            menubar.add(dep);	
            menubar.add(projetos);	
            menubar.add(ajuda);

            //actions listerns
            menuFuncionarioCadastro.addActionListener(this);
            menuFuncionariosListar.addActionListener(this);
            menuDepartamentoCadastro.addActionListener(this);
            menuDepartamentoListar.addActionListener(this);
            menuDependentesCadastro.addActionListener(this);
            menuDependentesListar.addActionListener(this);
            menuProjetosCadastro.addActionListener(this);
            menuProjetosListar.addActionListener(this);
            menuInit.addActionListener(this);
            menuAbout.addActionListener(this);
            menuExit.addActionListener(this);	            


            JToolBar barraFerramentas = new JToolBar();
            ArrayList<JButton> botoes = new ArrayList<JButton>();

            btnFunc = new JButton("Funcionarios");
            btnFunc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/funcionarios.png")));
            botoes.add(btnFunc);
            
            btnDep = new JButton("Dependentes");
            btnDep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/dependentes.png")));
            botoes.add(btnDep);	
            
            btnDepartamentos = new JButton("Departamentos");
            btnDepartamentos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/departamento.png")));
            botoes.add(btnDepartamentos);            

            btnProjetos = new JButton("Projetos");
            btnProjetos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/projetos.png")));
            botoes.add(btnProjetos); 
            
            btnFinancas = new JButton("Financas");
            btnFinancas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/finanacas.png")));
            botoes.add(btnFinancas);             

            btnPropagandas = new JButton("Propagandas");
            btnPropagandas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/propagandas.png")));
            botoes.add(btnPropagandas);   

            btnCalculadora = new JButton("Calculos Salarios");
            btnCalculadora.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/max-min.png")));
            botoes.add(btnCalculadora);
            
            
            btnCargaHoraria = new JButton("Carga Horaria");
            btnCargaHoraria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/carga-horaria.png")));
            botoes.add(btnCargaHoraria);
            
            for (JButton botao : botoes){
                    botao.setVerticalTextPosition(JButton.BOTTOM);
                    botao.setHorizontalTextPosition(JButton.CENTER);
                    botao.addActionListener(this);
                    botao.setBorderPainted(false);
                    botao.setFocusable(false);
            }

            barraFerramentas.add(btnFunc);
            barraFerramentas.add(btnDep);
            barraFerramentas.add(btnDepartamentos);
            barraFerramentas.add(btnProjetos);
            barraFerramentas.add(btnFinancas);
            barraFerramentas.add(btnPropagandas);
            barraFerramentas.add(btnCalculadora);
            barraFerramentas.add(btnCargaHoraria);
            
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

            if(origem == menuAbout){
                    JOptionPane.showMessageDialog(this,"Grupo:\nCaio Thomás\nPablo Henrique\nYuri Campos","Sobre", JOptionPane.PLAIN_MESSAGE);
            }		
            else if(origem == menuExit){
                    System.exit(0);
            }
            else if (origem == menuInit){
                    WindowGerente.card.show(WindowGerente.painelCentral, "inicio");
            }		
            else if (origem == btnFunc || origem == menuFuncionariosListar){
                    WindowGerente.card.show(WindowGerente.painelCentral, "funcionario");
            }
            else if (origem == btnDep || origem == menuDependentesListar){
                    WindowGerente.card.show(WindowGerente.painelCentral, "dependente");
            }  
            else if( origem == btnDepartamentos || origem == menuDepartamentoListar){
                    WindowGerente.card.show(WindowGerente.painelCentral, "departamento");
            }
            else if( origem == btnProjetos || origem == menuProjetosListar){
                    WindowGerente.card.show(WindowGerente.painelCentral, "projeto");
            }            
            else if (origem == menuFuncionarioCadastro){
                    new FormFuncionario(null);
            }		
            else if (origem == menuProjetosCadastro){
                    new FormProjetos(null);
            }            
            else if (origem == menuDepartamentoCadastro){
                    new FormDepartamento(null);
            }            
            else if (origem == menuDependentesCadastro){
                    new FormDependente(null);
            }            
    }
}

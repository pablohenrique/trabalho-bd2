/**
 * Window extens JFrame
 * ---------------------------------------
 * 
 * Esta classe representa a visao principal de todo programa.
 * Implementa o Action Listerner.
 * 
 */
package view;

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

public class Window extends JFrame implements ActionListener 
{
    private static final long serialVersionUID = 1L;

    private static JPanel painelCentral;

    private static JMenuItem menuCadastroFuncionarios;
    private static JMenuItem menuCadastroDepartamento;
    private static JMenuItem menuCadastroDependentes;   
    private static JMenuItem menuCadastroProjetos;	
    private static JMenuItem menuFuncionariosListar;
    
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

    public Window()
    {
            super("Sistema de Gerenciamento");
            //definir todos painel central
            card = new CardLayout();
            painelCentral = new JPanel(card);
            painelCentral.add(new PainelInit(), "inicio");
            painelCentral.add(new PainelFuncionarios(), "funcionarios");
            //painelCentral.add(new PainelDependentes(), "dependente");

            //menu
            JMenu arquivo = new JMenu("Arquivo");		
            
            menuExit = new JMenuItem("Sair");
            menuInit = new JMenuItem("Inicio");
            arquivo.add(menuInit);
            arquivo.add(new JSeparator());
            arquivo.add(menuExit);		
            
            JMenu funcionarios = new JMenu("Funcionarios");		
            menuCadastroFuncionarios = new JMenuItem("Cadastro de Funcionarios");            
            menuCadastroDependentes = new JMenuItem("Cadastro de Dependentes");
            menuFuncionariosListar = new  JMenuItem("Listar todos Funcionarios");
            
            funcionarios.add(menuCadastroFuncionarios);
            funcionarios.add(menuCadastroDependentes);
            funcionarios.add(new JSeparator());
            funcionarios.add(menuFuncionariosListar);
            
            //menuCadastroDepartamento = new JMenuItem("Cadastro de Departamentos");
            //menuCadastroProjetos = new JMenuItem("Cadastro de Projetos");           


            JMenu ajuda = new JMenu("Ajuda");
            menuAbout = new JMenuItem("Sobre");
            ajuda.add(menuAbout);

            JMenuBar menubar = new JMenuBar();
            menubar.add(arquivo);	
            menubar.add(funcionarios);	
            menubar.add(ajuda);

            //actions listerns
            menuCadastroFuncionarios.addActionListener(this);
            menuFuncionariosListar.addActionListener(this);
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
            
            for (JButton botao : botoes)
            {
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
    public void actionPerformed(ActionEvent e)
    {
            Object origem = e.getSource();

            if(origem == menuAbout)
            {
                    JOptionPane.showMessageDialog(this,"Grupo:\nCaio Thomás\nPablo Henrique\nYuri Campos","Sobre", JOptionPane.PLAIN_MESSAGE);
            }		
            else if(origem == menuExit)
            {
                    System.exit(0);
            }
            else if (origem == menuInit)
            {
                    Window.card.show(Window.painelCentral, "inicio");
            }		
            else if (origem == btnFunc || origem == menuFuncionariosListar)
            {
                    Window.card.show(Window.painelCentral, "funcionarios");
            }
            else if (origem == btnDep)
            {
                    Window.card.show(Window.painelCentral, "dependente");
            }            
            else if (origem == menuCadastroFuncionarios)
            {
                    new FormFuncionario(null, false);
            }		
    }
}

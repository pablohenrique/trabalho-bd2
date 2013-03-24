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
    private static JMenuItem menuExit;
    private static JMenuItem menuInit;
    private static JMenuItem menuAbout;

    private static JButton btnFunc; 
    private static JButton btnDep;
    private static CardLayout card;

    public Window()
    {
            super("Sistema de Gerenciamento");
            //definir todos painel central
            card = new CardLayout();
            painelCentral = new JPanel(card);
            painelCentral.add(new PainelInit(), "inicio");
            painelCentral.add(new PainelFuncionarios(), "funcionarios");

            //menu
            JMenu arquivo = new JMenu("Arquivo");		
            menuCadastroFuncionarios = new JMenuItem("Cadastro de Funcionarios");
            menuCadastroDepartamento = new JMenuItem("Cadastro de Departamentos");
            menuCadastroDependentes = new JMenuItem("Cadastro de Dependentes");
            menuCadastroProjetos = new JMenuItem("Cadastro de Projetos");

            menuExit = new JMenuItem("Sair");
            menuInit = new JMenuItem("Inicio");
            arquivo.add(menuInit);
            arquivo.add(new JSeparator());
            arquivo.add(menuCadastroFuncionarios);
            arquivo.add(menuCadastroDepartamento);
            arquivo.add(menuCadastroDependentes);
            arquivo.add(menuCadastroProjetos);
            arquivo.add(new JSeparator());
            arquivo.add(menuExit);		


            JMenu ajuda = new JMenu("Ajuda");
            menuAbout = new JMenuItem("Sobre");
            ajuda.add(menuAbout);

            JMenuBar menubar = new JMenuBar();
            menubar.add(arquivo);	
            menubar.add(ajuda);

            //actions listerns
            menuCadastroFuncionarios.addActionListener(this);
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
            else if (origem == btnFunc)
            {
                    Window.card.show(Window.painelCentral, "funcionarios");
            }
            else if (origem == menuCadastroFuncionarios)
            {
                    new FormFuncionario(null, false);
            }		
    }
}

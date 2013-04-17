/**
 * Window extens JFrame
 * ---------------------------------------
 * 
 * Esta classe representa a visao principal de todo programa.
 * Implementa o Action Listerner.
 * 
 */
package view;

import Model.Dependente;
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
import view.formularios.FormHoras;
import view.formularios.FormProjetosDepartamento;
import view.formularios.FormSalario;
import view.panel.PainelDepartamento;
import view.panel.PainelDependentes;
import view.panel.PainelFuncionarios;
import view.panel.PainelInit;
import view.panel.PainelProjetos;

public class WindowGerente extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    private static JPanel painelCentral;
    
    public static Empregado usuario = null;
    
    private JMenuItem menuFuncionarioCadastro;
    private JMenuItem menuDepartamentoCadastro;
    private JMenuItem menuDepartamentoListar;
    private JMenuItem menuDependentesCadastro;   
    private JMenuItem menuProjetosCadastro;	
    private JMenuItem menuProjetosListar;	
    private JMenuItem menuFuncionariosListar;
    private JMenuItem menuDependentesListar;
    
    private JMenuItem menuExit;
    private JMenuItem menuAbout;
    private JMenuItem menuInit;
    private JMenuItem menuSupervisor = new JMenuItem("Logar Supervisor");
    private JMenuItem menuFuncionario = new JMenuItem("Logar Funcionario");

    private JButton btnFunc; 
    private JButton btnDep;
    private JButton btnDepartamentos;
    private JButton btnProjetos;
    private JButton btnCargaHoraria;
    private JButton btnCalculadora;
    private JButton btnProjetosDepatamento;
    
    private static CardLayout card;    
    private static JPanel painel_projetos = null;
    private static JPanel painel_dependentes = null;
    private static JPanel painel_departamento = null;
    private static JPanel painel_funcionarios = null;
    private static FormSalario formSalario = null;
    private static FormProjetosDepartamento formDepProjetos = null;
    private static FormHoras formHoras = null;
    
    public WindowGerente(){
            super("Sistema de Gerenciamento");
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
                arquivo.add(menuSupervisor);   
                arquivo.add(menuFuncionario);
            } else if (Principal.value == 2){
                arquivo.add(new JSeparator()); 
                arquivo.add(menuFuncionario);
            }            
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
            //menuProjetosCadastro = new JMenuItem("Cadastro de Projetos");           
            menuProjetosListar = new JMenuItem("Listar Projetos");           
            
            //projetos.add(menuProjetosCadastro);
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
            menuProjetosListar.addActionListener(this);
            menuInit.addActionListener(this);
            menuAbout.addActionListener(this);
            menuExit.addActionListener(this);
            menuSupervisor.addActionListener(this);
            menuFuncionario.addActionListener(this);


            JToolBar barraFerramentas = new JToolBar();
            ArrayList<JButton> botoes = new ArrayList<JButton>();

            btnFunc = new JButton("Funcionários");
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
            
            btnCalculadora = new JButton("Checar Salários");
            btnCalculadora.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/max-min.png")));
            botoes.add(btnCalculadora);
            

            btnCargaHoraria = new JButton("Carga Horária");
            btnCargaHoraria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/carga-horaria.png")));
            botoes.add(btnCargaHoraria);
            
            btnProjetosDepatamento = new JButton("Trabalha Projetos");
            btnProjetosDepatamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/projetosDep.png")));
            botoes.add(btnProjetosDepatamento);
            
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
            barraFerramentas.add(btnCalculadora);
            barraFerramentas.add(btnCargaHoraria);
            barraFerramentas.add(btnProjetosDepatamento);
            
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

        if(origem == menuAbout)
            JOptionPane.showMessageDialog(this,"Grupo:\nCaio Thomás\nPablo Henrique\nYuri Campos","Sobre", JOptionPane.PLAIN_MESSAGE);        		
        else if(origem == menuExit)
            System.exit(0);
        else if (origem == menuInit)
            WindowGerente.card.show(WindowGerente.painelCentral, "inicio");		
        else if (origem == btnFunc || origem == menuFuncionariosListar){
            painelCentral.add(painelFuncionarios(), "funcionario");                                                                            
            WindowGerente.card.show(WindowGerente.painelCentral, "funcionario");
        } else if (origem == btnDep || origem == menuDependentesListar){
            painelCentral.add(painelDependentes(), "dependente");                                 
            WindowGerente.card.show(WindowGerente.painelCentral, "dependente");
        } else if( origem == btnDepartamentos || origem == menuDepartamentoListar){
            painelCentral.add(painelDepartamento(), "departamento");                
            WindowGerente.card.show(WindowGerente.painelCentral, "departamento");
        } else if( origem == btnProjetos || origem == menuProjetosListar){
            painelCentral.add(painelProjetos(), "projeto");            
            WindowGerente.card.show(WindowGerente.painelCentral, "projeto");
        } else if (origem == menuFuncionarioCadastro)
            PainelFuncionarios.form_funcionarios(null);
        else if (origem == menuProjetosCadastro)
            PainelProjetos.formProjeto(null);
        else if (origem == menuDepartamentoCadastro)
            PainelDepartamento.formDepartamentos(null);
        else if (origem == menuDependentesCadastro)
            PainelDependentes.form_dependente(null);
        else if (origem == btnCalculadora)
            WindowGerente.formSalario().setVisible(true);    
        else if (origem == btnProjetosDepatamento)
            WindowGerente.formProjetosDepartamentos();   
        else if(origem == btnCargaHoraria)
            WindowGerente.formHoras();
        else if(origem == menuFuncionario){
            Principal.user.setTipoLogin(0);                
            Principal.janela = new WindowFuncionario();       
            this.dispose();            
        }
        else if(origem == menuSupervisor){
            Principal.user.setTipoLogin(1);
            Principal.janela = new WindowSupervisor();              
            this.dispose();            
        }            
    }
    
    public static JPanel painelProjetos(){
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
    
    public static JPanel painelFuncionarios(){
        if(painel_funcionarios == null)
            painel_funcionarios = new PainelFuncionarios();
        
        PainelFuncionarios.setDataTable();        
        return painel_funcionarios;
    } 
    
    public static JPanel painelDepartamento(){
        if(painel_departamento == null)
            painel_departamento = new PainelDepartamento();              
        
        PainelDepartamento.setDataTable();        
        return painel_departamento;
    }          
    
    public static JPanel painelDependentes(){
        if(painel_dependentes == null){           
            try {                
                if(Principal.user.getTipoLogin() == 0 || Principal.user.getTipoLogin() == 1)
                   ViewObjectPool.set("todosDependentes", (Vector<Dependente>) Principal.cf.DependenteBuscaByEssn(Principal.user.getSsn()));
                else if (Principal.user.getTipoLogin() == 2)            
                   ViewObjectPool.set("todosDependentes", (Vector<Dependente>)Principal.cf.listarDependentes());
                
                painel_dependentes = new PainelDependentes();                            
            } catch (Exception ex) {
                 JOptionPane.showMessageDialog(null,"Erro Listar Dependentes: " + ex, "Atenção", JOptionPane.ERROR_MESSAGE);                                                                        
            }
        }
        PainelDependentes.setDataTable();
        return painel_dependentes;
    }  
    
    public static JPanel getPainelDependentes(){
        return painel_dependentes;
    }
    
    public static FormSalario formSalario(){
        if(formSalario == null)
            formSalario = new FormSalario();
        else
            formSalario.execute();
        
        return formSalario;
    }     
     
    
    public static FormHoras formHoras(){
        if(formHoras == null)
            formHoras = new FormHoras();
        else
            formHoras.execute();
        return formHoras;
    }  
    
    public static FormProjetosDepartamento formProjetosDepartamentos(){
        if(formDepProjetos == null)
            formDepProjetos = new FormProjetosDepartamento();
        else 
            formDepProjetos.execute();

        return formDepProjetos;
    }       
}

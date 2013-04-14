package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public final class WindowLoginSelect extends JFrame implements ActionListener
{
    private static final long serialVersionUID = 1L;
    private JComboBox select;
    private JButton btnSair;
    private JButton btnOK;
    private int value = -1;

    public WindowLoginSelect(int nivel)
    {		
        super("Bem-vindo ao Sistema de Gerenciamento");

        btnOK = new JButton("Iniciar sessão");
        btnOK.addActionListener(this);

        btnSair = new JButton("Sair");
        btnSair.addActionListener(this);
        
        select = new JComboBox<String>();
        
        this.nivel(nivel);
        
        select.addActionListener(this);
        
        select.setPreferredSize(new Dimension(50, 25));
        select.setMaximumSize(new Dimension(50, 25));
        
        JPanel login = new JPanel();
        login.setLayout(new GridLayout(2,1, 5,5));
        login.add(new JLabel("Selecione o seu tipo de login:"));
        login.add(select);                
        
        login.setMaximumSize(new Dimension(300,50));
        login.setPreferredSize(new Dimension(300,50));

        JPanel painel1 = new JPanel();
        painel1.setLayout(new BoxLayout(painel1, BoxLayout.Y_AXIS));
        painel1.add(Box.createVerticalGlue());
        painel1.add(login);
        painel1.add(Box.createVerticalGlue());

        JPanel painel2 = new JPanel();		
        JPanel botoes = new JPanel();
        painel2.setLayout(new BoxLayout(painel2, BoxLayout.Y_AXIS));
        painel2.add(new JSeparator());
        botoes.add(btnOK);
        botoes.add(btnSair);
        painel2.add(botoes);

        this.add(painel1, BorderLayout.CENTER);
        this.add(painel2, BorderLayout.SOUTH);
        this.setSize(400,250);
        this.setLocation((java.awt.Toolkit.getDefaultToolkit()
                          .getScreenSize().width / 2)
                          - (this.getWidth() / 2), (java.awt.Toolkit
                          .getDefaultToolkit().getScreenSize().height / 2)
                          - (this.getHeight() / 2));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

    public void nivel(int nivel)
    {  
        //nivel 1 eh supervisor e funcionario
        if(nivel == 1){
            select.addItem("Funcionario");        
            select.addItem("Supervisor");             
        } else if (nivel == 2){ // gerente e funcionario
            select.addItem("Funcionario");        
            select.addItem("Gerente");            
        } else if (nivel == 3) {//supervisor, gerente e funcionario
            select.addItem("Supervisor");                         
            select.addItem("Funcionario");                    
            select.addItem("Gerente");                        
        }            
    }
        
    public int getValue(){
        return value;
    }
    
    @Override
    public void actionPerformed(ActionEvent e){	
        
        if(e.getSource() == btnOK){
            this.dispose();
            
            if(select.getSelectedItem().equals("Funcionario")){
                Principal.user.setTipoLogin(0);                
                Principal.janela = new WindowFuncionario();                
            }else if(select.getSelectedItem().equals("Supervisor")){
                Principal.user.setTipoLogin(1);
                Principal.janela = new WindowSupervisor();                
            }else if(select.getSelectedItem().equals("Gerente")){
                Principal.user.setTipoLogin(2);                
                Principal.janela = new WindowGerente();         
            }else {
                Principal.janela = null;               
                System.exit(0);
            }                       
        }

        if(e.getSource() == btnSair)
            System.exit(0);
    }   
}

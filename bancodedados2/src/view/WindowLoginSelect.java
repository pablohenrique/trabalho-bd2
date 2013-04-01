package view;

import control.ControlFacade;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;

public class WindowLoginSelect extends JFrame implements ActionListener
{
    private static final long serialVersionUID = 1L;
    private JComboBox select;
    private JButton btnSair;
    private JButton btnOK;
    private int value = -1;

    public WindowLoginSelect()
    {		
        super("Bem-vindo ao Sistema de Gerenciamento");

        btnOK = new JButton("Iniciar sessão");
        btnOK.addActionListener(this);

        btnSair = new JButton("Sair");
        btnSair.addActionListener(this);
        
        select = new JComboBox<String>();
        select.addItem("Gerente");
        select.addItem("Supervisor");   
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

    @Override
    public void actionPerformed(ActionEvent e){	
        
        if(e.getSource() == btnOK){

        }

        if(e.getSource() == btnSair)
                System.exit(0);
    }
    

}

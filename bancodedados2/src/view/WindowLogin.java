package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;

public class WindowLogin extends JFrame implements ActionListener
{
    private static final long serialVersionUID = 1L;
    private JTextField usuario;
    private JPasswordField senha;
    private JButton btnOK;
    private JButton btnSair;    
    private int value = -1;

    public WindowLogin()
    {		
        super("Bem-vindo ao Sistema de Gerenciamento");

        usuario = new JTextField();
        usuario.setText("11024");
        senha = new JPasswordField();
        senha.setText("a");
        senha.requestFocus();
        btnOK = new JButton("Iniciar sessão");
        btnSair = new JButton("Sair");

        btnSair.addActionListener(this);
        btnOK.addActionListener(this);

        JPanel gridLogin = new JPanel();
        gridLogin.setLayout(new GridLayout(2,2, 10, 10));
        gridLogin.add(new JLabel("Identificação:"));
        gridLogin.add(usuario);
        gridLogin.add(new JLabel("Senha:"));
        gridLogin.add(senha);
        gridLogin.setMaximumSize(new Dimension(300,70));
        gridLogin.setPreferredSize(new Dimension(300,50));

        JPanel painel1 = new JPanel();
        painel1.setLayout(new BoxLayout(painel1, BoxLayout.Y_AXIS));
        painel1.add(Box.createVerticalGlue());
        painel1.add(gridLogin);
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
            String user = usuario.getText();

            if(user.equals(""))
                    JOptionPane.showMessageDialog(this,"Por favor, digite um nome de usuário.", "Atenção", JOptionPane.ERROR_MESSAGE);
            else{
                if(new String (senha.getPassword()).equals("")){
                    JOptionPane.showMessageDialog(this,"Por favor, digite uma senha.", "Atenção", JOptionPane.ERROR_MESSAGE);
                    return;
                }                 
                    try{
                        value = Principal.cf.login(user, new String (senha.getPassword()));
                                                
                        FactoryWindow fabrica = new FactoryWindow();
                        fabrica.execute(this, value, user);                                        
                    }
                    catch(Exception ex){
                        JOptionPane.showMessageDialog(this,"Error: " + ex , "Atenção", JOptionPane.ERROR_MESSAGE);                                    
                    }
                                                     
                    if(Principal.user == null)
                        JOptionPane.showMessageDialog(this,"Usuário e/ou senha incorretos!", "Atenção", JOptionPane.ERROR_MESSAGE);                                                                        
                }
            }

            if(e.getSource() == btnSair){
                    System.exit(0);
            }
    }
    

}

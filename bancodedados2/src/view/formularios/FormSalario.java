package view.formularios;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import view.Principal;

public class FormSalario extends JDialog implements ActionListener{
    private static final long serialVersionUID = 1L;    
        
    private JLabel medio = new JLabel();
    private JLabel min = new JLabel();
    private JLabel max = new JLabel();
    private JButton btnOK = new JButton("OK");
    
    public FormSalario(){
        super(Principal.janela,"Salarios dos Empregado", true);
        
        btnOK.setPreferredSize(new Dimension(100, 25));             
        
        btnOK.addActionListener(this);

        JLabel maximo = new JLabel("Maximo salário");        
        
        JPanel grid = new JPanel();
        grid.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        grid.setLayout(new GridLayout(4, 2, 5, 5));
        grid.add(maximo);
        grid.add(max);
        grid.add(new JLabel("Medio salário"));
        grid.add(medio);     
        grid.add(new JLabel("Minimo salário"));
        grid.add(min);
        
        maximo.setPreferredSize(new Dimension(250, 25));
        max.setPreferredSize(new Dimension(250, 25));
        max.setMaximumSize(new Dimension(250, 25));        
        
        this.execute();
        
        JPanel botoes = new JPanel();
        
        botoes.add(btnOK);
        botoes.add(Box.createVerticalStrut(45));
        botoes.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,Color.LIGHT_GRAY));  
                
        JPanel painel = new JPanel();
        painel.add(grid);              
        
        this.add(painel, BorderLayout.NORTH);
        this.add(botoes, BorderLayout.SOUTH);
        this.setSize(550, 200);
        this.setLocation((java.awt.Toolkit.getDefaultToolkit()
                                        .getScreenSize().width / 2)
                                        - (this.getWidth() / 2), (java.awt.Toolkit
                                        .getDefaultToolkit().getScreenSize().height / 2)
                                        - (this.getHeight() / 2));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //this.setVisible(true);
    }
         
    @Override
    public void actionPerformed(ActionEvent e){        
        
        if (e.getSource() == btnOK)
                this.dispose();
    }

    public void execute() {
        max.setText("R$" + String.valueOf(Principal.cf.maiorSalario()));
        medio.setText("R$" + String.valueOf(Principal.cf.mediaSalarios()));
        min.setText("R$" + String.valueOf(Principal.cf.menorSalario()));       
    }
}

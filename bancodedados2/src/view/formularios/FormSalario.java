package view.formularios;

import Model.Empregado;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import view.Principal;

public class FormSalario extends JDialog implements ActionListener
{
    private static final long serialVersionUID = 1L;    
        
    private static Empregado emp;
    private JLabel nome = new JLabel();
    private JLabel ssn = new JLabel();
    private JLabel salario = new JLabel();
    private JComboBox tipo = new JComboBox();
    private JButton btnOK = new JButton("OK");
    
    public FormSalario()
    {
        super(Principal.janela,"Salarios dos Empregado", true);
        
        btnOK.setPreferredSize(new Dimension(100, 25));             
        
        btnOK.addActionListener(this);
        tipo.addActionListener(this);
        
        tipo.addItem("Maximo salário");
        tipo.addItem("Medio salário");
        tipo.addItem("Minimo salário");
        
        JLabel busca = new JLabel("Busca: ");        
        
        JPanel grid = new JPanel();
        grid.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        grid.setLayout(new GridLayout(4, 2, 5, 5));
        grid.add(busca);
        grid.add(tipo);
        grid.add(new JLabel("Nome empregado: "));
        grid.add(nome);     
        grid.add(new JLabel("Ssn: "));
        grid.add(ssn);
        grid.add(new JLabel("Salario: "));
        grid.add(salario);
        
        busca.setPreferredSize(new Dimension(250, 25));
        tipo.setPreferredSize(new Dimension(250, 25));
        tipo.setMaximumSize(new Dimension(250, 25));
        
        JPanel botoes = new JPanel();
        
        botoes.add(btnOK);
        botoes.add(Box.createVerticalStrut(45));
        botoes.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,Color.LIGHT_GRAY));  
                
        JPanel painel = new JPanel();
        painel.add(grid);              
        
        this.add(painel, BorderLayout.NORTH);
        this.add(botoes, BorderLayout.SOUTH);
        this.setSize(550, 240);
        this.setLocation((java.awt.Toolkit.getDefaultToolkit()
                                        .getScreenSize().width / 2)
                                        - (this.getWidth() / 2), (java.awt.Toolkit
                                        .getDefaultToolkit().getScreenSize().height / 2)
                                        - (this.getHeight() / 2));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
 
        
    @Override
    public void actionPerformed(ActionEvent e)
    {
        
        if (e.getSource() == tipo){
            System.out.println(tipo.getSelectedItem());
        }
        
        if (e.getSource() == btnOK)
                this.dispose();
    }

    public void setEmpregado(Empregado e) {
        emp = e;
        salario.setText(e.getSalarioString());
        nome.setText(e.getNome());
        ssn.setText(e.getSsn());        
    }

    public void execute() {
        emp = null;
        salario.setText("");
        nome.setText("");
        ssn.setText("");        
        this.setVisible(true);        
    }
}

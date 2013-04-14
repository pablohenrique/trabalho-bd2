package view.formularios;

import Model.Empregado;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import view.Principal;
import view.ViewObjectPool;

public class FormProjetosDepartamento extends JDialog implements ActionListener
{
    private static final long serialVersionUID = 1L;    
        
    private static Empregado emp;
    private JLabel nome = new JLabel();
    private JLabel ssn = new JLabel();
    private JLabel departamentoNome = new JLabel();
    private JComboBox departamento = new JComboBox();
    private JButton btnOK = new JButton("OK");
    
    public FormProjetosDepartamento(){
        super(Principal.janela,"O empregado que trabalha em mais projetos de um Detartamento", true);
        
        btnOK.setPreferredSize(new Dimension(100, 25));             
        
        btnOK.addActionListener(this);
        departamento.addActionListener(this);
        
        try {
            departamento = new JComboBox((Vector<Object>) ViewObjectPool.get("todosDapartamentos"));  
        } catch (Exception ex) {
            departamento = new JComboBox();  
        }
        
        JLabel busca = new JLabel("Departamento: ");        
        
        JPanel grid = new JPanel();
        grid.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        grid.setLayout(new GridLayout(4, 2, 5, 5));
        grid.add(busca);
        grid.add(departamento);
        grid.add(new JLabel("Nome empregado: "));
        grid.add(nome);     
        grid.add(new JLabel("Ssn: "));
        grid.add(ssn);
        grid.add(new JLabel("Departamento: "));
        grid.add(departamentoNome);
        
        busca.setPreferredSize(new Dimension(250, 25));
        departamento.setPreferredSize(new Dimension(250, 25));
        departamento.setMaximumSize(new Dimension(250, 25));
        
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
    public void actionPerformed(ActionEvent e){
        
        if (e.getSource() == departamento){
            System.out.println(departamento.getSelectedItem());
        }
        
        if (e.getSource() == btnOK)
                this.dispose();
    }

    public void setEmpregado(Empregado e) {
        emp = e;
        departamento.setModel(new javax.swing.DefaultComboBoxModel((Vector) ViewObjectPool.get("todosDapartamentos")));
        departamentoNome.setText(e.getDepartamento().getNome());
        nome.setText(e.getNome());
        ssn.setText(e.getSsn());        
    }

    public void execute() {
        emp = null;
        departamentoNome.setText("");
        nome.setText("");
        ssn.setText("");        
        this.setVisible(true);        
    }
}

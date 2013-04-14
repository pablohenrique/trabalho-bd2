package view.formularios;

import Model.Departamento;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import view.Principal;

public class FormDepartamentoLocalForm extends JDialog implements ActionListener
{
    private static final long serialVersionUID = 1L;    
    private JTextField nomeLocal = new JTextField();
       
    private JButton btnOK = new JButton("OK");
    private JButton btnCancelar = new JButton("Cancelar");
    private Departamento dEdit = null;
    
    public FormDepartamentoLocalForm(Departamento d){       
        super(Principal.janela,"Inserir Empregado em Projeto", true);
        
        dEdit = d;
        btnOK.setPreferredSize(new Dimension(100, 25));
        btnCancelar.setPreferredSize(new Dimension(100, 25));

        btnOK.addActionListener(this);
        btnCancelar.addActionListener(this);

        JPanel grid = new JPanel();
        grid.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        grid.setLayout(new GridLayout(1, 2, 5, 5));      
        grid.add(new JLabel("Local: "));
        grid.add(nomeLocal);                                  
        
        nomeLocal.setPreferredSize(new Dimension(200, 25));        
        nomeLocal.addActionListener(this);
        
        JPanel painel = new JPanel();
        painel.add(grid);

        JPanel botoes = new JPanel();
        botoes.add(btnOK);
        botoes.add(btnCancelar);
        botoes.add(Box.createVerticalStrut(45));
        botoes.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,Color.LIGHT_GRAY));
       
        this.add(painel, BorderLayout.CENTER);
        this.add(botoes, BorderLayout.SOUTH);
        this.setSize(440, 150);
        this.setLocation((java.awt.Toolkit.getDefaultToolkit()
                                        .getScreenSize().width / 2)
                                        - (this.getWidth() / 2), (java.awt.Toolkit
                                        .getDefaultToolkit().getScreenSize().height / 2)
                                        - (this.getHeight() / 2));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
    
    public void execute(Departamento d){        
        dEdit = d;
        nomeLocal.setText("");        
    }       
  
    @Override
    public void actionPerformed(ActionEvent e){
        Object origem = e.getSource();
               
        if(origem == btnOK){
                try{                    
                    Principal.cf.inserirLocalizacoes(dEdit.getNumero() , nomeLocal.getText());                                           
                    
                    JOptionPane.showMessageDialog(this,"Cadastro realizado com sucesso!", "Atenção", JOptionPane.INFORMATION_MESSAGE);                                                                        
                    FormDepartamentoLocal.setDataTableDepLocal(dEdit);
                    this.dispose();
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(this,"Erro: " + ex, "Atenção", JOptionPane.ERROR_MESSAGE);                                                                                            
                }                                                
        }

        if (origem == btnCancelar)
                this.dispose();
    }
}

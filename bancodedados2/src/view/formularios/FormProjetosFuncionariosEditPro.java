package view.formularios;

import Model.Departamento;
import Model.Empregado;
import Model.Projeto;
import Model.Trabalha;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import view.Principal;
import view.ViewObjectPool;

public class FormProjetosFuncionariosEditPro extends JDialog implements ActionListener {
    private static final long serialVersionUID = 1L;    
    private JTextField horas = new JTextField();
    private JLabel projetos = new JLabel();
    
    private JComboBox empregados;    
       
    private JButton btnOK = new JButton("OK");
    private JButton btnCancelar = new JButton("Cancelar");
    private Projeto projeto = null;
    private Empregado emp = null;
    
    public FormProjetosFuncionariosEditPro(Trabalha t){       
        super(Principal.janela,"Inserir Empregado em Projeto", true);
                
        
        if(t.getProjeto() == null)
            return;
                
        
        projeto = t.getProjeto();
        emp = t.getEssn();
        
        System.out.println(projeto.getNome());
  
        try {
            //deve listar apenas empregados do ssn logado
            empregados = new JComboBox(Principal.cf.buscaSuperSnn(Principal.user.getSsn()));  
        } catch (Exception ex) {
            empregados = new JComboBox();  
        }               
        

        if (emp != null){
            try{
                this.editar(t);
            }
            catch (Exception ex){
                 JOptionPane.showMessageDialog(this,"Erro Trabalha EM: " + ex, "Atenção", JOptionPane.ERROR_MESSAGE);                                                                        
            }
        }
        
        btnOK.setPreferredSize(new Dimension(100, 25));
        btnCancelar.setPreferredSize(new Dimension(100, 25));

        btnOK.addActionListener(this);
        btnCancelar.addActionListener(this);
        
        projetos.setText(projeto.getNome());
        
        JPanel grid = new JPanel();
        grid.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        grid.setLayout(new GridLayout(3, 2, 5, 5));
        grid.add(new JLabel("Projeto: "));
        grid.add(projetos);           
        grid.add(new JLabel("Empregados: "));
        grid.add(empregados);                   
        grid.add(new JLabel("Horas: "));
        grid.add(horas);                                  
        
        empregados.setPreferredSize(new Dimension(200, 25));        
        empregados.addActionListener(this);
        
        JPanel painel = new JPanel();
        painel.add(grid);

        JPanel botoes = new JPanel();
        botoes.add(btnOK);
        botoes.add(btnCancelar);
        botoes.add(Box.createVerticalStrut(45));
        botoes.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,Color.LIGHT_GRAY));
       
        this.add(painel, BorderLayout.CENTER);
        this.add(botoes, BorderLayout.SOUTH);
        this.setSize(440, 200);
        this.setLocation((java.awt.Toolkit.getDefaultToolkit()
                                        .getScreenSize().width / 2)
                                        - (this.getWidth() / 2), (java.awt.Toolkit
                                        .getDefaultToolkit().getScreenSize().height / 2)
                                        - (this.getHeight() / 2));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    
    public void editar(Trabalha t) throws Exception{
        //new projeto
        if(t.getEssn() == null){
            emp = null;
            this.clean();
            return;
        }
                
        emp = t.getEssn();
        projeto = t.getProjeto();        
        
        this.setTitle("Editar Trabalha Em");
    }   
    
    public void clean(){
        horas.setText("");
        empregados.setSelectedIndex(0);
        this.setVisible(true);
    }
    
    public int selecionarComboBoxSup(Empregado e, JComboBox<Empregado> box){
        if(e == null)
            return box.getItemCount();
        
        for(int i=0; i < box.getItemCount(); i++)   
            if (e.getSsn().equals(box.getItemAt(i).getSsn()))
                    return i;        
        
        return box.getItemCount();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        Object origem = e.getSource();
        
        if(origem == btnOK && projeto != null){
            if(emp == null){
                try
                {
                    Empregado supervisionado = (Empregado) empregados.getSelectedItem();
                    
                    System.out.println("trabalha - " + supervisionado.getSsn() + " " + projeto.getNumero() + " - " + horas.getText());
                    
                    Principal.cf.inserirTrabalha(supervisionado.getSsn(), projeto.getNumero(), Float.valueOf(horas.getText()));
                    
                    JOptionPane.showMessageDialog(this,"Cadastro realizado com sucesso!", "Atenção", JOptionPane.INFORMATION_MESSAGE);                                                                        
                    
                    //FormProjetosFuncionarios.setDataTableFuncionariosProjetos();
                    this.dispose();
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(this,"Erro: " + ex, "Atenção", JOptionPane.ERROR_MESSAGE);                                                                                            
                }               
            } else{
               try
                {                
                    Empregado supervisionado = (Empregado) empregados.getSelectedItem();
                                        
                    Principal.cf.atualizarTrabalha(supervisionado.getSsn(), projeto.getNumero(), Float.valueOf(horas.getText()));
                    
                    JOptionPane.showMessageDialog(this,"Edicao realizada com sucesso!", "Atenção", JOptionPane.INFORMATION_MESSAGE);                                                                        
                    
                    //FormProjetosFuncionarios.setDataTableFuncionariosProjetos();
                    this.dispose();
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(this,"Erro: " + ex, "Atenção", JOptionPane.ERROR_MESSAGE);                                                                                            
                }                 
            }                                     
        }

        if (origem == btnCancelar)
            this.dispose();
    }
}

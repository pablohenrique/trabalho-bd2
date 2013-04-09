package view.formularios;

import Model.Departamento;
import Model.Projeto;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import view.Principal;
import view.panel.PainelProjetos;

public class FormProjetos extends JDialog implements ActionListener
{
    private static final long serialVersionUID = 1L;    
    private static JTextField nome;
    private static JTextField localizacao; 
    private static JComboBox departamento;    
    
    private static JButton btnOK;
    private static JButton btnCancelar;
    private Projeto pEdit = null;
    
    public FormProjetos(Projeto p)
    {
        super(Principal.janela,"Cadastro de Projetos", true);
        pEdit = p;
        nome = new JTextField();
        localizacao = new JTextField();                
        
        try {
            departamento = new JComboBox();  
            //departamento = new JComboBox(Principal.cf.listarDepartamentos());  
        } catch (Exception ex) {
            departamento = new JComboBox();  
        }
        
        if (pEdit != null)
        {
            try
            {
                this.editar(pEdit);
            }
            catch (Exception ex)
            {
                 JOptionPane.showMessageDialog(this,"Erro Projetos: " + ex, "Atenção", JOptionPane.ERROR_MESSAGE);                                                                        
            }
        }
        
        btnOK = new JButton("OK");
        btnCancelar = new JButton("Cancelar");
        btnOK.setPreferredSize(new Dimension(100, 25));
        btnCancelar.setPreferredSize(new Dimension(100, 25));

        btnOK.addActionListener(this);
        btnCancelar.addActionListener(this);

        JPanel grid = new JPanel();
        grid.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        grid.setLayout(new GridLayout(3, 2, 5, 5));
        grid.add(new JLabel("Nome: "));
        grid.add(nome);
        grid.add(new JLabel("Localizacao: "));
        grid.add(localizacao);                
        grid.add(new JLabel("Departamento: "));
        grid.add(departamento);                        
        
        nome.setPreferredSize(new Dimension(250, 25));

        JPanel painel = new JPanel();
        painel.add(grid);

        JPanel botoes = new JPanel();
        botoes.add(btnOK);
        botoes.add(btnCancelar);
        botoes.add(Box.createVerticalStrut(45));
        botoes.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,Color.LIGHT_GRAY));


        this.add(painel, BorderLayout.CENTER);
        this.add(botoes, BorderLayout.SOUTH);
        this.setSize(550, 200);
        this.setLocation((java.awt.Toolkit.getDefaultToolkit()
                                        .getScreenSize().width / 2)
                                        - (this.getWidth() / 2), (java.awt.Toolkit
                                        .getDefaultToolkit().getScreenSize().height / 2)
                                        - (this.getHeight() / 2));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public void editar(Projeto p) throws Exception {        
       if(p == null){
            pEdit = null;
            cleanProjetosForm();
            return;
        }
        pEdit = p;
        
        nome.setText(p.getNome());
        localizacao.setText(p.getLocalizacao());
        departamento.setSelectedIndex(this.selecionarComboBoxDep(p.getDepartamento().getNumero(), departamento));                
           
        this.setTitle("Editar Projeto");        
    }
    
    public void cleanProjetosForm(){
        nome.setText("");
        localizacao.setText("");
        departamento.setSelectedIndex(0);
        
        this.setTitle("Cadastro de Projeto");
    }    
    
    public int selecionarComboBoxDep(int id, JComboBox<Departamento> box){
        for(int i=0; i < box.getItemCount(); i++)   
            if (id == box.getItemAt(i).getNumero())
                    return i;        
        return -1;                
    }
        
    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object origem = e.getSource();

        if(origem == btnOK)
        {
            if(pEdit == null)//inserir novo elemento
            {
                try
                {
                    Departamento d = (Departamento) departamento.getSelectedItem();                      
                    System.out.println(nome.getText()+ " - "+ localizacao.getText()+ " - "+ d.getNumero());
                    Principal.cf.inserirProjeto(nome.getText(), localizacao.getText(), d.getNumero());
                    
                    JOptionPane.showMessageDialog(this,"Cadastro realizado com sucesso!", "Atenção", JOptionPane.INFORMATION_MESSAGE);                                                                        
                    PainelProjetos.setDataTable();
                    this.dispose();
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(this,"Erro: " + ex, "Atenção", JOptionPane.ERROR_MESSAGE);                                                                                            
                }
                
            }
            else
            {
                try
                {
                    Departamento d = (Departamento) departamento.getSelectedItem();                      

                    Principal.cf.atualizarProjeto(pEdit.getNumero(), nome.getText(), localizacao.getText(), d.getNumero());
                    
                    JOptionPane.showMessageDialog(this,"Atualizacao realizada com sucesso!", "Atenção", JOptionPane.INFORMATION_MESSAGE);                                                                        
                    PainelProjetos.setDataTable();
                    this.dispose();
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(this,"Erro: " + ex, "Atenção", JOptionPane.ERROR_MESSAGE);                                                                                            
                }                
            }
        }

        if (origem == btnCancelar)
        {
                this.dispose();
        } 
    }

}

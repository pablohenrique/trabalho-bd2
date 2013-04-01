package view.formularios;

import Model.Departamento;
import Model.Empregado;
import Model.Projeto;
import control.FuncoesControle;
import java.awt.BorderLayout;
import java.awt.Color;
import static java.awt.Component.TOP_ALIGNMENT;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.awt.image.ImageObserver.WIDTH;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import view.panel.PainelFuncionarios;
import static view.panel.PainelFuncionarios.modelo;
import static view.panel.PainelFuncionarios.tabela;
import view.Principal;

public class FormFuncionarioProjetosForm extends JDialog implements ActionListener
{
    private static final long serialVersionUID = 1L;    
    private static JTextField horas;

    private static JComboBox departamento;
    private static JComboBox projetos;
       
    private static JButton btnOK;
    private static JButton btnCancelar;
    private Projeto projeto_edit = null;
    
    public FormFuncionarioProjetosForm(Projeto p, Empregado emp, boolean logado)
    {       
        super(Principal.janela,"Inserir Empregado em Projeto", true);
        
        if(emp == null)
            return;
                
        projeto_edit = p;
        horas = new JTextField();
        try {
            departamento = new JComboBox(Principal.cf.listarDepartamentos());  
        } catch (Exception ex) {
            departamento = new JComboBox();  
        }
        
        projetos = new JComboBox();  
        this.setEnable(false);
        
        if (projeto_edit != null)
        {
            try
            {
                this.editarProjeto(projeto_edit);
            }
            catch (Exception ex)
            {
                 JOptionPane.showMessageDialog(this,"Erro Empregado: " + ex, "Atenção", JOptionPane.ERROR_MESSAGE);                                                                        
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
        grid.add(new JLabel("Departamento: "));
        grid.add(departamento);           
        grid.add(new JLabel("Projeto: "));
        grid.add(projetos);           
        grid.add(new JLabel("Horas: "));
        grid.add(horas);                                  
        
        departamento.setPreferredSize(new Dimension(200, 25));        
        departamento.addActionListener(this);
        
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

    
    public void editarProjeto(Projeto e) throws Exception
    {
        /*
        nome.setText(e.getNome());
        endereco.setText(e.getEndereco());
        dataNasc.setText(e.getDataNascimentoString());
        salario.setText(e.getSalarioString());
        ssn.setText(e.getSsn());
        senha.setText(e.getSenha());
        sexo.setSelectedItem(e.getSexo());
        departamento.setSelectedIndex(this.selecionarComboBoxDep(e.getDepartamento().getNumero(), departamento));
        
        
        System.out.println("ssn" + e.getSuperSsn().getSsn());

        if(e.getSuperSsn().getSsn() != null)
            supervisor.setSelectedIndex(this.selecionarComboBoxSup(e.getSuperSsn(), supervisor));
        else
            supervisor.setSelectedIndex(supervisor.getItemCount()-1);
        
        this.setTitle("Editar Empregado");*/
    }    
    
    public int selecionarComboBoxDep(int id, JComboBox<Departamento> box){
        for(int i=0; i < box.getItemCount(); i++)   
            if (id == box.getItemAt(i).getNumero())
                    return i;        
        return -1;                
    }

    public int selecionarComboBoxSup(Empregado e, JComboBox<Empregado> box){
        if(e == null)
            return box.getItemCount();
        
        for(int i=0; i < box.getItemCount(); i++)   
            if (e.getSsn().equals(box.getItemAt(i).getSsn()))
                    return i;        
        
        return box.getItemCount();
    }

    public void setEnable(boolean value){
        horas.setEnabled(value);
        projetos.setEnabled(value);  
    }          
    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object origem = e.getSource();
        
        if(origem == departamento){
             Departamento dep = (Departamento) departamento.getSelectedItem();
            try {  
                System.out.println("Depn" + dep.getNumero());
                projetos.removeAllItems();
                projetos.setModel(new DefaultComboBoxModel<>(Principal.cf.listarProjetosByNumeroDepto(dep.getNumero())));
                this.setEnable(true);
            } catch (Exception ex) {
                System.err.println("Erro em Funcionarios Projetos: " + ex);
                projetos.removeAllItems();              
                this.setEnable(false);
            }
        }         
        else if(origem == btnOK)
        {
            if(projeto_edit == null)//inserir novo elemento
            {/*
                try
                {
                    Departamento d = (Departamento) departamento.getSelectedItem();  
                    Empregado superssn = (Empregado) supervisor.getSelectedItem();
                    
                    Principal.cf.inserirEmpregado(ssn.getText(), nome.getText(), sexo.getItemAt(sexo.getSelectedIndex()), 
                                                  endereco.getText(), salario.getText(), dataNasc.getText(), d.getNumero(),
                                                 superssn.getSsn(), new String (senha.getPassword()));                                           
                    
                    JOptionPane.showMessageDialog(this,"Cadastro realizado com sucesso!", "Atenção", JOptionPane.INFORMATION_MESSAGE);                                                                        
                    PainelFuncionarios.setDataTable();
                    this.dispose();
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(this,"Erro: " + ex, "Atenção", JOptionPane.ERROR_MESSAGE);                                                                                            
                }
               */ 
            }
            else
            {
                /*
               try
                {                
                    Departamento d = (Departamento) departamento.getSelectedItem();  
                    Empregado superssn = (Empregado) supervisor.getSelectedItem();
                    
                    Principal.cf.atualizarEmpregado(ssn.getText(), nome.getText(), sexo.getItemAt(sexo.getSelectedIndex()), 
                                                    endereco.getText(), salario.getText(), dataNasc.getText(), d.getNumero(),
                                                    superssn.getSsn(), new String (senha.getPassword()));                                           
                    
                    JOptionPane.showMessageDialog(this,"Cadastro realizado com sucesso!", "Atenção", JOptionPane.INFORMATION_MESSAGE);                                                                        
                    PainelFuncionarios.setDataTable();
                    this.dispose();
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(this,"Erro: " + ex, "Atenção", JOptionPane.ERROR_MESSAGE);                                                                                            
                } */                   
            }
                                     
        }

        if (origem == btnCancelar)
        {
                this.dispose();
        } 
    }
}

package view.formularios;

import Model.Departamento;
import Model.Dependente;
import Model.Empregado;
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
import view.PainelFuncionarios;
import static view.PainelFuncionarios.modelo;
import static view.PainelFuncionarios.tabela;
import view.Principal;

public class FormDependente extends JDialog implements ActionListener
{
    private static final long serialVersionUID = 1L;    
    private static JTextField nome;
    private static JFormattedTextField dataNasc;  

    private static JComboBox<String> sexo;
    private static JComboBox<String> parentesco;
    private static JComboBox empregado;
    
    private static JButton btnOK;
    private static JButton btnCancelar;
    private Dependente dep_edit = null;
    
    public FormDependente(Dependente dep, boolean logado)
    {
        super(Principal.janela,"Cadastro de Dependente", true);
        dep_edit = dep;
        nome = new JTextField();
        dataNasc = new JFormattedTextField();                
        try
        {
            dataNasc.setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("##/##/####")));  
        }
        catch (ParseException ex)
        {
            Logger.getLogger(FormDependente.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro mascara! " + ex);
        }
        
        
        sexo = new JComboBox<String>();
        sexo.addItem("Masculino");
        sexo.addItem("Feminino");
        
        parentesco = new JComboBox<String>();
        parentesco.addItem("Filho(a)");
        parentesco.addItem("Conjugue");
        parentesco.addItem("Nao sei");
                               
        empregado = new JComboBox(Principal.cf.listarEmpregados());  
        
        if (dep != null)
        {
            try
            {
              //  this.editarEmpregado(dep);
            }
            catch (Exception ex)
            {
                 JOptionPane.showMessageDialog(this,"Erro Dependente: " + ex, "Atenção", JOptionPane.ERROR_MESSAGE);                                                                        
            }
        }
        
        btnOK = new JButton("OK");
        btnCancelar = new JButton("Cancelar");
        btnOK.setPreferredSize(new Dimension(100, 25));
        btnCancelar.setPreferredSize(new Dimension(100, 25));

        btnOK.addActionListener(this);
        btnCancelar.addActionListener(this);
        empregado.addActionListener(this);
        
        this.setEnable(false);
        
        JPanel grid = new JPanel();
        grid.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        grid.setLayout(new GridLayout(5, 2, 5, 5));
        grid.add(new JLabel("Empregado: "));
        grid.add(empregado);                  
        grid.add(new JLabel("Nome Dependente: "));
        grid.add(nome);
        grid.add(new JLabel("Sexo: "));
        grid.add(sexo);                              
        grid.add(new JLabel("Data Nascimento: "));
        grid.add(dataNasc);              
        grid.add(new JLabel("Parentesco: "));
        grid.add(parentesco);   
        
        empregado.setPreferredSize(new Dimension(250, 25));

        JPanel painel = new JPanel();
        painel.add(grid);

        JPanel botoes = new JPanel();
        botoes.add(btnOK);
        botoes.add(btnCancelar);
        botoes.add(Box.createVerticalStrut(45));
        botoes.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,Color.LIGHT_GRAY));
        
        this.add(painel, BorderLayout.CENTER);
        this.add(botoes, BorderLayout.SOUTH);
        this.setSize(550, 290);
        this.setLocation((java.awt.Toolkit.getDefaultToolkit()
                                        .getScreenSize().width / 2)
                                        - (this.getWidth() / 2), (java.awt.Toolkit
                                        .getDefaultToolkit().getScreenSize().height / 2)
                                        - (this.getHeight() / 2));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    
    public void editarEmpregado(Dependente e) throws Exception
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
        */
        this.setTitle("Editar Dependente");
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
        nome.setEnabled(value);
        sexo.setEnabled(value);
        parentesco.setEnabled(value);
        dataNasc.setEnabled(value);
    }

    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object origem = e.getSource();
        
        if(origem == empregado)
            this.setEnable(true);
        
        if(origem == btnOK)
        {/*
            if(emp_edit == null)//inserir novo elemento
            {
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
                
            }
            else
            {
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
                }                    
            }*/
                                     
        }

        if (origem == btnCancelar)
        {
                this.dispose();
        } 
    }
}

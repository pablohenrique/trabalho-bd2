package view.formularios;

import Model.Departamento;
import Model.Empregado;
import control.FuncoesControle;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import view.Principal;

public class FormFuncionario extends JDialog implements ActionListener
{
    private static final long serialVersionUID = 1L;    
    private static JTextField nome;
    private static JFormattedTextField ssn = new JFormattedTextField();
    private static JTextField endereco;
    private static JFormattedTextField salario = new JFormattedTextField();  
    private static JFormattedTextField dataNasc = new JFormattedTextField();  

    private static JComboBox<String> sexo;
    private static JComboBox departamento;
    private static JComboBox<String> supervisor;
    
    private static JPasswordField senha;
    
    private static JButton btnOK;
    private static JButton btnCancelar;
    
    public FormFuncionario(Empregado emp)
    {
        super(Principal.janela,"Cadastro de Empregado", true);

        nome = new JTextField();
        endereco = new JTextField();
               
        try
        {
            dataNasc.setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("##/##/####")));  
            salario.setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("######,##")));  
            ssn.setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("#########")));  
        }
        catch (ParseException ex)
        {
            Logger.getLogger(FormFuncionario.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro mascara! " + ex);
        }
        
        senha = new JPasswordField();
        
        sexo = new JComboBox<String>();
        sexo.addItem("Masculino");
        sexo.addItem("Feminino");    
        
        departamento = new JComboBox(Principal.cf.listarDepartamentos());        
        supervisor = new JComboBox(Principal.cf.listarEmpregados());
                
        /*
        combo = new JComboBox<String>();
        combo.addItem("Combo box 1");
        combo.addItem("DEPARTAMENTO 1");
        combo.addItem("REDES");
        combo.addItem("Outros");
        */
        
        if (emp != null) 
            this.editarEmpregado(emp);
        
        btnOK = new JButton("OK");
        btnCancelar = new JButton("Cancelar");
        btnOK.setPreferredSize(new Dimension(100, 25));
        btnCancelar.setPreferredSize(new Dimension(100, 25));

        btnOK.addActionListener(this);
        btnCancelar.addActionListener(this);

        JPanel grid = new JPanel();
        grid.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        grid.setLayout(new GridLayout(10, 2, 5, 5));
        grid.add(new JLabel("Nome: "));
        grid.add(nome);
        grid.add(new JLabel("Sexo: "));
        grid.add(sexo);                
        grid.add(new JLabel("Endereco: "));
        grid.add(endereco);                
        grid.add(new JLabel("Data Nascimento: "));
        grid.add(dataNasc);                
        grid.add(new JLabel("Seguridade Social: "));
        grid.add(ssn);        
        grid.add(new JLabel("Salario: "));
        grid.add(salario);                 
        grid.add(new JLabel("Senha: "));
        grid.add(senha);                
        grid.add(new JLabel("Departamento: "));
        grid.add(departamento);                
        grid.add(new JLabel("Supervisor: "));
        grid.add(supervisor);           
        
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
        this.setSize(550, 510);
        this.setLocation((java.awt.Toolkit.getDefaultToolkit()
                                        .getScreenSize().width / 2)
                                        - (this.getWidth() / 2), (java.awt.Toolkit
                                        .getDefaultToolkit().getScreenSize().height / 2)
                                        - (this.getHeight() / 2));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    
    public void editarEmpregado(Empregado e)
    {
        nome.setText(e.getNome());
        endereco.setText(e.getEndereco());
        dataNasc.setText(FuncoesControle.converteData(e.getDataNascimento()));
        salario.setText(String.format("%.2f", e.getSalario()));
        ssn.setText(e.getSsn());
        senha.setText(e.getSenha());
        sexo.setSelectedIndex(FuncoesControle.getSexo(e.getSexo()));
        //curso.setSelectedItem(aluno.getCurso());

        this.setTitle("Editar Empregado");
    }    

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object origem = e.getSource();

        if(origem == btnOK)
        {
                this.dispose();
        }

        if (origem == btnCancelar)
        {
                this.dispose();
        } 
    }
}

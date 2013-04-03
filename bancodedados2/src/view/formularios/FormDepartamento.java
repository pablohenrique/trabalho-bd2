package view.formularios;

import Model.Departamento;
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
import view.panel.PainelDepartamento;
import view.panel.PainelFuncionarios;
import static view.panel.PainelFuncionarios.modelo;
import static view.panel.PainelFuncionarios.tabela;
import view.Principal;

public class FormDepartamento extends JDialog implements ActionListener
{
    private static final long serialVersionUID = 1L;    
    
    private static JTextField nome;
    private static JFormattedTextField dataInicio;  
    private static JComboBox gerente;
    
    private static JButton btnOK;
    private static JButton btnCancelar;
    private Departamento dep_edit = null;
    
    public FormDepartamento(Departamento emp)
    {
        super(Principal.janela,"Cadastro de Departamento", true);
        dep_edit = emp;
        nome = new JTextField();
        dataInicio = new JFormattedTextField();
                
        
        try
        {
            dataInicio.setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("##/##/####")));  
        }
        catch (ParseException ex)
        {
            Logger.getLogger(FormDepartamento.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro mascara! " + ex);
        }
        try {
            gerente = new JComboBox(Principal.cf.listarEmpregados());  
        } catch (Exception ex) {
            gerente = new JComboBox();
        }
        
        if (dep_edit != null)
        {
            try
            {
                this.editar(dep_edit);
            }
            catch (Exception ex)
            {
                 JOptionPane.showMessageDialog(this,"Erro Departamento: " + ex, "Atenção", JOptionPane.ERROR_MESSAGE);                                                                        
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
        grid.add(new JLabel("Nome Departamento: "));
        grid.add(nome);            
        grid.add(new JLabel("Gerente: "));
        grid.add(gerente);                
        grid.add(new JLabel("Data Inicio: "));
        grid.add(dataInicio);                         
        
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

    
    public void editar(Departamento dep) throws Exception
    {

        nome.setText(dep.getNome());
        dataInicio.setText(dep.getGerenteDataInicioString());
        
        if(dep.getGerenteSsn().getSuperSsn().getSsn() != null)
            gerente.setSelectedIndex(this.selecionarComboBoxSup(dep.getGerenteSsn(), gerente));
            
        this.setTitle("Editar Departamento");
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

        if(origem == btnOK){
            if(dep_edit == null){
                try{                    
                    Empregado gerssn = (Empregado) gerente.getSelectedItem();
                    
                    Principal.cf.inserirDepartamento(nome.getText(), gerssn.getSsn(), dataInicio.getText());
                    
                    JOptionPane.showMessageDialog(this,"Cadastro realizado com sucesso!", "Atenção", JOptionPane.INFORMATION_MESSAGE);                                                                        
                    PainelDepartamento.setDataTable();
                    this.dispose();
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(this,"Erro: " + ex, "Atenção", JOptionPane.ERROR_MESSAGE);                                                                                            
                }
                
            }
            else{
               try{                
                    Empregado gerssn = (Empregado) gerente.getSelectedItem();
                    
                    Principal.cf.atualizarDepartamento(dep_edit.getNumero(), nome.getText(), gerssn.getSsn(), dataInicio.getText());
                    
                    JOptionPane.showMessageDialog(this,"Atualização realizada com sucesso!", "Atenção", JOptionPane.INFORMATION_MESSAGE);                                                                        
                    PainelDepartamento.setDataTable();
                    this.dispose();
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(this,"Erro: " + ex, "Atenção", JOptionPane.ERROR_MESSAGE);                                                                                            
                }                    
            }                                     
        }

        if (origem == btnCancelar){
                this.dispose();
        } 
    }
}

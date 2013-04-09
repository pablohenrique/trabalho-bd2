package view.formularios;

import Model.Departamento;
import Model.Empregado;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import view.Principal;
import view.panel.PainelDepartamento;

public class FormDepartamento extends JDialog implements ActionListener
{
    private static final long serialVersionUID = 1L;    
    
    private JTextField nome;
    private JFormattedTextField dataInicio;  
    private JComboBox gerente;
    
    private JButton btnOK = new JButton("OK");
    private JButton btnCancelar = new JButton("Cancelar");
    private JPanel botoes = new JPanel();
    private JPanel painel = new JPanel();
    private JPanel grid = new JPanel();
    
    private Departamento depEdit = null;
    
    public FormDepartamento(Departamento emp)
    {
        super(Principal.janela,"Cadastro de Departamento", true);
        depEdit = emp;
        nome = new JTextField();
        dataInicio = new JFormattedTextField();
                    
        try{
            dataInicio.setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("##/##/####")));  
        }
        catch (ParseException ex){
            Logger.getLogger(FormDepartamento.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro mascara! " + ex);
        }
        try {
            gerente = new JComboBox();
            //gerente = new JComboBox(Principal.cf.listarEmpregados());  
        } catch (Exception ex) {
            gerente = new JComboBox();
        }
        
        if (depEdit != null){
            try{
                this.editar(depEdit);
            }
            catch (Exception ex)
            {
                 JOptionPane.showMessageDialog(this,"Erro Departamento: " + ex, "Atenção", JOptionPane.ERROR_MESSAGE);                                                                        
            }
        }
        
        btnOK.setPreferredSize(new Dimension(100, 25));
        btnCancelar.setPreferredSize(new Dimension(100, 25));

        btnOK.addActionListener(this);
        btnCancelar.addActionListener(this);

        grid.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        grid.setLayout(new GridLayout(3, 2, 5, 5));
        grid.add(new JLabel("Nome Departamento: "));
        grid.add(nome);            
        grid.add(new JLabel("Gerente: "));
        grid.add(gerente);                
        grid.add(new JLabel("Data Inicio: "));
        grid.add(dataInicio);                         
        
        nome.setPreferredSize(new Dimension(250, 25));

        painel.add(grid);

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
       if(dep == null){
            depEdit = null;
            cleanProjetosForm();
            return;
        }
        depEdit = dep;
        
        nome.setText(dep.getNome());
        //dataInicio.setText(Principal.cf.converteDataString(dep.getGerenteDataInicioString()));
        
        if(dep.getGerenteSsn().getSuperSsn().getSsn() != null)
            gerente.setSelectedIndex(this.selecionarComboBoxSup(dep.getGerenteSsn(), gerente));
            
        this.setTitle("Editar Departamento");
    }    
    
    private void cleanProjetosForm() {
        nome.setText("");
        dataInicio.setText("");
        gerente.setSelectedIndex(0);
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
            if(depEdit == null){
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
                    
                    System.out.println(depEdit.getNumero()+ " - " + nome.getText()+ " - " +gerssn.getSsn()+ " - " +dataInicio.getText());
                    Principal.cf.atualizarDepartamento(depEdit.getNumero(), nome.getText(), gerssn.getSsn(), dataInicio.getText());
                    
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

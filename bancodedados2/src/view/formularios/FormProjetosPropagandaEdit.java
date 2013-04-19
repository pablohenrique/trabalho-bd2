package view.formularios;

import Model.Empregado;
import Model.Projeto;
import Model.Propaganda;
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

public class FormProjetosPropagandaEdit extends JDialog implements ActionListener {
    private static final long serialVersionUID = 1L;
    
    private JFormattedTextField dataInicio = new JFormattedTextField();
    private JFormattedTextField dataFim = new JFormattedTextField();
    private JTextField agencia = new JTextField();    
    private JTextField tarifa = new JTextField();    
       
    private JButton btnOK = new JButton("OK");
    private JButton btnCancelar = new JButton("Cancelar");
    private Propaganda publi = null;
    private Projeto proj = null;
    
    public FormProjetosPropagandaEdit(Propaganda p, Projeto projeto){       
        super(Principal.janela,"Inserir Propaganda em Projeto", true);
                        
        if(projeto == null)
            return;
        try{
            dataInicio.setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("##/##/####")));  
            dataFim.setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("##/##/####")));  
        }
        catch (ParseException ex){
            Logger.getLogger(FormFuncionario.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("Erro mascara! " + ex);
        }
        
        //System.out.println("Inserir propaganda projeto, new "+ projeto.getNome());
        publi = p;                      
        proj = projeto;
        
        if (publi != null){
            try{
                this.editar(p, projeto);
            }
            catch (Exception ex){
                 JOptionPane.showMessageDialog(this,"Erro Propaganda: " + ex, "Atenção", JOptionPane.ERROR_MESSAGE);                                                                        
            }
        }
        
        btnOK.setPreferredSize(new Dimension(100, 25));
        btnCancelar.setPreferredSize(new Dimension(100, 25));

        btnOK.addActionListener(this);
        btnCancelar.addActionListener(this);
        
        
        JPanel grid = new JPanel();
        grid.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        grid.setLayout(new GridLayout(4, 2, 5, 5));       
        grid.add(new JLabel("Data Inicial: "));
        grid.add(dataInicio);
        grid.add(new JLabel("Data Final: "));
        grid.add(dataFim);                   
        grid.add(new JLabel("Agencia: "));
        grid.add(agencia);                   
        grid.add(new JLabel("Tarifa: "));
        grid.add(tarifa);                                  
        
        dataInicio.setPreferredSize(new Dimension(200, 25));        
        dataInicio.addActionListener(this);
        
        JPanel painel = new JPanel();
        painel.add(grid);

        JPanel botoes = new JPanel();
        botoes.add(btnOK);
        botoes.add(btnCancelar);
        botoes.add(Box.createVerticalStrut(45));
        botoes.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,Color.LIGHT_GRAY));
       
        this.add(painel, BorderLayout.CENTER);
        this.add(botoes, BorderLayout.SOUTH);
        this.setSize(440, 250);
        this.setLocation((java.awt.Toolkit.getDefaultToolkit()
                                        .getScreenSize().width / 2)
                                        - (this.getWidth() / 2), (java.awt.Toolkit
                                        .getDefaultToolkit().getScreenSize().height / 2)
                                        - (this.getHeight() / 2));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    
    public void editar(Propaganda p, Projeto projeto) throws Exception{
        proj = projeto;        
        if(p == null){
            publi = null;   
            this.clean();
            this.setTitle("Inserir Propaganda em Projeto");
            return;
        }                    
        publi = p;
        
      dataInicio.setText(Principal.cf.converteDataString(p.getDataInicio()));
      dataFim.setText(Principal.cf.converteDataString(p.getDataFinal()));
      agencia.setText(p.getAgencia());
      tarifa.setText(String.valueOf(p.getTarifa()));
      
      this.setTitle("Editar Propaganda em Projeto");
    }   
    
    public void clean(){
          dataInicio.setText("");
          dataFim.setText("");
          agencia.setText("");
          tarifa.setText("");
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
        
        if(origem == btnOK && proj != null){
            if(publi == null){
                try{                                      
                    //System.out.println(proj.getNumero() + " " + proj.getNome());
                    Principal.cf.inserirPropaganda(agencia.getText(), dataFim.getText(), dataInicio.getText(), proj.getNumero(), Float.valueOf(tarifa.getText()));
                    
                    JOptionPane.showMessageDialog(this,"Cadastro realizado com sucesso!", "Atenção", JOptionPane.INFORMATION_MESSAGE);                                                                        
                    
                    FormProjetosPropagandas.setDataTableProjetosPropagandas(proj);
                    this.dispose();
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(this,"Erro: " + ex, "Atenção", JOptionPane.ERROR_MESSAGE);                                                                                            
                }               
            } else{
               try{
                    //System.out.println(proj.getNumero() + " " + proj.getNome());
                    Principal.cf.atualizarPropaganda(publi.getNumero(), agencia.getText(), dataFim.getText(), dataInicio.getText(), proj.getNumero(), Float.valueOf(tarifa.getText()));
                    
                    JOptionPane.showMessageDialog(this,"Cadastro realizado com sucesso!", "Atenção", JOptionPane.INFORMATION_MESSAGE);                                                                        
                    
                    FormProjetosPropagandas.setDataTableProjetosPropagandas(proj);
                    this.dispose();
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(this,"Erro: " + ex, "Atenção", JOptionPane.ERROR_MESSAGE);                                                                                            
                }                 
            }                                     
        }

        if (origem == btnCancelar)
            this.dispose();
    }
}

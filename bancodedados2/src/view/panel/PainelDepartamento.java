/*
 * Painel Departamentos
 * ---------------------------------------
 * 
 * - Esta classe representa a visao de dentro do programa
 * - Eh uma extensao de JPanel.
 * - Mostra todos departamentos.
 * 
 */
package view.panel;

import Model.Departamento;
import Model.Empregado;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import view.Principal;
import view.formularios.FormDepartamento;
import view.formularios.FormDepartamentoLocal;
import view.formularios.FormDepartamentoProjetos;

public final class PainelDepartamento extends JPanel  implements ActionListener {	
    
    private static final long serialVersionUID = 1L;
    private JButton novo = new JButton("Novo");
    private JButton editar  = new JButton("Editar");
    private JButton excluir  = new JButton("Excluir");  
    private JButton projetos  = new JButton("Projetos");
    private JButton localizacao  = new JButton("Localização");
    private static JLabel contaRegistros;
    
    public static JTable tabela;
    public static DefaultTableModel modelo;
    public static String[] colunas;
    public static FormDepartamento formDepartmanetos = null;
    public static FormDepartamentoProjetos formDepartmanetosProjetos = null;
    public static FormDepartamentoLocal formDepartmanetosLocalizacao = null;

    public PainelDepartamento(){			
        
        tabela = new JTable(){
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int rowIndex, int vColIndex){
                    return false;
            }
        };

        colunas = new String [] {"Nome Departamento", "Numero", "Gerente Nome", "Data Inicio", "Ssn", "Sexo",
                                 "Endereco", "Salario", "Data de Nascimento", "Departamento", "Dno", "Supervisor", "SuperSnn"};      
  
        PainelDepartamento.setDataTable();
        
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setGridColor(new Color(220,220,220));     
        
        PainelDepartamento.setSizeColumn();
        
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JPanel botoes = new JPanel();
        botoes.setLayout(new BoxLayout(botoes, BoxLayout.X_AXIS));
        botoes.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(64, 64, 64)));

        JLabel imagem = new JLabel();
        imagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/busca.png")));

        botoes.add(Box.createHorizontalStrut(5));
        botoes.add(novo);
        botoes.add(Box.createHorizontalStrut(3));
        botoes.add(editar);
        botoes.add(Box.createHorizontalStrut(3));
        botoes.add(excluir);
        botoes.add(Box.createHorizontalStrut(3));
        botoes.add(projetos);         
        botoes.add(Box.createHorizontalStrut(3));
        botoes.add(localizacao);        
        botoes.add(Box.createVerticalStrut(45));
        botoes.add(Box.createHorizontalGlue());
        botoes.add(contaRegistros);
        botoes.add(Box.createHorizontalStrut(7));

        novo.addActionListener(this);
        editar.addActionListener(this);
        excluir.addActionListener(this);        
        projetos.addActionListener(this);
        localizacao.addActionListener(this);
        
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.DARK_GRAY));
        this.add(botoes, BorderLayout.SOUTH);
        this.add(scrollPane, BorderLayout.CENTER);		
    }

    @Override
    public void actionPerformed(ActionEvent e){
        Object origem = e.getSource();	
        int item = tabela.getSelectedRow();
                
        if (origem == novo)
               formDepartamentos(null);
        else if (origem == editar && (item != -1)){            
            String numero = (String) tabela.getValueAt(item, tabela.getColumnModel().getColumnIndex("Numero"));
            Departamento dep;
            try {
                dep = Principal.cf.getDepartamentoByNumero(Integer.parseInt(numero));
                formDepartamentos(dep);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,ex, "Atenção", JOptionPane.ERROR_MESSAGE);
            }
            
        }         
        else if (origem == excluir  && (item != -1)) {            
            String numero = (String) tabela.getValueAt(item, tabela.getColumnModel().getColumnIndex("Numero"));
            String nome = (String) tabela.getValueAt(item, tabela.getColumnModel().getColumnIndex("Nome Departamento"));

            int opcao = JOptionPane.showConfirmDialog(this,"Deseja remover departamento "+nome+"?","Atenção!",JOptionPane.YES_NO_OPTION);    
            
            if(opcao == JOptionPane.YES_OPTION) {
                try {
                    Principal.cf.apagarDepartamento(Integer.parseInt(numero));
                    modelo.removeRow(item);
                    contaRegistros.setText(tabela.getRowCount() + " registro(s) encontrado(s)");                    
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(this,ex, "Atenção", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else if (origem == projetos && (item != -1)) {
            String numero = (String) tabela.getValueAt(item, tabela.getColumnModel().getColumnIndex("Numero"));
            Departamento dep;
            try {
                dep = Principal.cf.getDepartamentoByNumero(Integer.parseInt(numero));
                formProjetos(dep);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,ex, "Atenção", JOptionPane.ERROR_MESSAGE);
            }
            
        }
        else if (origem == localizacao && (item != -1)) {
            String numero = (String) tabela.getValueAt(item, tabela.getColumnModel().getColumnIndex("Numero"));
            Departamento dep;
            try {
                dep = Principal.cf.getDepartamentoByNumero(Integer.parseInt(numero));
                formLocalizacao(dep);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,ex, "Atenção", JOptionPane.ERROR_MESSAGE);
            }            
        }                
    }   
    
    public static void setSizeColumn(){
        tabela.getTableHeader().getColumnModel().getColumn(0).setMinWidth(250);
        tabela.getTableHeader().getColumnModel().getColumn(1).setMinWidth(50);
        tabela.getTableHeader().getColumnModel().getColumn(2).setMinWidth(250);
        tabela.getTableHeader().getColumnModel().getColumn(3).setMinWidth(100);
        tabela.getTableHeader().getColumnModel().getColumn(4).setMinWidth(50);
        tabela.getTableHeader().getColumnModel().getColumn(5).setMinWidth(50);
        tabela.getTableHeader().getColumnModel().getColumn(6).setMinWidth(250);
        tabela.getTableHeader().getColumnModel().getColumn(7).setMinWidth(50);
        tabela.getTableHeader().getColumnModel().getColumn(8).setMinWidth(250);
        tabela.getTableHeader().getColumnModel().getColumn(9).setMinWidth(250);        
        tabela.getTableHeader().getColumnModel().getColumn(10).setMinWidth(250);        
        tabela.getTableHeader().getColumnModel().getColumn(11).setMinWidth(250);        
    }
    
    public static void setDataTable(){
        String[][] dados = null;
        /*
        try {        
            dados = Principal.cf.getDepartamentosTable(Principal.cf.listarDepartamentos());
        } catch (Exception ex) {
            System.err.println("Erro Painel Departamentos: " + ex);
        }*/
        
        PainelDepartamento.modelo = new DefaultTableModel(dados, PainelDepartamento.colunas);
        PainelDepartamento.tabela.setModel(PainelDepartamento.modelo);                    
        PainelDepartamento.setSizeColumn();
        PainelDepartamento.contaRegistros = new JLabel();
        PainelDepartamento.contaRegistros.setText(tabela.getRowCount() + " registro(s) encontrado(s)");
    }
    
    public static void formDepartamentos(Departamento d){
        if(formDepartmanetos == null)
            formDepartmanetos = new FormDepartamento(d);
        else{
            try {
                formDepartmanetos.editar(d);
            } catch (Exception ex) {
                 JOptionPane.showMessageDialog(null,"Erro Departamento: " + ex, "Atenção", JOptionPane.ERROR_MESSAGE);                                                                        
            }
            formDepartmanetos.setVisible(true);
        }
    }    
    
    public static void formProjetos(Departamento d){
        if(formDepartmanetosProjetos == null)
            formDepartmanetosProjetos = new FormDepartamentoProjetos(d);
        else{
            try {
                formDepartmanetosProjetos.editar(d);
            } catch (Exception ex) {
                 JOptionPane.showMessageDialog(null,"Erro Departamento: " + ex, "Atenção", JOptionPane.ERROR_MESSAGE);                                                                        
            }
            formDepartmanetosProjetos.setVisible(true);
        }
    } 
    
    public static void formLocalizacao(Departamento d){
        if(formDepartmanetosLocalizacao == null)
            formDepartmanetosLocalizacao = new FormDepartamentoLocal(d);
        else{
            try {
                formDepartmanetosLocalizacao.editar(d);
            } catch (Exception ex) {
                 JOptionPane.showMessageDialog(null,"Erro Departamento: " + ex, "Atenção", JOptionPane.ERROR_MESSAGE);                                                                        
            }
            formDepartmanetosLocalizacao.setVisible(true);
        }
    }    
}

/*
 * PainelDependentes
 * ---------------------------------------
 * 
 * - Esta classe representa a visao de dentro do programa
 * - Eh uma extensao de JPanel.
 * - Mostra todos dependentes, faz pesquisa, edita, insere, exclui.
 * 
 */
package view;

import Model.Dependente;
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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import view.formularios.FormDependente;
import view.formularios.FormFuncionario;

public final class PainelDependentes extends JPanel  implements ActionListener {	
    
    private static final long serialVersionUID = 1L;
    private static JButton novo;
    private static JButton editar;
    private static JButton excluir;    
    private static JTextField txtBusca;
    private static JButton btnBusca;
    private static JLabel contaRegistros =  new JLabel();
    private static JComboBox empregados;
            
    public static JTable tabela;
    public static DefaultTableModel modelo;
    public static String[] colunas;
    
    public PainelDependentes(){			
        
        tabela = new JTable(){
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int rowIndex, int vColIndex){
                    return false;
            }
        };

        colunas = new String [] { "Nome", "Sexo", "Data de Nascimento", "Parentesco", "Nome Empregado", "Ssn", "Departamento"};  
        
        this.setDataTable();
        
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setGridColor(new Color(220,220,220));        
        this.setSizeColumn();
        
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JPanel botoes = new JPanel();
        botoes.setLayout(new BoxLayout(botoes, BoxLayout.X_AXIS));
        botoes.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(64, 64, 64)));

        JLabel imagem = new JLabel();
        imagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/busca.png")));

        novo = new JButton("Novo");
        editar = new JButton("Editar");
        excluir = new JButton("Excluir");
        
        Empregado em = new Empregado();
        em.setNome("Todos Empregados");
        
        try {
            empregados = new JComboBox(Principal.cf.listarEmpregados());
        } catch (Exception ex) {
            empregados = new JComboBox();
        }
        
        empregados.addItem(em);
        empregados.setSelectedItem(em);
        empregados.setPreferredSize(new Dimension(320, 24));
        empregados.setMaximumSize(new Dimension(320, 24));

        //contaRegistros = new JLabel();
        //contaRegistros.setText(tabela.getRowCount() + " registro(s) encontrado(s)");

        botoes.add(Box.createHorizontalStrut(5));
        botoes.add(novo);
        botoes.add(Box.createHorizontalStrut(3));
        botoes.add(editar);
        botoes.add(Box.createHorizontalStrut(3));
        botoes.add(excluir);
        botoes.add(Box.createVerticalStrut(45));
        botoes.add(Box.createHorizontalGlue());
        botoes.add(contaRegistros);
        botoes.add(Box.createHorizontalGlue());
        botoes.add(empregados);
        botoes.add(Box.createHorizontalStrut(7));

        novo.addActionListener(this);
        editar.addActionListener(this);
        excluir.addActionListener(this);        

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
                new FormDependente(null, false);
        else if (origem == editar && (item != -1)){
            
            String essn = (String) tabela.getValueAt(item, tabela.getColumnModel().getColumnIndex("Ssn"));
            String nome = (String) tabela.getValueAt(item, tabela.getColumnModel().getColumnIndex("Nome"));
            Dependente dep = null;
            try {
                dep = Principal.cf.buscaDependenteEssnNome(essn, nome);
                new FormDependente(dep, true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,ex, "Atenção", JOptionPane.ERROR_MESSAGE);
            }            
            
        }         
        else if (origem == excluir  && (item != -1)) {            
            String essn = (String) tabela.getValueAt(item, tabela.getColumnModel().getColumnIndex("Ssn"));
            String nome = (String) tabela.getValueAt(item, tabela.getColumnModel().getColumnIndex("Nome"));
            String nomeEmpregado = (String) tabela.getValueAt(item, tabela.getColumnModel().getColumnIndex("Nome Empregado"));
            
            int opcao = JOptionPane.showConfirmDialog(this,"Deseja remover dependente "+nome+" do empregado "+nomeEmpregado+" ("+essn.trim()+")?","Atenção!",JOptionPane.YES_NO_OPTION);    
            
            if(opcao == JOptionPane.YES_OPTION) {
                try {
                    Principal.cf.apagarDependente(nome, essn);
                    modelo.removeRow(item);
                    contaRegistros.setText(tabela.getRowCount() + " registro(s) encontrado(s)");                    
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(this,ex, "Atenção", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }
        /*
        else if (origem == btnBusca)
        {
                String filtro = txtBusca.getText().trim().toLowerCase();
                modelo = new DefaultTableModel(ControleAluno.vetorAlunos(filtro),
                                colunas);
                tabela.setModel(modelo);
                contaRegistros.setText(tabela.getRowCount()
                                + " registro(s) encontrado(s)");
                                * setSizeColumn()
        }*/
        
    }
    
    public static void setSizeColumn(){
        tabela.getTableHeader().getColumnModel().getColumn(0).setMinWidth(350);
        tabela.getTableHeader().getColumnModel().getColumn(1).setMinWidth(50);
        tabela.getTableHeader().getColumnModel().getColumn(2).setMinWidth(150);
        tabela.getTableHeader().getColumnModel().getColumn(3).setMinWidth(150);
        tabela.getTableHeader().getColumnModel().getColumn(4).setMinWidth(350);
        tabela.getTableHeader().getColumnModel().getColumn(5).setMinWidth(100);
        tabela.getTableHeader().getColumnModel().getColumn(6).setMinWidth(100);
    }
    
    public static void setDataTable(){
        String[][] dados = null;        

        try {
            dados = Principal.cf.getDependentesTable(Principal.cf.listarDependentes());
        } catch (Exception ex) {
            System.err.println("Erro listar dependentes: " + ex);
        }

        PainelDependentes.modelo = new DefaultTableModel(dados, PainelDependentes.colunas);
        PainelDependentes.tabela.setModel(PainelDependentes.modelo);                    
        PainelDependentes.contaRegistros.setText(PainelDependentes.tabela.getRowCount() + " registro(s) encontrado(s)");                    
        PainelDependentes.setSizeColumn();        
    }
}

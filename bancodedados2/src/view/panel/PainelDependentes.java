/*
 * PainelDependentes
 * ---------------------------------------
 * 
 * - Esta classe representa a visao de dentro do programa
 * - Eh uma extensao de JPanel.
 * - Mostra todos dependentes, faz pesquisa, edita, insere, exclui.
 * 
 */
package view.panel;

import Model.Dependente;
import Model.Empregado;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import view.Principal;
import view.ViewObjectPool;
import view.formularios.FormDependente;

public final class PainelDependentes extends JPanel  implements ActionListener {	
    
    private static final long serialVersionUID = 1L;
    private JButton novo  = new JButton("Novo");
    private JButton editar = new JButton("Editar");
    private JButton excluir = new JButton("Excluir"); 
    private JComboBox empregadosDependentes = null;
    private Empregado empDependentes = new Empregado();
    
    private static JLabel contaRegistros =  new JLabel();            
    public static JTable tabela;
    public static DefaultTableModel modelo;
    public static String[] colunas;
    public static FormDependente formDependente = null;
    
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

        empDependentes.setNome("Todos Empregados");
        empDependentes.setSsn("-1");
        
        Vector<Empregado> values = new Vector((Vector<Empregado>) ViewObjectPool.get("todosEmpregados"));
        empregadosDependentes = new JComboBox(values);
        empregadosDependentes.addItem(empDependentes);
        empregadosDependentes.setSelectedItem(empDependentes);
        empregadosDependentes.setPreferredSize(new Dimension(320, 24));
        empregadosDependentes.setMaximumSize(new Dimension(320, 24));

        botoes = nivelView(botoes);        
        
        novo.addActionListener(this);
        editar.addActionListener(this);
        excluir.addActionListener(this);        
        empregadosDependentes.addActionListener(this);
        
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.DARK_GRAY));         
        this.add(botoes, BorderLayout.SOUTH);
        this.add(scrollPane, BorderLayout.CENTER);		
    }
    
    public JPanel nivelView(JPanel botoes){                
        botoes.add(Box.createHorizontalStrut(5));
        
        if(Principal.user.getTipoLogin() == 2){
            botoes.add(novo);
            botoes.add(Box.createHorizontalStrut(3));
            botoes.add(editar);
            botoes.add(Box.createHorizontalStrut(3));
            botoes.add(excluir); 
            botoes.add(Box.createVerticalStrut(45));
            botoes.add(Box.createHorizontalGlue());            
            botoes.add(contaRegistros);
            botoes.add(Box.createHorizontalGlue());
            botoes.add(empregadosDependentes);
            botoes.add(Box.createHorizontalStrut(7));        
        } else if(Principal.user.getTipoLogin() == 0) {
            botoes.add(Box.createHorizontalGlue());        
            botoes.add(contaRegistros);                                 
            botoes.add(Box.createVerticalStrut(45));
        }                
        
        return botoes;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        Object origem = e.getSource();	
        int item = tabela.getSelectedRow();
                
        if (origem == novo)
                form_dependente(null);
        else if (origem == editar && (item != -1)){
            
            String essn = (String) tabela.getValueAt(item, tabela.getColumnModel().getColumnIndex("Ssn"));
            String nome = (String) tabela.getValueAt(item, tabela.getColumnModel().getColumnIndex("Nome"));
            Dependente dep = null;
            try {
                dep = Principal.cf.buscaDependenteEssnNome(essn, nome);
                form_dependente(dep);
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
                    FormDependente.updatePool();
                    contaRegistros.setText(tabela.getRowCount() + " registro(s) encontrado(s)");                                        
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(this,ex, "Atenção", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }
        else if (origem == empregadosDependentes){
            Empregado emp = (Empregado) empregadosDependentes.getSelectedItem();
            
            if(!emp.getSsn().equals("-1")){
                String[][] dados = null;        

                try {
                    dados = Principal.cf.getDependentesTable(Principal.cf.DependenteBuscaByEssn(emp.getSsn()));
                } catch (Exception ex) {
                    System.err.println("Erro listar dependentes: " + ex);
                }

                PainelDependentes.modelo = new DefaultTableModel(dados, PainelDependentes.colunas);
                PainelDependentes.tabela.setModel(PainelDependentes.modelo);                    
                PainelDependentes.contaRegistros.setText(PainelDependentes.tabela.getRowCount() + " registro(s) encontrado(s)");                    
                PainelDependentes.setSizeColumn();     
            } else
                PainelDependentes.setDataTable();
        }
        
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
            Vector<Dependente> values = new Vector((Vector<Dependente>) ViewObjectPool.get("todosDependentes"));
            dados = Principal.cf.getDependentesTable(values);
        } catch (Exception ex) {
            System.err.println("Erro listar dependentes: " + ex);
        }        
        
        PainelDependentes.modelo = new DefaultTableModel(dados, PainelDependentes.colunas);
        PainelDependentes.tabela.setModel(PainelDependentes.modelo);                    
        PainelDependentes.contaRegistros.setText(PainelDependentes.tabela.getRowCount() + " registro(s) encontrado(s)");                    
        PainelDependentes.setSizeColumn();        
    }
    
    public static void form_dependente(Dependente e){
        if(formDependente == null)
            formDependente = new FormDependente(e);
        else{
            try {
                formDependente.editar(e);
            } catch (Exception ex) {
                 JOptionPane.showMessageDialog(null,"Erro Empregado: " + ex, "Atenção", JOptionPane.ERROR_MESSAGE);                                                                        
            }
            formDependente.setVisible(true);
        }
    }
    
    
    public Dependente dadosItem(int item){                
        String essn = (String) tabela.getValueAt(item, tabela.getColumnModel().getColumnIndex("Ssn"));
        String nome = (String) tabela.getValueAt(item, tabela.getColumnModel().getColumnIndex("Nome"));
        String sexo = (String) tabela.getValueAt(item, tabela.getColumnModel().getColumnIndex("Sexo"));
        String data = (String) tabela.getValueAt(item, tabela.getColumnModel().getColumnIndex("Data de Nascimento"));
        String parentesco = (String) tabela.getValueAt(item, tabela.getColumnModel().getColumnIndex("Parentesco"));
        String nomeEmp = (String) tabela.getValueAt(item, tabela.getColumnModel().getColumnIndex("Nome Empregado"));
        
        Dependente dep = new Dependente();
        dep.setNome(nome);
        dep.setParentesco(parentesco);
        dep.setSexo(sexo);
        dep.setDataNascimento(Principal.cf.coverteStringData(data));
        
        Empregado e = new Empregado();
        e.setNome(nomeEmp);
        e.setSsn(essn);
        
        dep.setEssn(e);
        return dep;
    }
}

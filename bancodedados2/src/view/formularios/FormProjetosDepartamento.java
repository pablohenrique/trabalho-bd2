package view.formularios;

import Model.Departamento;
import Model.Projeto;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import view.Principal;
import view.ViewObjectPool;

public class FormProjetosDepartamento extends JDialog implements ActionListener
{
    private static final long serialVersionUID = 1L;    
        
    private static Departamento dep;
    private JComboBox departamento;
    private JButton btnOK = new JButton("OK");
    
    private static JTable tabela;
    private static DefaultTableModel modelo;
    private static String[] colunas;
    private static Projeto proj;    
    private Vector<Departamento> valuesDepartamento;

    public FormProjetosDepartamento(){
        super(Principal.janela,"Empregado que trabalha mais dado um Departamento", true);
        
        btnOK.setPreferredSize(new Dimension(100, 25));             
        
        btnOK.addActionListener(this);
        
        try {
            valuesDepartamento = new Vector((Vector<Departamento>) ViewObjectPool.get("todosDepartamento"));            
            departamento = new JComboBox(valuesDepartamento); 
        } catch (Exception ex) {
            departamento = new JComboBox();  
        }
        
        JLabel busca = new JLabel("Departamento: ");        
        
        JPanel grid = new JPanel();
        grid.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        grid.setLayout(new GridLayout(1, 2, 5, 5));
        grid.add(busca);
        grid.add(departamento);
        
        
        busca.setPreferredSize(new Dimension(250, 25));
        departamento.setPreferredSize(new Dimension(250, 25));
        departamento.setMaximumSize(new Dimension(250, 25));
        departamento.addActionListener(this);

        
        tabela = new JTable(){
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int rowIndex, int vColIndex){
                    return false;
            }
        };

        colunas = new String [] { "Nome", "Ssn", "Sexo", "Endereco", "Salario", "Data de Nascimento",
                                           "Departamento", "Dno", "Supervisor", "SuperSnn"};  
        
        dep = (Departamento) departamento.getSelectedItem(); 
        FormProjetosDepartamento.setDataTableDepartamentoProjetos();
        
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setGridColor(new Color(220,220,220));        
        
        FormProjetosDepartamento.setSizeColumnDepartmentoProjetos();
        
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());        
            
        JPanel botoes = new JPanel();
        
        botoes.add(btnOK);
        botoes.add(Box.createVerticalStrut(45));
        botoes.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,Color.LIGHT_GRAY));  
                
        JPanel painel = new JPanel();
        painel.add(grid);              
        
        this.add(painel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);             
        this.add(botoes, BorderLayout.SOUTH);
        this.setSize(550, 440);
        this.setLocation((java.awt.Toolkit.getDefaultToolkit()
                                        .getScreenSize().width / 2)
                                        - (this.getWidth() / 2), (java.awt.Toolkit
                                        .getDefaultToolkit().getScreenSize().height / 2)
                                        - (this.getHeight() / 2));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
 
        
    @Override
    public void actionPerformed(ActionEvent e){
        
        if (e.getSource() == departamento){
            Departamento d = (Departamento) departamento.getSelectedItem();  
            dep = d;
            System.out.println(dep.getNumero() + " " + dep.getNumero());
            FormProjetosDepartamento.setDataTableDepartamentoProjetos();
            dep = null;
        }
        
        if (e.getSource() == btnOK)
                this.dispose();
    }

    public static void setSizeColumnDepartmentoProjetos(){
        tabela.getTableHeader().getColumnModel().getColumn(0).setMinWidth(250);
        tabela.getTableHeader().getColumnModel().getColumn(1).setMinWidth(35);
        tabela.getTableHeader().getColumnModel().getColumn(2).setMinWidth(100);
        tabela.getTableHeader().getColumnModel().getColumn(3).setMinWidth(250);
        tabela.getTableHeader().getColumnModel().getColumn(4).setMinWidth(30);
        tabela.getTableHeader().getColumnModel().getColumn(5).setMinWidth(100);
        tabela.getTableHeader().getColumnModel().getColumn(6).setMinWidth(250);
        tabela.getTableHeader().getColumnModel().getColumn(7).setMinWidth(35);
        tabela.getTableHeader().getColumnModel().getColumn(8).setMinWidth(250);
        tabela.getTableHeader().getColumnModel().getColumn(9).setMinWidth(35);      
    }
    
    public static void setDataTableDepartamentoProjetos(){
        String[][] dados = null;
        try {
            dados = Principal.cf.getEmpregadosTable(Principal.cf.buscarEmpregadoTrabalhaMaisDepartamento(dep.getNumero()));        
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex, "Atenção", JOptionPane.ERROR_MESSAGE);
        }
        FormProjetosDepartamento.setDados(dados);
    }
    
    public static void setDados(String[][] dados){
        FormProjetosDepartamento.modelo = new DefaultTableModel(dados, FormProjetosDepartamento.colunas);
        FormProjetosDepartamento.tabela.setModel(FormProjetosDepartamento.modelo);                    
        FormProjetosDepartamento.setSizeColumnDepartmentoProjetos();              
    }
       
    public void execute() {
        dep = null;     
        valuesDepartamento = (Vector<Departamento>) ViewObjectPool.get("todosDepartamento");            
        departamento.setModel(new javax.swing.DefaultComboBoxModel(valuesDepartamento));          
        this.setVisible(true);        
    }
}

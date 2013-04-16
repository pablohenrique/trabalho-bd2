package view.formularios;

import Model.Departamento;
import Model.Empregado;
import Model.Projeto;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import view.Principal;
import view.ViewObjectPool;

public class FormHoras extends JDialog implements ActionListener
{
    private static final long serialVersionUID = 1L;    
        
    private static Departamento dep;
    private JComboBox tipo = new JComboBox();
    private JButton btnOK = new JButton("OK");
    
    private static JTable tabela;
    private static DefaultTableModel modelo;
    private static String[] colunas;
    private static Projeto proj;    
    private Vector<Departamento> valuesDepartamento;
    
    public FormHoras(){
        super(Principal.janela,"Encontre os empregados que trabalhavam mais/menos horas", true);
        
        btnOK.setPreferredSize(new Dimension(100, 25));             
        
        btnOK.addActionListener(this);
        
        tipo.addItem("Mais Horas");
        tipo.addItem("Menos Horas");
        
        JLabel busca = new JLabel("Busca: ");        
        
        JPanel grid = new JPanel();
        grid.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        grid.setLayout(new GridLayout(1, 2, 5, 5));
        grid.add(busca);
        grid.add(tipo);
        
        busca.setPreferredSize(new Dimension(250, 25));
        tipo.setPreferredSize(new Dimension(250, 25));
        tipo.setMaximumSize(new Dimension(250, 25));
        tipo.addActionListener(this);

        tabela = new JTable(){
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int rowIndex, int vColIndex){
                    return false;
            }
        };

        colunas = new String [] { "Nome", "Ssn", "Sexo", "Endereco", "Salario", "Data de Nascimento",
                                           "Departamento", "Dno", "Supervisor", "SuperSnn"};  
        
        FormHoras.setDataTableMaisHoras();
        
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setGridColor(new Color(220,220,220));        
        
        FormHoras.setSizeColumnFormHoras();
        
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
        String[][] dados = null;
        
        if (e.getSource() == tipo){
            if(tipo.getSelectedIndex() == 0){
                System.out.println("mais Horas");
                FormHoras.setDataTableMaisHoras();
            }else if(tipo.getSelectedIndex() == 1){
                System.out.println("menos Horas");
                dados = Principal.cf.getEmpregadosTable(Principal.cf.empregadoMenosHoras());                                    
                FormHoras.setDados(dados);
            }            
        }
        
        if (e.getSource() == btnOK)
                this.dispose();
    }

    public static void setSizeColumnFormHoras(){
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
    
    public static void setDataTableMaisHoras(){
        String[][] dados = null;
         System.out.println("imprimindo mais horas Horas");
         dados = Principal.cf.getEmpregadosTable(Principal.cf.empregadoMaisHoras());
        FormHoras.setDados(dados);
    }
    
    public static void setDados(String[][] dados){
        FormHoras.modelo = new DefaultTableModel(dados, FormHoras.colunas);
        FormHoras.tabela.setModel(FormHoras.modelo);                    
        FormHoras.setSizeColumnFormHoras();              
    }
       
    public void execute() {
        dep = null;     
        valuesDepartamento = (Vector<Departamento>) ViewObjectPool.get("todosDepartamento");            
        tipo.setModel(new javax.swing.DefaultComboBoxModel(valuesDepartamento));          
        
        this.setVisible(true);        
    }
}

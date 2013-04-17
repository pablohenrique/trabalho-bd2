package view.formularios;

import Model.Empregado;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import view.Principal;

public class FormFuncionarioProjetos extends JDialog implements ActionListener
{
    private static final long serialVersionUID = 1L;    
    
    private static JButton btnOK = new JButton("OK");
    
    private static JTable tabela;
    private static DefaultTableModel modelo;
    private static String[] colunas;
    private static Empregado emp;
    private JLabel nomel = new JLabel();
    private JLabel ssn = new JLabel();
    private JLabel dep = new JLabel();
    private JLabel sssn = new JLabel();
    
    public FormFuncionarioProjetos(Empregado e){
        super(Principal.janela,"Projeto(s) do empregado", true);
        
        btnOK.setPreferredSize(new Dimension(100, 25));             
        
        btnOK.addActionListener(this);
        
        JLabel nome = new JLabel("Nome: ");        
        
        JPanel grid = new JPanel();
        grid.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        grid.setLayout(new GridLayout(4, 2, 5, 5));
        grid.add(nome);
        grid.add(nomel);
        grid.add(new JLabel("Seguridade Social: "));
        grid.add(ssn);     
        grid.add(new JLabel("Departamento: "));
        grid.add(dep);
        grid.add(new JLabel("Supervisor: "));
        grid.add(sssn);
        
        nome.setPreferredSize(new Dimension(250, 25));
        
        JPanel botoes = new JPanel();
        botoes.setLayout(new BoxLayout(botoes, BoxLayout.X_AXIS));
        botoes.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,Color.LIGHT_GRAY));
        
        botoes.add(Box.createHorizontalStrut(5));
        botoes.add(Box.createVerticalStrut(45));
        botoes.add(btnOK);   
        botoes.add(Box.createHorizontalStrut(5));
        
                
        tabela = new JTable(){
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int rowIndex, int vColIndex){
                    return false;
            }
        };

        colunas = new String [] { "Nome Projeto", "Numero Projeto", "Localizacao", "Departamento", "Numero Departamento"};  
       
        this.setEmpregado(e);        
                
        this.setDataTableFuncionariosProjetos();
        
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setGridColor(new Color(220,220,220));        
        
        this.setSizeColumnFuncionariosProjetos();
        
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());        
        
        JPanel painel = new JPanel();
        painel.add(grid);              
        
        this.add(painel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);        
        this.add(botoes, BorderLayout.SOUTH);
        this.setSize(550, 610);
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
        if (e.getSource() == btnOK)
                this.dispose();
    }
    
    public static void setSizeColumnFuncionariosProjetos(){
        tabela.getTableHeader().getColumnModel().getColumn(0).setMinWidth(250);
        tabela.getTableHeader().getColumnModel().getColumn(1).setMinWidth(80);
        tabela.getTableHeader().getColumnModel().getColumn(2).setMinWidth(200);
        tabela.getTableHeader().getColumnModel().getColumn(3).setMinWidth(200);        
        tabela.getTableHeader().getColumnModel().getColumn(4).setMinWidth(200);        
    }
    
    public static void setDataTableFuncionariosProjetos(){
        String[][] dados = null;    
        
        try {
            dados = Principal.cf.getProjetoBySsn(Principal.cf.listarProjetosByEmp(emp.getSsn()));                        
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex, "Atenção", JOptionPane.ERROR_MESSAGE);
        }
        
        FormFuncionarioProjetos.modelo = new DefaultTableModel(dados, FormFuncionarioProjetos.colunas);
        FormFuncionarioProjetos.tabela.setModel(FormFuncionarioProjetos.modelo);                    
        FormFuncionarioProjetos.setSizeColumnFuncionariosProjetos();        
    }    

    public void setEmpregado(Empregado e) {
        emp = e;
        sssn.setText(e.getSuperSsn().getNome());
        nomel.setText(e.getNome());
        ssn.setText(e.getSsn());
        dep.setText(e.getDepartamento().getNome());
        FormFuncionarioProjetos.setDataTableFuncionariosProjetos();
    }
}

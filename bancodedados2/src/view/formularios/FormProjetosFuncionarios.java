package view.formularios;

import Model.Projeto;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import view.Principal;

public class FormProjetosFuncionarios extends JDialog implements ActionListener
{
    private static final long serialVersionUID = 1L;    
    
    private JButton novo  = new JButton("Add Empregado");
    private JButton editarHora  = new JButton("Editar Hora");
    private JButton excluir = new JButton("Excluir");   
    private JLabel nomeProjeto = new JLabel();
    private JLabel localProjeto = new JLabel();
    private JLabel depProjeto = new JLabel();

    private JButton btnOK = new JButton("OK");
    private JButton btnCancelar  = new JButton("Cancelar");
    
    private static JTable tabela;
    private static DefaultTableModel modelo;
    private static String[] colunas;

    private Projeto proj;    
    
    public FormProjetosFuncionarios(Projeto p)
    {
        super(Principal.janela,"Todos Empregados do Projeto", true);
                               
        btnOK.setPreferredSize(new Dimension(100, 25));
        btnCancelar.setPreferredSize(new Dimension(100, 25));
        proj = p;
        
        btnOK.addActionListener(this);
        btnCancelar.addActionListener(this);

        JLabel nome = new JLabel("Nome: ");
         
        
        JPanel grid = new JPanel();
        grid.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        grid.setLayout(new GridLayout(3, 2, 5, 5));
        grid.add(nome);
        grid.add(nomeProjeto);      
        grid.add(new JLabel("Localizacao Projeto: "));
        grid.add(localProjeto);        
        grid.add(new JLabel("Departamento: "));
        grid.add(depProjeto);
        
        nome.setPreferredSize(new Dimension(250, 25));
        
        JPanel botoes = new JPanel();
        botoes.setLayout(new BoxLayout(botoes, BoxLayout.X_AXIS));
        botoes.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,Color.LIGHT_GRAY));                

        botoes.add(Box.createHorizontalStrut(5));
        botoes.add(novo);
        botoes.add(Box.createHorizontalStrut(3));
        botoes.add(editarHora);
        botoes.add(Box.createHorizontalStrut(3));
        botoes.add(excluir);
        botoes.add(Box.createVerticalStrut(45));
        botoes.add(Box.createHorizontalStrut(3));
        botoes.add(btnOK);
        botoes.add(Box.createHorizontalStrut(3));
        botoes.add(btnCancelar);        
        botoes.add(Box.createHorizontalStrut(5));
        
        novo.addActionListener(this);
                
        tabela = new JTable(){
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int rowIndex, int vColIndex){
                    return false;
            }
        };

        colunas = new String [] { "Nome", "Ssn", "Departamento", "Dno", "Supervisor", "SuperSnn"};  
        
        this.setDataTableFuncionariosProjetos();
        
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setGridColor(new Color(220,220,220));        
        
        this.setSizeColumnFuncionariosProjetos();
        
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());        
        
        editar(proj);
        
        JPanel painel = new JPanel();
        painel.add(grid);              
        
        this.add(painel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);        
        this.add(botoes, BorderLayout.SOUTH);
        this.setSize(750, 610);
        this.setLocation((java.awt.Toolkit.getDefaultToolkit()
                                        .getScreenSize().width / 2)
                                        - (this.getWidth() / 2), (java.awt.Toolkit
                                        .getDefaultToolkit().getScreenSize().height / 2)
                                        - (this.getHeight() / 2));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
 
    public void editar(Projeto p){
        proj = p;
        
        nomeProjeto.setText(p.getNome());
        localProjeto.setText(p.getLocalizacao());
        depProjeto.setText(p.getDepartamento().getNome());
        
        FormProjetosFuncionarios.setDataTableFuncionariosProjetos();
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object origem = e.getSource();

        if(origem == novo)
            //new FormFuncionarioProjetosForm(null, proj, false);        
        if(origem == btnOK)
        {
                                     
        }

        if (origem == btnCancelar)
        {
                this.dispose();
        } 
    }

    public static void setSizeColumnFuncionariosProjetos(){
        tabela.getTableHeader().getColumnModel().getColumn(0).setMinWidth(250);
        tabela.getTableHeader().getColumnModel().getColumn(1).setMinWidth(80);
        tabela.getTableHeader().getColumnModel().getColumn(2).setMinWidth(250);
        tabela.getTableHeader().getColumnModel().getColumn(3).setMinWidth(50);        
        tabela.getTableHeader().getColumnModel().getColumn(4).setMinWidth(250);        
        tabela.getTableHeader().getColumnModel().getColumn(5).setMinWidth(50);        
    }
    
    public static void setDataTableFuncionariosProjetos(){
        String[][] dados = null;
        
        //Principal.cf.getEmpregadosTable(Principal.cf.listar(proj));        
        FormProjetosFuncionarios.modelo = new DefaultTableModel(null, FormProjetosFuncionarios.colunas);
        FormProjetosFuncionarios.tabela.setModel(FormProjetosFuncionarios.modelo);                    
        FormProjetosFuncionarios.setSizeColumnFuncionariosProjetos();        
    }    
}

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
import javax.swing.BoxLayout;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import static view.PainelDependentes.tabela;
import view.Principal;

public class FormFuncionarioProjetos extends JDialog implements ActionListener
{
    private static final long serialVersionUID = 1L;    
    
    private static JButton novo;
    private static JButton editarHora;
    private static JButton excluir;
    
    private static JButton btnOK;
    private static JButton btnCancelar;
    
    private static JTable tabela;
    private static DefaultTableModel modelo;
    private static String[] colunas;
    private static Empregado emp;
    
    public FormFuncionarioProjetos(Empregado e)
    {
        super(Principal.janela,"Todos Projetos do Empregado", true);
                               
        btnOK = new JButton("OK");
        btnCancelar = new JButton("Cancelar");
        btnOK.setPreferredSize(new Dimension(100, 25));
        btnCancelar.setPreferredSize(new Dimension(100, 25));
        emp = e;                
        
        btnOK.addActionListener(this);
        btnCancelar.addActionListener(this);

        JLabel nome = new JLabel("Nome: ");
         
        
        JPanel grid = new JPanel();
        grid.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        grid.setLayout(new GridLayout(4, 2, 5, 5));
        grid.add(nome);
        grid.add(new JLabel(e.getNome()));
        grid.add(new JLabel("Seguridade Social: "));
        grid.add(new JLabel(e.getSsn()));     
        grid.add(new JLabel("Departamento: "));
        grid.add(new JLabel(e.getDepartamento().getNome()));
        grid.add(new JLabel("Supervisor: "));
        grid.add(new JLabel(e.getSuperSsn().getNome()));
        
        nome.setPreferredSize(new Dimension(250, 25));
        
        JPanel botoes = new JPanel();
        botoes.setLayout(new BoxLayout(botoes, BoxLayout.X_AXIS));
        botoes.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,Color.LIGHT_GRAY));

        novo = new JButton("Add Projeto");
        editarHora = new JButton("Editar Hora");
        excluir = new JButton("Excluir");                

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

        colunas = new String [] { "Nome Projeto", "Numero Projeto", "Carga Horaria", "Localizacao", "Departamento", "Numero Departamento"};  
        
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
    public void actionPerformed(ActionEvent e)
    {
        Object origem = e.getSource();

        if(origem == novo)
            new FormFuncionarioProjetosForm(null, emp, false);        
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
        tabela.getTableHeader().getColumnModel().getColumn(2).setMinWidth(200);
        tabela.getTableHeader().getColumnModel().getColumn(3).setMinWidth(200);        
        tabela.getTableHeader().getColumnModel().getColumn(4).setMinWidth(200);        
        tabela.getTableHeader().getColumnModel().getColumn(5).setMinWidth(50);        
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
}

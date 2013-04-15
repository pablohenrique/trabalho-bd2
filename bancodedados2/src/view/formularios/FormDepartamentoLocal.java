package view.formularios;

import Model.Departamento;
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

public class FormDepartamentoLocal extends JDialog implements ActionListener
{
    private static final long serialVersionUID = 1L;    
    
    private JButton btnOK;
    private JButton btnNovo;
    private JButton btnRemover;
    
    private static JTable tabela;
    private static DefaultTableModel modelo;
    private static String[] colunas;
    private JLabel nome = new JLabel();
    private JLabel gerente = new JLabel();
    private JLabel dataInicio = new JLabel();
    private JPanel grid = new JPanel();
    private FormDepartamentoLocalForm formDepartmanetosLocalizacao = null;
    private Departamento depAtual = null;
    
    public FormDepartamentoLocal(Departamento d){
        super(Principal.janela,"Localizacao dos Departamentos", true);
        
        depAtual = d;
        btnNovo = new JButton("Novo");          
        btnRemover = new JButton("Remover");       
        btnOK = new JButton("OK");       
        btnOK.setPreferredSize(new Dimension(100, 25));
        btnOK.addActionListener(this);                     
        btnNovo.addActionListener(this);                     
        btnRemover.addActionListener(this);                     
        
        JLabel nomeLabel = new JLabel("Departamento: ");         
        
        grid.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        grid.setLayout(new GridLayout(4, 2, 5, 5));
        grid.add(nomeLabel);
        grid.add(nome);
        grid.add(new JLabel("Gerente: "));
        grid.add(gerente);     
        grid.add(new JLabel("Data Inicio: "));
        grid.add(dataInicio);
        
        nomeLabel.setPreferredSize(new Dimension(250, 25));
        
        JPanel botoes = new JPanel();
        botoes.setLayout(new BoxLayout(botoes, BoxLayout.X_AXIS));
        botoes.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,Color.LIGHT_GRAY)); 
        
        botoes.add(Box.createHorizontalStrut(5));       
        botoes.add(btnNovo);
        botoes.add(Box.createHorizontalStrut(3));       
        botoes.add(btnRemover);               
        botoes.add(Box.createVerticalStrut(45));
        botoes.add(Box.createHorizontalStrut(3));                          
        botoes.add(btnOK);               
        botoes.add(Box.createHorizontalStrut(5));        
                
        tabela = new JTable(){
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int rowIndex, int vColIndex){
                    return false;
            }
        };

        colunas = new String [] { "Local", "Nome Departamento"};  
        
        editar(d);
        
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setGridColor(new Color(220,220,220));        
                
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());        
        
        JPanel painel = new JPanel();
        painel.add(grid);              
        
        this.add(painel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);        
        this.add(botoes, BorderLayout.SOUTH);
        this.setSize(550, 510);
        this.setLocation((java.awt.Toolkit.getDefaultToolkit()
                                        .getScreenSize().width / 2)
                                        - (this.getWidth() / 2), (java.awt.Toolkit
                                        .getDefaultToolkit().getScreenSize().height / 2)
                                        - (this.getHeight() / 2));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
    
    public void editar(Departamento d){
        nome.setText(d.getNome());
        gerente.setText(d.getGerenteSsn().getNome());
        dataInicio.setText(Principal.cf.converteDataString(d.getGerenteDataInicio()));
        
        FormDepartamentoLocal.setDataTableDepLocal(d);
    }
 
        
    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object origem = e.getSource();
        int item = tabela.getSelectedRow();

        if(origem == btnOK)
            this.dispose();
        else if(origem == btnNovo)
            formLocalizacao(depAtual);
        else if(origem == btnRemover && (item != -1)) {
            String local = (String) tabela.getValueAt(item, tabela.getColumnModel().getColumnIndex("Local"));            

            int opcao = JOptionPane.showConfirmDialog(this,"Deseja remover a localizacao "+local+"?","Atenção!",JOptionPane.YES_NO_OPTION);    
            
            if(opcao == JOptionPane.YES_OPTION) {
                try {
                    Principal.cf.apagarLocalizacao(local);
                    modelo.removeRow(item);                   
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(this,ex, "Atenção", JOptionPane.ERROR_MESSAGE);
                }
            }            
        }
    }
    
    public static void setSizeColumnFuncionariosLocal(){
        tabela.getTableHeader().getColumnModel().getColumn(0).setMinWidth(350);
        tabela.getTableHeader().getColumnModel().getColumn(1).setMinWidth(250);       
    }
    
    public static void setDataTableDepLocal(Departamento d){
        String[][] dados = null;
        try {        
            System.out.println(d.getNumero());
            dados = Principal.cf.getLocalizacaoPorDep(Principal.cf.listarLocalizacaoPorDep(d.getNumero()));
        } catch (Exception ex) {
            System.err.println("Erro listar projetos de Departamento: " + ex);
        }
        FormDepartamentoLocal.modelo = new DefaultTableModel(dados, FormDepartamentoLocal.colunas);
        FormDepartamentoLocal.tabela.setModel(FormDepartamentoLocal.modelo);                    
        FormDepartamentoLocal.setSizeColumnFuncionariosLocal();        
    }
    
    public void formLocalizacao(Departamento d){
        if(formDepartmanetosLocalizacao == null)
            formDepartmanetosLocalizacao = new FormDepartamentoLocalForm(depAtual);
        else{
            try {
                formDepartmanetosLocalizacao.execute(depAtual);
            } catch (Exception ex) {
                 JOptionPane.showMessageDialog(null,"Erro Departamento: " + ex, "Atenção", JOptionPane.ERROR_MESSAGE);                                                                        
            }
            formDepartmanetosLocalizacao.setVisible(true);
        }
    }      
}

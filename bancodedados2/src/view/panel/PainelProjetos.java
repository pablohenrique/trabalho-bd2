/*
 * Painel Projetos
 * ---------------------------------------
 * 
 * - Esta classe representa a visao de dentro do programa
 * - Eh uma extensao de JPanel.
 * - Mostra todos projetos.
 * 
 */
package view.panel;

import Model.Projeto;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import view.formularios.FormProjetos;
import view.formularios.FormProjetosFuncionarios;

public final class PainelProjetos extends JPanel  implements ActionListener {	
    
    private static final long serialVersionUID = 1L;
    private static JButton novo;    
    private static JButton editar;
    private static JButton excluir;    
    private static JButton empregados;    
    private static JButton informacoes;
    private static JButton financeiro;
    private static JButton publicidade;
    private static JTextField txtBusca;
    private static JButton btnBusca;
    private static JLabel contaRegistros;
    
    public static JTable tabela;
    public static DefaultTableModel modelo;
    public static String[] colunas;
    private static FormProjetos formprojetos = null;
    
    public PainelProjetos(){			
        
        tabela = new JTable(){
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int rowIndex, int vColIndex){
                    return false;
            }
        };

        colunas = new String [] {"Nome Projeto", "Numero", "Nome Departamento", "Dno", "Nome Gerente", "Ssn"}; 
  
        PainelProjetos.setDataTable();
        
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setGridColor(new Color(220,220,220));        
        PainelProjetos.setSizeColumn();
        
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JPanel botoes = new JPanel();
        botoes.setLayout(new BoxLayout(botoes, BoxLayout.X_AXIS));
        botoes.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(64, 64, 64)));

        JLabel imagem = new JLabel();
        imagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/busca.png")));

        novo = new JButton("Novo");
        informacoes = new JButton("Informacoes");
        financeiro =  new JButton("Financeiro");
        publicidade =  new JButton("Publicidade");
        editar = new JButton("Editar");
        excluir = new JButton("Excluir");
        empregados = new JButton("Empregados");
        btnBusca = new JButton("Pesquisar");
        txtBusca = new JTextField();
        txtBusca.setPreferredSize(new Dimension(200, 24));
        txtBusca.setMaximumSize(new Dimension(200, 24));

        contaRegistros = new JLabel();
        contaRegistros.setText(tabela.getRowCount() + " registro(s) encontrado(s)");

        botoes = nivelView(botoes, imagem);
        
        novo.addActionListener(this);
        editar.addActionListener(this);
        btnBusca.addActionListener(this);
        excluir.addActionListener(this);        
        empregados.addActionListener(this);
        informacoes.addActionListener(this);
        
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.DARK_GRAY));
        this.add(botoes, BorderLayout.SOUTH);
        this.add(scrollPane, BorderLayout.CENTER);		
    }

    public JPanel nivelView(JPanel botoes, JLabel imagem)
    {        
        if(Principal.user.getTipoLogin() >= 1){          
            botoes.add(Box.createHorizontalStrut(5));
            botoes.add(novo);
            botoes.add(Box.createHorizontalStrut(3));
            botoes.add(editar);
            botoes.add(Box.createHorizontalStrut(3));
            botoes.add(excluir);
            botoes.add(Box.createHorizontalStrut(3));
            botoes.add(empregados);         
            botoes.add(Box.createHorizontalGlue());
            botoes.add(contaRegistros);
            botoes.add(Box.createHorizontalGlue());            
        }            
        else if(Principal.user.getTipoLogin() == 0){          
            botoes.add(Box.createHorizontalStrut(5));
            botoes.add(informacoes);        
            botoes.add(Box.createHorizontalStrut(3));
            botoes.add(publicidade);        
            botoes.add(Box.createHorizontalStrut(3));
            botoes.add(financeiro);                    
            botoes.add(Box.createHorizontalGlue());          
            botoes.add(Box.createHorizontalGlue());          
            botoes.add(contaRegistros);      ;            
            botoes.add(Box.createHorizontalGlue());
        }
        
        if(Principal.user.getTipoLogin() == 2){
            botoes.add(Box.createVerticalStrut(45));                       
            botoes.add(imagem);
            botoes.add(Box.createHorizontalStrut(5));
            botoes.add(txtBusca);
            botoes.add(Box.createHorizontalStrut(5));
            botoes.add(btnBusca);
            botoes.add(Box.createHorizontalStrut(7));            
        }
        
        return botoes;
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        Object origem = e.getSource();	
        int item = tabela.getSelectedRow();
                
        if (origem == novo)
                formProjeto(null);
        else if (origem == editar && (item != -1)){
            String numero = (String) tabela.getValueAt(item, tabela.getColumnModel().getColumnIndex("Numero"));
            Projeto p = null;
            try {
                p = Principal.cf.getProjetoByNumero(Integer.parseInt(numero));
                formProjeto(p);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,ex, "Atenção", JOptionPane.ERROR_MESSAGE);
            }             
        }         
        else if (origem == excluir  && (item != -1)) {
            String numero = (String) tabela.getValueAt(item, tabela.getColumnModel().getColumnIndex("Numero"));
            String nome = (String) tabela.getValueAt(item, tabela.getColumnModel().getColumnIndex("Nome Projeto"));
            
            int opcao = JOptionPane.showConfirmDialog(this,"Deseja remover projeto "+nome+"?","Atenção!",JOptionPane.YES_NO_OPTION);    
            
            if(opcao == JOptionPane.YES_OPTION) {
                try {
                    Principal.cf.apagarProjeto(Integer.parseInt(numero));
                    modelo.removeRow(item);
                    contaRegistros.setText(tabela.getRowCount() + " registro(s) encontrado(s)");                    
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(this,ex, "Atenção", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }
        else if (origem == empregados) {//&& (item != -1)
                new FormProjetosFuncionarios(null);
            /*
            String ssn = (String) tabela.getValueAt(item, tabela.getColumnModel().getColumnIndex("Ssn"));
            Empregado em;
            try {
                em = Principal.cf.getEmpregadoBySsn(ssn);
                new FormDepartamentoProjetos(em);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,ex, "Atenção", JOptionPane.ERROR_MESSAGE);
            }*/
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
        tabela.getTableHeader().getColumnModel().getColumn(0).setMinWidth(250);
        tabela.getTableHeader().getColumnModel().getColumn(1).setMinWidth(50);
        tabela.getTableHeader().getColumnModel().getColumn(2).setMinWidth(250);
        tabela.getTableHeader().getColumnModel().getColumn(3).setMinWidth(100);
        tabela.getTableHeader().getColumnModel().getColumn(4).setMinWidth(250);
        tabela.getTableHeader().getColumnModel().getColumn(5).setMinWidth(100);
    }
    
    public static void setDataTable(){       
        String[][] dados = null;
        
        try {        
            if(Principal.user.getTipoLogin() != 0)//supervisor (verificar gerente se pode crud projetos)
                dados = Principal.cf.getProjetosTable(Principal.cf.listarProjetos());
            else if(Principal.user.getTipoLogin() == 0)//funcionario
                dados = Principal.cf.getProjetosTable(Principal.cf.listarProjetosByEmp(Principal.user.getSsn()));
        } catch (Exception ex) {
            System.err.println("Erro Painel Projetos: " + ex);
        }
        
        PainelProjetos.modelo = new DefaultTableModel(dados, PainelProjetos.colunas);
        PainelProjetos.tabela.setModel(PainelProjetos.modelo);                    
        PainelProjetos.setSizeColumn();        
    }
    
    
    public static void formProjeto(Projeto p){
        if(formprojetos == null)
            formprojetos = new FormProjetos(p);
        else{
            try {
                formprojetos.editar(p);
            } catch (Exception ex) {
                 JOptionPane.showMessageDialog(null,"Erro Empregado: " + ex, "Atenção", JOptionPane.ERROR_MESSAGE);                                                                        
            }
            formprojetos.setVisible(true);
        }
    }       
}

/*
 * PainelFuncionarios
 * ---------------------------------------
 * 
 * - Esta classe representa a visao de dentro do programa
 * - Eh uma extensao de JPanel.
 * - Mostra todos funcionarios, faz pesquisa, edita, insere, exclui.
 * 
 */
package view.panel;

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
import view.formularios.FormFuncionario;
import view.formularios.FormFuncionarioProjetos;

public final class PainelFuncionarios extends JPanel  implements ActionListener {	
    
    private static final long serialVersionUID = 1L;
    private JButton novo = new JButton("Novo");
    private JButton editar  = new JButton("Editar");
    private JButton excluir = new JButton("Excluir");
    private JButton projetos = new JButton("Projetos");
    private JTextField txtBusca  = new JTextField();   
    private JButton btnBusca = new JButton("Pesquisar");
    
    public static JLabel contaRegistros = new JLabel();    
    public static JTable tabela;
    public static DefaultTableModel modelo;
    public static String[] colunas;
    private FormFuncionario formFuncionario = null;
    private FormFuncionarioProjetos formProjetos = null;
    
    public PainelFuncionarios(){			
        
        tabela = new JTable(){
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int rowIndex, int vColIndex){
                    return false;
            }
        };

        colunas = new String [] { "Nome", "Ssn", "Sexo", "Endereco", "Salario", "Data de Nascimento",
                                           "Departamento", "Dno", "Supervisor", "SuperSnn"};  
        
        PainelFuncionarios.setDataTable();
        
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setGridColor(new Color(220,220,220));        
        PainelFuncionarios.setSizeColumn();
        
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JPanel botoes = new JPanel();
        botoes.setLayout(new BoxLayout(botoes, BoxLayout.X_AXIS));
        botoes.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(64, 64, 64)));

        JLabel imagem = new JLabel();
        imagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/busca.png")));

        txtBusca.setPreferredSize(new Dimension(200, 24));
        txtBusca.setMaximumSize(new Dimension(200, 24));

        botoes.add(Box.createHorizontalStrut(5));
        if(Principal.user.getTipoLogin() == 2)
            botoes.add(novo);
        botoes.add(Box.createHorizontalStrut(3));
        botoes.add(editar);
        botoes.add(Box.createHorizontalStrut(3));
        if(Principal.user.getTipoLogin() != 1)
            botoes.add(excluir);
        botoes.add(Box.createHorizontalStrut(3));
        botoes.add(projetos);         
        botoes.add(Box.createVerticalStrut(45));
        botoes.add(Box.createHorizontalGlue());
        botoes.add(contaRegistros);
        botoes.add(Box.createHorizontalGlue());
        botoes.add(imagem);
        botoes.add(Box.createHorizontalStrut(5));
        botoes.add(txtBusca);
        botoes.add(Box.createHorizontalStrut(5));
        botoes.add(btnBusca);
        botoes.add(Box.createHorizontalStrut(7));

        novo.addActionListener(this);
        editar.addActionListener(this);
        btnBusca.addActionListener(this);
        excluir.addActionListener(this);        
        projetos.addActionListener(this);
        
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
                form_funcionarios(null);
         if (origem == editar && (item != -1)){
            
            String ssn = (String) tabela.getValueAt(item, tabela.getColumnModel().getColumnIndex("Ssn"));
            
            System.out.println("SSN " + ssn);            
            Empregado em;
            try {
                em = Principal.cf.getEmpregadoBySsn(ssn);
                form_funcionarios(em);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,ex, "Atenção", JOptionPane.ERROR_MESSAGE);
            }            
            
        }         
        else if (origem == excluir  && (item != -1)) {
            
            String ssn = (String) tabela.getValueAt(item, tabela.getColumnModel().getColumnIndex("Ssn"));
            int opcao = JOptionPane.showConfirmDialog(this,"Deseja remover empregado com Ssn "+ssn.trim()+"?","Atenção!",JOptionPane.YES_NO_OPTION);    
            
            if(opcao == JOptionPane.YES_OPTION) {
                try {
                    Principal.cf.apagarEmpregado(ssn);
                    modelo.removeRow(item);
                    contaRegistros.setText(tabela.getRowCount() + " registro(s) encontrado(s)");                    
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(this,ex, "Atenção", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else if (origem == projetos && (item != -1)) {
            String ssn = (String) tabela.getValueAt(item, tabela.getColumnModel().getColumnIndex("Ssn"));
            Empregado em;
            try {
                em = Principal.cf.getEmpregadoBySsn(ssn);
                form_projetos(em);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,ex, "Atenção", JOptionPane.ERROR_MESSAGE);
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
    
    public static void setDataTable(){
        
        String[][] dados = null;                         
       /* 
        try {
            if(Principal.user.getTipoLogin() == 1)
                dados = Principal.cf.getEmpregadosTable(Principal.cf.buscaSuperSnn(Principal.user.getSsn()));            
            else
                dados = Principal.cf.getEmpregadosTable(Principal.cf.listarEmpregados());
        } catch (Exception ex) {
            System.err.println("Erro em Painel Funcionario: " + ex);
        }
        */
        PainelFuncionarios.modelo = new DefaultTableModel(dados, PainelFuncionarios.colunas);
        PainelFuncionarios.tabela.setModel(PainelFuncionarios.modelo);                    
        PainelFuncionarios.contaRegistros.setText(PainelFuncionarios.tabela.getRowCount() + " registro(s) encontrado(s)");                    
        PainelFuncionarios.setSizeColumn();        
    }
    
    public void form_funcionarios(Empregado e){
        if(formFuncionario == null)
            formFuncionario = new FormFuncionario(e);
        else{
            try {
                formFuncionario.editarEmpregado(e);
            } catch (Exception ex) {
                 JOptionPane.showMessageDialog(null,"Erro Empregado: " + ex, "Atenção", JOptionPane.ERROR_MESSAGE);                                                                        
            }
            formFuncionario.setVisible(true);
        }
    }    
    
    public void form_projetos(Empregado e){
        if(formProjetos == null)
            formProjetos = new FormFuncionarioProjetos(e);
        else{
            try {
                formProjetos.setEmpregado(e);
            } catch (Exception ex) {
                 JOptionPane.showMessageDialog(null,"Erro Empregado: " + ex, "Atenção", JOptionPane.ERROR_MESSAGE);                                                                        
            }
            formProjetos.setVisible(true);
        }
    }    
}

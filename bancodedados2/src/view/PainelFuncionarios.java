/**
 * Painel_Init
 * ---------------------------------------
 * 
 * - Esta classe representa a visao de dentro do programa
 * - Eh uma extensao de JPanel.
 * - Mostra todos funcionarios, faz pesquisa, edita, insere, exclui.
 * 
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class PainelFuncionarios extends JPanel  implements ActionListener 
{	
    private static final long serialVersionUID = 1L;
    public static JButton novo;
    public static JButton editar;
    public static JButton excluir;
    public static JTable tabela;
    public static DefaultTableModel modelo;
    public static Vector<String> colunas;
    public static JTextField txtBusca;
    public static JButton btnBusca;
    public static JLabel contaRegistros;

    public PainelFuncionarios()
    {			
        tabela = new JTable()
        {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int rowIndex, int vColIndex)
            {
                    return false;
            }
        };

        String[][] dados = Principal.cf.getEmpregadosTable(Principal.cf.listarEmpregados());
        String[] colunas = new String [] { "Nome", "Ssn", "Sexo", "Endereco", "Salario", "Data de Nascimento",
                                           "Departamento", "Dno", "Supervisor", "SuperSnn"};  
        
        modelo = new DefaultTableModel(dados, colunas);
        
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setGridColor(new Color(220,220,220));
        tabela.setModel(modelo);
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
        btnBusca = new JButton("Pesquisar");
        txtBusca = new JTextField();
        txtBusca.setPreferredSize(new Dimension(200, 24));
        txtBusca.setMaximumSize(new Dimension(200, 24));

        contaRegistros = new JLabel();
        contaRegistros.setText(tabela.getRowCount() + " registro(s) encontrado(s)");

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
        botoes.add(imagem);
        botoes.add(Box.createHorizontalStrut(5));
        botoes.add(txtBusca);
        botoes.add(Box.createHorizontalStrut(5));
        botoes.add(btnBusca);
        botoes.add(Box.createHorizontalStrut(7));

        /*
        btnBusca.addActionListener(this);
        novo.addActionListener(this);
        excluir.addActionListener(this);
        editar.addActionListener(this);
         */

        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.DARK_GRAY));
        this.add(botoes, BorderLayout.SOUTH);
        this.add(scrollPane, BorderLayout.CENTER);		
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object origem = e.getSource();	
    }
}

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class FormFuncionario extends JDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private static JTextField nome;
	private static JComboBox<String> combo;
	private static JPasswordField senha;
	private static JButton btnOK;
	private static JButton btnCancelar;
        
        
        // jack delicia =]
        // by Caio
        
	
	public FormFuncionario()
	{
		super(Principal.janela,true);
		
		nome = new JTextField();
		senha = new JPasswordField();

		combo = new JComboBox<String>();
		combo.addItem("Combo box 1");
		combo.addItem("DEPARTAMENTO 1");
		combo.addItem("REDES");
		combo.addItem("Outros");
		
		btnOK = new JButton("OK");
		btnCancelar = new JButton("Cancelar");
		btnOK.setPreferredSize(new Dimension(100, 25));
		btnCancelar.setPreferredSize(new Dimension(100, 25));
		
		btnOK.addActionListener(this);
		btnCancelar.addActionListener(this);
		
		JPanel grid = new JPanel();
		grid.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		grid.setLayout(new GridLayout(3, 2, 5, 5));
		grid.add(new JLabel("Nome: "));
		grid.add(nome);
		grid.add(new JLabel("Senha: "));
		grid.add(senha);
		grid.add(new JLabel("ComboBox: "));
		grid.add(combo);
		
		nome.setPreferredSize(new Dimension(250, 25));
		
		JPanel painel = new JPanel();
		painel.add(grid);
		
		JPanel botoes = new JPanel();
		botoes.add(btnOK);
		botoes.add(btnCancelar);
		botoes.add(Box.createVerticalStrut(45));
		botoes.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,Color.LIGHT_GRAY));

		this.add(painel, BorderLayout.CENTER);
		this.add(botoes, BorderLayout.SOUTH);
		this.setSize(550, 510);
		this.setLocation((java.awt.Toolkit.getDefaultToolkit()
						.getScreenSize().width / 2)
						- (this.getWidth() / 2), (java.awt.Toolkit
						.getDefaultToolkit().getScreenSize().height / 2)
						- (this.getHeight() / 2));
		this.setTitle("Cadastro de Funcionario");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object origem = e.getSource();
		
		if(origem == btnOK)
		{
			this.dispose();
		}
		
		if (origem == btnCancelar)
		{
			this.dispose();
		} 
				
	}
}

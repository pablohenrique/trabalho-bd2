/**
 * Painel_Init
 * ---------------------------------------
 * 
 * - Esta classe representa a visao de dentro do programa
 * - Eh uma extensao de JPanel.
 * - Pagina incial.
 * 
 */
package view.panel;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import view.Principal;

public class PainelInit extends JPanel
{
    private static final long serialVersionUID = 1L;

    public PainelInit()
    {			
        this.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.DARK_GRAY));
        this.setBackground(Color.white);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel l1 = new JLabel("Bem-vindo ao Sistema de Gerenciamento!");
        l1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel l2 = new JLabel("Olá " + Principal.user.getNome() + ".");
        l2.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));	

        l1.setFont(new Font("", 1, 16));

        JPanel painel = new JPanel();
        painel.setBackground(Color.white);
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.add(l1);
        painel.add(l2);

        add(painel);
        add(Box.createVerticalGlue());		
    }

}

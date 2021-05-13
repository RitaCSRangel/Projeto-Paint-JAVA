package paintBasico;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import Comunicados.ComunicadoDeConsulta;

/*
Classe usada para construir nossa janela de tabelas que mostrará as informações da consulta requisitada
pelo usuário.
*/

public class JanelaConsulta extends JFrame {
	
    JPanel painelConsultas;
    JTable tabelaConsultas;
    JScrollPane barra;	        
    Object[][] infos;
    String [] colunas = {"NomeArquivo", "DataDeCriacao", "UltimaAtualizacao", "Autor"}; 
	     
    //CONSTRUTOR
    public JanelaConsulta(Vector<ComunicadoDeConsulta> desenhos) throws Exception {
    super ("Consulta de desenhos");
	        
        infos = new Object[desenhos.size()][4];
        for(int i=0; i<desenhos.size();i++) {
            infos[i][0] = desenhos.get(i).getNome();
            infos[i][1] = desenhos.get(i).getDataCria();
            infos[i][2] = desenhos.get(i).getUltimaData();
            infos[i][3] = desenhos.get(i).getAutor();
        }
	        
        criarJanelaDeConsultas();
    }
	     
    public void criarJanelaDeConsultas(){
	         
	painelConsultas = new JPanel();
	painelConsultas.setLayout(new GridLayout(1, 1));
	tabelaConsultas = new JTable(infos, colunas);
	        
	//Removendo a opção de editar a tabela
	DefaultTableModel tableModel = new DefaultTableModel(infos, colunas) {

	@Override
	public boolean isCellEditable(int row, int column) {
            return false;
	}
        };

	tabelaConsultas.setModel(tableModel);
	barra = new JScrollPane(tabelaConsultas);
	painelConsultas.add(barra); 
	         
	getContentPane().add(painelConsultas);
	setSize(720, 300);
	setVisible(true);
    }	     	
}

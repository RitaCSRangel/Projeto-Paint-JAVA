package Servidor;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import BD.daos.Figuras;
import BD.dbos.InfosFiguras;
import BD.dbos.ItemFiguras;
import Comunicados.*;

public class SupervisoraDeConexao extends Thread
{
    protected double              valor =0;
    protected Parceiro            usuario;
    protected Socket              conexao;
    protected ArrayList<Parceiro> usuarios;

    public SupervisoraDeConexao (Socket conexao, ArrayList<Parceiro> usuarios)
    throws Exception
    {
        if (conexao==null)
            throw new Exception ("Conexao ausente.");

        if (usuarios==null)
            throw new Exception ("Usuarios ausentes.");

        this.conexao  = conexao;
        this.usuarios = usuarios;
    }

    public void run ()
    {

        ObjectOutputStream transmissor;
        try
        {
            transmissor =
            new ObjectOutputStream(
            this.conexao.getOutputStream());
        }
        catch (Exception erro)
        {
            return;
        }
        
        ObjectInputStream receptor=null;
        try
        {
            receptor=
            new ObjectInputStream(
            this.conexao.getInputStream());
        }
        catch (Exception err0)
        {
            try
            {
                transmissor.close();
            }
            catch (Exception falha)
            {} 
            
            return;
        }

        try
        {
            this.usuario =
            new Parceiro (this.conexao,
                          receptor,
                          transmissor);
        }
        catch (Exception erro)
        {} 

        try
        {
            synchronized (this.usuarios)
            {
                this.usuarios.add (this.usuario);
            }


            for(;;)
            {
                Comunicado comunicado = this.usuario.envie ();

                if (comunicado==null)
                    return;
                else if (comunicado instanceof PedidoDeSalvar)
                {
                
                    PedidoDeSalvar pedidoSalvar = (PedidoDeSalvar)comunicado;
                    String nome = pedidoSalvar.getNome();
                    String dataCria = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(Calendar.getInstance().getTime());
                    String ultimaData = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(Calendar.getInstance().getTime());
                    String autor = pedidoSalvar.getID();		
                    InfosFiguras d = new InfosFiguras(nome, dataCria, ultimaData, autor);
					
					
                    Vector<String> desenhos = pedidoSalvar.getFigura();
                    Vector<ItemFiguras> itens = new Vector<ItemFiguras>();
                    for(int i=0;i<desenhos.size();i++) {
			String SItem = desenhos.get(i);
			ItemFiguras item = new ItemFiguras(nome, autor, SItem);
			itens.add(item);	
						
                    }
					
                    Figuras.incluir(d, itens);
					
                    synchronized (this.usuarios)
                    {
                        this.usuarios.remove (this.usuario);
                    }
                    this.usuario.adeus();
					
					System.out.println("inserido");

                }
                else if (comunicado instanceof PedidoDeAbrir)
                {
                    PedidoDeAbrir pedidoAbrir = (PedidoDeAbrir) comunicado;
                    String nome = pedidoAbrir.getNomeDesenho();
                    String autor = pedidoAbrir.getIdCliente();

                    DesenhoFinal df;
                    df = Figuras.getDesenho(nome, autor);

                
                    Comunicados.ComunicadoDeDesenho d = new Comunicados.ComunicadoDeDesenho();
                    d.setNome(nome);
                    d.setID(autor);
                    d.setDesenho(df.getItens());
                    
                    this.usuario.receba (d);
                    
                    System.out.println("Aberto");
                    
                    //desconectar o usuario
                    synchronized (this.usuarios)
                    {
                        this.usuarios.remove (this.usuario);
                    }
                    this.usuario.adeus();
                }
                else if (comunicado instanceof PedidoDeConsultar)
                {
                	Vector<ComunicadoDeConsulta> desenhos = Figuras.getDesenhos();
                	RespostaDeConsultar res = new RespostaDeConsultar(desenhos);
                	usuario.receba(res);
                	System.out.println("consultado.");
                	
                }
            }
        }
        catch (Exception erro)
        {
            try
            {
                transmissor.close ();
                receptor   .close ();
            }
            catch (Exception falha)
            {} // so tentando fechar antes de acabar a thread

            return;
        }
    }
}

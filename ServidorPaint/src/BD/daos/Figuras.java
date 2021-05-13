package BD.daos;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;
import BD.*;
import BD.core.*;
import BD.dbos.*;
import Servidor.DesenhoFinal;
import Comunicados.ComunicadoDeConsulta;

public class Figuras
{
    public static boolean cadastrado (String nome, String autor) throws Exception
    {
        boolean r = false;

        try
        {
            String sqlCode;

            sqlCode = "SELECT * " +
                      "FROM InfosFiguras " +
                      "WHERE Nome = ? and Autor = ?";

            BDSQLServer.COMANDO.prepareStatement (sqlCode);
            BDSQLServer.COMANDO.setString (1, nome);
            BDSQLServer.COMANDO.setString (2, autor);
            
            MeuResultSet result = (MeuResultSet)BDSQLServer.COMANDO.executeQuery ();
            r = result.first(); 

        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao procurar o desenho: "+erro.getMessage());
        }

        return r;
    }

    public static void incluir (InfosFiguras figs, Vector<ItemFiguras> itens) throws Exception
    {
        if (figs==null)
            throw new Exception ("Os desenhos não foram fornecidos.");
        try
        {
            if(cadastrado(figs.getNome(), figs.getAutor())) {
        	alterar(figs.getNome(), figs.getAutor(), itens);
            }
            else {
	        String sqlCode;
                
                sqlCode = "INSERT INTO InfosFiguras " +
		          "(Nome,DataCria,UltimaData,Autor) " +
		          "VALUES " +
		          "(?,?,?,?)";
		
		BDSQLServer.COMANDO.prepareStatement (sqlCode);
		BDSQLServer.COMANDO.setString  (1, figs.getNome());
		BDSQLServer.COMANDO.setString  (2, figs.getDataCria());
		BDSQLServer.COMANDO.setString  (3, figs.getUltimaData());
		BDSQLServer.COMANDO.setString  (4, figs.getAutor());
		BDSQLServer.COMANDO.executeUpdate ();
		BDSQLServer.COMANDO.commit        ();           
	            

	        sqlCode = "INSERT INTO ItemFiguras " +
	                  "(NomeArquivo,IDCliente,Item) " +
	                  "VALUES " +
	                  "(?,?,?)";
	            
	        for(int i=0; i<itens.size();i++) {
	            BDSQLServer.COMANDO.prepareStatement (sqlCode);
                    BDSQLServer.COMANDO.setString  (1, itens.get(i).getNome());
	            BDSQLServer.COMANDO.setString  (2, itens.get(i).getID());
	            BDSQLServer.COMANDO.setString  (3, itens.get(i).getItem());
                    BDSQLServer.COMANDO.executeUpdate ();
	            BDSQLServer.COMANDO.commit        ();
	        }
            
            }                   
        }
        catch (SQLException erro)
        {
			BDSQLServer.COMANDO.rollback();
            throw new Exception ("Erro ao inserir.");
        }
    }

    public static void alterar (String nome, String id, Vector<ItemFiguras> novosItens) throws Exception
    {
        if (nome==null)
            throw new Exception ("Os Desenhos não foram fornecidos.");

        if (!cadastrado (nome, id))
            throw new Exception ("O desenho requerido não está cadastrado em nosso banco.");

        try
        {
            String sqlCode;

            sqlCode = "UPDATE InfosFiguras " +
                      "SET UltimaData=? " +
                      "WHERE Nome = ? and Autor = ?";

            BDSQLServer.COMANDO.prepareStatement (sqlCode);

            String lastDate = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(Calendar.getInstance().getTime());
            BDSQLServer.COMANDO.setString (1, lastDate);
            BDSQLServer.COMANDO.setString  (2, nome);
            BDSQLServer.COMANDO.setString  (3, id);
            BDSQLServer.COMANDO.executeUpdate ();
            BDSQLServer.COMANDO.commit        ();
            
            sqlCode = "DELETE FROM ItemFiguras " +
                      "WHERE NomeArquivo=? and IDCliente=?";

            BDSQLServer.COMANDO.prepareStatement (sqlCode);
            BDSQLServer.COMANDO.setString(1, nome);
            BDSQLServer.COMANDO.setString(2, id);
            BDSQLServer.COMANDO.executeUpdate ();
            BDSQLServer.COMANDO.commit        ();  
              

	    sqlCode = "INSERT INTO ItemFiguras " +
	              "(NomeArquivo,IDCliente,Item) " +
	              "VALUES " +
	              "(?,?,?)";
	            
	             
	    for(int i=0; i<novosItens.size();i++) {
	        BDSQLServer.COMANDO.prepareStatement (sqlCode);
                BDSQLServer.COMANDO.setString  (1, novosItens.get(i).getNome());
	        BDSQLServer.COMANDO.setString  (2, novosItens.get(i).getID());
	        BDSQLServer.COMANDO.setString  (3, novosItens.get(i).getItem());
                BDSQLServer.COMANDO.executeUpdate ();
	        BDSQLServer.COMANDO.commit        ();
	    }          
        }
        catch (SQLException erro)
        {
            BDSQLServer.COMANDO.rollback();
            throw new Exception ("Erro ao atualizar dados do arquivo.");
        }
    }

    public static DesenhoFinal getDesenho (String nome, String IDCliente) throws Exception
    {
        InfosFiguras figuras = null;
        DesenhoFinal desenhof = null;
        Vector<String> itens = new Vector<String>();
        
        try
        {
            String sqlCode;

            sqlCode = "SELECT * " +
                      "FROM InfosFiguras " +
                      "WHERE Nome = ? and Autor = ?";

            BDSQLServer.COMANDO.prepareStatement (sqlCode);
            BDSQLServer.COMANDO.setString(1, nome);
            BDSQLServer.COMANDO.setString(2, IDCliente);

            MeuResultSet resultado = (MeuResultSet)BDSQLServer.COMANDO.executeQuery ();

            if (!resultado.first())
                throw new Exception ("Arquivo não cadastrado.");
            
           
            figuras = new InfosFiguras (resultado.getString("Nome"),resultado.getString("DataCria"),resultado.getString("UltimaData"),resultado.getString("Autor"));

            
            sqlCode = "SELECT * " +
                      "FROM ItemFiguras " +
                      "WHERE NomeArquivo = ? and IDCliente = ?";

              BDSQLServer.COMANDO.prepareStatement (sqlCode);

              BDSQLServer.COMANDO.setString(1, nome);
              BDSQLServer.COMANDO.setString(2, IDCliente);
              MeuResultSet r = (MeuResultSet)BDSQLServer.COMANDO.executeQuery ();
            
            while(r.next()) {
            	itens.add(r.getString("Item"));
            }
            
            desenhof = new DesenhoFinal (figuras, itens);
            
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao procurar o arquivo: "+erro.getMessage());
        }

        return desenhof;
    }

    public static Vector<ComunicadoDeConsulta> getDesenhos () throws Exception
    {
        MeuResultSet r = null;
        Vector<ComunicadoDeConsulta> desenhos = new Vector<ComunicadoDeConsulta>();
        
        try
        {
            String sqlCode;

            sqlCode = "SELECT * " +
                      "FROM InfosFiguras";

            BDSQLServer.COMANDO.prepareStatement (sqlCode);

            r = (MeuResultSet)BDSQLServer.COMANDO.executeQuery ();
            
            while(r.next()) {
            	ComunicadoDeConsulta cd = new ComunicadoDeConsulta(r.getString("Nome"),r.getString("DataCria"),r.getString("UltimaData"),r.getString("Autor"));
            	desenhos.add(cd);
            }
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao recuperar desenhos: "+erro.getMessage());
        }

        return desenhos;
    }
}
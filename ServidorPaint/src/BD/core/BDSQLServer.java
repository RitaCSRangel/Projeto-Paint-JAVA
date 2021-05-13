package BD.core;

import BD.core.*;
import BD.daos.*;
import java.sql.*;

public class BDSQLServer
{
    public static final MeuPreparedStatement COMANDO;

    static
    {
    	MeuPreparedStatement comando = null;

    	try
        {
            comando =
            new MeuPreparedStatement ("com.microsoft.sqlserver.jdbc.SQLServerDriver","jdbc:sqlserver://localhost:1433;databasename=master;TRUSTED_CONNECTION=TRUE",
            "ritarita", "123");
        }
        catch (Exception erro)
        {
            System.err.println ("Problemas de conexao com o BD: "+erro.getMessage());
            System.exit(0); // aborta o programa
        }
        
        COMANDO = comando;
    }
}
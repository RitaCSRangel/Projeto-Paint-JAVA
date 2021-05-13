package Servidor;
import java.io.*; 

/*Resumo da Classe
Nossa classe teclado é uma classe em comum, ou seja, deve estar presente tanto no cliente quanto no servidor. Aqui estamos
no nosso programa paint, que é o nosso cliente, por isso temos uma classe teclado nela. A classe teclado serve para que
possamos digitar e ler as coisas vindas do teclado do usuário. Em java uma classe específica deve ser implementada para esse
funcionamento e é isso que veremos agora. 

- Para que a nossa classe teclado funcione fizemos a importação da biblioteca java.io.* que faz o import de outras 3 bibliotecas
cruciais para o funcionamento dessa classe: BufferedReader, InputStreamReader, IOEXception. 
- Antes de qualquer coisa nós criamos um objeto do tipo BufferedReader para pegar o conteúdo do teclado. 

- Em todos os métodos usamos o método readLine do nosso objeto para retornar o que foi digitadado pelo usuário, porém isso será
retornado sempre em forma de string. Por conta disso, cada método que criamos para cada um dos tipos primitivos irá tratar esse
retorno de uma maneira diferente, fazendo as conversões necessárias. 

NO STRING
- Guardamos isso dentro de um objeto do tipo string.
- Não precisamos de nenhuma conversão, podemos apenas guardar esse valor no nosso objeto.
- E dado o return no objeto que criamos do tipo string, contendo o conteúdo recebido do readLine. 

NOS DEMAIS
- Para cada um dos outros tipos primitivos que não sejam string o que fazemos é dar um parse no conteúdo pego do nosso readLine 
e guardar dentro de um objeto daquele tipo primitivo. 
- Tudo isso deve ser feito dentro de um try catch para saber se o valor digitado pode ser posto dentro daquele tipo primitivo 
especifico.   

*/

public class Teclado
{
    private static BufferedReader teclado =
                   new BufferedReader (
                   new InputStreamReader (
                   System.in));

    public static String getUmString ()
    {
        String ret=null;

        try
        {
            ret = teclado.readLine ();
        }
        catch (IOException erro)
        {} 

        return ret;
    }

    public static byte getUmByte () throws Exception
    {
        byte ret=(byte)0;

        try
        {
            ret = Byte.parseByte (teclado.readLine ());
        }
        catch (IOException erro)
        {} 
        catch (NumberFormatException erro)
        {
            throw new Exception ("Byte invalido!");
        }

        return ret;
    }
 
    public static short getUmShort () throws Exception
    {
        short ret=(short)0;

        try
        {
            ret = Short.parseShort (teclado.readLine ());
        }
        catch (IOException erro)
        {} 
        catch (NumberFormatException erro)
        {
            throw new Exception ("Short invalido!");
        }

        return ret;
    }

    public static int getUmInt () throws Exception
    {
        int ret=0;

        try
        {
            ret = Integer.parseInt (teclado.readLine ());
        }
        catch (IOException erro)
        {} 
        catch (NumberFormatException erro)
        {
            throw new Exception ("Int invalido!");
        }

        return ret;
    }

    public static long getUmLong () throws Exception
    {
        long ret=0L;

        try
        {
            ret = Long.parseLong (teclado.readLine ());
        }
        catch (IOException erro)
        {} 
        catch (NumberFormatException erro)
        {
            throw new Exception ("Long invalido!");
        }

        return ret;
    }

    public static float getUmFloat () throws Exception
    {
        float ret=0.0F;

        try
        {
            ret = Float.parseFloat (teclado.readLine ());
        }
        catch (IOException erro)
        {} 
        catch (NumberFormatException erro)
        {
            throw new Exception ("Float invalido!");
        }

        return ret;
    }

    public static double getUmDouble () throws Exception
    {
        double ret=0.0;

        try
        {
            ret = Double.parseDouble (teclado.readLine ());
        }
        catch (IOException erro)
        {} 
        catch (NumberFormatException erro)
        {
            throw new Exception ("Double invalido!");
        }

        return ret;
    }

    public static char getUmChar () throws Exception
    {
        char ret=' ';

        try
        {
            String str = teclado.readLine ();

            if (str==null)
                throw new Exception ("Char invalido!");

            if (str.length() != 1)
                throw new Exception ("Char invalido!");

             ret = str.charAt(0);
        }
        catch (IOException erro)
        {} 

        return ret;
    }

    public static boolean getUmBoolean () throws Exception
    {
        boolean ret=false;

        try
        {
            String str = teclado.readLine ();

            if (str==null)
                throw new Exception ("Boolean invalido!");

            if (!str.equals("true") && !str.equals("false"))
                throw new Exception ("Boolean invalido!");

            ret = Boolean.parseBoolean (str);
        }
        catch (IOException erro)
        {} 

        return ret;
    }
}
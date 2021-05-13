package Comunicados;
import java.net.*;
import Servidor.Parceiro;

/*Resumo da Classe:
- Essa classe é uma thread responsável por ficar de olho na vinda de um comunicado de desligamento
- A classe TratadoraDeComunicadoDeDesligamento vai estar rodando simultaneamente com as demais operações
realizadas pelo programa, sempre esperando receber um comunicado de desligamento. Logo, quando o programa
cliente for iniciado, essa classe específica do cliente vai rodar seu método run, responsável por, dentro
de um loop infinito, verificar se o próximo comunicado (através do espe do nosso servidor) é ou não um
comunicado de desligamento e, se for, mostrar as mensagens de encerramento e encerrar o programa. 
*/

public class TratadoraDeComunicadoDeDesligamento extends Thread
{
    private Parceiro servidor;

    public TratadoraDeComunicadoDeDesligamento (Parceiro servidor) throws Exception
    {
        if (servidor==null)
            throw new Exception ("Esta porta é inválida.");
        this.servidor = servidor;
    }

    public void run ()
    {
        for(;;)
        {
            try
            {
                if (this.servidor.espie() instanceof ComunicadoDeDesligamento)
                {
                    System.out.println ("\nO servidor vai ser desligado agora;");
                    System.err.println ("volte mais tarde!\n");
                    System.exit(0);
                }
            }
            catch (Exception erro)
            {}
        }
    }
}

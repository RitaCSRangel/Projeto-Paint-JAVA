package Servidor;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.Semaphore; //Para que possamos fazer o controle de threads
import Comunicados.Comunicado; //Para que possamos usar nossas classes de comunicados e pedidos vindas do nosso package Comunicados.

/*Resumo da Classe
Quando usada no cliente a classe parceiro representa o servidor e quando usada no servidor ela representa o cliente, por isso
o nome parceiro. Ela é uma classe em comum, ou seja, deve estar presente tanto no cliente quanto no servidor. Aqui estamos
no nosso programa paint, que é o nosso cliente, por isso temos uma classe parceiro nela, para que ela possa se comunicar com . 
o nosso outro programa chamado servidor.

- Ela possui os objetos socket, um receptor e transmissor que são usados para receber e transmitir dados.
- Possui um objeto da classe comunicado chamada próximoComunicado que vai servir para espiarmos o próximo comunicado sem 
necessariamente atendê-lo ainda. 
- O semaphore valendo 1 significa que contém apenas 1 recurso, ou seja, caso mais de um recurso seja requisitado não será 
executado até que o recurso anterior seja liberado. 1 recurso por vez, como um sinal verde para 1 pessoa de  
cada vez atravessar a rua, se uma pessoa já estiver atravessando a rua, as demais devem aguardar ela chegar ao outro lado.
- Construtor: Deve receber um socket, um transmissor e um receptor por parâmetro. Isso porque a classe parceiro é a responsável 
pela comunicação entre servidor e cliente usando a lógica da primeira aula de cliente servidor, dos telefones com fio dos quais uma. 
ponta do fio está em uma casa (cliente) e a outra ponta do fio na outra casa (servidor), os dois se comunicando pelo alto-falante
(receptor) e o microfone (trabsmissor) do telefone.
- Nesse construtor são feitos os tratamentos de excessão para caso algum desses 3 elementos comentados acima esteja faltando e se 
tudo tiver certo os parâmetros enviados são aplicados aos seus devidos campos.
- Temos por fim 4 métodos:

--------------------------------------------------

O método receba: 
- Ao contrário do que parece o receba não é um método para receber, ele é um método de comando. Ele vai mandar o parceiro receber,
ou seja, ele está transmitindo algo para o outro parceiro receber.  
- Como o receba é responsável por enviar informações ele, ao contrário dos demais, possui um parâmetro da classe comunicado que 
chamamos de x. 
- Ele envia o transmissor com o writeobject e depois da um flush para que esse envio seja instantâneo. 

--------------------------------------------------

O método espie:
- Serve ara saber o que foi mandado sem consumir o que foi mandado. 
- Requisita um recurso do mutEx, vira sinal vermelho para que mais nada seja feito até isso terminar.
- Verifica se o comunicado é nulo (o que inicialmente é) e se for ele vai deixar de ser nulo recebendo o na classe comunicado.
- Libera o sinal do mutEx. 
- Retorna o próximo comunicado para que o programa possa espiar. 
- Se ao chamar espie ele não for nulo não precisamos fazer ele receber o objeto da classe comunicado porque ele já tem um
então já retorna o comunicado para espiarmos.  

-------------------------------------------------

O método envie:
- Ao contrário do que parece o envie também é um comando, ele vai mandar o parceiro enviar, ou seja, ele está recebendo o que o 
outro parceiro está enviando. 
- Ele consome o que está sendo enviado, ele pega e usa. 
- Se o proximocomunicado for nulo ele faz o mesmo que espie, faz com que deixe de ser nulo ao ler o que está contido no objeto de
comunicado. 
- Se não for nulo, ou seja, se já havia um comunicado ali ele guarda o conteúdo no ret, limpa o próximo comunicado e retorna ret. 
Ou seja, recebemos e limpamos o comunicado recebido para recebermos outros dali para frente. 

-----------------------------------------------

O método adeus:
- Fecha o servidor encerrando o socket, transmissor e receptor. 
*/

public class Parceiro
{
    private Socket             conexao;
    private ObjectInputStream  receptor;
    private ObjectOutputStream transmissor;
    
    private Comunicado proximoComunicado=null;

    private Semaphore mutEx = new Semaphore (1,true);

    public Parceiro (Socket             conexao,
                     ObjectInputStream  receptor,
                     ObjectOutputStream transmissor)
                     throws Exception 
    {
        if (conexao==null)
            throw new Exception ("Conexao ausente");

        if (receptor==null)
            throw new Exception ("Receptor ausente");

        if (transmissor==null)
            throw new Exception ("Transmissor ausente");

        this.conexao     = conexao;
        this.receptor    = receptor;
        this.transmissor = transmissor;
    }

    public void receba (Comunicado x) throws Exception
    {
        try
        {
            this.transmissor.writeObject (x);
            this.transmissor.flush       ();
        }
        catch (IOException erro)
        {
            throw new Exception ("Erro de transmissao");
        }
    }

    public Comunicado espie () throws Exception
    {
        try
        {
            this.mutEx.acquireUninterruptibly();
            if (this.proximoComunicado==null) this.proximoComunicado = (Comunicado)this.receptor.readObject();
            this.mutEx.release();
            return this.proximoComunicado;
        }
        catch (Exception erro)
        {
            throw new Exception ("Erro de recepcao");
        }
    }

    public Comunicado envie () throws Exception
    {
        try
        {
            if (this.proximoComunicado==null) this.proximoComunicado = (Comunicado)this.receptor.readObject();
            Comunicado ret         = this.proximoComunicado;
            this.proximoComunicado = null;
            return ret;
        }
        catch (Exception erro)
        {
            throw new Exception ("Erro de recepcao: "+erro.getMessage());
        }
    }

    public void adeus () throws Exception
    {
        try
        {
            this.transmissor.close();
            this.receptor   .close();
            this.conexao    .close();
        }
        catch (Exception erro)
        {
            throw new Exception ("Erro de desconexao");
        }
    }
}

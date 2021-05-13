package Comunicados;

/*Resumo da Classe:
- Herda de Comunicado, ou seja, herda de serializable. 
- Nossa classe PedidoDeAbertura faz exatamente como o nome sugere, ela faz o pedido de abertura de um desenho
presente no banco de dados. 
- Nela temos os campos String chamados 
    - nome para que o nome do desenho possa ser encontrado no Banco
    - id para que o cliente que criou o desenho possa ser encontrado no Banco
- Nosso construtor recebe essas informações e as assimila nos campos adequados.
- Temos os métodos:
    - Setters e Getters
    - ToString
*/

public class PedidoDeAbrir extends Comunicado
{
    private String nome;
    private String id;
    
    //CONSTRUTOR
    public PedidoDeAbrir(String nomeDesenho, String idCliente) {
        this.nome = nomeDesenho;
        this.id = idCliente;
    }
    
    //SETTERS
    
    public void setNomeDesenho(String nome) {
        this.nome = nome;
    }
	    
    public void setIdCliente (String id) {
        this.id = id;
    }

    //GETTERS
    
    public String getNomeDesenho() {
        return this.nome;
    }
	    
    public String getIdCliente() {
        return this.id;
    }
}

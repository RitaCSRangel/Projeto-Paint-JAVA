package Comunicados;
import java.util.Vector;
import Comunicados.ComunicadoDeDesenho;

/*Resumo da Classe:
- Herda de Comunicado, ou seja, herda de serializable. 
- Nossa classe PedidoDeConsulta faz exatamente como o nome sugere, ela faz o pedido de abertura consulta das informações
de um desenho presente no banco de dados. 
- Nela temos os campos:
    - figurinhas, que é o vetor de figuras/desenhos que usaremos
    - nome para guardar o nome do desenho 
    - id para especificar quem é o cliente que criou o desenho 
- Nosso construtor recebe essas informações e as assimila nos campos adequados.
- Temos os métodos:
    - Setters e Getters
    - ToString
*/

public class PedidoDeSalvar extends Comunicado
{
    private Vector<String> figurinhas;
    private String nome;
    private String id;
    
    //CONSTRUTOR
    public PedidoDeSalvar(ComunicadoDeDesenho desenho)
    {
        this.figurinhas= desenho.getDesenho();
        this.nome = desenho.getNome();
        this.id = desenho.getID();
    }
    
    //SETTERS
    
    public void setNome(String nome) {
    	this.nome = nome;
    }
    
    public void setID (String id) {
    	this.id = id;
    }
    
    //GETTERS
    
    public Vector<String> getFigura(){
    	return this.figurinhas;	
    }
    
    public String getNome() {
    	return this.nome;
    }
    
    public String getID() {
    	return this.id;
    }
    
    //-----------------------------------
    
    public String toString ()
    {
        return (""+this.nome);
    }
}

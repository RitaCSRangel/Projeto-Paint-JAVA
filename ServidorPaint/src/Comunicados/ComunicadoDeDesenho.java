package Comunicados;
import java.util.Vector;

/*Resumo da Classe:
- Herda de Comunicado, ou seja, herda de serializable. 
- Nossa classe ComunicadoDesenho é a responsável por fazer a transmissão dos nossos objetos de desenho.
- Nela temos os campos de String chamados 
    - nome para armazenar o nome do nosso desenho
    - id para armazenar o ID do cliente que estará guardando esse desenho no nosso banco de dados. 
- Nosso construtor será vazio pois não precisamos dele para receber ou contruir nada.
- Temos os métodos:
    - addDesenho: Adiciona a figura recebida no nosso vetor figurinhass.
    - Setters e Getters
    - ToString
*/

public class ComunicadoDeDesenho extends Comunicado
{
    private double valorResultante; //para usar no toString
    private Vector<String> figurinhas = new Vector<String>();
    private String nome;
    private String id;
    
    //CONSTRUTOR
    public ComunicadoDeDesenho ()
    {
    	
    }
    
    public void addDesenho (String figura) throws Exception
    {
    	figurinhas.add(figura);
    }
    
    //SETTERS 
    
    public void setDesenho(Vector<String> figura) {
    	this.figurinhas = figura;
    }
    
    public void setNome(String nome) {
    	this.nome = nome;
    }
    
    public void setID (String id) {
    	this.id = id;
    }
    //GETTERS
    
    public Vector<String> getDesenho(){
    	return this.figurinhas;	
    }
    
    public String getNome() {
    	return this.nome;
    }
    
    public String getID() {
    	return this.id;
    }
    
    //TOSTRING
    
    public String toString ()
    {
        return (""+this.valorResultante);
        
    }

}

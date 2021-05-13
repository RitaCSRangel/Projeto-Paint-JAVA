package Comunicados;
import java.util.Vector;

/*Resumo da Classe:
- Herda de Comunicado, ou seja, herda de serializable. 
- Nossa classe representa a resposta enviada pelo servidor quando um pedido de consultar for recebido. 
- Nela temos os campos:
    - figuras, que é o vetor de figuras/desenhos que usaremos
- Nosso construtor recebe essas informações e as assimila nos campos adequados.
- Temos os métodos Setters e Getters
*/

public class RespostaDeConsultar extends Comunicado
{
    private Vector<ComunicadoDeConsulta> figuras;

//CONSTRUTOR
public RespostaDeConsultar(Vector<ComunicadoDeConsulta> desenhos) {
    this.figuras= desenhos;
}

//SETTERS

public void setDesenhos(Vector<ComunicadoDeConsulta> desenhos) {
    this.figuras = desenhos;
}

//GETTERS
public Vector<ComunicadoDeConsulta> getDesenhos() {
    return figuras;
}
	
}

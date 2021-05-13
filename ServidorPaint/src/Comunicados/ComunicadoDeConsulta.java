package Comunicados;

/*Resumo da Classe:
- Herda de Comunicado, ou seja, herda de serializable. 
- Nossa classe ComunicadoConsulta é a responsável por fazer a consulta com o banco de dados sobre informações dos desenhos
armazenados.
- Nela temos os campos String chamados 
    - nome para consultar o nome do nosso desenho
    - autor para consultar quem fez o desenho
    - ultimaData para consultar quando o desenho foi aberto pela última vez
    - dataCria para consultar a data de criação do desenho
- Nosso construtor recebe essas informações e as assimila nos campos adequados.
- Temos os métodos:
    - Setters e Getters
    - ToString
*/

public class ComunicadoDeConsulta extends Comunicado
{
    private String nome;
    private String autor;
    private String dataCria;
    private String ultimaData;
    
    //CONSTRUTOR
    public ComunicadoDeConsulta (String nome, String autor, String dataC, String dataU) throws Exception
    {
        this.setNome (nome);
        this.setAutor (autor);
        this.setDataCria (dataC);
        this.setUltimaData (dataU);
    }
    
    //SETTERS
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void setAutor(String autor) {
        this.autor = autor;
    }
    
    public void setDataCria (String dataC) {
        this.dataCria = dataC;
    }
    
    public void setUltimaData (String dataU) {
        this.ultimaData = dataU;
    }
    
    //GETTERS
    
    public String getNome() {
        return nome;
    }

    public String getAutor() {
        return autor;
    }
    
    public String getDataCria () {
        return dataCria;
    }
    
    public String getUltimaData() {
        return ultimaData;
    }

}
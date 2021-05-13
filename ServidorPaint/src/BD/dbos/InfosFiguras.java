package BD.dbos;

public class InfosFiguras implements Cloneable
{
    private String nome;
    private String autor;
    private String dataCria;
    private String ultimaData;
    
    //CONSTRUTOR
    public InfosFiguras (String nome,String dataCria, String ultimaData,String autor) throws Exception
    {
        this.setNome(nome);
        this.setDataCria(dataCria);
        this.setUltimaData(ultimaData);
        this.setAutor(autor);
    }
    
    //MODELO
    public InfosFiguras (InfosFiguras modelo) throws Exception
    {
        this.nome = modelo.nome; 
        this.dataCria   = modelo.dataCria; 
        this.ultimaData  = modelo.ultimaData;  
        this.autor  = modelo.autor;  
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
    
    //TO STRING
    public String toString ()
    {
        String ret="";

        ret+="NomeArquivo: "+this.nome +"\n";
        ret+="DataCriacao: "+this.dataCria  +"\n";
        ret+="DataUltimaAtt: "+this.ultimaData  +"\n";
        ret+="AutorDesenho: "+this.autor;

        return ret;
    }
    
    //EQUALS
    public boolean equals (Object obj)
    {
        if (this==obj)
            return true;

        if (obj==null)
            return false;

        if (!(obj instanceof InfosFiguras))
            return false;

        InfosFiguras l = (InfosFiguras)obj;

        if (this.nome.equals(l.nome))
            return false;

        if (this.dataCria.equals(l.dataCria))
            return false;

        if (this.ultimaData.equals(l.ultimaData))
            return false;
        
        if (this.autor.equals(l.autor))
            return false;

        return true;
    }
    
    //HASH
    public int hashCode ()
    {
        int ret=666;

        ret = 7*ret + this.nome.hashCode();
        ret = 7*ret + this.dataCria.hashCode();
        ret = 7*ret + this.ultimaData.hashCode();
        ret = 7*ret + this.autor.hashCode();

        return ret;
    }
    
    //CLONE
    public Object clone ()
    {
        InfosFiguras ret=null;

        try
        {
            ret = new InfosFiguras (this);
        }
        catch (Exception erro)
        {}
        return ret;
    }
}
package BD.dbos;

public class ItemFiguras implements Cloneable
{

    private String nome;
    private String id;
    private String item;
    
    //CONSTRUTOR
      public ItemFiguras (String nome,String id, String item) throws Exception
    {
        this.setNome(nome);
        this.setID(id);
        this.setItem(item);

    }
    
    //MODELO
    public ItemFiguras (ItemFiguras modelo) throws Exception
    {
        this.nome = modelo.nome; 
        this.id   = modelo.id;  
        this.item  = modelo.item;  

    }
    
    //SETTERS
    public void setNome(String nome) {
	this.nome = nome;
    }
    
    public void setID (String id) {
	this.id = id;
    }
    
    public void setItem (String item) {
	this.item = item;
    }
    
    //GETTERS
    public String getNome() {
	return nome;
    }

    public String getID() {
	return id;
    }

    public String getItem() {
        return item;
    }

    //TO STRING
    public String toString ()
    {
        String ret="";

        ret+="NomeArquivoo: "+this.nome+"\n";
        ret+="IDCliente: "+this.id  +"\n";
        ret+="Item: "+this.item  +"\n";

        return ret;
    }
    
    //EQUALS
    public boolean equals (Object obj)
    {
        if (this==obj)
            return true;

        if (obj==null)
            return false;

        if (!(obj instanceof ItemFiguras))
            return false;

        ItemFiguras l = (ItemFiguras)obj;

        if (this.nome.equals(l.nome))
            return false;

        if (this.id.equals(l.id))
            return false;

        if (this.item.equals(l.item))
            return false;
        

        return true;
    }
    
    //HASH
    public int hashCode ()
    {
        int ret=666;

        ret = 7*ret + this.nome.hashCode();
        ret = 7*ret + this.id.hashCode();
        ret = 7*ret + this.item.hashCode();

        return ret;
    }
    
    //CLONE
    public Object clone ()
    {
        ItemFiguras ret=null;

        try
        {
            ret = new ItemFiguras (this);
        }
        catch (Exception erro)
        {} 

        return ret;
    }
}
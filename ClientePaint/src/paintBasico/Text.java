package paintBasico;
import java.awt.*;
import java.util.*;
 
public class Text extends Figura {
    protected Ponto inicio;
    protected String   string;
    protected  Font fonte;
   
    protected int xi, yi;
       
    public Text (int x, int y,String texto, Font fonte) throws Exception{
        this (x, y, texto, Color.BLACK, Color.white, fonte);
    }
       
    public Text (int x, int y, String texto, Color cor, Color cor2, Font ft) throws Exception{
        super (cor, cor2);
       
        this.xi = x;
        this.yi = y;
 
        this.inicio  = new Ponto (x,y);
        this.string   = texto;
        this.fonte    = ft;
    }
 
    public Text (String s) throws Exception{
        StringTokenizer quebrador = new StringTokenizer(s,":");
 
        quebrador.nextToken();
 
        int   x   = Integer.parseInt(quebrador.nextToken());
        int   y   = Integer.parseInt(quebrador.nextToken());
 
        Color cor = new Color (Integer.parseInt(quebrador.nextToken()),  
                               Integer.parseInt(quebrador.nextToken()),  
                               Integer.parseInt(quebrador.nextToken())); 
         
        Color cor2 = new Color (Integer.parseInt(quebrador.nextToken()),  
                                                Integer.parseInt(quebrador.nextToken()),  
                                                Integer.parseInt(quebrador.nextToken())); 
 
        this.inicio   = new Ponto (x,y,cor, cor2);
        this.cor      = cor;
        this.preen    = cor2;
    }
 
    public void setInicio (int x, int y) throws Exception{
        this.inicio = new Ponto (x,y,this.getCor(), this.preen);
    }
 
    public void setText (String texto) {
        this.string = texto;
    }
       
    public Ponto getInicio () {
        return this.inicio;
    }
 
    public String setText () {
        return this.string;
    }
 
    public void torneSeVisivel (Graphics g) {                          
            g.setColor (this.cor);
            g.setFont(this.fonte);
            g.drawString(this.string, this.inicio.getX(), this.inicio.getY());            
    }
 
    public String toString() {
        return "t:" +
               this.xi +
               ":" +
               this.yi +
               ":" +
               this.string +
               ":" +
               this.getCor().getRed() +
               ":" +
               this.getCor().getGreen() +
               ":" +
               this.getCor().getBlue() +
                ":" +
               this.getPreen().getRed() +
               ":" +
               this.getPreen().getGreen() +
               ":" +
               this.getPreen().getBlue() +
               ":" +
               this.fonte.getSize() +
               ":" +
               this.fonte.getFamily() +
               ":" +
               this.fonte.getStyle();
    }
    
    public boolean cliquePertence(int x, int y) {                      
        if(x >= this.inicio.getX() && y <= this.inicio.getY()) {
            if((Math.sqrt(Math.pow(this.inicio.getX() - x,2)) <= ((this.fonte.getSize()/2) * this.string.length()))) {
                    if((Math.sqrt(Math.pow(this.inicio.getY() - y,2))) <= (this.fonte.getSize()/2)) {
                            return true;
                    }
                    else {
                            return false;
                    }
            }
            else {
                return false;
            }
        }
        else{
            return false;
        }
   
    }
    
    public void move(int x, int y) {
        this.inicio.setX(x);
        this.inicio.setY(y);
    }
    public int hashCode() {
    	int resultado=1;
    	resultado = resultado*7 +
    			this.xi;
    	resultado = resultado*7 +
				this.yi;
    	
    	return resultado;
    }
    
}
package paintBasico;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;

public class Ponto extends Figura
{
    private int x,  y;

    public Ponto (int x, int y) throws Exception
    {
        this (x, y, Color.BLACK);
    }
	  
    public Ponto (int x, int y, Color cor) throws Exception
    {
        super (cor);

  	this.x = x;
        this.y = y;
    }
    
    public Ponto (int x, int y, Color cor, Color preen) throws Exception
    {
        super (cor);

  	this.x = x;
        this.y = y;
    }

    public Ponto (String s) throws Exception
    {
        StringTokenizer quebrador = new StringTokenizer(s,":");

        quebrador.nextToken();

        this.x = Integer.parseInt(quebrador.nextToken());
        this.y = Integer.parseInt(quebrador.nextToken());

        this.cor = new Color (Integer.parseInt(quebrador.nextToken()),  
                              Integer.parseInt(quebrador.nextToken()),  
                              Integer.parseInt(quebrador.nextToken())); 
    }

    public void setX (int x)
    {
        this.x = x;
    }
	  
    public void setY (int y)
    {
        this.y = y;
    }
	  
    public int getX ()
    {
        return this.x;
    }
	  
    public int getY ()
    {
  	return this.y;
    }
	  
    public void torneSeVisivel (Graphics g)
    {
  	g.setColor (this.cor);
  	g.drawLine (this.x,this.y,this.x,this.y);
    }

    public String toString()
    {
        return "p:" +
               this.x +
               ":" +
               this.y +
               ":" +
               this.getCor().getRed() +
               ":" +
               this.getCor().getGreen() +
               ":" +
               this.getCor().getBlue();
    }
    
    public boolean cliquePertence (int x, int y){
        int HIT_BOX_SIZE = 10;
        
        Point2D ponto_aux;
        ponto_aux = new Point2D.Double();
        ponto_aux.setLocation((int)this.x, (int)this.y);
        
        if(Math.abs(x-this.x) <= HIT_BOX_SIZE && Math.abs(y-this.y) <= HIT_BOX_SIZE) {
            return true;
        }
        
        return false;
    }
    
    public void move (int x, int y){
        this.setX(x);
        this.setY(y);
    }

}

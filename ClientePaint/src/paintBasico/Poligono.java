package paintBasico;
import java.awt.*;
import java.util.*;
 
public class Poligono extends Figura
{
    protected int xiz[] = new int[99];
    protected int ypsilom[] = new int[99];
   
    protected int vert;
   
    protected int       x[]={12, 120, 300};
    protected int       y[]={30, 300, 50};
       
    public Poligono (int x[], int y[], int n) throws Exception
    {
        this (x, y, n, Color.BLACK, Color.white);
    }
       
    public Poligono (int x[], int y[], int n, Color cor, Color cor2) throws Exception
    {
        super (cor, cor2);
       
        for(int i = 0; i < n; i++){
            xiz[i] = x[i];
            ypsilom[i] = y[i];
        }
       
        vert = n;
 
    }
 
    public Poligono (String s) throws Exception
    {
        StringTokenizer quebrador = new StringTokenizer(s,":");
 
        quebrador.nextToken();
 
        int   x   = Integer.parseInt(quebrador.nextToken());
        int   y   = Integer.parseInt(quebrador.nextToken());
 
        int   w   = Integer.parseInt(quebrador.nextToken());
        int   h   = Integer.parseInt(quebrador.nextToken());
       
        //auxiliares
 
        Color cor = new Color (Integer.parseInt(quebrador.nextToken()), 
                               Integer.parseInt(quebrador.nextToken()),  
                               Integer.parseInt(quebrador.nextToken())); 
         
        Color cor2 = new Color (Integer.parseInt(quebrador.nextToken()), 
                                                Integer.parseInt(quebrador.nextToken()), 
                                                Integer.parseInt(quebrador.nextToken())); 
        this.cor      = cor;
        this.preen     = cor2;
       
    }
 
    public void torneSeVisivel (Graphics g)
    {
            g.setColor(this.preen);
            g.fillPolygon(xiz, ypsilom, vert);
            g.setColor (this.cor);
            g.drawPolygon(xiz, ypsilom, vert);
 
    }
 
    public String toString()
    {
        String ponto = "";
        for(int i = 0; i < vert; i++){
            ponto = ponto + xiz[i] + ":" + ypsilom[i] + ":";
        }
        return "g:" +
                vert +
                 ":" +
                ponto +
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
               this.getPreen().getAlpha();
    }
    
    public boolean cliquePertence(int x, int y){
    	int maxX=0, minX=9999, maxY=0, minY=9999;
    	for(int i = 0; i < vert; i++){
    		if(xiz[i] > maxX){
    			maxX = xiz[i];
    		}
    		if(ypsilom[i] > maxY){
    			maxY = ypsilom[i];
    		}
    		if(xiz[i] < minX){
    			minX = xiz[i];
    		}
    		if(ypsilom[i] < minY){
    			minY = ypsilom[i];
    		}
    	}
    	
    	if((x>minX && x<maxX) && (y>minY && y<maxY)){
    		return true;
    	}
    	else{
    		return false;
    	}
    }
    
    public void move(int x, int y){
        if(x > this.xiz[0]){
            for(int i=0;i<=this.vert;i++){
                this.xiz[i] = this.xiz[i]+8;
            }
        }
        if(y > this.ypsilom[0]){
            for(int i=0;i<=this.vert;i++){
                this.ypsilom[i] = this.ypsilom[i]+8;
            }
        }
        if(x < this.xiz[0]){
            for(int i=0;i<=this.vert;i++){
                this.xiz[i] = this.xiz[i]-8;
            }
        }
        if(y < this.ypsilom[0]){
            for
                    
                    (int i=0;i<=this.vert;i++){
                this.ypsilom[i] = this.ypsilom[i]-8;
            }
        }
    }
    

}
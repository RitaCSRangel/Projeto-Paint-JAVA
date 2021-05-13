package paintBasico;
import java.awt.*;
import java.util.*;

public class Elipse extends Figura {
    protected int centrox, centroy, x1, y1, x2, y2;
    protected int raiox, raioy;
	
    public Elipse (int x, int y, int x1, int y1) throws Exception{
        this (x, y, x1, y1, Color.BLACK, Color.WHITE);
    }
    
    public Elipse (int x, int y, int x1, int y1, Color cor) throws Exception{
        this (x, y, x1, y1, cor, Color.WHITE);
    }
    
    public Elipse (int x, int y, int x0, int y0, Color cor, Color preen) throws Exception{
        super (cor,preen);
        x1 = x;
        y1 = y;
        x2 = x0;
        y2 = y0;
        this.raiox = (int)Math.abs(x-x0)/2;
        this.raioy = (int)Math.abs(y-y0)/2;
        
        if(y0 > y){
            if(x0 > x){
                this.centrox = (x+raiox);
                this.centroy = (y+raioy);
            }
            else{
                this.centrox = (x-raiox);
                this.centroy = (y+raioy);
            }
        }
        else{
            if(x0 < x){
                this.centrox = (x-raiox);
                this.centroy = (y-raioy);
            }
            else{
                this.centrox = (x+raiox);
                this.centroy = (y-raioy);
            }
        }
    }

    public Elipse (String s) throws Exception{
        StringTokenizer quebrador = new StringTokenizer(s,":");

        quebrador.nextToken();

        int   x   = Integer.parseInt(quebrador.nextToken());
        int   y   = Integer.parseInt(quebrador.nextToken());

        int   rx  = Integer.parseInt(quebrador.nextToken());
        int   ry  = Integer.parseInt(quebrador.nextToken());

        Color cor = new Color (Integer.parseInt(quebrador.nextToken()),  
                               Integer.parseInt(quebrador.nextToken()),  
                               Integer.parseInt(quebrador.nextToken()));
        
        Color preen = new Color (Integer.parseInt(quebrador.nextToken()),
                               Integer.parseInt(quebrador.nextToken()), 
                               Integer.parseInt(quebrador.nextToken()), 
                               Integer.parseInt(quebrador.nextToken())); 

        this.centrox = x;
        this.centroy = y;
        this.raiox  = rx;
        this.raioy  = ry;
        this.cor    = cor;
        this.preen = preen;
    }


    public void setCentro (Ponto centro) throws Exception{
        this.centrox = centro.getX();
        this.centroy = centro.getY();
    }
    
    public void setCentro (int x, int y) {
        this.centrox = x;
        this.centroy = y;
    }
    
    public void setCentroX (int x) {
        this.centrox = x;
    }
    
    public void setCentroY (int y) {
        this.centrox = y;
    }

    public void setRaioX (int raiox) {
        this.raiox = raiox;
    }

    public void setRaioY (int raioy) {
        this.raioy = raioy;
    }
    

    public Ponto getCentro () throws Exception {
        Ponto ponto = new Ponto(this.centrox, this.centroy, this.cor);
        return ponto;
    }

    public int getRaioX () {
        return this.raiox;
    }

    public int getRaioY () {
        return this.raioy;
    }
    
    public void torneSeVisivel (Graphics g) {
        g.setColor (this.preen);
        g.fillOval (this.centrox-raiox, this.centroy-raioy, 2*raiox, 2*raioy);
        g.setColor (this.cor);
        g.drawOval (this.centrox-raiox, this.centroy-raioy, 2*raiox, 2*raioy);
    }

    public String toString() {
        return "e:" +
               this.x1 +
               ":" +
               this.y1 +
               ":" +
               this.x2 +
               ":" +
               this.y2 +
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
               this.getPreen().getAlpha();
    }
    
    public boolean cliquePertence (int x, int y){
        int raioAuxX, raioAuxY;
        
        raioAuxX = (int)Math.abs(x - centrox);
        raioAuxY = (int)Math.abs(y - centroy);
        
        if(raioAuxX <= this.raiox && raioAuxY <= this.raioy)
            return true;
        else
            return false;
    }
    
    public void move (int x, int y){
        this.setCentro(x,y);
    }
    

}

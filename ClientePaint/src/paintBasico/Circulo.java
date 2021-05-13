package paintBasico;
import java.awt.*;
import java.util.*;

public class Circulo extends Figura {
    protected int centrox, centroy, x1, y1, x2, y2;
    protected int   diametro;
	
    public Circulo (int x, int y, int x1, int y1, int r) throws Exception {
        this (x, y, x1, y1, Color.BLACK, Color.WHITE);
    }
	
    public Circulo (int x, int y, int x1, int y1, Color cor) throws Exception {
        this (x, y, x1, y1, cor, Color.WHITE);
        this.x1 = x1;
        this.y1 = y1;
    }
    
    public Circulo (int x, int y, int x1, int y1, Color cor, Color preen) throws Exception {
        super(cor,preen);
        this.x2 = x;
        this.y2 = y;
        this.x1 = x1;
        this.y1 = y1;
        
        
        if(Math.abs(x - x1) > Math.abs(y - y1))
            this.diametro = (int)(Math.abs(x - x1));
        else 
            this.diametro = (int)(Math.abs(y - y1));
        
        
        if(y1 > y){
            if(x1 > x){
                this.centrox = (x+diametro/2);
                this.centroy = (y+diametro/2);
            }
            else{
                this.centrox = (x-diametro/2);
                this.centroy = (y+diametro/2);
            }
        }
        else{
            if(x1 < x){
                this.centrox = (x-diametro/2);
                this.centroy = (y-diametro/2);
            }
            else{
                this.centrox = (x+diametro/2);
                this.centroy = (y-diametro/2);
            }
        }
        
    }

    public Circulo (String s) throws Exception{
        StringTokenizer quebrador = new StringTokenizer(s,":");

        quebrador.nextToken();

        int   x   = Integer.parseInt(quebrador.nextToken());
        int   y   = Integer.parseInt(quebrador.nextToken());

        int   d   = Integer.parseInt(quebrador.nextToken());

        Color cor = new Color (Integer.parseInt(quebrador.nextToken()), 
                               Integer.parseInt(quebrador.nextToken()),  
                               Integer.parseInt(quebrador.nextToken())); 
        
        Color preen = new Color (Integer.parseInt(quebrador.nextToken()),  
                               Integer.parseInt(quebrador.nextToken()),  
                               Integer.parseInt(quebrador.nextToken()),  
                               Integer.parseInt(quebrador.nextToken())); 

        this.centrox = x;
        this.centroy = y;
        this.diametro  = d;
        this.cor    = cor;
        this.preen = preen;
    }


    public void setCentro (Ponto centro) throws Exception{
        this.centrox = centro.getX();
        this.centroy = centro.getY();
    }
    
    public void setCentroX (int x) {
        this.centrox = x;
    }
    
    public void setCentroY (int y) {
        this.centroy = y;
    }

    public void setRaio (int r) {
        this.diametro = r*2;
    }
    
    public void setDiametro (int d) {
        this.diametro = d;
    }


    public Ponto getCentro() throws Exception{
        Ponto centro = new Ponto(this.centrox, this.centroy);
        return centro;
    }
    
    public int getCentroX () {
        return this.centrox;
    }
    
    public int getCentroY () {
        return this.centroy;
    }
    
    public int getRaio () {
        return this.diametro/2;
    }
    
    public int getDiametro () {
        return this.diametro;
    }

    public void torneSeVisivel (Graphics g) {
        int upperLeftX = centrox-diametro/2;
        int upperLeftY = centroy-diametro/2;
        
        g.setColor (this.preen);
        g.fillOval (upperLeftX, upperLeftY, diametro, diametro);
        g.setColor (this.cor);
        g.drawOval (upperLeftX, upperLeftY, diametro, diametro);
    }

    public String toString() {
        return "c:" +
               this.x2 +
               ":" +
               this.y2 +
               ":" +
               this.x1 +
               ":" +
               this.y1 +
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
        int raioAux;
        if(Math.abs(x - centrox) > Math.abs(y - centroy))
            raioAux = (int)Math.abs(x - centrox);
        else
            raioAux = (int)Math.abs(y - centroy);
        
        if(raioAux < (this.diametro / 2))
            return true;
        else
            return false;
            
    }
    
    public void move(int x, int y)    {
    	this.setCentroX(x);
    	this.setCentroY(y);
    }
    
}

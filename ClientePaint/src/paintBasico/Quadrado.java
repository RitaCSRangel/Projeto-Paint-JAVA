package paintBasico;
import java.awt.*;
import java.util.*;

public class Quadrado extends Figura {
    protected int x[] = new int[4];
    protected int y[] = new int[4];
    
    protected int lado;
	
    public Quadrado (int x[], int y[]) throws Exception{
        this (x, y, Color.BLACK, Color.WHITE);
    }
	
    public Quadrado (int x[], int y[], Color cor) throws Exception{
        this (x, y, cor, Color.WHITE);
    }
    
    public Quadrado (int x[], int y[], Color cor, Color preen) throws Exception{
        super(cor,preen);

        if(Math.abs(x[0] - x[1]) > Math.abs(y[0] - y[1]))
            this.lado = (int)(Math.abs(x[0] - x[1]));
        else 
            this.lado = (int)(Math.abs(y[0] - y[1]));
        
        this.x[0] = x[0]; this.y[0] = y[0]; 
        if(y[1] > y[0]){
            if(x[1] > x[0]){
                this.x[1] = x[0] + this.lado; this.y[1] = y[0]; 
                this.x[2] = x[0] + this.lado; this.y[2] = y[0] + this.lado; 
                this.x[3] = x[0];             this.y[3] = y[0] + this.lado; 
            }
            else{
                this.x[1] = x[0];             this.y[1] = y[0] + this.lado; 
                this.x[2] = x[0] - this.lado; this.y[2] = y[0] + this.lado; 
                this.x[3] = x[0] - this.lado; this.y[3] = y[0];
            }
        }
        else{
            if(x[1] < x[0]){

                this.x[1] = x[0] - this.lado; this.y[1] = y[0]; 
                this.x[2] = x[0] - this.lado; this.y[2] = y[0] - this.lado; 
                this.x[3] = x[0];             this.y[3] = y[0] - this.lado; 
            }
            else{

                this.x[1] = x[0];             this.y[1] = y[0] - this.lado; 
                this.x[2] = x[0] + this.lado; this.y[2] = y[0] - this.lado; 
                this.x[3] = x[0] + this.lado; this.y[3] = y[0]; 
            }
        }
    }

    public Quadrado (String q) throws Exception{
        StringTokenizer quebrador = new StringTokenizer(q,":");

        quebrador.nextToken();
        int x[] = new int[3];
        int y[] = new int[3];

        x[0]   = Integer.parseInt(quebrador.nextToken());
        y[0]   = Integer.parseInt(quebrador.nextToken());
        x[1]   = Integer.parseInt(quebrador.nextToken());
        y[1]   = Integer.parseInt(quebrador.nextToken());
        x[2]   = Integer.parseInt(quebrador.nextToken());
        y[2]   = Integer.parseInt(quebrador.nextToken());
        x[3]   = Integer.parseInt(quebrador.nextToken());
        y[3]   = Integer.parseInt(quebrador.nextToken());

        int   lado   = Integer.parseInt(quebrador.nextToken());

        Color cor = new Color (Integer.parseInt(quebrador.nextToken()), 
                               Integer.parseInt(quebrador.nextToken()),  
                               Integer.parseInt(quebrador.nextToken())); 
        
        Color preen = new Color (Integer.parseInt(quebrador.nextToken()), 
                               Integer.parseInt(quebrador.nextToken()),  
                               Integer.parseInt(quebrador.nextToken()),  
                               Integer.parseInt(quebrador.nextToken())); 
        
        this.x[0] = x[0]; this.x[1] = x[1]; this.x[2] = x[2]; this.x[3] = x[3];
        this.y[0] = y[0]; this.y[1] = y[1]; this.y[2] = y[2]; this.y[3] = y[3];
        this.cor    = cor;
        this.preen  = preen;
    }

    public void setP0 (int x, int y) {
        this.x[0] = x;
        this.y[0] = y;
    }
    
    public void setPontos (int x[], int y[]) {
        this.x = x;
        this.y = y;
    }

    public void setLado (int lado) {
        this.lado = lado;
    }
    
    public int getLado () {
        return this.lado;
    }

    public Ponto getP0 () throws Exception{
        Ponto P0 = new Ponto(this.x[0], this.y[0]);
        return P0;
    }

    public void torneSeVisivel (Graphics g) {
        g.setColor (this.preen);
        g.fillPolygon (x, y, 4);
        g.setColor (this.cor);
        g.drawPolygon (x, y, 4);
    }

    public String toString() {
        return "q:" +
               this.x[0] +
               ":" +
               this.y[0] +
               ":" +
               this.x[1] +
               ":" +
               this.y[1] +
               ":" +
               this.x[2] +
               ":" +
               this.y[2] +
               ":" +
               this.x[3] +
               ":" +
               this.y[3] +
               ":" +
               this.lado +
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
    
    public boolean cliquePertence (int xiz, int ypilisom){
        Polygon poligono_aux = new Polygon(x, y, 4);
        if(poligono_aux.contains((double)xiz, (double)ypilisom))
            return true;
        else
            return false;
    }
    
    public void move (int x, int y){
        int x1[] = new int [4];
        int y1[] = new int [4];
        
        x1[0] = x - this.lado/2;  y1[0] = y + this.lado/2;
        x1[1] = x + this.lado/2;  y1[1] = y + this.lado/2;
        x1[2] = x + this.lado/2;  y1[2] = y - this.lado/2;
        x1[3] = x - this.lado/2;  y1[3] = y - this.lado/2;
        
        
        setPontos(x1,y1);
    }
    
}

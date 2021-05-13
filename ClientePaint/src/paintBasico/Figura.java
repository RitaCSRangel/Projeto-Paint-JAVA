package paintBasico;
import java.awt.*;

public abstract class Figura 
{
    protected Color cor;
    protected Color preen;

    protected Figura () {
        this (Color.BLACK);
    }

    protected Figura (Color cor) {
        this (cor, Color.WHITE);
    }

    protected Figura (Color cor, Color preen) {
        this.setCor (cor, preen);
    }

    public void setCor (Color cor, Color preen) {
        this.cor = cor;
        this.preen = preen;
    }

    public Color getCor() {
        return this.cor;
    }

    public Color getPreen() {
        return this.preen;
    }

  //public abstract Object  clone          ();
  //public abstract boolean equals         (Object obj);
  //public abstract int     hashCode       ();
    public abstract String  toString       ();
    public abstract void    torneSeVisivel (Graphics g);
    public abstract boolean cliquePertence (int x, int y);
    public abstract void    move           (int x, int y) throws Exception;
}

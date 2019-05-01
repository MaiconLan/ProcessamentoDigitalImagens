package model;

import java.util.ArrayList;
import java.util.List;

public class Pixel {
    public Double r,g,b;
    public int x,y;

    private List<Pixel> vizinhosC = new ArrayList<>();
    private List<Pixel> vizinhosX = new ArrayList<>();
    private List<Pixel> vizinhos3 = new ArrayList<>();

    public Pixel(Double r, Double g, Double b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Pixel(Double r, Double g, Double b, int x, int y) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.x = x;
        this.y = y;
    }

    public Double getR() {
        return r;
    }

    public void setR(Double r) {
        this.r = r;
    }

    public Double getG() {
        return g;
    }

    public void setG(Double g) {
        this.g = g;
    }

    public Double getB() {
        return b;
    }

    public void setB(Double b) {
        this.b = b;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public List<Pixel> getVizinhosC() {
        return vizinhosC;
    }

    public void setVizinhosC(List<Pixel> vizinhosC) {
        this.vizinhosC = vizinhosC;
    }

    public List<Pixel> getVizinhosX() {
        return vizinhosX;
    }

    public void setVizinhosX(List<Pixel> vizinhosX) {
        this.vizinhosX = vizinhosX;
    }

    public List<Pixel> getVizinhos3() {
        return vizinhos3;
    }

    public void setVizinhos3(List<Pixel> vizinhos3) {
        this.vizinhos3 = vizinhos3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pixel pixel = (Pixel) o;

        if (r != null ? !r.equals(pixel.r) : pixel.r != null) return false;
        if (g != null ? !g.equals(pixel.g) : pixel.g != null) return false;
        return b != null ? b.equals(pixel.b) : pixel.b == null;

    }

    @Override
    public int hashCode() {
        int result = r != null ? r.hashCode() : 0;
        result = 31 * result + (g != null ? g.hashCode() : 0);
        result = 31 * result + (b != null ? b.hashCode() : 0);
        return result;
    }
}

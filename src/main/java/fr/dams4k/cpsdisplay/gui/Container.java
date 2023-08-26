package fr.dams4k.cpsdisplay.gui;

public class Container {
    private int x;
    private int y;
    private int width;
    private int height;

    public Container(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isInside(Component component) {
        boolean inXRange = component.getX() >= this.getX() && component.getX() < this.getX()+this.getWidth();
        boolean inYRange = component.getY() >= this.getY() && component.getY() < this.getY()+this.getHeight();
        return inXRange && inYRange;
    }

    public float[] getComponentUVPosition(Component component) {
        float x = (component.getX()-this.getX())/this.getWidth();
        float y = (component.getY()-this.getY())/this.getHeight();
        
        return new float[]{x, y};
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}

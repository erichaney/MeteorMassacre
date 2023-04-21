
public class Entity
{
    double x;
    double y;
    double vx;
    double vy;
    double width;
    double height;
    
    Entity(double x, double y, double width, double height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        
        this.vx = 0;
        this.vy = 0;
        
    }
    
    void move() 
    {
        x += vx;
        y += vy;
    }
    
    // source: https://stackoverflow.com/questions/20925818/algorithm-to-check-if-two-boxes-overlap
    boolean intervalOverlap(double start1, double end1, double start2, double end2)
    {
        return (start1 < start2 && start2 < end1) || (start2 < start1 && start1 < end2);
    }
    
    // source: https://stackoverflow.com/questions/20925818/algorithm-to-check-if-two-boxes-overlap
    boolean collidesWith(Entity other)
    {
        boolean xAxisOverlap = intervalOverlap(this.x, this.x + this.width, other.x, other.x + other.width);
        boolean yAxisOverlap = intervalOverlap(this.y, this.y + this.height, other.y, other.y + other.height);
        
        return xAxisOverlap && yAxisOverlap;
    }
}

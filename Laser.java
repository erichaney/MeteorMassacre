
public class Laser extends Entity
{
    int lifespan = 100;
    
    Laser(double x, double y, double vx, double vy)
    {
        super(x, y, 5, 5);
        
        this.vx = vx;
        this.vy = vy;
    }
}

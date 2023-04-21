import java.util.ArrayList;

public class Ship extends Entity
{
    enum Direction 
    {
        UP, DOWN, LEFT, RIGHT;
    }
    
    int hp;
    int laserCount;
    Direction direction;
    ArrayList<Laser> lasers;
   
    Ship(double x, double y)
    {
        super(x, y, 40, 40);
        
        this.hp = 5;
        this.laserCount = 6;
        
        this.direction = Direction.UP;
        this.lasers = new ArrayList<Laser>();
    }
    
    void accelerate(double horizontal, double vertical)
    {
        this.vx += horizontal;
        this.vy += vertical;
        
        if (vx > 10)
        {
            vx = 10;
        }
        
        if (vx < -10)
        {
            vx = -10;
        }
        
        if (vy > 10)
        {
            vy = 10;
        }
        
        if (vy < -10)
        {
            vy = -10;
        }
    }
    
    void shoot()
    {
        if (lasers.size() >= laserCount)
        {
            return;
        }
        
        Laser shot;
        
        if (direction == Direction.UP)
        {
            shot = new Laser(x + 20, y + 20, 0, -8);
            lasers.add(shot);
        }
        
        if (direction == Direction.DOWN)
        {
            shot = new Laser(x + 20, y + 20, 0, 8);
            lasers.add(shot);
        }
        
        if (direction == Direction.LEFT)
        {
            shot = new Laser(x + 20, y + 20,-8, 0);
            lasers.add(shot);
        }
        
        if (direction == Direction.RIGHT)
        {
            shot = new Laser(x + 20, y + 20, 8, 0);
            lasers.add(shot);
        }
        
        
    }
}

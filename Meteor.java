
public class Meteor extends Entity
{
    int hp;

    Meteor(double x, double y, double velocityMultiplier)
    {
        super(x, y, 20 + Math.random() * 30, 20 + Math.random() * 30);

        hp = 1;

        this.vx = Math.random() * 2 * velocityMultiplier;
        this.vy = Math.random() * 2 * velocityMultiplier;
        
        if (Math.random() > 0.5)
        {
            vx = -1;
        }
        
        if (Math.random() > 0.5)
        {
            vy = -1;
        }

        this.width = 20 + Math.random() * 30;
        this.height = 20 + Math.random() * 30;

    }
}

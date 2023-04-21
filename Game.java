import java.util.ArrayList;

class Game
{
    Ship player;
    ArrayList<Meteor> meteors;
    ArrayList<Entity> deadEntities;
    int points;
    int level;
    double width, height;
    Controls controls;
    boolean isGameOver;
    boolean friendlyFire = true;
    
    int shotsFired;
    int shotsHit;

    Game()
    {
        width = 800;
        height = 800;

        player = new Ship(width/2, height/2);

        points = 0;
        level = 1;

        meteors = new ArrayList<Meteor>();
        deadEntities = new ArrayList<Entity>();
        spawnMeteors(10);

        controls = new Controls();
        isGameOver = false;
        
        shotsFired = 0;
        shotsHit = 0;
    }

    void spawnMeteors(int number)
    {
        for (int i = 0; i < number; i++)
        {
            double x = Math.random() * width;
            double y = Math.random() * height;

            Meteor m = new Meteor(x, y, level);

            if (m.collidesWith(player) == false)
            {
                meteors.add(m);
            }
        }
    }

    void update()
    {
        if (isGameOver == true)
        {
            return;
        }

        updateShip();
        updateLasers();
        updateMeteors();

        checkMeteorShipCollision();
        checkLaserMeteorCollision();
        checkLaserShipCollision();

        checkLevelAdvance();
        checkGameOver();
    }

    void checkGameOver()
    {
        if (player.hp <= 0)
        {
            isGameOver = true;
        }
    }

    void checkLevelAdvance()
    {
        if (meteors.size() > 0)
        {
            return;
        }

        level++;

        spawnMeteors(10 + 2 * level);
    }

    void checkLaserShipCollision()
    {
        if (friendlyFire == false)
        {
            return;
        }

        for (Laser laser : player.lasers)
        {
            if (laser.collidesWith(player) && laser.lifespan < 80)
            {
                player.hp--;
                laser.lifespan = 0;
            }
        }
    }

    void checkLaserMeteorCollision()
    {
        for (Laser laser : player.lasers)
        {
            for (Meteor m : meteors)
            {
                if (laser.collidesWith(m))
                {
                    laser.lifespan = 0;
                    m.hp--;
                    points++;
                    shotsHit++;
                }
            }
        }
    }

    void checkMeteorShipCollision()
    {
        for (Meteor m : meteors)
        {
            if (m.collidesWith(player))
            {
                player.hp--;
                deadEntities.add(m);
            }
        }

        meteors.removeAll(deadEntities);
        deadEntities.clear();
    }

    void updateMeteors()
    {
        for (Meteor m : meteors)
        {
            m.x += m.vx;
            m.y += m.vy;

            //Wrap around the other side
            screenWrap(m);

            if (m.hp <= 0)
            {
                deadEntities.add(m);
            }
        }

        meteors.removeAll(deadEntities);
        deadEntities.clear();
    }

    void updateShip()
    {
        // move the ship
        player.move();

        // accelerate ship
        if (controls.up)
        {
            player.accelerate(0, -0.4);
            player.direction = Ship.Direction.UP;
        }

        if (controls.down)
        {
            player.accelerate(0, 0.4);
            player.direction = Ship.Direction.DOWN;
        }

        if (controls.left)
        {
            player.accelerate(-0.4, 0);
            player.direction = Ship.Direction.LEFT;
        }

        if (controls.right)
        {
            player.accelerate(0.4, 0);
            player.direction = Ship.Direction.RIGHT;
        }

        if (controls.shoot)
        {
            player.shoot();
            shotsFired++;
            controls.shoot = false;
        }

        // limit the minimum velocity
        if (Math.abs(player.vx) < 0.05)
        {
            player.vx = 0;
        }

        if (Math.abs(player.vy) < 0.05)
        {
            player.vy = 0;
        }

        screenWrap(player);
    }

    void screenWrap(Entity e)
    {
        if (e.x > width)
        {
            e.x = 0;
        }

        if (e.x < 0)
        {
            e.x = width;
        }

        if (e.y > height)
        {
            e.y = 0;
        }

        if (e.y < 0)
        {
            e.y = height;
        }
    }

    void updateLasers()
    {
        for (Laser laser : player.lasers)
        {
            laser.move();
            screenWrap(laser);

            laser.lifespan--;

            if (laser.lifespan <= 0)
            {
                deadEntities.add(laser);
            }
        }

        player.lasers.removeAll(deadEntities);
        deadEntities.clear();
    }

    double calculateAccuracy()
    {
        if (shotsFired == 0)
        {
            return 100;
        }
        else
        {
            return 100 * shotsHit / shotsFired;
        }
    }
    
    class Controls
    {
        boolean up;
        boolean down;
        boolean left;
        boolean right;
        boolean shoot;
    }
}
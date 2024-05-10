/***
 * Stores an ArrayList of asteroids, each storing a location of x and y values and a pair of distances used to move each step.
 * If it makes contact with a rocket, it shrinks in size. Asteroids are displayed as blue circles with an initial size of 20 pixels.
 */

import java.awt.*;

public class Asteroid {
    public double x, y; // current position of ‘this’ asteroid
    private double dx, dy; // displacement (delta) dx & dy for next position
    public int size = 20; // initial size 20 pixels

    //constructor sets x and y coordinates and the change in them
    public Asteroid(double ix, double iy, double idx, double idy) {
        x = ix;
        y = iy;
        dx = idx;
        dy = idy;
    }

    //changes the x and y coordinates by dx and dy, respectively, causing the asteroid to move to the next position
    public void move() {
        x += dx;
        y += dy;
    }

    //paints the asteroid blue at the x and y coordinates and 20 by 20 dimensions
    public void paint(Graphics g) { // Hey asteroid, paint yourself
        g.setColor(Color.blue); // as a blue circle
        g.drawOval((int) x, (int) y, size, size); // must be int coordinates
    }

    //shrinks if the asteroid has been hit
    public void hit() {
        size = size - 4;
    }

    //detects if asteroid coordinates are too close to another sender's coordinates
    public boolean nearTo(double tx, double ty) {
        // use Pythagorean theorem to determine distance between points
        double distance = Math.sqrt((x - tx) * (x - tx) + (y - ty) * (y - ty));
        return distance < 10;
    }

    //checks if asteroid is within the playing window
    public boolean isWithin(int width, int height) {
        return(x >= 0 && x < width && y >= 0 && y < height);
    }
}

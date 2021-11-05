import java.awt.*;
import java.util.LinkedList;

public class Sprite {

    public final static double gravity=10;

    public double gravityMultiplyer;

    public double mass;

    public double acceleration;

    public Vector vector;

    public Point position;

    public LinkedList<Force> forceList = new LinkedList<Force>();




}

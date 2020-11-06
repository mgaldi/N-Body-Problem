import java.awt.*;
import java.util.Objects;
import java.util.StringJoiner;

public class CelestialBody {

    public static final double G = 6.673e-11;

    private String name;
    private double mass;
    private double xCoord, yCoord;
    private double xVel, yVel;
    private double xAcc, yAcc;
    private int size;
    private Color color;

    public CelestialBody() {}

    public String getName() {
        return name;
    }

    public CelestialBody setName(String name) {
        this.name = name;
        return this;
    }

    public double getMass() {
        return mass;
    }

    public CelestialBody setMass(double mass) {
        this.mass = mass;
        return this;
    }

    public double getXCoord() {
        return xCoord;
    }

    public CelestialBody setXCoord(double xCoord) {
        this.xCoord = xCoord;
        return this;
    }

    public double getYCoord() {
        return yCoord;
    }

    public CelestialBody setYCoord(double yCoord) {
        this.yCoord = yCoord;
        return this;
    }

    public double getXVel() {
        return xVel;
    }

    public CelestialBody setXVel(double xVel) {
        this.xVel = xVel;
        return this;
    }

    public double getYVel() {
        return yVel;
    }

    public CelestialBody setYVel(double yVel) {
        this.yVel = yVel;
        return this;
    }

    public double getXAcc() {
        return xAcc;
    }

    public CelestialBody setXAcc(double xAcc) {
        this.xAcc = xAcc;
        return this;
    }

    public double getYAcc() {
        return yAcc;
    }

    public CelestialBody setYAcc(double yAcc) {
        this.yAcc = yAcc;
        return this;
    }

    public int getSize() {
        return size;
    }

    public CelestialBody setSize(int size) {
        this.size = size;
        return this;
    }

    public Color getColor() {
        return this.color;
    }

    public CelestialBody setColor(Color color) {
        this.color = color;
        return this;
    }
    public double distanceTo(CelestialBody body) {
        double xDist = body.xCoord - this.xCoord;
        double yDist = body.yCoord - this.yCoord;
        return Math.sqrt((xDist * xDist) + (yDist * yDist));
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CelestialBody.class.getSimpleName() + "{", "}")
                .add("name=" + this.name)
                .add("mass=" + this.mass)
                .add("xCoord=" + this.xCoord)
                .add("yCoord=" + this.yCoord)
                .add("xDir=" + this.xVel)
                .add("yDir=" + this.yVel)
                .add("xAcc=" + this.xAcc)
                .add("yAcc=" + this.yAcc)
                .add("size=" + this.size)
                .add("color=" + this.color)
                .toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof CelestialBody))
            return false;
        CelestialBody celestialBody = (CelestialBody) other;
        return  Objects.equals(celestialBody.name, this.name) &&
                celestialBody.mass == this.mass &&
                celestialBody.xCoord == this.xCoord &&
                celestialBody.yCoord == this.yCoord &&
                celestialBody.xVel == this.xVel &&
                celestialBody.yVel == this.yVel &&
                celestialBody.xAcc == this.xAcc &&
                celestialBody.yAcc == this.yAcc &&
                celestialBody.size == this.size &&
                Objects.equals(celestialBody.color, this.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, mass, xCoord, yCoord, xVel, yVel, xAcc, yAcc, size, color);
    }
}
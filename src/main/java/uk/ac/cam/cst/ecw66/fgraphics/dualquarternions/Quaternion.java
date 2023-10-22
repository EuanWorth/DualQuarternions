package uk.ac.cam.cst.ecw66.fgraphics.dualquarternions;

import java.util.Objects;

public class Quaternion implements IQuaternion {
    private final double realComponent;
    private final double iComponent;
    private final double jComponent;
    private final double kComponent;


    public Quaternion(double realComponent, double iComponent, double jComponent, double kComponent) {
        this.realComponent = realComponent;
        this.iComponent = iComponent;
        this.jComponent = jComponent;
        this.kComponent = kComponent;
    }

    @Override
    public double getRealComponent() {
        return realComponent;
    }

    @Override
    public double getiComponent() {
        return iComponent;
    }

    @Override
    public double getjComponent() {
        return jComponent;
    }

    @Override
    public double getkComponent() {
        return kComponent;
    }

    @Override
    public double magnitude() {
        return Math.sqrt(realComponent * realComponent
                + iComponent * iComponent
                + jComponent * jComponent
                + kComponent * kComponent);
    }

    @Override
    public Quaternion conjugate() {
        return new Quaternion(realComponent, -iComponent, -jComponent,-kComponent);
    }

    @Override
    public Quaternion multiply(double scalar) {
        return new Quaternion(realComponent * scalar,
                iComponent * scalar,
                jComponent * scalar,
                kComponent * scalar);
    }


    public Quaternion multiply(Quaternion that) {
        return new Quaternion(
                this.realComponent * that.realComponent
                - this.iComponent * that.iComponent
                - this.jComponent * that.jComponent
                - this.kComponent * that.kComponent,
                this.realComponent * that.iComponent
                + this.iComponent * that.realComponent
                + this.jComponent * that.kComponent
                - this.kComponent * that.jComponent,
                this.realComponent * that.jComponent
                + this.jComponent * that.realComponent
                + this.kComponent * that.iComponent
                - this.iComponent * that.kComponent,
                this.realComponent * that.kComponent
                + this.kComponent * that.realComponent
                + this.iComponent * that.jComponent
                - this.jComponent * that.iComponent
        );
    }

    public Quaternion() {
        this(0,0,0,0);
    }

    public DualQuaternion multiply(DualQuaternion that) {
        return new DualQuaternion(this, new Quaternion(0,0,0,0)
        ).multiply(that);
    }

    @Override
    public DualQuaternion multiply(IQuaternion that) {
        return null;
    }

    public Quaternion add(Quaternion that) {
        return new Quaternion(
                this.realComponent + that.realComponent,
                this.iComponent + that.iComponent,
                this.jComponent + that.jComponent,
                this.kComponent + that.kComponent
        );
    }

    public DualQuaternion add(DualQuaternion that) {
        return new DualQuaternion(this, new Quaternion());
    }

    public Quaternion add(IQuaternion that) {
        return null;
    }

    public IQuaternion subtract(IQuaternion that) {
        return this.add(that.multiply(-1f));
    }

    @Override
    public Quaternion inverse() {
        return this.conjugate().multiply(1 / this.magnitude());
    }

    @Override
    public Quaternion power(double scalar) {
        return log().multiply(scalar).exp();
    }

    @Override
    public Quaternion exp() {
        double m = magnitude();
        return new Quaternion(
                Math.cos(m), 0, 0, 0
        ).add(normalise().multiply(Math.sin(m)));
    }

    @Override
    public Quaternion log(double base) {
        return this.log().multiply(1 / Math.log(base));
    }

    @Override
    public Quaternion log() {
        return imaginary().multiply(angle());
    }

    @Override
    public double angle() {
        return 2 * Math.acos(realComponent);
    }

    @Override
    public double real() {
        return this.realComponent;
    }

    @Override
    public Quaternion imaginary() {
        return new Quaternion(0, iComponent, jComponent, kComponent);
    }

    @Override
    public boolean axis() {
        return Math.sqrt(iComponent * iComponent
                + jComponent * jComponent
                + kComponent * kComponent) == 1;
    }

    @Override
    public Quaternion normalise() {
        double m = magnitude();
        return multiply(1 / m);
    }

    @Override
    public String toString() {
        return realComponent + "+"
                + iComponent + "i+"
                + jComponent + "j+"
                + kComponent + "k";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quaternion that = (Quaternion) o;
        return realComponent == that.realComponent && iComponent == that.iComponent && jComponent == that.jComponent && kComponent == that.kComponent;
    }

    @Override
    public int hashCode() {
        return Objects.hash(realComponent, iComponent, jComponent, kComponent);
    }

    public static Quaternion fromRotation(float theta, float xaxis, float yaxis, float zaxis) {
        double axisMagnitude = Math.sqrt(
                xaxis * xaxis
                        + yaxis * yaxis
                        + zaxis * zaxis
        );
        double sTheta = Math.sin(theta);
        return new Quaternion(
                Math.cos(theta / 2),
                xaxis * sTheta / axisMagnitude,
                yaxis * sTheta / axisMagnitude,
                zaxis * sTheta / axisMagnitude
        );
    }
}

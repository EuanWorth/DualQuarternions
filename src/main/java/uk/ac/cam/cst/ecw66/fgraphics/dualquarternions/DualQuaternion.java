package uk.ac.cam.cst.ecw66.fgraphics.dualquarternions;

import java.util.Objects;

public class DualQuaternion implements IDualQuaternion {
    private final Quaternion realComponent;
    private final Quaternion dualComponent;


    public DualQuaternion(Quaternion realComponent, Quaternion imaginaryComponent) {
        this.realComponent = realComponent;
        this.dualComponent = imaginaryComponent;
    }

    public DualQuaternion(Quaternion realComponent) {
        this(realComponent, new Quaternion());
    }

    public DualQuaternion() {
        this(new Quaternion(), new Quaternion());
    }

    public double getRealComponent() {
        return realComponent.getRealComponent();
    }

    @Override
    public double getiComponent() {
        return this.realComponent.getiComponent();
    }

    @Override
    public double getjComponent() {
        return this.realComponent.getjComponent();
    }

    @Override
    public double getkComponent() {
        return this.realComponent.getjComponent();
    }

    @Override
    public double magnitude() {
        return Math.sqrt(
                Math.pow(this.realComponent.magnitude(), 2)
                + Math.pow(this.dualComponent.magnitude(), 2)
        );
    }

    @Override
    public DualQuaternion conjugate() {
        return new DualQuaternion(realComponent.conjugate(), dualComponent.conjugate());
    }

    @Override
    public DualQuaternion multiply(double scalar) {
        return new DualQuaternion(realComponent.multiply(scalar), dualComponent.multiply(scalar));
    }

    public DualQuaternion multiply(Quaternion that) {
        return multiply(new DualQuaternion(that));
    }

    public DualQuaternion multiply(DualQuaternion that) {
        return new DualQuaternion(
                this.realComponent.multiply(that.realComponent),
                this.realComponent.multiply(that.dualComponent)
                        .add(this.dualComponent.multiply(that.realComponent)
                )
        );
    }

    @Override
    public IQuaternion multiply(IQuaternion that) {
        return null;
    }

    public DualQuaternion add(Quaternion that) {
        return new DualQuaternion(this.realComponent.add(that), this.dualComponent);
    }


    public DualQuaternion add(DualQuaternion that) {
        return new DualQuaternion(this.realComponent.add(that.realComponent),
                this.dualComponent.add(that.dualComponent));
    }

    @Override
    public IQuaternion add(IQuaternion that) {
        return null;
    }

    @Override
    public IQuaternion subtract(IQuaternion that) {
        return this.add(that.multiply(-1f));
    }

    @Override
    public DualQuaternion inverse() {
        return null;
    }

    @Override
    public DualQuaternion power(double scalar) {
        return null;
    }

    @Override
    public DualQuaternion exp() {
        return null;
    }

    @Override
    public DualQuaternion log(double base) {
        return null;
    }

    @Override
    public DualQuaternion log() {
        return null;
    }

    @Override
    public double angle() {
        return this.realComponent.angle();
    }

    @Override
    public double real() {
        return this.realComponent.real();
    }

    @Override
    public Quaternion imaginary() {
        return this.realComponent.imaginary();
    }


    public Quaternion dual() {
        return this.dualComponent;
    }

    @Override
    public boolean axis() {
        return this.realComponent.axis();
    }

    @Override
    public IQuaternion normalise() {
        return this.multiply(1 / magnitude());
    }

    public Quaternion getDualComponent() {
        return dualComponent;
    }

    @Override
    public String toString() {
        return realComponent +
                "(" + dualComponent +
                ")ε";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DualQuaternion that = (DualQuaternion) o;
        return Objects.equals(realComponent, that.realComponent) && Objects.equals(dualComponent, that.dualComponent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(realComponent, dualComponent);
    }

    public static DualQuaternion fromTraslation(float tx, float ty, float tz) {
        return new DualQuaternion(new Quaternion(1, 0,0,0),
                new Quaternion(0, tx, ty, tz));
    }

    public static DualQuaternion fromRotation(float theta, float xaxis, float yaxis, float zaxis) {
        double axisMagnitude = Math.sqrt(
                xaxis * xaxis
                + yaxis * yaxis
                + zaxis * zaxis
        );
        double sTheta = Math.sin(theta);
        return new DualQuaternion(new Quaternion(
                Math.cos(theta / 2),
                xaxis * sTheta / axisMagnitude,
                yaxis * sTheta / axisMagnitude,
                zaxis * sTheta / axisMagnitude),
                new Quaternion());
    }
}

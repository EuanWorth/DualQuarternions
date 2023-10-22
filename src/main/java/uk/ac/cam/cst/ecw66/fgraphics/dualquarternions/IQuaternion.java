package uk.ac.cam.cst.ecw66.fgraphics.dualquarternions;

public interface IQuaternion {
    double getRealComponent();

    double getiComponent();

    double getjComponent();

    double getkComponent();

    double magnitude();

    IQuaternion conjugate();

    IQuaternion multiply(double scalar);

    IQuaternion multiply(IQuaternion that);


    IQuaternion add(IQuaternion that);

    IQuaternion subtract(IQuaternion that);

    IQuaternion inverse();

    IQuaternion power(double scalar);

    IQuaternion exp();

    IQuaternion log(double base);

    IQuaternion log();

    double angle();

    double real();

    IQuaternion imaginary();

    boolean axis();

    IQuaternion normalise();
}

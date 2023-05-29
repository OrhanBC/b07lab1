public class Polynomial {

    double[] coefficients;

    public Polynomial() {
        coefficients = new double[1];
		coefficients[0] = 0;
    }

    public Polynomial(double[] coefficients) {
        this.coefficients = coefficients;
    }

    public Polynomial add(Polynomial f) {
        int max = Math.max(coefficients.length, f.coefficients.length);
        double[] result = new double[max];

        for (int i = 0; i < max; i++) {
            result[i] = ((i < coefficients.length ? coefficients[i]:0)) + (((i < f.coefficients.length ? f.coefficients[i]:0)));
        }

        return new Polynomial(result);
    }

    public double evaluate(double x) {
        double result=0;
        for (int i = 0; i < coefficients.length; i++) {
            result +=  coefficients[i] * Math.pow(x, i);
        }

        return result;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0.0;
    }


}
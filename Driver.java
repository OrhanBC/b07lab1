import java.io.File;

public class Driver {
    public static void main(String [] args) throws Exception{
        
             // Create two polynomials
             double[] coefficients1 = { 1.5, 2.0, -3.7 };
             int[] exponents1 = { 2, 1, 0 };
             Polynomial polynomial1 = new Polynomial(coefficients1, exponents1);
     
             double[] coefficients2 = { -1.0, 0.5, 2.5 };
             int[] exponents2 = { 3, 1, 0 };
             Polynomial polynomial2 = new Polynomial(coefficients2, exponents2);
     
             // Display the polynomials
             polynomial1.saveToFile("polynomial1.txt");
             polynomial2.saveToFile("polynomial2.txt");
     
             // Perform addition
             Polynomial sum = polynomial1.add(polynomial2);
             sum.saveToFile("sum");
     
             // Perform multiplication
             Polynomial product = polynomial1.multiply(polynomial2);
             product.saveToFile("product.txt");
     
             // Evaluate polynomial at x = 2
             double x = 2.0;
             double result = polynomial1.evaluate(x);
             System.out.println("Evaluation at x = " + x + ": " + result);
     
             // Check if polynomial has a root at x = 1
             double root = 1.0;
             boolean hasRoot = polynomial1.hasRoot(root);
             System.out.println("Polynomial has root at x = " + root + ": " + hasRoot);
     
            
           
             

        
    }
}
    
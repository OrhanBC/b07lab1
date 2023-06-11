import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Polynomial {

    double[] coefficients;
    int[] exponents;

    public Polynomial() {
        coefficients = null;
        exponents = null;
    }

    public Polynomial(double[] coefficients, int[] exponents) {
        this.coefficients = coefficients;
        this.exponents = exponents;
    }

    public Polynomial(File file) throws Exception{

        Scanner scanner = new Scanner(file);

        if(!scanner.hasNextLine()) {
            this.coefficients = null;
            this.exponents = null;
        } else {
            String line = scanner.nextLine();

            line = line.replace("-", "+-");

            String[] poly_arr = line.split("\\+");

            coefficients = new double[poly_arr.length];
            exponents = new int[poly_arr.length];

            for(int i = 0; i < poly_arr.length; i++) {

                String[] subArray = poly_arr[i].split("x");

                coefficients[i] = Double.parseDouble(subArray[0]);

                if (subArray.length > 1) {
                    exponents[i] = Integer.parseInt(subArray[1]);
                } else {
                    exponents[i] = 0;
                }

            }
        }

        scanner.close();

    }

    public Polynomial add(Polynomial f) {

        if (exponents == null) {
            return f;
        } else if (f.exponents == null) {
            return this;
        } else if (!isValid() || !f.isValid()) {
            return new Polynomial();
        }

        // int[] mergedArray = new int[exponents.length + f.exponents.length];
        
        // for (int i = 0; i < exponents.length; i++) {
        //     mergedArray[i] = exponents[i];
        //     mergedArray[exponents.length + i] = f.exponents[i];
        // }

        // int result_length = mergedArray.length;

        // for (int i = 0; i < mergedArray.length; i++) {
        //     for (int j = 0; j < i; j++) {
        //         if (mergedArray[i] == mergedArray[j]) {
        //             result_length = result_length - 1;
        //         }
        //     }
        // }

        // int [] result_exponent = new int[result_length];

        Polynomial big = exponents.length >= f.exponents.length ? this : f;
        Polynomial small = exponents.length <= f.exponents.length ? f : this;

        if (exponents.length > f.exponents.length) {
            big = this;
            small = f;
        } else {
            big = f;
            small = this;
        }

        int[] max = big.exponents;
        int[] min = small.exponents;

        int[] isInMin = new int[max.length];

        int counter = 0;

        for (int i = 0; i < max.length; i++) {
            for (int j = 0; j < min.length; j++) {
                if (max[i] == min[j]) {
                    isInMin[i] = 1;
                    counter++;
                    break;
                }
            }
        }

        

        int resultLength = exponents.length + f.exponents.length - counter;

        int[] resultExponents = new int[resultLength];

        int idx = 0;

        for (int i = 0; i < exponents.length; i++) {
            if (isInMin[i] == 0) {
                resultExponents[idx] = max[i];
                idx++;
            }
        }

        

        for (int i = 0; i < min.length; i++) {
            resultExponents[idx] = min[i];
            idx++;
        }

        int n = resultExponents.length;
        boolean swapped;
        
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            
            for (int j = 0; j < n - i - 1; j++) {
                if (resultExponents[j] > resultExponents[j + 1]) {
                    // Swap elements
                    int temp = resultExponents[j];
                    resultExponents[j] = resultExponents[j + 1];
                    resultExponents[j + 1] = temp;
                    
                    swapped = true;
                }
            }
            
            // If no elements were swapped in the inner loop, the array is already sorted
            if (!swapped) {
                break;
            }
        }

        double[] resultCoeffcients = new double[resultLength];

        for (int i = 0; i < max.length; i++) {
            int j;
            for (j = 0; j < resultExponents.length; j++) {
                if (big.exponents[i] == resultExponents[j]) {
                    resultCoeffcients[j]+=big.coefficients[i];
                    break;
                }
            }
        }

        for (int i = 0; i < min.length; i++) {
            int j;
            for (j = 0; j < resultExponents.length; j++) {
                if (small.exponents[i] == resultExponents[j]) {
                    resultCoeffcients[j]+=small.coefficients[i];
                    break;
                }
            }
    
        }   

        return new Polynomial(resultCoeffcients, resultExponents);

    }

    public Polynomial multiply(Polynomial f) {

        Polynomial result = new Polynomial();

        if (!isValid() || !f.isValid()) {
            return result;
        }

        for (int i = 0; i < exponents.length; i++) {

            Polynomial temp = new Polynomial();

            for (int j = 0; j < f.exponents.length; j++) {
                int[] exponent = {exponents[i] + f.exponents[j]};
                double[] coefficient = {coefficients[i] * f.coefficients[j]};
                temp = temp.add(new Polynomial(coefficient, exponent));
            }

            result = result.add(temp);
        }

        return result;
    }

    public double evaluate(double x) {
        double result=0;

        if (!isValid()) {
            return -1;
        }

        for (int i = 0; i < coefficients.length; i++) {
            result +=  coefficients[i] * Math.pow(x, exponents[i]);
        }

        return result;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0.0;
    }

   public void saveToFile(String myFile) throws Exception {

        if (!isValid()) {
            return;
        }

        String polyString = "";

        for (int i = 0; i < exponents.length; i++) {
            polyString += coefficients[i];
            if (exponents[i] != 0) {
                polyString+="x" + exponents[i];
            }

            polyString+="+";
        }

        

        if(polyString.endsWith("+")) {
            polyString = polyString.substring(0, polyString.length()-1);
        }

        FileWriter myWriter = new FileWriter(new File(myFile));
        myWriter.write(polyString);
        myWriter.close();
   }

   public boolean isValid() {
        return (exponents != null && coefficients != null) && (exponents.length == coefficients.length);
   }


}
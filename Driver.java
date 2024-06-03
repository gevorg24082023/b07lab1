import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.io.FileNotFoundException;

public class Driver {
    public static void main(String[] args) {
        try {
            double[] coef_1 = {1, 1, 3};
            int[] exp_1 = {1, 0, 2};
            Polynomial p1 = new Polynomial(coef_1, exp_1);

            double[] coef_2 = {2, 3, 4};
            int[] exp_2 = {2, 1, 0};
            Polynomial p2 = new Polynomial(coef_2, exp_2);

            double[] coef_3 = {2, 3, 4};
            int[] exp_3 = {2, 1, 0};
            Polynomial p3 = new Polynomial(coef_3, exp_3);

            Polynomial sum = p1.add(p2);
            Polynomial product = p1.multiply(p3);

            System.out.println(Arrays.toString(sum.coefficients));
            System.out.println(Arrays.toString(sum.exponents));
            
            System.out.println(Arrays.toString(product.coefficients));
            System.out.println(Arrays.toString(product.exponents));
            
            
            File file_1 = new File("poly.txt");
            Polynomial poly = new Polynomial(file_1);
            poly.saveToFile("new.txt");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error occurred while processing the file: " + e.getMessage());
        }
    }
}

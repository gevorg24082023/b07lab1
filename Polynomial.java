import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;


public class Polynomial {
	//fields
	public double[] coefficients; 
	public int[] exponents; 
	
	//constructor
	public Polynomial() {
		this.coefficients = null; 
		this.exponents = null; 
	}
	//constructor
	public Polynomial(double[]arr_1, int[] arr_2) {
		if (arr_1.length != arr_2.length) {
			this.coefficients = null;
			this.exponents = null; 
			return; 
		}
		this.coefficients = arr_1; 
		this.exponents = arr_2; 
	}
	
	//constructor
	
	public Polynomial(File file) throws IOException, FileNotFoundException{
		String path = file.getAbsolutePath(); 
		BufferedReader to_read = new BufferedReader(new FileReader(path)); 
		String line = to_read.readLine(); 
		to_read.close(); 
		
		String[] terms = line.split("(?=[+-])"); 
		int arr_length = terms.length; 
		double[] coef_arr = new double[arr_length]; 
		int[] exp_arr = new int[arr_length]; 
		
		for (int i = 0; i < arr_length; i++) {
			String[] sections = terms[i].split("(?<=\\d|^-)(?=x)|(?=x)(\\d)");
			int len = sections.length; 
			
			if (len == 1) {
				coef_arr[i] = Double.parseDouble(sections[0]);
				exp_arr[i] = 0; 
			}
			else if (len == 2) {
				coef_arr[i] = Double.parseDouble(sections[0]);
				exp_arr[i] = 1; 
			}
			else if (len == 3) {
				coef_arr[i] = Double.parseDouble(sections[0]);
				exp_arr[i] = Integer.parseInt(sections[2]); 
			}
			
		}
		
	}
		
	public void saveToFile(String filename) throws IOException{
		
		int length = this.exponents.length; 
		String result = ""; 
		
		for (int i = 0; i < length; i++) {
			if (this.exponents[i] != 0) {
				result += String.valueOf(this.coefficients[i]) + "x" + String.valueOf(this.exponents[i]) + "+"; 
			}
			else {
				result += String.valueOf(this.coefficients[i]) + "+"; 
			}
		}
		
		if (result.charAt(result.length() - 1) == '+') {
			result = result.substring(0, result.length() - 1); 
		}
		result = result.replace("+-", "-"); 
		
		FileWriter writer = new FileWriter(filename);
		writer.write(result);
		writer.close(); 
		
		
	}
	
	
	public boolean contains(int[]exps, int value) {
		for (int i = 0; i < exps.length; i++) {
			if (exps[i] == value)
				return true; 
		}
		return false; 
	}
	
	
	public int find_index(int value, int[] exponents) {
	
		for (int i = 0; i < exponents.length; i++) {
			if (exponents[i] == value) {
				return i; 
			}
		}
		
		return -1; 
	}
	
	
	public Polynomial add(Polynomial poly_to_add) {
		
		if (isNull(this) && isNull(poly_to_add)) {
			return new Polynomial();
		}
		else if (isNull(this)) {
			return new Polynomial(poly_to_add.coefficients, poly_to_add.exponents); 
		}
		else if (isNull(poly_to_add)) {
			return new Polynomial(this.coefficients, this.exponents); 
		}

		
		int len_1 = this.exponents.length; 
		int result_len = len_1;
		int len_2 = poly_to_add.exponents.length; 
		for (int i = 0; i < len_2; i++) {
			if (!contains(this.exponents, poly_to_add.exponents[i])) {
				result_len++; 
			}
		} 
		//Now result_len is the correct length of the resulting polynomial
		
		double[] result_coef = new double[result_len]; 
		int[] result_exps = new int[result_len]; 
		
		for (int j = 0; j < this.exponents.length; j++) {
			result_exps[j] = this.exponents[j]; 
		}
		
		int index = len_1; 
		for (int k = 0; k < len_2; k++) {
			int val = poly_to_add.exponents[k]; 
			if (!contains(this.exponents, val)) {
				result_exps[index] = val; 
				index++;
			} 
		}
		
		for (int i = 0; i < result_len; i++) {
			int current_exponent = result_exps[i]; 
			
			int this_index = find_index(current_exponent, this.exponents); 
			int poly_to_add_index = find_index(current_exponent, poly_to_add.exponents); 
			
			if (this_index == -1 && poly_to_add_index != -1) { //only found in poly_to_add
				result_coef[i] = poly_to_add.coefficients[poly_to_add_index]; 
			}
			else if (this_index != -1 && poly_to_add_index == -1) { //only found in this poly
				result_coef[i] = this.coefficients[this_index]; 
			}
			else if (this_index != -1 && poly_to_add_index != -1) { //found in both
				result_coef[i] = this.coefficients[this_index] + poly_to_add.coefficients[poly_to_add_index]; 
			}
		}
		//result_coef is the coefficients array after adding but before removing redundant zero's
		
		
		int final_len = result_len; 
		
		for (int i = 0; i < result_len; i++) {
			if (result_coef[i] == 0) final_len--; 
		}
		
		double[] final_coef = new double[final_len]; 
		int[] final_exps = new int[final_len]; 
		
		
		int count = 0; 
		for (int i = 0; i < result_len; i++) {
			if (result_coef[i] != 0) {
				final_coef[count] = result_coef[i]; 
				final_exps[count] = result_exps[i]; 
				count++; 
			}
		}
		
		Polynomial final_polynomial = new Polynomial(final_coef, final_exps); 
		return final_polynomial; 
			
					
		}
	
		
		public Polynomial multiply(Polynomial poly_2) {
			
			if (isNull(poly_2) || isNull(this)) return new Polynomial(); 
			
			int this_length = this.exponents.length; 
			int poly_2_length = poly_2.exponents.length; 
			
			double[] product_coef = new double[this_length * poly_2_length]; 
			int[] product_exp = new int[this_length * poly_2_length]; 
			
			int count = 0; 
			for (int i = 0; i < this_length; i++) {
				for (int j = 0; j < poly_2_length; j++) {
					product_coef[count] = this.coefficients[i] * poly_2.coefficients[j]; 
					product_exp[count] = this.exponents[i] + poly_2.exponents[j]; 
					count++; 
				}
			}
			
			//Now we have the resulting array but there may be redundant exponents. 
			
			int num_same = 0; 
			for (int i = 0; i < count - 1; i++) {
				for (int j = i + 1; j < count; j++ ) {
					if (product_exp[i] == product_exp[j]) {
						product_coef[i] += product_coef[j]; 
						product_coef[j] = 0; 
						num_same++; 
					}
				}
			}
			
			double[] final_coef = new double[count - num_same]; 
			int[] final_exp = new int[count - num_same]; 
			
			
			int index = 0; 
			for (int i = 0; i < count; i++) {
				if (product_coef[i] != 0) {
					final_coef[index] = product_coef[i]; 
					final_exp[index] = product_exp[i]; 
					index++; 
				}
			}
			
			return new Polynomial(final_coef, final_exp); 
			
			
			
		}
		
		
	public double evaluate(double value) {
		double result = 0.0;
		if (isNull(this) == true) return result; 
		int len = this.coefficients.length; 
		
		for(int i = 0; i < len; i++) {
			result += (coefficients[i]* Math.pow(value, exponents[i]));
		}
		
		return result; 
	}
	
	
	
	public boolean hasRoot(double x_val) {
		if(isNull(this) == true) return true; 
		double poly_val = evaluate(x_val);
		if(poly_val == 0) {
			return true; 
		}
		return false; 
	}
	
	public boolean isNull(Polynomial polynomial) {
		if (polynomial.exponents == null && polynomial.coefficients == null) {
			return true; 
		}
	
		return false;
	}
	
	
}
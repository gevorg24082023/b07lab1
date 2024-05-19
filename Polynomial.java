public class Polynomial {
	//fields
	public double[] coefficients; 
	
	//constructor
	public Polynomial() {
		coefficients = new double[]{0}; 
	}
	//constructor
	public Polynomial(double[]arr) {
		coefficients = arr; 
	}
	
	public Polynomial add(Polynomial inital_poly) {
		
		
		int length1 = this.coefficients.length;
		int length2 =  inital_poly.coefficients.length;
		
		int max_length; 
		
		
		if(length1 > length2) {
			max_length = length1;
		}
		else {
			max_length = length2; 
		}
		
		
		double[] resultcoef = new double[max_length]; 
		
		
		for(int i = 0; i < max_length; i++) {
			double coef1; 
			double coef2; 
			
			if(i < length1) {
				coef1 = this.coefficients[i];
			}
			else {
				coef1 = 0; 
			}
			
			if(i < length2) {
				coef2 = inital_poly.coefficients[i]; 
			}
			else {
				coef2 = 0; 
			}
			
			resultcoef[i] = coef1 + coef2; 
			
			
		}
		
		
		Polynomial result = new Polynomial(resultcoef);
		
		return result; 
	}
	
	
	
	public double evaluate(double value) {
		double result = 0.0;
		int len = this.coefficients.length; 
		
		for(int i = 0; i < len; i++) {
			result += (coefficients[i]* Math.pow(value, i));
		}
		
		return result; 
	}
	
	
	
	public boolean hasRoot(double x_val) {
		
		double poly_val = evaluate(x_val);
		if(poly_val == 0) {
			return true; 
		}
		return false; 
	}
	
	
	
	
	
}

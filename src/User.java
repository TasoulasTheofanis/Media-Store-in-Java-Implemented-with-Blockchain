import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class User {
    
	private String name, email, password;
	private int balance;
    
    public User(String name, String email, String password, int balance){
        this.name = name;
        this.email = email;
        try {
			this.password = hashPassword(password);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        this.balance = balance;
    }
    
    public String getAll() {
		return "name:"+ name + ", balance: " + balance;
	}
    
    public String getUser() {
    	return this.name;
    }
    
    public String getEmail() {
    	return this.email;
    }
    
    public String getPassword() {
    	return this.password;
    }
    
    public double getBalance() {
    	return this.balance;
    }
    
    public void addBalance(int balance) {
    	this.balance = this.balance+balance;
    }
    
    public void rent() {
    	this.balance = this.balance - 1;
    }
       
    public boolean checkPassword(String password) throws NoSuchAlgorithmException {
        String hashedPassword = hashPassword(password);
        return hashedPassword.equals(this.password);
    }
    
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedPassword = md.digest(password.getBytes());
        return Arrays.toString(hashedPassword);
    }
}
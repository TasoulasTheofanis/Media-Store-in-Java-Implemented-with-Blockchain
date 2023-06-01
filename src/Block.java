import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Block {
	private int id;
	private int nonce;
	private long timeStamp;
	private String hash;
	private String previousHash;
	public List<Transaction> transactions;
	
	public Block(String previousHash, int id) {
		this.id = id;
		this.transactions = new ArrayList<Transaction>();
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		Random random = new Random(this.timeStamp);
		this.nonce = random.nextInt();
		random = null; // set the reference to null to release the object
		generateHash(); 
	}
	
	public String getAll() {
		return "id:"+ id + ", nonce:" + nonce + ", timeStamp:"+ timeStamp + ", hash:" + hash + ", previousHash:" + previousHash + getAllTransactions();
	}
	public String getAllTransactions() {
		String allTransactions = "\n     transactions: ";
		for (int i=0; i<this.transactions.size(); i++) {
			allTransactions = allTransactions + "\n     " + i +") " + this.transactions.get(i).getAll();
		}
		return allTransactions;
	}
	
	public void generateHash() {
		String dataToHash = Integer.toString(id)+previousHash+Long.toString(timeStamp)+transactions.toString()+Integer.toString(nonce);
		String hashValue = CryptographyHelper.generateHash(dataToHash);
		this.hash = hashValue;
	}
	
	public void incrementNonce() {
		this.nonce++;
	}
	
	public String getHash() {
		return this.hash;
	}
	
	//check if the given transaction is valid or not
	public boolean addTransaction(Transaction transaction, PublicKey sender) {		
		if(transaction == null) return false;		
		//if the block is the genesis block we do not process
		if((!previousHash.equals("0000000000000000000000000000000000000000000000000000000000000000"))) {
			if((!transaction.verifyTransaction(sender))) {
				System.out.println("Transaction is not valid...");
				return false;
			}
		}
		transactions.add(transaction);
		return true;
	}
}

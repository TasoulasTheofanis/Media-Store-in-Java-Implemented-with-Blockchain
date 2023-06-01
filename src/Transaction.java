import java.security.PrivateKey;
import java.security.PublicKey;

public class Transaction {
	
	//id of the transaction is a hash
	private String transactionId; 
	//we use PublicKeys in String format to reference the sender or receiver
	private String sender; 
	private String receiver;
	//amount of coins the transaction sends to the receiver from the sender
	//make sure the transaction is signed to prevent anyone else from spending the coins
	private byte[] signature;
	private String action; 
	private String title; 
	
	// Constructor: 
	public Transaction(String sender, String receiver, String transactionId, String action, String title) {
		this.action = action;
		this.title = title;
		this.sender = sender;
		this.receiver = receiver;
		this.transactionId = transactionId;
		calulateHash();
	}
	
	public boolean verifyTransaction(PublicKey sender) {
		//Sender's public key must me verified
		if (!this.sender.equals(sender.toString())) {
			return false;
		}
		String data = sender.toString() + receiver.toString() + transactionId.toString()+action+title;
		return CryptographyHelper.verifyECDSASignature(sender, data, signature);
	}	
	
	public void generateSignature(PrivateKey privateKey) {
		String data = sender.toString() + receiver.toString() + transactionId.toString()+action+title;
		signature = CryptographyHelper.applyECDSASignature(privateKey,data);		
	}	

	private void calulateHash() {
		String hashData = sender.toString()+receiver.toString()+transactionId.toString()+action+title;
		this.transactionId = CryptographyHelper.generateHash(hashData);
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getReceiver() {
		return receiver;
	}

	public byte[] getSignature() {
		return signature;
	}

	public void setSignature(byte[] signature) {
		this.signature = signature;
	}
	
	public String getAll() {
		return "transactionId:"+ transactionId + ", signature: " + signature + "\n     action: " + action 
				+ ", title: " + title + "\n     sender:" + sender + "     receiver:"+ receiver;
	}
}

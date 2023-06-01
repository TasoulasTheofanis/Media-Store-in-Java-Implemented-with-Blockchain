import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Wallet {
	
	private PrivateKey privateKey;
	private PublicKey publicKey;
	
	public Wallet() {
		KeyPair keyPair = CryptographyHelper.ellipticCurveCrypto();
		this.privateKey = keyPair.getPrivate();
		this.publicKey = keyPair.getPublic();
	}		
	
	public PublicKey getPublicKey() {
		return publicKey;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}
	
	public String getAll() {
		return publicKey.toString() + "   " + privateKey.toString();
	}
}

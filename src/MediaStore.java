/*Επωνυμο:    Τασούλας  
  Ονομα:      Θεοφάνης
  ΑΜ:         Π2015092
  Ημερομηνια: 29/11/2016
  Λειτουργια: Η παρακατω κλαση υλοποιει αντικειμενα mediaStore χειροκινητα μεσω της main, η μεσω του μενου (μεθοδος showTextII()). 
  Αυτα μπορουν να ενοικιασθουν, να επιστραφουν, να εμφανιστουν μαζι με τα χαρακτηριστικα τους και το αν ειναι διαθεσιμα ή ενοικιασμενα.
*/
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

public class MediaStore
{  
    private final List<Media> streamingMedia = new ArrayList<Media>();      // Λιστα με τα streaming media αντικειμενα (παραλληλη με την streamingTitle)
    private final List<Media> physicalMedia = new ArrayList<Media>();       // Λιστα με τα physical media αντικειμενα (παραλληλη με την physicalTitle)
    private List<User> allUsers = new ArrayList<User>();              		// Λιστα η οποια θα δεχεται τους χρήστες που μπορούν να δανιστούν medias. Οι χρηστες βρισκονται σε αρχειο json.
    private List<Wallet> allWallets = new ArrayList<Wallet>();				// Λιστα η οποια θα δεχεται τα πορτοφόλια των χρηστων. Τα πορτοφολια εχουν τα ιδιωτικα και δημοσια κλειδια και δημιουργουνται κατα την εκκινηση του προγραμματος. 
    private final List<Block> blockList = new ArrayList<Block>(); 				// Λιστα με τα blocks των συναλλαγων που εγιναν. Καθε block χρειαζεται ενα hash για να δημιουργηθει, δηλαδη block(hash).
    private final List<Transaction> transactionList= new ArrayList<Transaction>();// Λιστα η οποια θα δεχεται τα πορτοφόλια των συναλλαγών.

    private final Scanner sc = new Scanner(System.in); // Αυτο ειναι ενα αντικειμενο για να διαβαζουμε τιμες απο το χρηστη
    private Media object;           // Βοηθητικό αντικείμενο media, που δεχεται τιτλο και τυπο απο το χρηστη, και στο τελος εκχωρειται στην λιστα αντικειμενων (physical ή streaming)
    private int i;                  // Αυτη η μεταβλητη ειναι βοηθητικη και δεχεται τιμες απο το χρηστη
    private int x;                  // Αυτη η μεταβλητη ειναι βοηθητικη και δεχεται τιμες απο το χρηστη
    private String preTitle;        // Αυτη η μεταβλητη ειναι βοηθητικη και δεχεται τιμες απο το χρηστη
    private String preType;         // Αυτη η μεταβλητη ειναι βοηθητικη και δεχεται τιμες απο το χρηστη
    private User loggedinUser;
    private int loggedinUserindex;
    private Block newBlock;
    private int lastIndex;

    public MediaStore() {}

    public void addStreamMedia(final String title, final String type) {
        if (type.trim().toLowerCase().equals("stream-audio") || type.trim().toLowerCase().equals("stream-video")) {// Η μεθοδος αυτη, εαν ο τυπος που δοθηκε ειναι εγκυρη τιμη,
            object = new Media(title, type);    // τότε θα δημιουργησει ενα νεο media αντικειμενο,
            setTheOtherStuff(object);           // μετα θα κληθει η μεθοδος setTheOtherStuff για να δωθουν οι υπολοιπες πληροφοριες του media (length,artists,κτλ)
            streamingMedia.add(object);         // και τελος θα εκχωρηθει το αντικειμενο στη λιστα streamingMedia.
            System.out.println("Stream media was succesfully created.");
        } else {
            System.out.println("Streaming media with type " + type + " can not be created.");
        }
    }

    public void addPhysicalMedia(final String title, final String type, final int avail) {
        if (type.trim().toLowerCase().equals("dvd") || type.trim().toLowerCase().equals("book")) { // Η μεθοδος αυτη, εαν ο τυπος που δοθηκε ειναι εγκυρη τιμη,
            if (avail >= 0) {						// και εαν η διαθεσιμοτητα ειναι μεγαλυτερη ή ιση του 0,
                object = new Media(title, type);	// θα δημιουργησει ενα νεο media αντικειμενο,
                setTheOtherStuff(object);			// μετα θα κληθει η μεθοδος setTheOtherStuff για να δωθουν οι υπολοιπες πληροφοριες του media (length,artists,κτλ)
                physicalMedia.add(object);			// και τελος θα εκχωρηθει το αντικειμενο στη λιστα physicalMedia
                System.out.println("Physical media was succesfully created.");
            } else {
                System.out.println("Physical media was not created, cause its availability must be equal or greater than 0. ");
            }
        } else {
            System.out.println("Physical media with type " + type + " can not be created.");
        }
    }

    public void addPhysicalMediaToList(Media media) { // Συνάρτηση που προσθέτει ένα object τύπου Media στη λίστα αντικειμένων Media του MediaStore
        physicalMedia.add(media);
    }

    public void addStreamMediatoList(Media media) {// Συνάρτηση που προσθέτει ένα object τύπου Media στη λίστα αντικειμένων Media του MediaStore
        streamingMedia.add(media);
    }

    public Media setTheOtherStuff(final Media object) throws IllegalArgumentException // Αυτη η μεθοδος, εισαγει τις υπόλοιπες πληροφορίες του αντικειμένου (διάρκεια, είδος, καλιτέχνες, κτλ)
    { 
        do {
            if (object.type.equals("stream-audio") || object.type.equals("stream-video") || object.type.equals("dvd")) {
                System.out.println("Set the duration in seconds for the media " + object.title);
            } else {
                System.out.println("Set the number of pages for the media " + object.title);
            }
            try {
                x = sc.nextInt();                                       // Διάρκεια (θετικη τιμη)
            }
            catch (Exception e)	{										//Εαν υπαρξει καποιο προβλημα, τοτε εμφανιζουμε καταλληλο μηνυμα
            	System.out.println("Excuse me, you didn't press a number");
            }
            object.setLength(x);
        } while (x < 0);
        do {
            System.out.println("Set the year date for the media " + object.title); // Ημερομηνία (θετικη τιμη)
            try
            {
                x = sc.nextInt();
            }
            catch (Exception e)											//Εαν υπαρξει καποιο προβλημα, τοτε εμφανιζουμε καταλληλο μηνυμα
            {
                System.out.println("Excuse me, you didn't press a number");
            } 
            object.setDate(x);
        } while (x < 0);
        do { // Βαθμολογία-Κριτική
            System.out.println("Set the ratings (1-5) for the media " + object.title + " or press 0 to stop");
            try {
                x = sc.nextInt();
            }
            catch (Exception e) {											//Εαν υπαρξει καποιο προβλημα, τοτε εμφανιζουμε καταλληλο μηνυμα
                System.out.println("Excuse me, you didn't press a number");
            } 
            if (x >= 1 && x <= 5) {    // Εαν δωσει τιμη μεταξυ 1 και 5
                object.addRating(x); // Τοτε εκχωρουμε την κριτικη
                System.out.println("Rating added succesfully.");
            } else if (x != 0) { // Εαν δεν ειναι μεταξυ 0 και 5
                System.out.println("The ratings must be between 1 and 5"); // Εμφανιζουμε καταλληλο μηνυμα
            }
        } while (x != 0); // Εαν δωσει 0, σταματαμε να δεχομαστε βαθμολογιες

        sc.nextLine(); // Τωρα συνεχιζουμε με τις πληροφοριες που δεχονται κειμενο. Βαζουμε το sc.nextLine() γιατι προηγουμενως οι 
                       // τιμες που δεχοταν το scanner ηταν αριθμοι. Ετσι το ξεμπλοκαρουμε και ξερει πλεον οτι θα δεχθει string τιμες.
        System.out.println("Set the genre for the media " + object.title); // Είδος
        preTitle = sc.nextLine().trim();
        object.setGenre(preTitle);
        System.out.println("Set the description for the media " + object.title); // Περιγραφή
        preTitle = sc.nextLine().trim();
        object.setDescription(preTitle);
        System.out.println("Add artists for the media " + object.title + " or enter space to stop");
        do {
            preTitle = sc.nextLine().trim(); // Καλιτέχνες, μέχρι να δωθεί το κενό
            if (!preTitle.trim().equals("")) // Στη συγκεκριμένη γραμμή, αποτρέπω να μπει ο καλλιτέχνης με τιμή κενό
                object.addArtists(preTitle);
        } while (!preTitle.trim().equals(""));
        return object; // Τελος επιστρεφουμε το αντικειμενο μας πισω. Πλεον ειναι ετοιμο να εκχωρηθει στη λιστα streamingMedia ή physicalMedia
    }

    public boolean rentMedia(final String title) {
        if (searchPosition("streaming", title) >= 0 ) {// Εαν υπαρχει στη streamingTitle λιστα, επιστρεφουμε τιμη true;
        	doTransaction("rent", title);
            return true;
        } else {						// Εαν δε βρεθει στη streamingTitle, τοτε μπορει να υπαρχει στη physicalTitle λιστα.
            i = searchPosition("physical", title); // Το position ειναι μια μεταβλητη που της εκχωρειται η τιμη, της θεσης στην οποια βρισκεται ο τιτλος της ταινιας. Εαν δεν βρεθει ο τιτλος της ταινιας, τοτε θα παρει τιμη -1.
            if (i >= 0)					// Εαν το position ειναι μεγαλυτερο του 0, σημαινει οτι αυτη η ταινια υπαρχει στη λιστα physicalTitle.
            {
                if (physicalMedia.get(i).getAvailability() > 0) // Τωρα ελεγχουμε εαν η διαθεσιμοτητα ειναι μεγαλυτερη του 0.
                {
                    physicalMedia.get(i).setAvailability(physicalMedia.get(i).getAvailability()-1); // availability-1
                    doTransaction("rent", title);
                    return true;
                } else {
                    System.out.println("This media is not available.");
                    return false; // Διαφορετικα, επιστρεφουμε false, γιατι παρολο που υπαρχει ο τιτλος της ταινιας, δεν υπαρχει διαθεσιμοτητα.
                }
            } else { // Εαν το position ειναι μικροτερο του 0, δεν υπαρχει ουτε στην physicalTitle ο τιτλος που δοθηκε. Και επιστρεφουμε τιμη false.
                System.out.println("There is no media with that title.");
                return false;
            }
        }
}
    public void doTransaction(String action, String title) {
    	if (action == "rent"){
	    	loggedinUser.rent();			//Μειων ενα ευρω για την ενοικιαση
	    	allUsers.get(0).addBalance(1);	//Το ευρω το παιρνει ο admin παντα
    	}
        Transaction transaction = new Transaction(allWallets.get(loggedinUserindex).getPublicKey().toString(), allWallets.get(0).getPublicKey().toString(), blockList.size()+newBlock.getHash().toString(), action, title); //admin is always in the 0 position and he will always receive the money
        transaction.generateSignature(allWallets.get(loggedinUserindex).getPrivateKey());
        newBlock.addTransaction(transaction, allWallets.get(loggedinUserindex).getPublicKey());
        blockList.set(lastIndex, newBlock); //Αποθηκευουμε το τελευταιο block στην τελευταια θεση της λιστας
        transactionList.add(transaction);        
    }

    public boolean returnMedia(final String title) {
    	if (searchPosition("streaming", title) >= 0 )	// Εαν βρεθει στη streamingTitle λιστα, τοτε επιστρεφουμε τιμη true, που σημαινει οτι ο τιτλος της ταινιας  που δοθηκε υπαρχει στη λιστα και οτι ο χρηστης δεν προσπαθει να επιστρεψει εναν τιτλο που δεν υπαρχει στη λιστα.
        {
    		doTransaction("return", title);
            return true;
        } else 											// Εαν δε βρεθει στη streamingTitle, τοτε μπορει να υπαρχει στη physicalTitle λιστα.
        {
            i = searchPosition("physical", title);
            if (i >= 0)									// Εαν το position ειναι μεγαλυτερο του 0, σημαινει οτι αυτη η ταινια υπαρχει στη λιστα physicalTitle.
            {
            	physicalMedia.get(i).setAvailability(physicalMedia.get(i).getAvailability()+1); // availability+1
            	doTransaction("return", title);
                return true;
            } else 
            {
                System.out.println("Title was not found.");
                return false; // Διαφορετικα, επιστρεφουμε false, γιατι δεν υπαρχει ο τιτλος της ταινιας σε καμια λιστα.
            }
        }
    }

    public int searchPosition(String type, String title) // Αυτη η μεθοδος ψαχνει μεσα σε μια λιστα (physical ή streaming) εαν υπαρχει ο τιτλος που ζητηθηκε για ενοικιαση ή επιστροφη.
    {
    	if (type.equals("physical")) {
    		for (i = 0; i < physicalMedia.size(); i++) {
	            if (title.trim().toLowerCase().equals(physicalMedia.get(i).getTitle().trim().toLowerCase())){
	                return i;
	            }
	        }
    	}
    	else {
    		for (i = 0; i < streamingMedia.size(); i++) {
	            if (title.equals(streamingMedia.get(i).getTitle())){
	                return i;
	            }
	        }
    	}    		
        return -1; // Διαφορετικα επιστρεφεται η τιμη -1.
    }

    public void showMedias()
    {
        for (i = 0; i < physicalMedia.size(); i++) // Σε αυτην την επαναληψη θα ασχοληθουμε ΜΟΝΟ ΜΕ ΤΑ PHYSICAL MEDIA. Η επαναληψη θα γινει τοσες φορες, οσο ειναι το μεγεθος της λιστας physicalMedia.
        {
            if (physicalMedia.get(i).getAvailability() == 0) // Εαν το media δεν εχει διαθεσιμοτητα
            {
                System.out.println("Title: " + physicalMedia.get(i).getTitle() + ", Type: " + physicalMedia.get(i).getType() + " (Not available)");
            } else 
            {
                System.out.println("Title: " + physicalMedia.get(i).getTitle() + ", Type: " + physicalMedia.get(i).getType());
            }
        }
        for (i = 0; i < streamingMedia.size(); i++) 
        {
            System.out.println("Title: " + streamingMedia.get(i).getTitle() + ", Type: " + streamingMedia.get(i).getType());
        } 
    }
   
    public void showTextUI() throws IllegalArgumentException {
        try {                               //Κάνουμε τη συνάρτηση να ξεκινά μετά από 2 δευτερόλεπτα, για να μην πετάγεται απότομα το κείμενο στο χρήστη
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        do {
            System.out.println("\n MediaStore Menu\n =====================\n 1. Add new media\n 2. List all media in store\n 3. Rent media\n 4. Return media\n 5. Add balance\n 6. LogOut ");
            try
            {
                x = sc.nextInt();
            }
            catch (Exception e)	//Εαν υπαρξει καποιο προβλημα, τοτε εμφανιζουμε καταλληλο μηνυμα
            {
                System.out.println("Excuse me, you didn't press a number");
                showTextUI();
            } 
            sc.nextLine();  // Βαζουμε το sc.nextLine() για να ξεμπλοκαρουμε το scanner αντικειμενο. Εαν δεν το εκανα αυτο, θα εμφανιζονταν μαζι τα "Enter the title..." και "Enter the type..." και θα παρουσιαζοταν προβλημα στις επομενες σειρες.
        } while (x < 1 || x > 7);
        if (x == 1)
        {
            System.out.println("Enter the title of the media that you want to add: ");
            preTitle = sc.nextLine();
            if (preTitle.trim().equals(""))
            {
                System.out.println("title cant be space");
            } else 
            {
                System.out.println("Enter the type(stream-video,stream-audio,book,dvd): ");
                preType = sc.nextLine();
                if (preType.trim().toLowerCase().equals("dvd") || preType.trim().toLowerCase().equals("book")){                
                    System.out.println("Enter the balance: ");
                    x = sc.nextInt();
                    addPhysicalMedia(preTitle, preType, x);
                } else if (preType.trim().toLowerCase().equals("stream-audio") || preType.trim().toLowerCase().equals("stream-video")) // Εαν ο τιτλος ειναι εγκυρη streaming τιμη
                {
                    addStreamMedia(preTitle, preType); // Καλουμε την addStreamingMedia για να δημιουργηθει το επομενο media
                } else 
                {
                    System.out.println("Media with type " + preType + " cannot be created.");
                }
            }
            showTextUI(); // Αφου τελειωσει η διαδικασια, ξανακαλουμε το μενου
        } else if (x == 2)
        {
            showMedias(); // Καλουμε τη toString μεθοδο για να εμφανιστουν τα media
            showTextUI();
        } else if (x == 3)
        {
        	if (loggedinUser.getBalance()>=1) {
        		System.out.println("Enter the title of the media that you want to rent: ");
        		preTitle = sc.nextLine();
	            if (rentMedia(preTitle) == false)
	            {
	                System.out.println("Media could not be rented.");
	            } else
	            {
	                System.out.println("Media was succesfully rented.");
	            }
        	}else {
        		System.out.println("User doesn't have enough balance to rent.");
        	}
            showTextUI();
        } else if (x == 4)
        {
            System.out.println("Enter the title of the media that you want to return: ");
            preTitle = sc.nextLine();
            if (returnMedia(preTitle) == false)
            {
                System.out.println("Media could not be returned.");
            } else {
                System.out.println("Media was succesfully returned.");
            }
            showTextUI();
        }
        else if (x == 5)
        {
            System.out.println("Enter the amount to add: ");
            try {
                x = sc.nextInt();
                loggedinUser.addBalance(x);
                System.out.println("You have now "+loggedinUser.getBalance() + "USD");
            }
            catch (Exception e) {
            	System.out.println("Excuse me, you didn't press a number");
            } 
            sc.nextLine();
            showTextUI();
        }
         else if (x==7 && loggedinUser.getUser().equals("admin"))
         {
        	 System.out.println("\nAll Wallets:");
        	 for (i = 0; i < allWallets.size(); i++) {
        		 System.out.println(i + ") " + allWallets.get(i).getAll());
        	 }
        	 System.out.println("\nAll Users:");
        	 for (i = 0; i < allUsers.size(); i++) {
                 System.out.println(i + ") " + allUsers.get(i).getAll());
        	 }
        	 System.out.println("\nAll Blocks:");
        	 for (i = 0; i < blockList.size(); i++) {
        		 System.out.println(i + ")" + blockList.get(i).getAll());
        	 }
             showTextUI();
         }
         else
        {
            System.out.println("User Logged Out");
        }
    }

    public void sortStream() {
        if (streamingMedia.size() > 1) {
            for (i = 0; i < streamingMedia.size(); i++) {
                if (streamingMedia.get(i).averageRating > streamingMedia.get(i + 1).averageRating) {
                    System.out.print("Message");
                }
            }
        }
    }

    public void addFromJson() {
    	try {
			JsonExport.exportJsonToMediaList(physicalMedia, "JSON/physical.json");
			JsonExport.exportJsonToMediaList(streamingMedia, "JSON/streaming.json");
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
        try {
			allUsers = JsonExport.exportJsonToUser();
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        createWallets();
        
        try {
        	JsonExport.exportJsonToBlocks(blockList);
		} catch (Exception e) {
			e.printStackTrace();
		}        
    }
    
    private void createWallets() {
    	Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        for (i = 0; i < allUsers.size(); i++) {        	
        	Wallet wallet = new Wallet();
        	allWallets.add(wallet);
        }        
    }
    
    public void welcomePage() {
        do {
            System.out.println("\n Welcome to MediaStore\n =====================\n 1. Login\n 2. Register\n 3. Exit\n");
            try
            {
                x = sc.nextInt();
            }
            catch (Exception e)											//Εαν υπαρξει καποιο προβλημα, τοτε εμφανιζουμε καταλληλο μηνυμα
            {
                System.out.println("Excuse me, you didn't press a number");
            }
            sc.nextLine();  // Βαζουμε το sc.nextLine() για να ξεμπλοκαρουμε το scanner αντικειμενο. Εαν δεν το εκανα αυτο, θα εμφανιζονταν μαζι τα "Enter the title..." και "Enter the type..." και θα παρουσιαζοταν προβλημα στις επομενες σειρες.
        } while (x < 1 || x > 3);
        if (x == 1)
        {
        	String name;
        	String password;
        	loggedinUser=null;
        	System.out.println("Enter the user's name: ");
        	name = sc.nextLine();
        	for (i = 0; i < allUsers.size(); i++) {
        		if (name.equals(allUsers.get(i).getUser())) {
        			loggedinUser = allUsers.get(i);
        			loggedinUserindex = i;
        			for (int j = 3; j>0; j--) {
	        		   System.out.println("Enter the user's password: ");
	        		   password = sc.nextLine();
	        		    try {
							if (loggedinUser.checkPassword(password)) {
								break;
							} else if (j>1)
							{
								System.out.println("Wrong password. Try again.");
							} else
							{
								System.out.println("Too many wrong attemps. Please re-login.");
							}
						} catch (NoSuchAlgorithmException e) {
							e.printStackTrace();
						}	        		    
        			}
        	   }
        		if (loggedinUser != null) {
        			break;
        		}
        	}
        	if (loggedinUser == null) {
        		System.out.println("User not found");
        	}else {
        		System.out.println("User loggedIn Successfully");
        		showTextUI();
        	}
        	welcomePage();
        }
        else if (x == 2) {
        	String name;
        	String password;
        	String email;
        	int balance;
        	System.out.println("Enter the user's name: ");
        	name = sc.nextLine();
        	System.out.println("Enter the user's email: ");
        	email = sc.nextLine();
        	System.out.println("Enter the user's password: ");
        	password = sc.nextLine();       	
        	System.out.println("Enter the balance you want in USD (each media costs 1 USD): ");
        	balance = sc.nextInt();
            sc.nextLine();  // Βαζουμε το sc.nextLine() για να ξεμπλοκαρουμε το scanner αντικειμενο.
        	User newUser = new User(name, email, password, balance);
        	try {
        		JsonExport.exportUserToJson(newUser);
        		System.out.println("User created.");
        		Wallet wallet = new Wallet();
            	allWallets.add(wallet);
        	} catch (Exception e) {
        		e.printStackTrace();
        		System.out.println("Could not create user.");
        	}
            welcomePage();
        } else {
        	saveToJson();
        	System.out.println("Thank you, good bye!");
        }
    }

    public void saveToJson() {
        try {
        	JsonExport.exportMediaListToJson(this.streamingMedia, "JSON/streaming.json");
		} catch (IOException e){
			System.out.println("Could not save streaming data.");
        }
        try {
        	JsonExport.exportMediaListToJson(this.physicalMedia, "JSON/physical.json");
        } catch (IOException e){
			System.out.println("Could not save physical data.");
        }
        try {
        	JsonExport.exportUsersToJson(allUsers);
        } catch (IOException e){
			System.out.println("Could not save users data.");
        }
        if (!newBlock.getAllTransactions().equals("\n     transactions: ")) {	//Εαν δεν εγινε καμια συναλλαγη, δεν αποθηκευουμε
	        try {
				JsonExport.exportBlocksToJson(this.blockList);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    }
    
    public void createBlock() {
		if (blockList.size()>0) {
			lastIndex = blockList.size();
			newBlock = new Block(blockList.get(blockList.size()-1).getHash(), blockList.size()); //Το νεο block λαμβανει ως παραμετρο hash του το τελευταιου block
		} else {
			lastIndex = 0;
			newBlock = new Block("0000000000000000000000000000000000000000000000000000000000000000", 0); //Εκτος και αν ειναι το 1ο block, οποτε θα λαβει μηδενικα και θα προστεθει στο blockchain
	    }
		blockList.add(newBlock);
    }
}
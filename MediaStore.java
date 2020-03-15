/*Επωνυμο:    Τασούλας  
  Ονομα:      Θεοφάνης
  ΑΜ:         Π2015092
  Ημερομηνια: 29/11/2016
  Λειτουργια: Η παρακατω κλαση υλοποιει αντικειμενα mediaStore χειροκινητα μεσω της main, η μεσω του μενου (μεθοδος showTextII()). Αυτα μπορουν να ενοικιασθουν, να επιστραφουν, να εμφανιστουν μαζι με τα χαρακτηριστικα τους και το αν ειναι διαθεσιμα ή ενοικιασμενα.
  Ακομα να αναφερω οτι πολλες φορες χρησιμοποιω τις μεθοδους toLowerCase() για μεγαλυτερη ευκολια στην εισαγωγη τυπου (απο το χρηστη) και trim() για καλυτερη αναγνωση των string τιμων που εδωσε ο χρηστης 
*/
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

public class MediaStore
{    
    private final List<Media> streamingMedia = new ArrayList<Media>();      // Λιστα με τα streaming media αντικειμενα (παραλληλη με την streamingTitle)
    private final List<String> streamingTitle = new ArrayList<String>();    // Λιστα με τους τιτλους των streaming media αντικειμενων (παραλληλη με την streamingMedia), η οποια θα βοηθησει στην ευρεση των media που θελουμε να ενοικιασουμε ή να επιστρεψουμε
    private final List<Media> physicalMedia = new ArrayList<Media>();       // Λιστα με τα physical media αντικειμενα (παραλληλη με την physicalTitle)
    private final List<String> physicalTitle = new ArrayList<String>();     // Λιστα με τους τιτλους των physical media αντικειμενων (παραλληλη με την physicalMedia), η οποια θα βοηθησει στην ευρεση των media που θελουμε να ενοικιασουμε ή να επιστρεψουμε
    private final List<Integer> availability = new ArrayList<Integer>();    // Λιστα με τη διαθεσιμοτητα των physical media αντικειμενων (παραλληλη με την physicalMedia)
    private final String systemRentName;                                    // Μεταβλητη που δεχεται το ονομα του συστηματος ενοικιασης περιεχομενου.
    private final List<String> list = new ArrayList<String>();              // To list ειναι μια λιστα η οποια θα δεχεται τους τιτλους των media που πρεπει να εμφανισθουν με τη βοηθεια των listRented και listAvailable. Η χρηση της  θα γινει πιο κατανοητη μεσα στις μεθοδους αυτες.
    private final List<Media> toStringRented = new ArrayList<Media>();      // Το toStringRented ειναι μια λιστα που θα δεχεται ολα τα physical media τα οποια δεν ειναι διαθεσιμα και θα τα εμφανιζει στην οθονη. Η χρηση της θα γινει κατανοητη μεσα στις μεθοδους toString και listRented
    private final List<Media> toStringAvailable = new ArrayList<Media>();   // Το toStringAvailable ειναι μια λιστα που θα δεχεται ολα τα physical media τα οποια ειναι διαθεσιμα και θα τα εμφανιζει στην οθονη. Η χρηση της θα γινει κατανοητη μεσα στις μεθοδους toString και listAvailable
    
    private final Scanner sc = new Scanner(System.in); // Αυτο ειναι ενα αντικειμενο για να διαβαζουμε τιμες απο το χρηστη
    private Media object;           // Βοηθητικό αντικείμενο media, που δεχεται τιτλο και τυπο απο το χρηστη, και στο τελος εκχωρειται στην λιστα αντικειμενων (physical ή streaming)
    private int i;                  // Αυτη η μεταβλητη ειναι βοηθητικη και δεχεται τιμες απο το χρηστη
    private int position;           // Αυτη η μεταβλητη ειναι βοηθητικη και δεχεται τιμες απο το χρηστη
    private int x;                  // Αυτη η μεταβλητη ειναι βοηθητικη και δεχεται τιμες απο το χρηστη
    private String preTitle;        // Αυτη η μεταβλητη ειναι βοηθητικη και δεχεται τιμες απο το χρηστη
    private String preType;         // Αυτη η μεταβλητη ειναι βοηθητικη και δεχεται τιμες απο το χρηστη

    public MediaStore(final String systemRentName) {
        this.systemRentName = new String(systemRentName); // Ο κατασκευαστης δινει τιμη στο ονομα του συστηματος
                                                          // ενοικιασης περιεχομενου
    }

    public void addStreamMedia(final String title, final String type) {
        if (type.trim().toLowerCase().equals("stream-audio") || type.trim().toLowerCase().equals("stream-video")) // Η μεθοδος αυτη, εαν ο τυπος που δοθηκε ειναι εγκυρη τιμη,
        {
            object = new Media(title, type);    // τότε θα δημιουργησει ενα νεο media αντικειμενο,
            streamingTitle.add(title);          // στη συνεχεια θα εκχωρηθει ο τιτλος του (μονο) στη λιστα streamingTitle
            setTheOtherStuff(object);           // μετα θα κληθει η μεθοδος setTheOtherStuff για να δωθουν οι υπολοιπες
                                                // πληροφοριες του media (length,artists,κτλ)
            streamingMedia.add(object);         // και τελος θα εκχωρηθει το αντικειμενο στη λιστα streamingMedia.
            System.out.println("Stream media was succesfully created.");

        } else {
            System.out.println("Streaming media with type " + type + " can not be created.");
        }
    }

    public void addPhysicalMedia(final String title, final String type, final int avail) {
        if (type.trim().toLowerCase().equals("dvd") || type.trim().toLowerCase().equals("book")) // Η μεθοδος αυτη, εαν ο τυπος που δοθηκε ειναι εγκυρη τιμη,
        {
            if (avail >= 0)                         // και εαν η διαθεσιμοτητα ειναι μεγαλυτερη ή ιση του 0,
            {
                object = new Media(title, type);    // θα δημιουργησει ενα νεο media αντικειμενο,
                physicalTitle.add(title);           // στη συνεχεια θα εκχωρηθει στη λιστα streamingTitle ο τιτλος μονο, του νεου
                                                    // αντικειμενου,
                availability.add(avail);            // στη λιστα availability θα εκχωρηθει η διαθεσιμοτητα
                setTheOtherStuff(object);           // μετα θα κληθει η μεθοδος setTheOtherStuff για να δωθουν οι υπολοιπες
                                                    // πληροφοριες του media (length,artists,κτλ)
                physicalMedia.add(object);          // και τελος θα εκχωρηθει το αντικειμενο στη λιστα physicalMedia
                System.out.println("Physical media was succesfully created.");
            } else {
                System.out.println(
                        "Physical media was not created, cause its availability must be equal or greater than 0. ");
            }
        } else {
            System.out.println("Physical media with type " + type + " can not be created.");
        }
    }

    public void addPhysicalMediaToList(Media media) { // Συνάρτηση που προσθέτει ένα object τύπου Media στη λίστα αντικειμένων Media του MediaStore
        physicalMedia.add(media);
        physicalTitle.add(media.title);
        availability.add(media.availability);
    }

    public void addStreamMediatoList(Media media) {// Συνάρτηση που προσθέτει ένα object τύπου Media στη λίστα αντικειμένων Media του MediaStore
        streamingMedia.add(media);
        streamingTitle.add(media.title);
    }

    public Media setTheOtherStuff(final Media object) throws IllegalArgumentException // Αυτη η μεθοδος, εισαγει τις υπόλοιπες πληροφορίες του αντικειμένου (διάρκεια, είδος, καλιτέχνες, κτλ)
    { 
        do {
            if (object.type.equals("stream-audio") || object.type.equals("stream-video") || object.type.equals("dvd"))
            {
                System.out.println("Set the duration in seconds for the media " + object.title);
            } else
            {
                System.out.println("Set the number of pages for the media " + object.title);
            }
            try
            {
                x = sc.nextInt();                                       // Διάρκεια (θετικη τιμη)
            }
            catch (Exception e)											//Εαν υπαρξει καποιο προβλημα, τοτε εμφανιζουμε καταλληλο μηνυμα
            {
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
            try
            {
                x = sc.nextInt();
            }
            catch (Exception e)											//Εαν υπαρξει καποιο προβλημα, τοτε εμφανιζουμε καταλληλο μηνυμα
            {
                System.out.println("Excuse me, you didn't press a number");
            }  
            if (x >= 1 && x <= 5)    // Εαν δωσει τιμη μεταξυ 1 και 5
            {
                object.addRating(x); // Τοτε εκχωρουμε την κριτικη
                System.out.println("Rating added succesfully.");
            } else if (x != 0) // Εαν δεν ειναι μεταξυ 0 και 5
            {
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

        return object; // Τελος επιστρεφουμε το αντικειμενο μας πισω. Πλεον ειναι ετοιμο να εκχωρηθει
                       // στη λιστα streamingMedia ή physicalMedia
    }

    public boolean rentMedia(final String title) {
        if (streamingTitle.contains(title)) // Εαν υπαρχει στη streamingTitle λιστα, επιστρεφουμε τιμη true;
        {
            return true;
        } else                  // Εαν δε βρεθει στη streamingTitle, τοτε μπορει να υπαρχει στη physicalTitle λιστα.
        {
            position = searchPosition(physicalTitle, title); // Το position ειναι μια μεταβλητη που της εκχωρειται η τιμη, της θεσης στην οποια βρισκεται ο τιτλος της
                                                             // ταινιας. Εαν δεν βρεθει ο τιτλος της ταινιας, τοτε θα παρει τιμη -1.
            if (position >= 0) // Εαν το position ειναι μεγαλυτερο του 0, σημαινει οτι αυτη η ταινια υπαρχει στη λιστα physicalTitle.
            {
                if (availability.get(position) > 0) // Τωρα ελεγχουμε εαν η διαθεσιμοτητα ειναι μεγαλυτερη του 0.
                {
                    availability.set(position, availability.get(position) - 1); // Εαν ειναι μεγαλυτερη του 0, τοτε μειωνουμε κατα 1 τη διαθεσιμοτητα στη
                                                                                // συγκεκριμενη θεση και επιστρεφουμε τιμη true.
                    return true;
                } else {
                    System.out.println("This media is not available.");
                    return false; // Διαφορετικα, επιστρεφουμε false, γιατι παρολο που υπαρχει ο τιτλος της ταινιας, δεν υπαρχει διαθεσιμοτητα.
                }
            } else // Εαν το position ειναι μικροτερο του 0, δεν υπαρχει ουτε στην physicalTitle ο τιτλος που δοθηκε. Και επιστρεφουμε τιμη false.
            {
                System.out.println("There is no media with that title.");
                return false;
            }
        }
    }

    public boolean returnMedia(final String title) {
        if (streamingTitle.contains(title)) // Εαν βρεθει στη streamingTitle λιστα, τοτε επιστρεφουμε τιμη true, που σημαινει οτι ο τιτλος της ταινιας 
                                            // που δοθηκε υπαρχει στη λιστα και οτι ο χρηστης δεν προσπαθει να επιστρεψει εναν τιτλο που δεν υπαρχει στη λιστα.
        {
            return true;
        } else                              // Εαν δε βρεθει στη streamingTitle, τοτε μπορει να υπαρχει στη physicalTitle λιστα.
        {
            position = searchPosition(physicalTitle, title); // Το position ειναι μια μεταβλητη που της εκχωρειται η τιμη, της θεσης στην οποια βρισκεται ο τιτλος της
                                                             // ταινιας. Εαν δεν βρεθει ο τιτλος της ταινιας, τοτε θα  παρει τιμη -1.
            if (position >= 0)                               // Εαν το position ειναι μεγαλυτερο του 0, σημαινει οτι αυτη η ταινια υπαρχει στη λιστα physicalTitle.
            {
                availability.set(position, availability.get(position) + 1); // Αυξανουμε κατα 1 τη διαθεσιμοτητα στη
                                                                            // συγκεκριμενη θεση και επιστρεφουμε τιμη true.
                return true;
            } else 
            {
                System.out.println("Title was not found.");
                return false; // Διαφορετικα, επιστρεφουμε false, γιατι δεν υπαρχει ο τιτλος της ταινιας σε καμια λιστα.
            }
        }
    }

    public int searchPosition(final List<String> list, final String title) // Αυτη η bonus μεθοδος ψαχνει μεσα σε μια λιστα
                                                                   // (physicalTitle ή streamingTitle) εαν υπαρχει ο τιτλος που ζητηθηκε για ενοικιαση ή επιστροφη.
    {
        for (i = 0; i < list.size(); i++) {
            if (title.equals(list.get(i))) // Εαν λοιπον ο τιτλος ειναι ισος με το τιτλο της τρεχουσας θεσης της λιστας,
                                           // επιστρεφεται η θεση αυτη. Να επισημανω οτι αυτη θεση, θα εκχωρειθει αμεσως  στη μεταβλητη position.
            {
                return i;
            }
        }
        return -1; // Διαφορετικα επιστρεφεται η τιμη -1.
    }

    public List<String> listAvailable() {
        list.clear(); // Διαγραφουμε ολα τα (τυχον) στοιχεια της λιστας (εαν υπαρχουν)
        for (i = 0; i < streamingTitle.size(); i++) // Προσθετουμε στη λιστα ολους τους τιτλους των streaming media
        {
            list.add(streamingTitle.get(i));
        }
        for (i = 0; i < physicalTitle.size(); i++) // Προσθετουμε στη λιστα ολους τους τιτλους των physical media που ειναι διαθεσιμα
        {
            if (availability.get(i) > 0) // Για να μπει στη λιστα πρεπει η διαθεσιμοτητα να ειναι μεγαλυτερη του 0.
            {
                list.add(physicalTitle.get(i));
            }
        }
        return list; // Επιστρεφουμε τη λιστα
    }

    public List<String> listRented() {
        list.clear(); // Διαγραφουμε ολα τα στοιχεια της λιστας (εαν υπαρχουν)
        for (i = 0; i < physicalTitle.size(); i++) // Προσθετουμε στη λιστα ολους τους τιτλους των physical media που δεν ειναι διαθεσιμα
        {
            if (availability.get(i) == 0) // Για να μπει στη λιστα πρεπει η διαθεσιμοτητα να ειναι ιση με το 0.
            {
                list.add(physicalTitle.get(i));
            }
        }
        return list; // Επιστρεφουμε τη λιστα
    }

    public String toString() // Το σκεπτικο μου σε αυτη τη μεθοδο ειναι: να εμφανισω ολα τα stream media
                             // καθως και οσα physical media εχουν availability>0 ως "Available media" και
                             // οσα physical media με availability<0 να τα εμφανισω ως "rented media"
    {
        toStringRented.clear(); // Αρχικα διαγραφουμε ολα τα στοιχεια αυτης της λιστας (εαν υπαρχουν)
        toStringAvailable.clear(); // Διαγραφουμε ολα τα στοιχεια και αυτης της λιστας (εαν υπαρχουν)
        for (i = 0; i < physicalMedia.size(); i++) // Σε αυτην την επαναληψη θα ασχοληθουμε ΜΟΝΟ ΜΕ ΤΑ PHYSICAL MEDIA. Η
                                                   // επαναληψη θα γινει τοσες φορες, οσο ειναι το μεγεθος της λιστας
                                                   // physicalMedia.
        {
            if (availability.get(i) == 0) // Εαν το media δεν εχει διαθεσιμοτητα
            {
                toStringRented.add(physicalMedia.get(i)); // Θα μπει στη toStringRented λιστα (εδω μπαινουν τα physical
                                                          // αντικειμενα που δεν ειναι διαθεσιμα)
            } else 
            {
                toStringAvailable.add(physicalMedia.get(i)); // Διαφορετικα θα μπει στην toStringAvailable λιστα (εδω
                                                             // μπαινουν τα physical αντικειμενα που ειναι διαθεσιμα)
            }
        }
        return "MediaStore <" + systemRentName + ">\nAvailable:\n" + streamingMedia + toStringAvailable + "\nRented\n" + toStringRented; // Τελος τα εμφανιζουμε
    }

    public void showTextUI() throws IllegalArgumentException {
        try {                               //Κάνουμε τη συνάρτηση να ξεκινά μετά από 2 δευτερόλεπτα, για να μην πετάγεται απότομα το κείμενο στο χρήστη
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        do {
            System.out.println("\n MediaStore Menu\n =============================\n 1. Add new media\n 2. List all media in store\n 3. Rent media\n 4. Return media\n 5. Exit\n Select choice (1-5): ");
            try
            {
                x = sc.nextInt();
            }
            catch (Exception e)											//Εαν υπαρξει καποιο προβλημα, τοτε εμφανιζουμε καταλληλο μηνυμα
            {
                System.out.println("Excuse me, you didn't press a number");
                showTextUI();
            }             
            sc.nextLine();  // Βαζουμε το sc.nextLine() για να ξεμπλοκαρουμε το scanner αντικειμενο. Εαν δεν το εκανα αυτο, θα εμφανιζονταν μαζι τα "Enter the title..." 
                            // και "Enter the type..." και θα παρουσιαζοταν προβλημα στις επομενες σειρες.
        } while (x < 1 || x > 5);

        if (x == 1) // Εαν ο χρηστης πατησει 1
        {
            System.out.println("Enter the title of the media that you want to add: ");
            preTitle = sc.nextLine();       // Θα διαβασουμε τον τιτλο και τον τυπο
            if (preTitle.trim().equals("")) // Εαν ο τιτλος ειναι κενο, τοτε πεταμε exception
            {       
                System.out.println("title cant be space");
            } else 
            {
                System.out.println("Enter the type(stream-video,stream-audio,book,dvd): ");
                preType = sc.nextLine();
                if (preType.trim().toLowerCase().equals("dvd") || preType.trim().toLowerCase().equals("book")){ // Εαν ο τιτλος ειναι εγκυρη physical τιμη
                
                    System.out.println("Enter the amount: ");   // Ζηταμε την ποσοτητα και καλουμε την addPhysicalMedia για να
                                                                // δημιουργηθει το επομενο media
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
        } else if (x == 2) // Εαν πατησει 2
        {
            System.out.println(toString()); // Καλουμε τη toString μεθοδο για να εμφανιστουν τα media
            showTextUI(); // Αφου τελειωσει η διαδικασια, ξανακαλουμε το μενου
        } else if (x == 3) // Εαν πατησει 3
        {
            System.out.println("Enter the title of the media that you want to rent: ");
            preTitle = sc.nextLine();
            if (rentMedia(preTitle) == false) // Η μεθοδος rentMedia επιστρεφει τιμη ψευδης ή αληθης. Αναλογα με την
                                              // τιμη που θα επιστρεψει, εμφανιζουμε αναλογο μηνυμα στο χρηστη
            {
                System.out.println("Media could not be rented.");
            } else 
            {
                System.out.println("Media was succesfully rented.");
            }
            showTextUI();   // Αφου τελειωσει η διαδικασια, ξανακαλουμε το μενου
        } else if (x == 4)  // Εαν πατησει 4
        {
            System.out.println("Enter the title of the media that you want to return: ");
            preTitle = sc.nextLine();
            if (returnMedia(preTitle) == false) // Η μεθοδος returnMedia επιστρεφει τιμη ψευδης ή αληθης. Αναλογα με την
                                                // τιμη που θα επιστρεψει, εμφανιζουμε αναλογο μηνυμα στο χρηστη
            {
                System.out.println("Media could not be returned.");
            } else {
                System.out.println("Media was succesfully returned.");
            }
            showTextUI();   // Αφου τελειωσει η διαδικασια, ξανακαλουμε το μενου
        } else              // Εαν πατησει 5
        {
            System.out.println("Goodbye then!"); // Αποχαιρετουμε το χρηστη και τερματιζουμε το προγραμμα
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

    public void addFromJson(String jsonfilename) {
        java.util.List<Media> list = new ArrayList <Media>();
        try
		{
            MediaExport.exportJsonToMediaList(list, jsonfilename);
            System.out.println("Data from " + jsonfilename + " imported successfully.");
		} catch (IOException e)											//Εαν υπαρξει καποιο προβλημα, τοτε εμφανιζουμε καταλληλο μηνυμα
		{
			System.out.println(jsonfilename + "'s data could not be imported.");
		}
		for(Media mediaVariable : list) {                               // Για κάθε media που αντλήθηκε από το json αρχείο
            if (mediaVariable.type.trim().toLowerCase().equals("dvd") || mediaVariable.type.trim().toLowerCase().equals("book"))  //εάν είναι φυσικό Media
            {
                this.addPhysicalMediaToList(mediaVariable);           // Θα περαστεί στη φυσική λίστα του Media Store
            } else if (mediaVariable.type.trim().toLowerCase().equals("stream-audio") || mediaVariable.type.trim().toLowerCase().equals("stream-video"))
            {
                this.addStreamMediatoList(mediaVariable);             // Ειδάλλως στη stream λίστα
            } else
            {
                System.out.println(mediaVariable.getTitle() + "wasn't added, cause it is not a book, stream-audio, etc.");
            }
        }        
    }

    public void saveToJson() {
        try
		{
            MediaExport.exportMediaListToJson(this.physicalMedia, "streaming.json");
            System.out.println("Streaming data saved sucessfully to streaming.json");
		} catch (IOException e)											//Εαν υπαρξει καποιο προβλημα, τοτε εμφανιζουμε καταλληλο μηνυμα
		{
			System.out.println("Could not save streaming data.");
        }    
        try
		{
            MediaExport.exportMediaListToJson(this.streamingMedia, "physical.json");
            System.out.println("physical data saved sucessfully to physical.json");
        } catch (IOException e)											//Εαν υπαρξει καποιο προβλημα, τοτε εμφανιζουμε καταλληλο μηνυμα
		{
			System.out.println("Could not save physical data.");
        }   
    }

    public static void main(final String[] args) {
        final MediaStore mediastore = new MediaStore("Dvd Club X"); // Δημιουργουμε ενα αντικειμενο MediaStore
        mediastore.addFromJson("physical.json");                    // Αντλούμε τα δεδομένα μας από τα JSON αρχεία
        mediastore.addFromJson("streaming.json");
        mediastore.showTextUI();                                    // Καλουμε το menu
        mediastore.saveToJson();                                    // Αποθηκεύουμε τα δεδομένα μας από τα JSON αρχεία
    }

}
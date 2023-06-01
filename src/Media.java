/*Επωνυμο:	Τασούλας  
  Ονομα:	  Θεοφάνης
  ΑΜ:		 Π2015092
  Ημερομηνια: 28/1/2017
  Λειτουργια: Η παρακατω κλαση υλοποιει αντικειμενα media με χαρακτηριστικα: title, type, κλπ με τη βοηθεια των μεθοδων get και addArtists. Ακομα
  δινει τη δυνατοτητα να εμφανιστουν στην οθονη με τις μεθοδους set και toString. 
  Επισης εχει διεπαφη με τη Ratable.java και μπορει να δεχεται βαθμολογιες και να τις εμφανιζει στην οθονη με ακριβεια 0.5.
*/

import java.lang.IllegalArgumentException;
import java.util.ArrayList;											//Για να δουλεψω με list χρειαζεται να εισαγω τα ArrayList και List
import java.util.List;

public class Media
{
	protected String title;											//Αρχικοπιηση των data members
	protected String type;
	protected int date;
	protected double length;
	protected String genre;
	protected String description;
	protected List <String> artists = new ArrayList <String>();
	protected int availability;
	 
	protected float			averageRating=0;						// Μεση τιμη των βαθμολογιων
	protected transient int	ratingsCount=0;						 	// Συνολο των βαθμολογιων, δε χρειαζεται να εμφανιστει με τη βοηθεια του JSON
	protected transient int	sumOfRatings=0;						 	// Αθροισμα των βαθμολογιων, δε χρειαζεται να εμφανιστει με τη βοηθεια του JSON
	protected transient float  x;									// Μεταβλητη που εμφανιζει το μεσο ορο με ακριβεια 0.5, δε χρειαζεται να εμφανιστει με τη βοηθεια του JSON

   
	public Media(String tit,String typ)								//Ο constructor παιρνει ως παραμετρους δυο String μεταβλητες tit και typ για να βαλουμε τιμη στα title και type αντιστοιχα
	{
		if (!tit.trim().toLowerCase().equals("") )					//Εαν ο τιτλος δεν ειναι ισοδυναμος με το κενο, θα εκχωριθει ο νεος τιτλος
		{
			title = tit;
		}
		setType(typ);												//Καλουμε την setType για τη δημιουργεια τυπου
	}
	
	public String getTitle()										//Η μεθοδος αυτη επιστρεφει την τιμη του title
	{
		return title;
	}
	public int getAvailability()									//Η μεθοδος αυτη επιστρεφει την τιμη του availability
	{
		return availability;
	}
	public String getType()											//Η μεθοδος αυτη επιστρεφει την τιμη του type
	{
		return type;
	}
	public int getDate()											//Η μεθοδος αυτη επιστρεφει την τιμη του date
	{
		return date;
	}
	public double getLength()										//Η μεθοδος αυτη επιστρεφει την τιμη του length
	{
		return length;
	}
	public String getGenre()										//Η μεθοδος αυτη επιστρεφει την τιμη του genre
	{
		return genre;
	}
	public String getDescription()									//Η μεθοδος αυτη επιστρεφει την τιμη του description
	{
		return description;
	}
	public List<String> getArtists()								//Η μεθοδος αυτη επιστρεφει τις τιμες της λιστας artsists
	{
		return artists;
	}
	
	public void setType(String typ)									//Η μεθοδος αυτη εκχωρει τιμη στο type με τη βοηθεια της String παραμετρου typ
	{
		if (typ.trim().toLowerCase().equals("stream-video") || typ.trim().toLowerCase().equals("stream-audio") || typ.trim().toLowerCase().equals("book") || typ.trim().toLowerCase().equals("dvd") )			   //Εαν παει να εκχωρισει καποια αποδεκτη τιμη, τοτε την εκχωρουμε στο type, διαφορετικα εμφανιζουμε καταλληλο μηνυμα
		{
			type=typ;
		}
		else
		{
			System.out.println("setType error. Type can only be: stream-video, dvd,  stream-audio or book");
		}
	}
	public void setAvailability(int availability)					//Η μεθοδος αυτη επιστρεφει την τιμη του availability
	{
		this.availability = availability;
	}
	public void setDate(int dat)									//Η μεθοδος αυτη εκχωρει τιμη στο date με τη βοηθεια της int παραμετρου dat
	{
		date=dat;
	}
	public void setLength(double len)								//Η μεθοδος αυτη εκχωρει τιμη στο length με τη βοηθεια της double παραμετρου len
	{
		length=len;
	}
	public void setGenre(String gen)								//Η μεθοδος αυτη εκχωρει τιμη στο genre με τη βοηθεια της String παραμετρου gen 
	{
		genre=gen;
	}
	public void setDescription(String des)							//Η μεθοδος αυτη εκχωρει τιμη στο description με τη βοηθεια της String παραμετρου des
	{
		description=des;
	}
	public void addArtists(String art)								//Η μεθοδος αυτη αυξανει κατα ενα κελι τη λιστα artists, με τη βοηθεια της String παραμετρου art
	{
		artists.add(art);
	}
	 
	public String toString()										//Η μεθοδος αυτη επιστρεφει ολα τα στοιχεια ενος αντικειμενου (εκτος απο τη λιστα artists και το description) 
	{
		return ("Media:{'title': '"   +title   +"', 'date': '"   +date   +"', 'length': '"   + length   +"', 'genre': '"   +genre   +"', 'type': '"   +type  +"', 'averageRating': '"  +getAverageRating()	+"', 'ratingsCount': '"   +ratingsCount   +"' }" );
	}   
	
	public float getAverageRating()								   	// Η μεθοδος αυτη, επιστρεφει τη μεση τιμη των βαθμολογιων (rating), με ακριβεια 0.5
	{
		if (ratingsCount!=0)										// Εαν υπαρχουν βαθμολογιες 
		{
			if (averageRating < 1.25)								// Ελεγχουμε την τιμη της μεσης τιμης και εκχωρουμε την την καταλληλη τιμη στη μεταβλητη x
			{
				x=1;												// Προκειται να εμφανισουμε τη μεταβλητη x και οχι την μεταβλητη averageRating, για να εχουμε ακριβεια 0.5 
			}
			else if (averageRating < 1.75)
			{
				x=1.5f;
			}
			else if (averageRating < 2.25)
			{
				x=2;
			}
			else if (averageRating < 2.75)
			{
				x=2.5f;
			}
			else if (averageRating < 3.25)
			{
				x=3;
			}
			else if (averageRating < 3.75)
			{
				x=3.5f;
			}
			else if (averageRating < 4.25)
			{
				x=4;
			}
			else if (averageRating < 4.75)
			{
				x=4.5f;
			}
			else
			{
				x=5;
			}
			return x;												// Επιστρεφουμε τη μεταβλητη x;
		}
		else														// Εαν δεν υπαρχουν βαθμολογιες, επιστρεδουμε 0.
		{
			return 0;
		}
	}
	
	public int getRatingsCount()									// Η μεθοδος αυτη, επιστρεφει το ποσες φορες δοθηκε μια βαθμολογια(rating)
	{
		return ratingsCount;
	}
	
	
	public void addRating(int a) throws IllegalArgumentException	// Η μεθοδος αυτη, δεχεται μια κριτικη(rating) και...
	{
		if (a<1 || a>5)												// Εαν η τιμη δεν ειναι εγκυρη
		{
			throw new IllegalArgumentException("Vallue must be between 1 and 5.");   // Τοτε πεταμε ενα exception με καταληλο μηνυμα
		}
		else
		{
			this.sumOfRatings = sumOfRatings + a;					// Διαφορετικα, αυξανουμε το αθροισμα των βαθμολογιων 
			ratingsCount++;											// Αυξανουμε το συνολο των βαθμολογιων 
			this.averageRating = (float) sumOfRatings/ratingsCount;	// Και διαιρουμε το αθροισμα των βαθμολογιων με το συνολο τους (ως float), για να παρουμε τη μεση τιμη
		}
	}
}
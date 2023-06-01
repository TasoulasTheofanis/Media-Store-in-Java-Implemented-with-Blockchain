class app {
    public static void main(String[] args){
        final MediaStore mediastore = new MediaStore();	// Δημιουργουμε ενα αντικειμενο MediaStore
        mediastore.addFromJson();						// Αντλούμε τα δεδομένα μας από τα JSON αρχεία
        mediastore.createBlock();						// Δημιουργουμε ενα block από συναλλαγές
        mediastore.welcomePage();						// Καλουμε το menu
        mediastore.saveToJson();						// Αποθηκεύουμε τα δεδομένα μας από τα JSON αρχεία
    }
}
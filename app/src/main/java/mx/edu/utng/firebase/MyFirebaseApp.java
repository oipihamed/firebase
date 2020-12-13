package mx.edu.utng.firebase;

import com.google.firebase.database.FirebaseDatabase;

public class MyFirebaseApp extends android.app.Application {
    public void onCreate(){
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}

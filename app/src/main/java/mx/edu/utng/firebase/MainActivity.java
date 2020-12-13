package mx.edu.utng.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Video> listVideo=new ArrayList<Video>();
    ArrayAdapter<Video> arrayaAdapterVideo;
    ListView listV_videos;

    EditText etTema;
    Spinner spiArea;
    Spinner spiSec;
    Button btnRegistrar;
    FirebaseDatabase firebaseDatabase;
    Video videoSeleccionada;
    private DatabaseReference bdDiario;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etTema=(EditText)findViewById(R.id.etTema);
        spiArea=(Spinner)findViewById(R.id.spiArea);
        spiSec=(Spinner)findViewById(R.id.spiSeccion);
        btnRegistrar=(Button)findViewById(R.id.btnRegistrar);
        listV_videos=findViewById(R.id.lv_datosVideos);
        listV_videos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                videoSeleccionada=(Video) parent.getItemAtPosition(position);
                etTema.setText(videoSeleccionada.getDescripcion());


                }
        });
        inicializarFireBase();
        listarDatos();

        btnRegistrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                registrarVideo();
            }
        });






        /*btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarClase();
            }
        });*/
    }
    public void inicializarFireBase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase=FirebaseDatabase.getInstance();

        bdDiario=firebaseDatabase.getReference("Video");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.icon_add:{
                registrarVideo();
                break;
            }
            case R.id.icon_save:{
                saveVideo();
                Toast.makeText(this,"Video actualizado",Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.icon_del:{
                delVideo();
                break;
            }
            default:break;
        }
            return true;
    }
    private void saveVideo(){
        Video v=new Video();
        v.setVideoId(videoSeleccionada.getVideoId());
        String tipo=spiSec.getSelectedItem().toString();
        String categoria=spiArea.getSelectedItem().toString();
        String descripcion= etTema.getText().toString();
        v.setCategoria(categoria);
        v.setDescripcion(descripcion);
        v.setTipo(tipo);
        bdDiario.child("Lista").child(v.getVideoId()).setValue(v);
    }
    private void delVideo(){
        Video v=new Video();
        v.setVideoId(videoSeleccionada.getVideoId());
        bdDiario.child("Lista").child(v.getVideoId()).removeValue();
        Toast.makeText(this,"Video Eliminado",Toast.LENGTH_LONG).show();
    }
private void listarDatos(){
        bdDiario.child("Lista").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               listVideo.clear();
               for (DataSnapshot objectSnapshot:dataSnapshot.getChildren()){
                   Video v=objectSnapshot.getValue(Video.class);
                   listVideo.add(v);
                   arrayaAdapterVideo=new ArrayAdapter<Video>(MainActivity.this,android.R.layout.simple_list_item_1,listVideo);
                   listV_videos.setAdapter(arrayaAdapterVideo);
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
}
    public void registrarVideo(){
        String tipo=spiSec.getSelectedItem().toString();
        String categoria=spiArea.getSelectedItem().toString();
        String descripcion= etTema.getText().toString();

        if(!TextUtils.isEmpty(categoria)){
            String id=bdDiario.push().getKey();
            Video video=new Video(id,tipo,categoria,descripcion);
            bdDiario.child("Lista").child(id).setValue(video);
            Toast.makeText(this,"Video registrado",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"Video no registrada",Toast.LENGTH_LONG).show();
        }

    }
    public void registrarClase(){
        String seccion=spiSec.getSelectedItem().toString();
        String area=spiArea.getSelectedItem().toString();
        String tema= etTema.getText().toString();

        if(!TextUtils.isEmpty(tema)){
            String id=bdDiario.push().getKey();
            Clase leccion=new Clase(id,seccion,area,tema);
            bdDiario.child("Leccion").child(id).setValue(leccion);
            Toast.makeText(this,"Clase registrada",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"Clase no registrada",Toast.LENGTH_LONG).show();
        }
    }
}
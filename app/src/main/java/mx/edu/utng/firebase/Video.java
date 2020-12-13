package mx.edu.utng.firebase;

public class Video {
    private String videoId,tipo,categoria,descripcion;
    public Video(String videoId,String tipo,String categoria,String descripcion){
        this.videoId=videoId;
        this.tipo=tipo;
        this.categoria=categoria;
        this.descripcion=descripcion;
    }
public Video(){

}
    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return
                "Id='"+videoId+"\'"+
                " Tipo= '" + tipo + '\'' +
                "\nCategoria= '" + categoria + '\'' +
                "\nDescripcion='" + descripcion + '\'' ;
    }
}

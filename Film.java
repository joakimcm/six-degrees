public class Film extends Node{
    String ttid;
    String navn;
    float rating;
    int antallStemmer;

    public Film(String ttid, String navn, float rating, int antallStemmer){
        this.ttid = ttid;
        this.navn = navn;
        this.rating = rating;
        this.antallStemmer = antallStemmer;
    }

    public float vekt(){
        return rating;
    }


    public String toString(){
        return navn + " " + rating;
    }
}
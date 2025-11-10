
    public class Skuespiller extends Node{
        String nmid;
        String navn;
        String[] filmer;
        float rating = 0;
    
        public Skuespiller(String nmid, String navn, String[] filmer){
            this.nmid = nmid;
            this.navn = navn;
            this.filmer = filmer;
        }

        
        public String toString(){
            return navn;
        }
    }
    
  


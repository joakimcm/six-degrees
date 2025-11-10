
import java.util.Scanner;
import java.io.File;

public class Hoved{
    public static void main(String[] args){
        Graf graf = new Graf();

        Scanner s = null;

        try{
            s = new Scanner(new File("movies.tsv"));
        }catch(Exception e){
            System.out.println("Kan ikke finne fil!");
            System.exit(1);
        }

        while(s.hasNextLine()){
            graf.lagFilmer(s.nextLine().split("\t"));
        }

        s = null;
        try{
            s = new Scanner(new File("actors.tsv"));
        }catch(Exception e){
            System.out.println("Kan ikke finne fil!");
            System.exit(1);
        }

        while(s.hasNextLine()){
            graf.lagSkuespillere(s.nextLine().split("\t"));
        }

        s.close();
        System.out.println("");

        Scanner ny = new Scanner(System.in);
        System.out.println("Skriv 1 for å finne korteste sti, 2 for chilleste sti og -1 for å avslutte");
        int svar = ny.nextInt();
        ny.nextLine();
        
        while(svar != -1){
            if(svar == 1){
                System.out.println("nmID på skuespiller 1: ");
                String forste = ny.nextLine();
                System.out.println("");
                
                System.out.println("nmID på skuespiller 2: ");
                String andre = ny.nextLine();
                System.out.println("");

                System.out.println("-----------------" + "\n");
                graf.finnKortesteSti(forste, andre);
                System.out.println("-----------------" + "\n");

                System.out.println("Skriv 1 for å finne korteste sti, 2 for chilleste sti og -1 for å avslutte");
                svar = ny.nextInt();
                ny.nextLine();
            }else if(svar == 2){
                System.out.println("nmID på skuespiller 1: ");
                String forste = ny.nextLine();
                System.out.println("");
                
                System.out.println("nmID på skuespiller 2: ");
                String andre = ny.nextLine();
                System.out.println("");

                System.out.println("-----------------" + "\n");
                graf.finnKortesteStiVektet(forste, andre);
                System.out.println("-----------------" + "\n");

                System.out.println("Skriv 1 for å finne korteste sti, 2 for chilleste sti og -1 for å avslutte");
                svar = ny.nextInt();
                ny.nextLine();
            }
            
        }

      
        ny.close();

        
    }
}


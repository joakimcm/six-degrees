
import java.util.List;
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
        boolean ferdig = false;

        while (!ferdig) {
            System.out.println("\n=== Skuespiller-graf ===");
            System.out.println("1) Finn korteste sti mellom to skuespillere");
            System.out.println("2) Finn 'chilleste' sti mellom to skuespillere");
            System.out.println("0) Avslutt");
            System.out.print("Valg: ");

            String valg = ny.nextLine().trim();

        switch (valg) {
            case "1":
                System.out.println("Velg skuespiller 1:");
                Skuespiller a = velgSkuespiller(graf, ny);

                System.out.println("Velg skuespiller 2:");
                Skuespiller b = velgSkuespiller(graf, ny);

                System.out.println("-----------------" + "\n");
                graf.finnKortesteSti(a.nmid, b.nmid);
                System.out.println("-----------------" + "\n");
                break;
            case "2":
                System.out.println("Velg skuespiller 1:");
                Skuespiller aC = velgSkuespiller(graf, ny);

                System.out.println("Velg skuespiller 2:");

                
                Skuespiller bC = velgSkuespiller(graf, ny);

                System.out.println("-----------------" + "\n");
                graf.finnKortesteSti(aC.nmid, bC.nmid);
                System.out.println("-----------------" + "\n");
                break;
            case "0":
                ferdig = true;
                break;
            default:
                System.out.println("Ugyldig valg, prøv igjen.");
        }
    }

    ny.close();
    System.out.println("Ha det!");  
        
    }

    private static Skuespiller velgSkuespiller(Graf graf, Scanner input) {
    while (true) {
        System.out.print("Skriv inn navn (eller del av navn) på skuespiller: ");
        String navnDel = input.nextLine().trim();

        if (navnDel.isEmpty()) {
            System.out.println("Du må skrive noe.");
            continue;
        }

        List<Skuespiller> kandidater = graf.finnSkuespillere(navnDel);

        if (kandidater.isEmpty()) {
            System.out.println("Fant ingen skuespillere som matcher. Prøv igjen.");
            continue;
        }

        // Vis kandidater
        for (int i = 0; i < kandidater.size(); i++) {
            Skuespiller s = kandidater.get(i);
            System.out.println((i + 1) + ") " + s.navn + " [" + s.nmid + "]");
        }

        System.out.print("Velg nummer (1-" + kandidater.size() + "), eller 0 for å søke på nytt: ");
        String svar = input.nextLine().trim();

        int valg;
        try {
            valg = Integer.parseInt(svar);
        } catch (NumberFormatException e) {
            System.out.println("Ugyldig tall, prøv igjen.");
            continue;
        }

        if (valg == 0) {
            continue; // nytt søk
        }

        if (valg < 1 || valg > kandidater.size()) {
            System.out.println("Ugyldig valg, prøv igjen.");
            continue;
        }

        return kandidater.get(valg - 1);
    }
}
}


import java.util.*;

public class Graf {

    int antallNoder = 0;
    int antallKanter = 0;

    HashMap<String, Skuespiller> skuespillerOppslag = new HashMap<>();
    HashMap<String, Film> filmOppslag = new HashMap<>();

    HashMap<Node, Set<Node>> kanter = new HashMap<>(); 

    // Jeg fant ikke helt ut hvordan jeg skulle vekte grafen 
    // Lagde derfor en HashMap med HashSet som nøkkel, samt en metode hentVekt(Node en, Node to)
    // Forstår at det kan være uheldig og ikke helt riktig
    HashMap<Set<Node>, Float> vekt = new HashMap<>();

    HashSet<Node> alleNoder = new HashSet<>();

    public void lagFilmer(String[] linje){
        String ttid = linje[0];
        String tittel = linje[1];
        float rating = Float.parseFloat(linje[2]);
        int antallS = Integer.parseInt(linje[3]);

        Film film = new Film(ttid, tittel, rating, antallS);
        alleNoder.add(film);
        filmOppslag.put(ttid, film);

        antallNoder++;
       
    }

    public void lagSkuespillere(String[] linje){
        String nmid = linje[0];
        String navn = linje[1];
        String[] ttid = Arrays.copyOfRange(linje, 2, linje.length);
        Skuespiller skueS = new Skuespiller(nmid, navn, ttid);
        alleNoder.add(skueS);

        skuespillerOppslag.put(nmid, skueS);

        antallNoder++;

        // Siden jeg vet at jeg har skrevet alle filmNodene
        // Så begynner jeg å koble de sammen allerede her 
        // (Altså hver gang jeg lager en ny skuesepillernode)

        for(String id : ttid){
            Film film = filmOppslag.get(id);

            // Hvis filmen ikke eksisterer i movies.tsv skal jeg ignorere den
            if(film != null){
                kanter.putIfAbsent(film, new HashSet<>());
                kanter.get(film).add(skueS);
                
                kanter.putIfAbsent(skueS, new HashSet<>());
                kanter.get(skueS).add(film);

                //Mapper vekt mellom to noder
                vekt.put(new HashSet<>(Arrays.asList(film, skueS)), film.vekt());

                antallKanter++;
            }   
        }
    }



    public void finnKortesteSti(String ID1, String ID2){
        Node start = skuespillerOppslag.get(ID1);
        Node slutt = skuespillerOppslag.get(ID2);

        ArrayList<Node> liste = BFSKortesteStiMellom(start, slutt);
        System.out.println(liste.get(0));

        for(int i = 1; i < liste.size(); i++){
            if(i%2 == 1){
                System.out.print("===[ " + liste.get(i) + " ] ===>");
            }else{
                System.out.println("  " + liste.get(i) + "\n");
            }
        }
    }

    private ArrayList<Node> BFSKortesteStiMellom(Node start, Node slutt){
        HashMap<Node, Node> foreldre = BFSKortesteSti(start);
        Node n = slutt;
        ArrayList<Node> sti = new ArrayList<>();

        if(!foreldre.containsKey(slutt)){
            return sti;
        }

        while(n != null){
            sti.add(n);
            n = foreldre.get(n);
        }

        Collections.reverse(sti);
        return sti;
    }

    private HashMap<Node, Node> BFSKortesteSti(Node start){
        HashMap<Node, Node> foreldre = new HashMap<>();
        Deque<Node> ko = new ArrayDeque<>();
        foreldre.put(start, null);
        ko.add(start);

        while(!ko.isEmpty()){
            Node denne = ko.removeFirst();
            for(Node n : kanter.get(denne)){
                if(!foreldre.containsKey(n)){
                    foreldre.put(n, denne);
                    ko.add(n);
                }
            }
        }
        return foreldre;
    }


    public void finnKortesteStiVektet(String ID1, String ID2){
        Node start = skuespillerOppslag.get(ID1);
        Node slutt = skuespillerOppslag.get(ID2);
        float totalVekt = 0;

        ArrayList<Node> liste = DijkstraKortesteStiMellom(start, slutt);
        System.out.println(liste.get(0));

        for(Node n : liste){
            if(n instanceof Film){
                Film film = (Film) n;
                totalVekt += 10-film.rating;
            }
        }

        for(int i = 1; i < liste.size(); i++){
            if(i%2 == 1){
                System.out.print("===[ " + liste.get(i) + " ] ===>");
            }else{
                System.out.println("  " + liste.get(i) + "\n");
            }
            
        }
        System.out.println(totalVekt);
    }

    private ArrayList<Node> DijkstraKortesteStiMellom(Node start, Node slutt){
        HashMap<Node, Node> foreldre = Dijkstra(start);
        Node n = slutt;
        ArrayList<Node> sti = new ArrayList<>();

        if(!foreldre.containsKey(slutt)){
            return sti;
        }

        while(n != null){
            sti.add(n);
            n = foreldre.get(n);
        }

        Collections.reverse(sti);
        return sti;
    }

    private HashMap<Node, Node> Dijkstra(Node start){
        HashMap<Node, Double> dist = new HashMap<>();
        dist.put(start, 0.0);
        Double standardVerdi = Double.MAX_VALUE;
        HashMap<Node, Node> foreldre = new HashMap<>();
        foreldre.put(start, null);

        // For å ha alt på det rene så legger jeg inn disse kommentarene: 
        // Følgende prioritetskø og innsettingsmetode er laget med chatGPT
        // den lager en prioritetskø der jeg kan lagre en node og dens prioritet sammen
        PriorityQueue<Map.Entry<Node, Double>> pq = new PriorityQueue<>(
            Comparator.comparingDouble(Map.Entry::getValue)
        );

        pq.add(new AbstractMap.SimpleEntry<>(start, 0.0));
       
        while(!pq.isEmpty()){
            Map.Entry<Node, Double> objekt = pq.poll();
            Node u = objekt.getKey();
            double kostnad = objekt.getValue();
            
            if(kostnad != dist.get(u)){
                continue;
            }
            for(Node v : kanter.get(u)){
                double c = kostnad + hentVekt(u, v); 
                // computeIfAbsent gjør at verdien er standardVerdi (Double.MAX_VALUE) om v ikke er definert i dist enda. 
                if(c < dist.computeIfAbsent(v, k -> standardVerdi)){
                    dist.put(v, c);
                    pq.add(new AbstractMap.SimpleEntry<>(v, c));
                    foreldre.put(v, u);
                }
            }
        }
        return foreldre;
    }

    private Double hentVekt(Node en, Node to){
        HashSet<Node> nokkel = new HashSet<>(Arrays.asList(en, to));
        return 10.0 - vekt.get(nokkel);
    }



//Jeg får en StackOverflowError med denne algortimen
//Jeg har prøvd å skrive den med en stack istedenfor, men da ser det ut som jeg får feil svar 
// Kaller derfor ikke på den i main metoden min 
    public int antallKomponenter(){
        HashSet<Node> besokt = new HashSet<>();
        int teller = 0;

        for(Node v : alleNoder){
            if(!besokt.contains(v)){
                DFS(v, besokt);
                teller++;
            }
        }
        return teller;

    }

    public void DFS (Node v, HashSet<Node> besokt){
        besokt.add(v);
        
        for(Node u : kanter.get(v)){
            if(!besokt.contains(u)){
                DFS(u, besokt);
            }
        }
    }
    
}

# Six degrees of IMDB

Et morsomt terminal-program som lar deg finne ut hvilke filmer du må se for å komme fra den ene skuespilleren til den andre! 

## Oppsett
1. Klon prosjektet til din lokale maskin 
2. Navigér til repoet:  
`cd six-degrees`

3. Bygg programmet:  
`javac Hoved.java`

## Bruke programmet 
1. Kjør programmet:  
`javac Hoved`

2. Velg hvilket modus du vil ha
- Med korteste sti finner programmet korteste vei mellom to skuespillere.
- Med chilleste sti finner programmet korteste vei med de filmene som har best anmeldelser.

3. Skriv inn navnet på skuespiller a og velg riktig skuespiller.
4. Gjør det samme for skuespiller b.  

Vipps! Så vet du hva du skal se på neste lørdagskveld. 

## Eksempel bruk: 
```
=== Six-degrees-of-IMDB ===
1) Finn korteste sti mellom to skuespillere
2) Finn 'chilleste' sti mellom to skuespillere
0) Avslutt
Valg: 1


Velg skuespiller 1:
Skriv inn navn (eller del av navn) på skuespiller: tom han
1) Tom Hanslmaier [nm2450019]
2) Tom Hanks [nm0000158]
Velg nummer (1-2), eller 0 for å søke på nytt: 2


Velg skuespiller 2:
Skriv inn navn (eller del av navn) på skuespiller: liv ullm
1) Liv Ullmann [nm0880521]
Velg nummer (1-1), eller 0 for å søke på nytt: 1
-----------------

Tom Hanks  ===[ Big 7.3 ] ===>  Robert Loggia

Robert Loggia  ===[ Gaby: A True Story 6.8 ] ===>  Liv Ullmann

-----------------


=== Six-degrees-of-IMDB ===
1) Finn korteste sti mellom to skuespillere
2) Finn 'chilleste' sti mellom to skuespillere
0) Avslutt
Valg: 0


Ha det!
```




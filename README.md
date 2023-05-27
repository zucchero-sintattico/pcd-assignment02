# Obiettivo del Progetto

L'assignment consiste nell'affrontare il problema illustrato nel primo
assignment utilizzando le tecniche di programmazione asincrona viste nel
corso. In particolare:

1.  Approccio asincrono a Task -- Framework Executors

2.  Approccio basato Virtual Threads

3.  Approccio asincrono ad eventi (event-loop) -- Framework Vert.x o
    equivalenti

4.  Approccio basato su programmazione reattiva -- Framework RxJava o
    equivalenti

Per ogni approccio, si richiede che la soluzione sia organizzata in modo
da incapsulare la funzionalità di analisi dei sorgenti in una libreria,
che implementi un'interfaccia SourceAnalyser includa due metodi
asincroni (in pseudocodice):

-   `getReport(Directory d)`: metodo asincrono che deve restituire un
    report che include i risultati richiesti dall'analisi (gli N
    sorgenti con il numero maggiore di linee di codice e la
    distribuzione complessiva -- come indicato nell'assignment 01).

-   `analyzeSources(Directory d)`: metodo asincrono che, a differenza
    del precedente, permetta di effettuare un'analisi incrementale, con
    produzione incrementale dei risultati e interrompibile


# Progetto Ingegneria del Software – Appello 1 A.A. 24-25

- Nome studente: Luca Dal Bianco
- Matricola: 2102603
- E-mail: luca.dalbianco@studenti.unipd.it
- Descrizione: Implementazione dell'adapter per l'interfaccia List (J2SE 1.4.2) utilizzando Vector (CLDC 1.1).
- Librerie usate: JUnit 4.13.2, Hamcrest 1.3 (in /lib)

## Esecuzione test

Compilazione:
javac -cp "lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" -d bin myAdapter/*.java myTest/*.java

Esecuzione:
java -cp "bin;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" myTest.TestRunner

JavaDoc (con tag personalizzati):
javadoc -tag design.test:cm:"IngSoftware-Appello1:" -tag summary.test:cm:"Sommario:" -tag description.test:cm:"Descrizione:" -tag precondition.test:cm:"Condizione iniziale:" -tag postcondition.test:cm:"Condizione finale:" -tag result.test:cm:"Risultato:" -private -d doc/javadoc -cp lib/junit-4.13.2.jar myAdapter\*.java myTest\*.java

##

package org.example;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Wybierz opcje");
        System.out.println("1. Kodowanie pliku");
        System.out.println("2. Dekodowanie pliku");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        if (choice == 1) {
            System.out.println("Podaj sciezke pliku");
            scanner.nextLine();
            String path = scanner.nextLine();
            System.out.println(path);
            System.out.println("Podaj sciezke pod ktora ma byc zapisany zakodowany tekst (pelnaSciezka/plik_tekstowy.txt)");
            String encodedtxt = scanner.nextLine();
            System.out.println(encodedtxt);
            System.out.println("Podaj sciezke pod ktora ma byc zapisany zakodowany tekst (pelnaSciezka/plik_binarny.bin)");
            String encodedbin = scanner.nextLine();
            System.out.println(encodedbin);

            var text = FileReader.readFromFile(path);
            EncodeText encodeText = new EncodeText(text);
            var encoded = encodeText.encodeText();

            encodeText.writeToTextFile(encodedtxt, encoded);
            encodeText.writeToBinFile(encodedbin, encoded);
        } else if (choice == 2) {
            System.out.println("Podaj sciezke pliku binarnego do odkodowania");
            scanner.nextLine();
            String path = scanner.nextLine();
            System.out.println("Podaj sciezke pliku do zapisu odkodowanego pliku");
            String savepath = scanner.nextLine();
            DecodeText.decodeFile(path, savepath);
        } else {
            System.out.println("blad");
            System.exit(1);
        }
    }
}
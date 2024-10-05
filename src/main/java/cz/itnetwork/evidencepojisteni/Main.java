/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package cz.itnetwork.evidencepojisteni;

import java.util.Scanner;

/**
 *
 * @author Pavel Šach
 */
public class EvidencePojisteni {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, "UTF-8");
        UzivatelskyVstup vstup = new UzivatelskyVstup(scanner);
        UzivatelskeRozhrani ui = new UzivatelskeRozhrani(scanner, vstup);
        ui.vykresliUvodniObrazovku();
    }
}

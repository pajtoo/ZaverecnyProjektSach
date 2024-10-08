/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.itnetwork.evidencepojisteni;

/**
 * Třída reprezentující pojištěnce
 * @author Pavel
 */
public class PojistenecDTO {

    private Long id;
    private String jmeno;
    private String prijmeni;
    private int vek; // maximální věk = 130
    private String telefon;
    /* Ukládá se v mezinárodním formátu bez mezer. V případě nezadání
    mezinárodní předvolby je automaticky doplněna předvolba +420 */

    /**
     * Konstruktor pojištěnce
     * @param jmeno Jméno
     * @param prijmeni Příjmení
     * @param vek Věk
     * @param telefon Telefon
     */
    public PojistenecDTO(String jmeno, String prijmeni, int vek, String telefon) {
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.vek = vek;
        this.telefon = telefon;
    }

    /**
     * @return the jmeno
     */
    public String getJmeno() {
        return jmeno;
    }

    /**
     * @param jmeno the jmeno to set
     */
    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    /**
     * @return the prijmeni
     */
    public String getPrijmeni() {
        return prijmeni;
    }

    /**
     * @param prijmeni the prijmeni to set
     */
    public void setPrijmeni(String prijmeni) {
        this.prijmeni = prijmeni;
    }

    /**
     * @return the vek
     */
    public int getVek() {
        return vek;
    }

    /**
     * @param vek the vek to set
     */
    public void setVek(int vek) {
        this.vek = vek;
    }

    /**
     * @return the telefon
     */
    public String getTelefon() {
        return telefon;
    }

    /**
     * @param telefon the telefon to set
     */
    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("%d: %s, %s, %d, %s", id, prijmeni, jmeno, vek, telefon);
    }
}

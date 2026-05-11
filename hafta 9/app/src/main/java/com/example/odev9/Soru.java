package com.example.odev9;

public class Soru {
    private int id;
    private String soruMetni;
    private String secenekA;
    private String secenekB;
    private String secenekC;
    private String secenekD;
    private int dogruCevap; // 0=A, 1=B, 2=C, 3=D

    public Soru(int id, String soruMetni, String secenekA, String secenekB,
                String secenekC, String secenekD, int dogruCevap) {
        this.id = id;
        this.soruMetni = soruMetni;
        this.secenekA = secenekA;
        this.secenekB = secenekB;
        this.secenekC = secenekC;
        this.secenekD = secenekD;
        this.dogruCevap = dogruCevap;
    }

    public int getId() { return id; }
    public String getSoruMetni() { return soruMetni; }
    public String getSecenekA() { return secenekA; }
    public String getSecenekB() { return secenekB; }
    public String getSecenekC() { return secenekC; }
    public String getSecenekD() { return secenekD; }
    public int getDogruCevap() { return dogruCevap; }
}

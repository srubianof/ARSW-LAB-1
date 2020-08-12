package edu.eci.arsw.threads;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

import java.util.LinkedList;

public class BlackListSearchThread  extends Thread
{
    private String ip;
    private int servidorInicial;
    private int servidorFinal;
    private LinkedList<Integer> blackListOcurrences=new LinkedList<>();

    private int ocurrencesCount=0;
    private HostBlacklistsDataSourceFacade facade;

    public BlackListSearchThread(String ip, int inicial, int fin, HostBlacklistsDataSourceFacade skds) {
        this.ip = ip;
        this.servidorInicial = inicial;
        this.servidorFinal = fin;
        this.facade = skds;
    }

    public void run(){
        for (int i = servidorInicial; i <= servidorFinal ; i++) {

            if (facade.isInBlackListServer(i, ip)){

                blackListOcurrences.add(i);

                ocurrencesCount++;
            }

        }

    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getServidorInicial() {
        return servidorInicial;
    }

    public void setServidorInicial(int servidorInicial) {
        this.servidorInicial = servidorInicial;
    }

    public int getServidorFinal() {
        return servidorFinal;
    }

    public void setServidorFinal(int servidorFinal) {
        this.servidorFinal = servidorFinal;
    }

    public LinkedList<Integer> getBlackListOcurrences() {
        return blackListOcurrences;
    }

    public void setBlackListOcurrences(LinkedList<Integer> blackListOcurrences) {
        this.blackListOcurrences = blackListOcurrences;
    }

    public int getOcurrencesCount() {
        return ocurrencesCount;
    }

    public void setOcurrencesCount(int ocurrencesCount) {
        this.ocurrencesCount = ocurrencesCount;
    }

    public HostBlacklistsDataSourceFacade getFacade() {
        return facade;
    }

    public void setFacade(HostBlacklistsDataSourceFacade facade) {
        this.facade = facade;
    }
}

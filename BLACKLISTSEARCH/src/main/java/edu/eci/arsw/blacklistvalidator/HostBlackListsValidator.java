/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import edu.eci.arsw.threads.BlackListSearchThread;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author hcadavid
 */
public class HostBlackListsValidator {
    private static final int BLACK_LIST_ALARM_COUNT = 5;

    /**
     * Check the given host's IP address in all the available black lists,
     * and report it as NOT Trustworthy when such IP was reported in at least
     * BLACK_LIST_ALARM_COUNT lists, or as Trustworthy in any other case.
     * The search is not exhaustive: When the number of occurrences is equal to
     * BLACK_LIST_ALARM_COUNT, the search is finished, the host reported as
     * NOT Trustworthy, and the list of the five blacklists returned.
     *
     * @param ipaddress suspicious host's IP address.
     * @return Blacklists numbers where the given host's IP address was found.
     */
    public List<Integer> checkHost(String ipaddress, int n) {
        LinkedList<Integer> blackListOcurrences = new LinkedList<>();

        AtomicInteger ocurrencesCount = new AtomicInteger();
        AtomicInteger checkedListsCount = new AtomicInteger();

        HostBlacklistsDataSourceFacade skds = HostBlacklistsDataSourceFacade.getInstance();
        int totalServers = skds.getRegisteredServersCount();
        int avg = totalServers / n;
        int last = 0;
        int i = 0;
        int actualThread = 1;
        BlackListSearchThread[] hilos = new BlackListSearchThread[n];

        while (last < totalServers) {
            if (actualThread != n) {
                hilos[i] = new BlackListSearchThread(ipaddress, last, last + avg, skds, ocurrencesCount, checkedListsCount, blackListOcurrences);
                actualThread++;
                last += avg;
            } else {
                int a = n == 1 ? totalServers : last + (totalServers % n);
                hilos[i] = new BlackListSearchThread(ipaddress, last, a, skds, ocurrencesCount, checkedListsCount, blackListOcurrences);
                last = totalServers;
            }
            i++;
        }
        for (BlackListSearchThread t : hilos) {
            t.start();
        }
        for (BlackListSearchThread t : hilos) {
            try {
                t.join();
            } catch (InterruptedException e) {
                t.interrupt();
            }
        }
        if (ocurrencesCount.get() >= BLACK_LIST_ALARM_COUNT) {
            skds.reportAsNotTrustworthy(ipaddress);
        } else {
            skds.reportAsTrustworthy(ipaddress);
        }
        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{checkedListsCount, skds.getRegisteredServersCount()});
        return blackListOcurrences;
    }

    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());


}

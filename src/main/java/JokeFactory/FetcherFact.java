/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JokeFactory;

import Interface.IJokeFetcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Interface.IFetcherFactory;
/**
 *
 * @author micha
 */
public class FetcherFact implements IFetcherFactory{

    private final List<String> availableTypes
            = Arrays.asList("EduProgram", "ChuckNorris", "Moma", "Tambal");

    @Override
    public List<String> getAvailableTypes() {
        return availableTypes;
    }

    @Override
    public List<IJokeFetcher> getJokeFetchers(String jokesToFetch) {
        List list = new ArrayList();
        list.add(new EduProgram());
        list.add(new ChuckNorris());
        list.add(new Moma());
        list.add(new Tambal());
        return list;
    }
}

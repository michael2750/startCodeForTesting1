package testex;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import Interface.IDateFormatter;
import Interface.IFetcherFactory;
import Interface.IJokeFetcher;
import JokeFactory.ChuckNorris;
import JokeFactory.EduProgram;
import JokeFactory.Moma;
import JokeFactory.Tambal;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class JokeFetcherTest {

    private JokeFetcher jokeFetcher;
    String[] invalidTokens;
    @Mock
    IDateFormatter dateFormatter;
    @Mock
    EduProgram eduJokeMock;
    @Mock
    ChuckNorris chuckMock;
    @Mock
    Moma momaMock;
    @Mock
    Tambal tambalMock;
    @Mock
    IFetcherFactory factory;

    @Before
    public void setup() {
        jokeFetcher = new JokeFetcher(new SimpleDateFormat("dd MMM yyyy hh:mm aa"), factory);
        invalidTokens = new String[]{"ieduprog", "ichucknorris", "imoma", "itambal"};
        when(factory.getJokeFetchers(any())).thenReturn(Arrays.asList(eduJokeMock, chuckMock, momaMock, tambalMock));
        when(factory.getAvailableTypes()).thenReturn(Arrays.asList("EduJoke", "ChuckNorris", "Moma", "Tambal"));
    }

    @Test
    @DisplayName("Factory returns objects of isntance IJokeFetcher")
    public void factoryTest() {
        List<IJokeFetcher> result = factory.getJokeFetchers("EduJoke,ChuckNorris,Moma,Tambal");
        assertAll("Checking each iJokeFetcher", () -> {
            assertEquals(4, result.size());
            result.forEach(jokeFetcher -> {
                assertTrue(jokeFetcher instanceof IJokeFetcher);
            });
        });
    }

    @Test
    @DisplayName("Checking available types")
    public void getAvailableTypesTest() {
        assertAll("Testing the arraylist has all the types", () -> {
            List<String> list = factory.getAvailableTypes();
            assertFalse(list.isEmpty());
            factory.getAvailableTypes().forEach(token -> assertTrue(list.contains(token)));
        });
    }

    @Test
    @DisplayName("Checking String")
    public void isStringValidTest() {
        assertAll("Testing for tokens", () -> {
            factory.getAvailableTypes().forEach(token -> assertTrue(jokeFetcher.isStringValid(token)));
            Arrays.stream(invalidTokens).forEach(invalidToken -> assertFalse(jokeFetcher.isStringValid(invalidToken)));
        });
    }

    @Test
    @DisplayName("Getting Jokes")
    public void getJokesTest() throws JokeException {
        when(dateFormatter.getFormattedDate(any(), any(), any())).thenReturn("Europe/Copenhagen");
        Jokes jokes = jokeFetcher.getJokes("EduJoke,ChuckNorris,ChuckNorris,Moma,Tambal", "Europe/Copenhagen", dateFormatter);
        assertAll("Checking the state in Jokes", () -> {
            assertFalse(jokes.getJokes().isEmpty());
            assertEquals("Europe/Copenhagen", jokes.getTimeZoneString());
            verify(dateFormatter, times(1)).getFormattedDate(any(), any(), any());
            verify(factory, times(1)).getJokeFetchers(any());
        });
        assertAll("Checking each joke", () -> {
            jokes.getJokes().forEach(joke -> {
                if (joke != null) {
                    assertFalse(joke.getJoke().isEmpty());
                    assertFalse(joke.getReference().isEmpty());
                }
            });
        });
    }
}

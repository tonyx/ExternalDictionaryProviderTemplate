package test.org.externalLangProvider;

import org.externalLangProvider.MyContentFilter;
import org.externalLangProvider.MyContentProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.tonyxzt.language.core.GenericDictionary;
import org.tonyxzt.language.core.Translator;
import org.tonyxzt.language.io.InMemoryOutStream;
import org.tonyxzt.language.io.InputStream;
import org.tonyxzt.language.io.OutStream;
import org.tonyxzt.language.util.BrowserActivator;
import org.tonyxzt.language.util.FakeBrowserActivator;

import java.awt.geom.Area;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 25/01/11
 * Time: 2.06
 * To change this template use File | Settings | File Templates.
 */
public class GoogleApi94Test {
    Map<String,GenericDictionary> mapDictionaries;
    OutStream ios;
    Translator translator;
    BrowserActivator fakeBrowserActivator;
    @Before
    public void SetUp() {
        ios= mock(OutStream.class);
        fakeBrowserActivator = mock(BrowserActivator.class);

        mapDictionaries = new HashMap<String,GenericDictionary>(){
               {
                    put("myDic",new GenericDictionary("myDic",new MyContentProvider(),new MyContentFilter()));
               }
        };
        translator = new Translator(mapDictionaries,fakeBrowserActivator,ios);
    }


    @Test
    public void orilanItalianTargetEnSayHi() throws Exception {
        translator.setCommand(new String[]{"--dic=myDic","--oriLang=it","--targetLang=en","ciao"});
        translator.doAction();

        verify(ios).output("ciao = hello");
     }


    @Test
    public void shouldBeAbleToManageAnyInputStreamer() throws Exception {
        translator.setCommand(new String[]{"--dic=myDic", "--oriLang=en", "--targetLang=fr", "hi"});

        translator.doAction();

        verify(ios).output("hi = salut");
    }


    @Test
    public void canReadFromInputStream() throws Exception {
        InputStream inputStream = mock(InputStream.class);
        when(inputStream.next()).thenReturn("ciao").thenReturn(null);

        translator.setCommand(new String[] {"--dic=myDic", "--oriLang=it","--targetLang=en","--inFile=infile"});
        translator.setInputStream(inputStream);

        translator.doAction();
        verify(ios).output("ciao = hello");
    }


    @Test
    public void canReadFromStremEnglishToItalian() throws Exception {
        InputStream inputStream = mock(InputStream.class);
        translator.setCommand(new String[] {"--dic=myDic","--oriLang=en","--targetLang=it","--inFile=infile"});
        when(inputStream.next()).thenReturn("hi").thenReturn(null);
        translator.setInputStream(inputStream);

        translator.doAction();

        verify(ios).output("hi = ciao");
    }

//
//
    @Test
    public void supportLanguagesContainsItalian() throws Exception {
        ArgumentMatcher<String> containsItalian = new ArgumentMatcher<String>() {
            @Override
            public boolean matches(Object object) {
                return ((String)object).contains("ITALIAN");
            }
        };
        translator.setCommand(new String[]  {"--dic=myDic","--languages"});
        translator.doAction();
        verify(ios).output(argThat(containsItalian));

    }
//
    @Test
    public void testCyrillic() throws Exception {
        translator.setCommand(new String[]{"--dic=myDic","--oriLang=en","--targetLang=ru","hi"});
        translator.doAction();

        verify(ios).output("hi = привет");
     }

    @Test
    public void activateBrowser() throws Exception {
        translator.setCommand(new String[]{"--dic=myDic","--info"});
        translator.doAction();

        verify(fakeBrowserActivator).activateBrowser("http://http://translate.google.com");

     }

}





package test.org.externalLangProvider;

import org.externalLangProvider.MyContentFilter;
import org.externalLangProvider.MyContentProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.tonyxzt.language.core.GenericDictionary;
import org.tonyxzt.language.core.Translator;
import org.tonyxzt.language.io.InMemoryOutStream;
import org.tonyxzt.language.io.InputStream;
import org.tonyxzt.language.util.FakeBrowserActivator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 25/01/11
 * Time: 2.06
 * To change this template use File | Settings | File Templates.
 */
public class GoogleApi94Test {
    Map<String,GenericDictionary> mapDictionaries;
    InMemoryOutStream ios;
    Translator translator;
    FakeBrowserActivator fakeBrowserActivator;
    @Before
    public void SetUp() {
        ios= new InMemoryOutStream();
        fakeBrowserActivator= new FakeBrowserActivator();
        mapDictionaries = new HashMap<String,GenericDictionary>(){
               {
                    put("myDic",new GenericDictionary("myDic",new MyContentProvider(),new MyContentFilter()));
               }
        };
        translator = new Translator(mapDictionaries,fakeBrowserActivator,ios);
    }


    @Test
    public void orilanItalianTargetEnSayHi() throws Exception {
        InMemoryOutStream ios = new InMemoryOutStream();
        translator.setOutStream(ios);
        translator.setCommand(new String[]{"--dic=myDic","--oriLang=it","--targetLang=en","ciao"});
        translator.doAction();
        org.junit.Assert.assertTrue(ios.getContent().contains("hello"));
     }


    @Test
    public void shouldBeAbleToManageAnyInputStreamer() throws Exception {
        translator.setOutStream(ios);
        translator.setCommand(new String[]{"--dic=myDic", "--oriLang=en", "--targetLang=fr", "hi"});

        translator.doAction();

        Assert.assertTrue(ios.getContent().contains("salut"));
    }


    @Test
    public void canReadFromInputFileMultipleLines() throws Exception {
        InputStream inputStream = new InputStream(){ boolean start = true; public String next() {if (start) { start=false; return "ciao";} else return null;}};
        translator.setCommand(new String[] {"--dic=myDic", "--oriLang=it","--targetLang=en","--inFile=infile"});
        translator.setInputStream(inputStream);
        translator.setOutStream(ios);

        translator.doAction();

        Assert.assertTrue(ios.getContent().contains("hello"));
    }

    @Test
    public void canReadFromInputFile() throws Exception {
        translator.setCommand(new String[] {"--dic=myDic","--oriLang=en","--targetLang=it","--inFile=infile"});
        InputStream inputStream = new InputStream(){ boolean start = true; public String next() {if (start) { start=false; return "hi";} else return null;}};
        translator.setInputStream(inputStream);
        translator.setOutStream(ios);

        translator.doAction();

        Assert.assertTrue(ios.getContent().contains("ciao"));
    }


    @Test
    public void manageOutputFile() throws Exception {
        translator.setCommand(new String[]  {"--dic=myDic","--oriLang=it","--targetLang=en","ciao"});
        translator.setOutStream(ios);
        translator.doAction();

        Assert.assertTrue(ios.getContent().contains("hello"));
    }

    @Test
    public void supportLanguagesContainsItalian() throws Exception {
        translator.setCommand(new String[]  {"--dic=myDic","--languages"});
        translator.setOutStream(ios);
        translator.doAction();

        Assert.assertTrue(ios.getContent().contains("ITALIAN"));
    }

    @Test
    public void testCyrillic() throws Exception {
        translator.setCommand(new String[]{"--dic=myDic","--oriLang=en","--targetLang=ru","hi"});
        translator.setOutStream(ios);
        translator.doAction();

        Assert.assertTrue(ios.getContent().contains("привет"));
     }




}




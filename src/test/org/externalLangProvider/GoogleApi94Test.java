package test.org.externalLangProvider;

import com.google.api.GoogleAPI;
import com.google.api.translate.Language;
import com.google.api.translate.Translate;
import org.externalLangProvider.MyContentFilter;
import org.externalLangProvider.MyContentProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.tonyxzt.language.*;

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
    Translator translator;
    @Before
    public void SetUp() {
        mapDictionaries = new HashMap<String,GenericDictionary>(){
               {
                    put("myDic",new GenericDictionary("myDic",new MyContentProvider(),new MyContentFilter()));
               }
        };
        translator = new Translator(mapDictionaries);
    }
    @Before
    public void setUp() {
        //GoogleAPI.setHttpReferrer("http://code.google.com/p/google-api-translate-java/");
    }
    @Test
    public void myTest() throws Exception {
        Assert.assertEquals("ciao", Translate.execute("hi", Language.ENGLISH,Language.ITALIAN));
    }
    @Test
    public void mySecondTest() throws Exception {
        Assert.assertEquals("ciao", Translate.execute("hi", Language.fromString("en"),Language.fromString("it")));
    }

    @Test
    public void myThirdTest() throws Exception {
        Assert.assertEquals("ciao", Translate.execute("hi", Language.fromString("en"),Language.fromString("it")));
    }

    @Test
    public void msdklf() throws Exception {
        Assert.assertEquals("ora", Translate.execute("now", Language.fromString("en"),Language.fromString("it")));
    }

    @Test
    public void asldfkjasldf() throws Exception {
        GenericDictionary genericdic = new GenericDictionary("custom",new MyContentProvider(),new MyContentFilter());
        Assert.assertEquals("Vai", genericdic.lookUp("go","en","it"));
    }

    @Test
    public void orilanItalianTargetEnSayHi() throws Exception {
        InMemoryOutStream ios = new InMemoryOutStream();
        translator.setOutStream(ios);
        translator.wrapCommandLineParameters(new String[]{"--dic=myDic","--oriLang=it","--targetLang=en","ciao"});
        translator.doAction(new String[] {"--dic=myDic","--oriLang=it","--targetLang=en","ciao"});
        org.junit.Assert.assertTrue(ios.getContent().contains("hello"));
     }
}


package org.externalLangProvider;

import com.google.api.GoogleAPI;
import com.google.api.translate.Language;
import com.google.api.translate.Translate;
import org.tonyxzt.language.ContentProvider;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 23/01/11
 * Time: 18.57
 * To change this template use File | Settings | File Templates.
 */
public class MyContentProvider implements ContentProvider {
    public MyContentProvider() {
        GoogleAPI.setHttpReferrer("http://code.google.com/p/google-api-translate-java/");
    }
    public String retrieve(String s, String s1, String s2) throws Exception {
        return Translate.execute(s, Language.fromString(s1),Language.fromString(s2));
    }

    public String supportedLanguges() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

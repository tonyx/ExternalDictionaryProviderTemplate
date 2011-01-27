package org.externalLangProvider;


import org.tonyxzt.language.core.ContentFilter;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 23/01/11
 * Time: 18.58
 * To change this template use File | Settings | File Templates.
 */
public class MyContentFilter implements ContentFilter {
    public String filter(String s) {
        return s;
    }
}

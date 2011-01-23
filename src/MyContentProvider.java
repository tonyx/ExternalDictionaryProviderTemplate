import org.tonyxzt.language.ContentProvider;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 23/01/11
 * Time: 18.57
 * To change this template use File | Settings | File Templates.
 */
public class MyContentProvider implements ContentProvider {
    public String retrieve(String s, String s1, String s2) throws Exception {
        return "hi";
    }
}

package testonly;

import javax.crypto.KeyAgreement;
import java.net.URL;

/**
 * Created by v644593 on 2/4/2016.
 */
public class JCETest
{
    public static void main(String[] args) throws Exception
    {
        Class klass = KeyAgreement.class;
        URL location = klass.getResource('/'+klass.getName().replace('.', '/')+".class");
        System.out.println("KeyAgreement class is from " + location.toString());

    }

}

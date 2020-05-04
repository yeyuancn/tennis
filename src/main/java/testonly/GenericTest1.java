package testonly;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by v644593 on 11/6/2015.
 */
public class GenericTest1
{
    public static void main(String[] args) throws Exception
    {

        System.out.println(1 << 4);

        GenericTest1 test1 = new GenericTest1();

        Set<Integer> numbers = new HashSet<Integer>();

        numbers.add(1);numbers.add(-1);numbers.add(100);

        List result = test1.convert();

        for (Object i: result)
        {
            System.out.println(i);
        }


    }

    public <T> List<T> convert()
    {
        List<T> result = new ArrayList<T>();
        result.add((T) new Integer(1));
        return result;
    }

}

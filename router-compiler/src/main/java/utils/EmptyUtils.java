package utils;

import java.util.Collection;

/**
 * @author zhang
 * @date 2020/3/28 0028
 * @time 21:34
 * @describe TODO
 */
public class EmptyUtils {
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isCollectionEmpty(final Collection collection) {
        if (collection == null || collection.isEmpty())
            return true;
        return false;
    }
}

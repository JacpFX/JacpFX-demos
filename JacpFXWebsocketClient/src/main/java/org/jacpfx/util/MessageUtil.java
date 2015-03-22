package org.jacpfx.util;

import java.io.IOException;

import static org.jacpfx.util.Serializer.deserialize;

/**
 * Created with IntelliJ IDEA.
 * User: Andy Moncsek
 * Date: 27.11.13
 * Time: 10:15
 * To change this template use File | Settings | File Templates.
 */
public class MessageUtil {

    public static <T> T getMessage(byte[] bytes, Class<T> clazz) throws IOException, ClassNotFoundException {
        return clazz.cast(deserialize(bytes));
    }
}

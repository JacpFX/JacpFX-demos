package org.jacpfx.util;

/**
 * Created with IntelliJ IDEA.
 * User: Andy Moncsek
 * Date: 27.11.13
 * Time: 09:58
 * Serialisation / Deserialisation Helper
 */

import java.io.*;

public class Serializer {
    public static byte[] serialize(Object obj) throws IOException {
        final ByteArrayOutputStream b = new ByteArrayOutputStream();
        final ObjectOutputStream o = new ObjectOutputStream(b);
        o.writeObject(obj);
        return b.toByteArray();
    }

    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        final ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        final ObjectInputStream o = new ObjectInputStream(b);
        return o.readObject();
    }
}
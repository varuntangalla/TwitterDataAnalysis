package utils;

import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.*;


public class Serializer {

    public static String toString(Serializable object) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        oos.close();
        return new String(Base64Coder.encode(baos.toByteArray()));
    }

    public static Object fromString(String serializedObject) throws IOException, ClassNotFoundException {
        byte[] data = Base64Coder.decode(serializedObject);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        Object object = ois.readObject();
        ois.close();
        return object;
    }
}

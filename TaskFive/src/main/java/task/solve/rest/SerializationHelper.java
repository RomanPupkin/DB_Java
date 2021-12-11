package task.solve.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.io.*;

public class SerializationHelper<T extends Serializable> {

    Class<T> serializationType;

    public SerializationHelper(Class<T> serializationType) {
        this.serializationType = serializationType;
    }

    private Logger log = Logger.getLogger(getClass());

    ObjectMapper mapper = new ObjectMapper();

    /*
      Необходимо десереализовать объект из файла по указанному пути
    */
    public T loadFromFile(String path) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(path)))
        {
            return (T) in.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
      Необходимо сохранить сереализованный объект в файл по указанному пути
    */
    public boolean saveToFile(String path, T toSave) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))) {
            out.writeObject(toSave);
            out.flush();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String convertToJsonString(T toConvert) {
        try {
            String json = mapper.writeValueAsString(toConvert);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void writeJsonToStream(OutputStream output, T toWrite) {
        try {
            mapper.writeValue(output, toWrite);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public T parseJson(String json) {
        try {
            return mapper.readValue(json, serializationType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

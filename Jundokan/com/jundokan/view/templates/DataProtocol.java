package jundokan.view.templates;

import java.io.*;
import java.sql.Blob;
import java.util.HashMap;

import javax.sql.rowset.serial.SerialBlob;

public class DataProtocol{
	/**
	 * @Autor Soul
	 */
	public void serializationInFile() {
        try {
            FileOutputStream outputStream = new FileOutputStream("Test100.data");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(this.dataProtocol);
            objectOutputStream.flush();
            objectOutputStream.close();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void unSerializationOutFile() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Test100.data"));
            this.dataProtocol = (HashMap<String, String>)objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    /*public SerialBlob serializationDataProtocol() {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(this.dataProtocol);
            objectOutputStream.flush();
            objectOutputStream.close();
            return new SerialBlob(outputStream.toByteArray());
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }*/
    public byte[] serializationDataProtocol() {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(this.dataProtocol);
            objectOutputStream.flush();
            objectOutputStream.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }
    public void unSerializationDataProtocol(byte [] array) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(array);
            ObjectInputStream objectStream = new ObjectInputStream(inputStream);
            this.dataProtocol = (HashMap<String, String>)objectStream.readObject();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    /*public void unSerializationDataProtocol(Blob array) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(array.getBytes(1, (int)array.length()));
            ObjectInputStream objectStream = new ObjectInputStream(inputStream);
            this.dataProtocol = (HashMap<String, String>)objectStream.readObject();
        } catch (Exception e) {
            System.err.println(e);
        }
    }*/
    public DataProtocol() {
        this.dataProtocol = new HashMap();
    }
    public DataProtocol(final int idProtocol) {
        //this.dataProtocol = this.unSerializationDataProtocol(/*sql connector return Blob value*/);
    }   
    public HashMap<String, String> getDataProtocol() {
        return dataProtocol;
    }
    public String getValue(String key) {
        return this.dataProtocol.get(key);
    }
    public void setValue(String key, String value) {
        this.dataProtocol.put(key, value);
    }
    private HashMap<String, String> dataProtocol;
}

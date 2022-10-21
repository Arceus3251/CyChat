import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.util.Base64;

public class Encryption {
    private SecretKey key;
    private final int KEY_SIZE = 128; //key size, fixed for now
    private final int DATA_LENGTH = 128; //length of data, fixed for now
    private Cipher encryptionCipher;

    //generates key using chosen algorithm
    public void init() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES"); //list of algorithms that can be used:
        keyGenerator.init(KEY_SIZE);                                 //https://docs.oracle.com/en/java/javase/11/docs/specs/security/standard-names.html#cipher-algorithm-names
        key = keyGenerator.generateKey();
    }

    //converts string to bytes, encrypts bytes using chosen cipher
    public String encrypt(String data) throws Exception {
        byte[] dataInBytes = data.getBytes();
        encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = encryptionCipher.doFinal(dataInBytes);
        return encode(encryptedBytes);
    }

    //decrypts byes, converts bytes to string
    public String decrypt(String encryptedData) throws Exception {
        byte[] dataInBytes = decode(encryptedData);
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(DATA_LENGTH, encryptionCipher.getIV());
        decryptionCipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] decryptedBytes = decryptionCipher.doFinal(dataInBytes);
        return new String(decryptedBytes);
    }

    //string encoder
    private String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    //string decoder
    private byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }
}
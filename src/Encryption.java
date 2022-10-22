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
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES"); //list of algorithms that can be used for encryption/decryption:
        keyGenerator.init(KEY_SIZE);                                 //https://docs.oracle.com/en/java/javase/11/docs/specs/security/standard-names.html#cipher-algorithm-names
        key = keyGenerator.generateKey();                            //the lists of algorithm modes and padding modes are also included in that link ^^^
    }

    //converts string to bytes, encrypts bytes using the chosen algorithm, algorithm mode, and padding mode
    public String encrypt(String data) throws Exception {
        byte[] dataInBytes = data.getBytes();
        encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding"); //(Algorithm) / (Algorithm Mode) / (Padding Mode)
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key);            //some algorithm modes and padding modes may not be compatible with certain algorithms, will have to test this
        byte[] encryptedBytes = encryptionCipher.doFinal(dataInBytes);
        return encode(encryptedBytes);
    }

    //decrypts bytes, converts bytes to string
    public String decrypt(String encryptedData) throws Exception {
        byte[] dataInBytes = decode(encryptedData);
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding"); //(Algorithm) / (Algorithm Mode) / (Padding Mode)
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
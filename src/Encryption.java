import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.util.Base64;

public class Encryption {
    private SecretKey key; //declaration of the key to be used for encryption of type SecretKey
    private final int KEY_SIZE = 128; //setting the key size to 128 bits (will be configurable by the user at some point)
    private final int DATA_LENGTH = 128; //setting the length of the data to 128 bits (the length will be determined by the length of the message the user is sending)
    private Cipher encryptionCipher; //declaration of encryptioncipher of type Cipher

    //generates key using chosen algorithm
    public void init() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES"); //key generated using chosen algorithm   -list of algorithms that can be used for encryption/decryption:
        keyGenerator.init(KEY_SIZE); //size of key is set to KEY_SIZE                                         -https://docs.oracle.com/en/java/javase/11/docs/specs/security/standard-names.html#cipher-algorithm-names
        key = keyGenerator.generateKey(); //key is generated                                                  -the lists of algorithm modes and padding modes are also included in that link ^^^
    }

    //encryption method: converts string to bytes, encrypts bytes using the chosen algorithm, algorithm mode, and padding mode
    public String encrypt(String data) throws Exception {
        byte[] dataInBytes = data.getBytes(); //converts "data" into bytes for encryption
        encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding"); //encryption options are selected           (Algorithm) / (Algorithm Mode) / (Padding Mode)
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key); //cipher is in encryption mode, uses key to encrypt   -some algorithm modes and padding modes may not be compatible with certain algorithms, will have to test this
        byte[] encryptedBytes = encryptionCipher.doFinal(dataInBytes); //the cipher is applied to the bytes, now we have the encrypted bytes
        return encode(encryptedBytes); //bytes are encoded
    }

    //descryption method: decrypts bytes, converts bytes to string
    public String decrypt(String encryptedData) throws Exception {
        byte[] dataInBytes = decode(encryptedData); //bytes are decoded
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding"); //decryption options are selected (should be same as encryption)
        GCMParameterSpec spec = new GCMParameterSpec(DATA_LENGTH, encryptionCipher.getIV());
        decryptionCipher.init(Cipher.DECRYPT_MODE, key, spec); //cipher is set to decryption mode, uses key to decrypt
        byte[] decryptedBytes = decryptionCipher.doFinal(dataInBytes); //decryption cipher is applied to bytes, now we have the decrypted bytes
        return new String(decryptedBytes); //bytes are converted to string
    }

    //encoder method
    private String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    //decoder method
    private byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }
}
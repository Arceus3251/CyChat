package Backend;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;


public class Encryption
{

    private static byte[] key;
    private static SecretKeySpec secureKey; //declaration of the key to be used for encryption, uses javax.crypto library


    //generates key using chosen algorithm
    public static void createKey(String userKey)
    {
        try
        {
            MessageDigest sha1 = null; //uses java.security library, see documentation for more info
            key = userKey.getBytes("UTF-8");
            sha1 = MessageDigest.getInstance("SHA-1");

            key = sha1.digest(key);
            key = Arrays.copyOf(key, 16);
            secureKey = new SecretKeySpec(key, "AES");

        } catch (UnsupportedEncodingException e)
            {
                e.printStackTrace(); //used for testing and error logs
            } catch (NoSuchAlgorithmException e)
                {
                    e.printStackTrace(); // need this catch in order to use .getInstance, uses java.security library
                }

    }

    /*
    -list of algorithms that can be used for encryption/decryption:
    -https://docs.oracle.com/en/java/javase/11/docs/specs/security/standard-names.html#cipher-algorithm-names
    -the lists of algorithm modes and padding modes are also included in that link ^^^
     */

    //encryption method: converts string to bytes, encrypts bytes using the chosen algorithm, algorithm mode, and padding mode
    public static String encrypt(String data, String crypt)
    {
        try
        {
            createKey(crypt);
            Cipher algo = Cipher.getInstance("AES/ECB/PKCS5Padding"); //encryption options are selected           (Algorithm) / (Algorithm Mode) / (Padding Mode)

            algo.init(Cipher.ENCRYPT_MODE, secureKey); //cipher is in encryption mode, uses key to encrypt   -some algorithm modes and padding modes may not be compatible with certain algorithms, will have to test this

            return Base64.getEncoder().encodeToString(algo.doFinal(data.getBytes("UTF-8"))); //bytes are encoded
        } catch (Exception e)
            {
                System.out.println("Error encrypting message");
            }
            return null;
    }


    //decryption method: decrypts bytes, converts bytes to string
    public static String decrypt(String encryptedData, String crypt)
    {
       try
       {
           createKey(crypt);
           Cipher algo = Cipher.getInstance("AES/ECB/PKCS5Padding"); //decryption options are selected (should be same as encryption)

           algo.init(Cipher.DECRYPT_MODE, secureKey); //cipher is set to decryption mode, uses key to decrypt

           return new String(algo.doFinal(Base64.getDecoder().decode(encryptedData))); //bytes are converted to string
       }catch (Exception e)
           {
               System.out.println("Error decrypting message");
           }
           return null;
    }
}
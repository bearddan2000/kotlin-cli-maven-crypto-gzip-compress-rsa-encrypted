/*
 * copyright
 * http://timarcher.com/blog/2007/04/simple-java-class-to-des-encrypt-strings-such-as-passwords-and-credit-card-numbers/
 */

package example;

// Java program to perform the
// encryption and decryption
// using asymmetric key
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;

class Main {

    val RSA = "RSA";

    // Generating public & private keys
    // using RSA algorithm.
    @Throws(Exception::class)
    fun generateRSAKkeyPair(): KeyPair
    {
        val secureRandom: SecureRandom = SecureRandom();
        val keyPairGenerator: KeyPairGenerator = KeyPairGenerator.getInstance(RSA);

        keyPairGenerator.initialize(2048, secureRandom);

        return keyPairGenerator.generateKeyPair();
    }

    // Encryption function which converts
    // the plainText into a cipherText
    // using private Key.
    @Throws(Exception::class)
    fun do_RSAEncryption( plainText: String, privateKey: PrivateKey): ByteArray
    {
        val cipher: Cipher = Cipher.getInstance(RSA);

        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return cipher.doFinal(plainText.toByteArray());
    }

    // Decryption function which converts
    // the ciphertext back to the
    // original plaintext.
    @Throws(Exception::class)
    fun do_RSADecryption(cipherText: ByteArray, publicKey: PublicKey): String
    {
        val cipher: Cipher = Cipher.getInstance(RSA);

        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        val result: ByteArray = cipher.doFinal(cipherText);

        return String(result);
    }
}

// Driver code
@Throws(Exception::class)
fun main(args: Array<String>)
{
  val obj: Main = Main()

  val keypair: KeyPair = obj.generateRSAKkeyPair();

  val plainText = "pass";

  val cipherText: ByteArray = obj.do_RSAEncryption( plainText, keypair.private);

  println("The Public Key is: " + DatatypeConverter.printHexBinary(keypair.public.getEncoded()));

  println("The Private Key is: " + DatatypeConverter.printHexBinary(keypair.private.getEncoded()));

  print("The Encrypted Text is: ");

  var rsaText: String = DatatypeConverter.printHexBinary(cipherText);

  println(rsaText);

  println(String.format("The Encrypted Text length is: %d", rsaText.length));

  val compress: ByteArray? = GZIPCompression.compress(rsaText);

  if(compress == null)
  {
    println(String.format("The Encrypted Compressed Text length is: %d", -1));

    return

  } else {
    println(String.format("The Encrypted Compressed Text length is: %d", compress.size));
  }
  rsaText = GZIPCompression.decompress(compress);

  println(String.format("The Encrypted Decompressed Text length is: %d", rsaText.length));

  val decryptedText: String = obj.do_RSADecryption( cipherText, keypair.public);

  println( "The decrypted text is: " + decryptedText);
}

import java.io.*;
import java.security.*;

public class CallbackDigest implements Runnable {
    private String fileName;

    public CallbackDigest(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void run() {
        try {
            FileInputStream in = new FileInputStream(fileName);
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            DigestInputStream din = new DigestInputStream(in, sha);

            while (din.read() != -1);
            din.close();

            byte[] digest = sha.digest();
            CallbackDigestUserInterface.receiveDigest(digest, fileName);
        } catch (IOException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
    }
}

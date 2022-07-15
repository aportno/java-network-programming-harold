import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class DigestThread extends Thread {
    private final String fileName;

    public DigestThread(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void run() {
        try {
            FileInputStream in = new FileInputStream(fileName);
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            DigestInputStream digestInputStream = new DigestInputStream(in, sha);
            while (digestInputStream.read() != -1);
            digestInputStream.close();
            byte[] digest = sha.digest();

            String result = fileName + ": " +
                    Base64.getEncoder().encodeToString(digest);
            System.out.println(result);
        } catch (IOException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        for (String fileName : args) {
            Thread t = new DigestThread(fileName);
            t.start();
        }
    }
}

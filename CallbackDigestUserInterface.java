import java.util.Base64;

public class CallbackDigestUserInterface {

    public static void receiveDigest(byte[] digest, String name) {
        String res = name + ": " +
                Base64.getEncoder().encodeToString(digest);
        System.out.println(res);
    }

    public static void main(String[] args) {
        for (String filename : args) {
            CallbackDigest cb = new CallbackDigest(filename);
            Thread t = new Thread(cb);
            t.start();
        }
    }
}

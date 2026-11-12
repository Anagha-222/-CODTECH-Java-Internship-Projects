import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

public class Block {

    int index;
    String timestamp;
    String data;
    String previousHash;
    String hash;
    int nonce;

    // Constructor
    public Block(int index, String data, String previousHash) {

        this.index = index;
        this.timestamp = Instant.now().toString();
        this.data = data;
        this.previousHash = previousHash;
        this.nonce = 0;
        this.hash = calculateHash();
    }

    // Generate SHA-256 hash
    public String calculateHash() {

        try {

            String input = index + timestamp + data
                    + previousHash + nonce;

            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");

            byte[] hashBytes =
                    digest.digest(input.getBytes());

            StringBuilder hexString = new StringBuilder();

            for (byte b : hashBytes) {

                String hex =
                        Integer.toHexString(0xff & b);

                if (hex.length() == 1) {
                    hexString.append('0');
                }

                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {

            throw new RuntimeException(e);
        }
    }

    // Proof of Work Mining
    public void mineBlock(int difficulty) {

        String target = "0".repeat(difficulty);

        System.out.println("Mining block " + index + "...");

        while (!hash.startsWith(target)) {

            nonce++;
            hash = calculateHash();
        }

        System.out.println("Block successfully mined!");
        System.out.println("Nonce: " + nonce);
        System.out.println("Mined Hash: " + hash);
    }
}
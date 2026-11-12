import java.util.ArrayList;

public class Blockchain {

    ArrayList<Block> chain;
    int difficulty = 3;

    // Constructor
    public Blockchain() {

        chain = new ArrayList<>();

        // Create and mine the Genesis Block
        Block genesisBlock = new Block(
                0,
                "Genesis Block",
                "0"
        );

        genesisBlock.mineBlock(difficulty);
        chain.add(genesisBlock);
    }

    // Get the latest block
    public Block getLatestBlock() {

        return chain.get(chain.size() - 1);
    }

    // Add a new block
    public void addBlock(String data) {

        Block previousBlock = getLatestBlock();

        Block newBlock = new Block(
                chain.size(),
                data,
                previousBlock.hash
        );

        // Mine the new block before adding it
        newBlock.mineBlock(difficulty);

        chain.add(newBlock);
    }

    // Validate the blockchain
    public boolean isChainValid() {

        String target = "0".repeat(difficulty);

        for (int i = 0; i < chain.size(); i++) {

            Block currentBlock = chain.get(i);

            // Check whether the block hash is correct
            if (!currentBlock.hash.equals(
                    currentBlock.calculateHash())) {

                return false;
            }

            // Check Proof of Work
            if (!currentBlock.hash.startsWith(target)) {

                return false;
            }

            // Check connection with the previous block
            if (i > 0) {

                Block previousBlock = chain.get(i - 1);

                if (!currentBlock.previousHash.equals(
                        previousBlock.hash)) {

                    return false;
                }
            }
        }

        return true;
    }

    // Display all blocks
    public void displayBlockchain() {

        for (Block block : chain) {

            System.out.println("\n----------------------");
            System.out.println("Block Index: " + block.index);
            System.out.println("Timestamp: " + block.timestamp);
            System.out.println("Data: " + block.data);
            System.out.println("Previous Hash: " + block.previousHash);
            System.out.println("Hash: " + block.hash);
            System.out.println("Nonce: " + block.nonce);
            System.out.println("----------------------");
        }
    }
}
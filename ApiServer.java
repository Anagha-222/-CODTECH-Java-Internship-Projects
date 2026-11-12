import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class ApiServer {

    static Blockchain blockchain = new Blockchain();

    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(
                new InetSocketAddress(8081), 0);

        server.createContext("/api/", ApiServer::handleRequest);

        server.start();

        System.out.println("Blockchain server started!");
        System.out.println("Open your browser at:");
        System.out.println("http://localhost:8080");
    }

    private static void handleRequest(HttpExchange exchange)
            throws IOException {

        exchange.getResponseHeaders().set(
                "Access-Control-Allow-Origin", "*");

        exchange.getResponseHeaders().set(
                "Access-Control-Allow-Methods", "GET, POST, OPTIONS");

        exchange.getResponseHeaders().set(
                "Access-Control-Allow-Headers", "Content-Type");

        if (exchange.getRequestMethod().equals("OPTIONS")) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        String path = exchange.getRequestURI().getPath();
        String response;

        try {

            if (path.equals("/api/chain")) {

                response = getBlockchainData();

            } else if (path.equals("/api/add")) {

                String data = new String(
                        exchange.getRequestBody().readAllBytes(),
                        StandardCharsets.UTF_8);

                blockchain.addBlock(data);

                response = "{\"message\":\"Block mined successfully!\"}";

            } else if (path.equals("/api/validate")) {

                boolean valid = blockchain.isChainValid();

                response = "{\"valid\":" + valid + "}";

            } else if (path.equals("/api/tamper")) {

                if (blockchain.chain.size() < 2) {
                    response = "{\"message\":\"Add a block first!\"}";
                } else {

                    blockchain.chain.get(1).data =
                            "HACKED: Modified transaction";

                    boolean valid = blockchain.isChainValid();

                    response = "{\"valid\":" + valid + "}";
                }

            } else {

                response = "{\"message\":\"Invalid API request\"}";
            }

            sendResponse(exchange, 200, response);

        } catch (Exception e) {

            sendResponse(exchange, 500,
                    "{\"message\":\"" + escapeJson(e.getMessage()) + "\"}");
        }
    }

    private static String getBlockchainData() {

        StringBuilder json = new StringBuilder("[");
        
        for (int i = 0; i < blockchain.chain.size(); i++) {

            Block block = blockchain.chain.get(i);

            if (i > 0) {
                json.append(",");
            }

            json.append("{")
                .append("\"index\":").append(block.index).append(",")
                .append("\"data\":\"").append(escapeJson(block.data)).append("\",")
                .append("\"hash\":\"").append(block.hash).append("\",")
                .append("\"previousHash\":\"").append(block.previousHash).append("\",")
                .append("\"nonce\":").append(block.nonce)
                .append("}");
        }

        json.append("]");

        return json.toString();
    }

    private static String escapeJson(String value) {

        if (value == null) return "";

        return value.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r");
    }

    private static void sendResponse(
            HttpExchange exchange, int status, String response)
            throws IOException {

        exchange.getResponseHeaders().set(
                "Content-Type", "application/json; charset=UTF-8");

        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);

        exchange.sendResponseHeaders(status, bytes.length);

        try (var output = exchange.getResponseBody()) {
            output.write(bytes);
        }
    }
}
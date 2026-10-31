import java.net.*;
import java.io.*;

// δημιουργω τις κλασεις για data και result
class Data {
    int a;
    int b;
    String op;
}

class Result {
    char op;
    int r;
}

public class ServerProtocol {

    public String processRequest(String theInput) {

        System.out.println("Received calculation from client: " + theInput);
        Data data = getRequestData(theInput); // σπαω την απαντηση
        Result result = doServerComputation(data); // υλοποιηση της λυσης
        String reply = buildReplyMessage(result); // μετατροπη απαντησης σε String
        return reply;
    }

    private Data getRequestData(String request) {
        // σπαω το request και επιστρεψω αντικειμενο της κλασης data
        String[] parts = request.split(" ");
        Data data = new Data();
        data.a = Integer.parseInt(parts[0]);
        data.op = parts[1];
        data.b = Integer.parseInt(parts[2]);
        return data;
    }

    private Result doServerComputation(Data data) {
        // αναλογα με το op του data δημιουργω το αντικειμενο result
        Result result = new Result();
        switch (data.op) {
            case "+":
                result.op = 'R';
                result.r = data.a + data.b;
                break;

            case "-":
                result.op = 'R';
                result.r = data.a - data.b;
                break;

            case "*":
                result.op = 'R';
                result.r = data.a * data.b;
                break;

            case "/":
                result.op = 'R';
                if (data.b == 0) {
                    result.op = 'E';
                    result.r = 0;
                } else {
                    result.op = 'R';
                    result.r = data.a / data.b;
                }
                break;

            default:
                result.op = 'E';
                result.r = 0;
                break;
        }
        return result;
    }

    // αυτη η συναρτηση που επιστρεφει την απαντηση σε String
    private String buildReplyMessage(Result result) {
        if (result.op == 'E') {
            return "ERROR!";
        }
        return "Result is: " + result.r;
    }
}
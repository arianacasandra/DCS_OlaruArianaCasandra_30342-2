package main.java.lab.scd.net;

import java.io.*;
import java.net.*;

public class CalculatorServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            System.out.println("Calculator Server is running...");

            while (true) {
                try (Socket socket = serverSocket.accept();
                     DataInputStream input = new DataInputStream(socket.getInputStream());
                     DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

                    double num1 = input.readDouble();
                    double num2 = input.readDouble();
                    String operation = input.readUTF();

                    double result = 0;
                    boolean validOperation = true;

                    switch (operation) {
                        case "+":
                            result = num1 + num2;
                            break;
                        case "-":
                            result = num1 - num2;
                            break;
                        case "*":
                            result = num1 * num2;
                            break;
                        case "/":
                            if (num2 != 0) {
                                result = num1 / num2;
                            } else {
                                output.writeUTF("Error: Division by zero");
                                validOperation = false;
                            }
                            break;
                        default:
                            output.writeUTF("Error: Invalid operation");
                            validOperation = false;
                            break;
                    }

                    if (validOperation) {
                        output.writeUTF("Result: " + result);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package org.example.guestbook.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	public static void main (String[] args) {
		Client.Start();
	}

	private static String newMessage = "[CLIENT]: Enter message:";
	
	// The command the client should send before
	// the connection is closed.
	private static String exitCommand = "EXIT";
	private static int port = 6666;
	private static String host = "localhost";
	
	private static void Start() {
		
		try {
			var socket = new Socket(host, port);
			var dataInputStream = new DataInputStream(socket.getInputStream());
			var dataOutputStream = new DataOutputStream(socket.getOutputStream());
			var reader = new InputStreamReader(System.in);
			var bufferedReader = new BufferedReader(reader);
			var shouldRun = true;
			
			String clientMsg = "", serverMsg = "";

			while (shouldRun)
			{
				System.out.println(newMessage);
				clientMsg = bufferedReader.readLine();
				
				shouldRun = !clientMsg.toUpperCase().equals(exitCommand);
				
				dataOutputStream.writeUTF(clientMsg);
				dataOutputStream.flush();

				serverMsg = dataInputStream.readUTF();
				System.out.println("[SERVER]: "+serverMsg);
			}
			
			socket.close();
			dataInputStream.close();
			dataOutputStream.close();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

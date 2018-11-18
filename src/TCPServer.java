
//18/11/2018
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

public class TCPServer extends Thread
{
	private ServerSocket server;
	private final int PORT=7;
	private final int MAX_NUMERO_THREAD=5;
	private ExecutorService threads;

	public TCPServer() throws IOException
	{
		server=new ServerSocket(PORT);
		server.setSoTimeout(1000);
		threads=Executors.newFixedThreadPool(MAX_NUMERO_THREAD);
	}
	
	public void run()
	{
		Socket connection = null;
		OutputStreamWriter output = null;
		
		
		
		while (!interrupted())
		{
			try 
			
			{
				connection=server.accept();
				ClientThread clientThread=new ClientThread(connection);
				try
				{
					threads.submit(clientThread);
				}
				catch (RejectedExecutionException e) 
				{
					connection.close();
				}
			//	Thread nuovoThread=new Thread (clientThread);
			//	nuovoThread.start();
			//	System.out.println("Connessione con: "+connection.getInetAddress().getHostAddress()+"/"+connection.getPort());
				
			} 
			
			
			
			catch (SocketTimeoutException e) 
			{
				System.err.println("Timeout");
			}
			
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	public static void main(String[] args) 
	{
		TCPServer server;
		ConsoleInput tastiera=new ConsoleInput();
		try 
		{
			server= new TCPServer();
			server.start();
			tastiera.readLine();
			server.interrupt();
			server.join();
			
		} 
		catch (IOException e) 
		{
			
			e.printStackTrace();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}

	}

}

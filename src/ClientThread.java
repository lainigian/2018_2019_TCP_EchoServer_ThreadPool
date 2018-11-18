import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientThread implements Runnable
{
	private Socket connection;
	private InputStream in;
	private OutputStream out;
	
	
	public ClientThread(Socket connectionX) throws IOException
	{
		
		connection= connectionX;
		in=connection.getInputStream();
		out=connection.getOutputStream();
	}
	
	
	public void run()
	{
		byte[] buffer=new byte[1024];
		int n;
		
		try 
		{
			while((n=in.read(buffer))!=-1)
			{
				if (n>0)
				{
					out.write(buffer, 0, n);
					out.flush();
				}
			}
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try 
		{
			out.close();
			in.close();
			connection.shutdownInput();
			connection.shutdownOutput();
			connection.close();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	
}

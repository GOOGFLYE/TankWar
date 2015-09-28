import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class NetClient {
	TankClient tc;
	public static final int UDP = 2333;
	public int udpPort = 2333;
	public DatagramSocket ds = new DatagramSocket(UDP);
			
	public NetClient(TankClient tc) {
		this.tc = tc;
	}

	public void connect(String IP, int port) {
		Socket s = null;
		try {
			s = new Socket(IP, port);
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			dos.writeInt(udpPort);
			DataInputStream dis = new DataInputStream(s.getInputStream());
			int id = dis.readInt();
			tc.myTank.id = id;
System.out.println("Connected to server! and server give me a ID:" + id);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(s != null) {
				try {
					s.close();
					s = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		TankNewMsg newMsg = new TankNewMsg(tc.myTank);
		send(newMsg);
		
		new Thread(new UDPRecv()).start();
	}
	
	public void send(TankNewMsg newMsg) {
		newMsg.send(ds,"127.0.0.1",TankServer.UDP_PORT);
	}
	
	private class UDPRecv implements Runnable{

		public void run() {
			
			byte[] buf = new byte[1024];
			
			while(ds!=null) {
				DatagramPacket dp =new DatagramPacket(buf,buf.length);
				try {
					ds.receive(dp);
System.out.println("A packet received from server!");					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}
}


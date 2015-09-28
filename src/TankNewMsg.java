import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;


public class TankNewMsg {

	Tank t;
	
	public TankNewMsg(Tank t) {
		this.t = t;
	}

	public void send(DatagramSocket ds,String ServerIP,int udpPort) {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		
		if(ds!=null) {
			try {
				dos.writeInt(t.id);
				dos.writeInt(t.x);
				dos.writeInt(t.y);
				dos.writeInt(t.dir.ordinal());
				dos.writeBoolean(t.good);
				
				byte[] buf = baos.toByteArray();
				DatagramPacket dp = new DatagramPacket(buf,buf.length,new InetSocketAddress(ServerIP,udpPort));
				ds.send(dp);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
}

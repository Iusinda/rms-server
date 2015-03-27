package fyp.rms.utility;

import java.util.ArrayList;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class GCMHelper {

	private String messageContent;
	private String regId;

	public GCMHelper(String messageContent, String regId) {
		this.messageContent = messageContent;
		this.regId = regId;
	}

	public void sendMessage() {
		new Thread() {
			public void run() {
				try {
					Sender sender = new Sender(regId);

					Message message = new Message.Builder()
							.collapseKey("message").timeToLive(3)
							.delayWhileIdle(true)
							.addData("message", messageContent).build();

					Result result = sender.send(message, regId, 1);
					System.out.println("Message Result: " + result.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

}

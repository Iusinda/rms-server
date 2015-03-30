package fyp.rms.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class GCMHelper {
	private String messageContent;
	private String regId;
	private static final Logger logger = LoggerFactory
			.getLogger(GCMHelper.class);

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
					logger.info("Send Message to Device " + regId + ": "
							+ result.toString());
				} catch (Exception e) {
					logger.info("Fail to send Message to Device " + regId);
					e.printStackTrace();
				}
			}
		}.start();
	}
}

package fyp.rms.utility;
import java.util.ArrayList;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
public class GCMHelper {

	private String messageContent;
	private String regId;
		
	public GCMHelper(String messageContent,String regId)
	{
		this.messageContent  = messageContent;
		this.regId = regId;
	}
		public void sendMessage() {
			   new Thread(){
		            public void run(){
		                try {
		                    //Please add here your project API key: "Key for browser apps (with referers)".
		                    //If you added "API key Key for server apps (with IP locking)" or "Key for Android apps (with certificates)" here
		                    //then you may get error responses.
		                    Sender sender = new  Sender("AIzaSyDFqKGeYuqbbj5Hs4fsEnDtXlpJyq0Qrgk");

		                    // use this to send message with payload data
		                    Message message = new Message.Builder()
		                    .collapseKey("message")
		                    .timeToLive(3)
		                    .delayWhileIdle(true)
		                    .addData("message", messageContent) //you can get this message on client side app
		                    .build();  
		                    
		                    //Use this code to send notification message to a single device
		                    Result result = sender.send(message,regId,1);
		                    System.out.println("Message Result: "+result.toString()); //Print message result on console

		                } catch (Exception e) {
		                    e.printStackTrace();
		                }
		            }
		        }.start();    	}

	}


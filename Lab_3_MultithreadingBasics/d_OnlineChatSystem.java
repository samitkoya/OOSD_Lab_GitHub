/* Design a Java application to simulate an Online Chat System where multiple users send 
messages concurrently. Each user runs in a separate thread, displaying sent and received 
messages.
*/

class ChatUser1 implements Runnable {
    @Override
    public void run() {
        System.out.println("User1 sent: Hello everyone!");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("User1 received: Hey User1!");
    }
}

class ChatUser2 implements Runnable {
    @Override
    public void run() {
        System.out.println("User2 sent: Good morning!");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("User2 received: Morning User2!");
    }
}

class ChatUser3 implements Runnable {
    @Override
    public void run() {
        System.out.println("User3 sent: How is everyone?");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("User3 received: We are good!");
    }
}

public class d_OnlineChatSystem {
    public static void main(String[] args) {

        Thread user1Thread = new Thread(new ChatUser1());
        Thread user2Thread = new Thread(new ChatUser2());
        Thread user3Thread = new Thread(new ChatUser3());

        user1Thread.start();
        user2Thread.start();
        user3Thread.start();
    }
}

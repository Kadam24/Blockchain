import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Client {

    public static List<Block> chain = new ArrayList<Block>();
    public static List<Tranzaction> transactionsToAdd = new ArrayList<Tranzaction>();
    String kto="";
    String doKogo="";
    String tmp = "";
    String tmp2 = "";
    int ile = 0;
    int ilosc = 0;
    boolean shouldRefresh = false;
    BufferedReader in;
    PrintWriter out;
    public static JFrame frame = new JFrame("Chatter");
    JTextField textField = new JTextField(40);
    JTextArea messageArea = new JTextArea(8, 40);
    public String sendTo="";
    private JMenuBar menuBar = new JMenuBar();
 //   private JMenu userMenu = new JMenu("SendTo");


    public Client() {
        // Layout GUI

      //  textField.setEditable(false);
      //  messageArea.setEditable(false);

       // frame.getContentPane().add(textField, "North");
        //frame.getContentPane().add(new JScrollPane(messageArea), "Center");
       // frame.setJMenuBar(menuBar);

     //   menuBar.add(userMenu);

      //  frame.pack();

        // Add Listeners
    //    textField.addActionListener(new ActionListener() {
            //po wcisnieciu enter wysylamy wiadomosc, po wyslaniu ustawiamy pole gdzie mozemy wyslac wiad na pusty

        //    public void actionPerformed(ActionEvent e) {
          //      if(sendTo.equals(""))
            //    {
             //       out.println(textField.getText());
            //        textField.setText("");
            //        return;
          //      }
                //wysylanie wiadomosci do konkretnego uzytkownika, wypisuje nam co wyslalismy
          //      messageArea.append("me:"+textField.getText()+"\n");
                //wysylamy do serwera to co chcemy wyslac uzytkownikowi o danej nazwie
           //     out.println("SENDTO="+sendTo+"@"+kto+":"+textField.getText());
                //out.println(textField.getText());
            //    textField.setText("");
          //  }
       // });
    }


    private String getServerAddress() {
        return "localhost";
    }

    private String getCount() {
        return JOptionPane.showInputDialog(frame, "Count of Transactions", "Selection",
                JOptionPane.PLAIN_MESSAGE);
    }

    private String getWho() {
        return JOptionPane.showInputDialog(frame, "Who?", "Selection",
                JOptionPane.PLAIN_MESSAGE);
    }

    private String getTo() {
        return JOptionPane.showInputDialog(frame, "To?", "Selection",
                JOptionPane.PLAIN_MESSAGE);
    }

    private String getHowMuch() {
        return JOptionPane.showInputDialog(frame, "How much?", "Selection",
                JOptionPane.PLAIN_MESSAGE);
    }


    void run() throws Exception {

        String serverAddress = getServerAddress();
        Socket socket = new Socket(serverAddress, 9001);

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        Blockchain testBlock = new Blockchain();
        while (true) {
            String line = in.readLine();
            if(line.startsWith("ILE")){
                tmp2 = getCount();
                ilosc = Integer.parseInt(tmp2);
                out.println(tmp2);
            }
            else if (line.startsWith("DANE")) {
                for (int i = 0; i < ilosc; i++) {
                    kto = getWho();
                    out.println(kto);
                    doKogo = getTo();
                    out.println(doKogo);
                    tmp = getHowMuch();
                    ile = Integer.parseInt(tmp);
                    out.println(ile);
                }
            }
            else if (line.startsWith("OK")) {
                System.out.println("Mining 1st block...");
                Tranzaction t1 = new Tranzaction(kto, doKogo, ile);
                transactionsToAdd.add(t1);
                Miner.mineNewBlock("mining first block ");
                testBlock.chain.remove(0);
                System.out.println("PoW for first block " + testBlock.proofOfWork());
                System.out.println("Hash:" + testBlock.getLastBlock().getHash());
                System.out.println("Prev hash:" + testBlock.getLastBlock().getPrevHash());
                if (testBlock.isChainValid()) {
                    System.out.println("you can download the chain, because it is valid, have fun :)");
                }
            }
        }
    }

    /**
     * Runs the client as an application with a closeable frame.
     */
    public static void main(String[] args) throws Exception {
        //Server.main(null);

        Client client = new Client();
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setVisible(true);
        client.run();

    }
}
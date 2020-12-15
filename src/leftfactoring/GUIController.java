package leftfactoring;

import java.net.URL;
import javafx.fxml.FXML;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import java.util.StringTokenizer;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author Usama Sarwar <UsamaSarwarOfficial@gmail.com>
 * @version v1.0.0
 * @since 2020
 *
 */
public class GUIController implements Initializable {

    @FXML
    private Button button;
    @FXML
    private TextField input;
    @FXML
    private TextArea output;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        readGrammar();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    void readGrammar() {
        commonPrefixes(input.getText());
    }

    void commonPrefixes(String line) {
        System.out.println("Input : " + line);
        //Input : S=iEtS|iEtSes|a  1st iteration
        System.out.println("LEFT FACTORING : ");
        output.setText("LEFT FACTORING : ");
        StringTokenizer tokenizer = new StringTokenizer(line, "=|");
        // split grammmer on the base of equal and or operator or both

        String first = tokenizer.nextToken();
        System.out.println("first : " + first);
        output.appendText("\n" + "first : " + first);
        // first contains first : S
        String commonPrefixes = "";

        ArrayList<String> tokens = new ArrayList<String>();

        tokens.clear();
        /*
        iEtS
        iEtSes
        a
         */
        while (tokenizer.hasMoreTokens()) {
            tokens.add(tokenizer.nextToken());

        }
        System.out.println("tokens : " + tokens);
        output.appendText("\n" + "tokens : " + tokens);
        //tokens : [iEtS, iEtSes, a]
        int max = 0;
        System.out.println("tokens size : " + tokens.size());
        output.appendText("\n" + "tokens size : " + tokens.size());
        for (int i = 0; i < tokens.size() - 1; i++) {
            for (int j = i + 1; j < tokens.size(); j++) {
                /*
                1st itration tokens.get(i)=iEtS and tokens.get(j)= iEtSes
                 */
                System.out.println("tokens.size(i) : " + tokens.get(i) + "tokens.size(j) :" + tokens.get(j));
                output.appendText("\n" + "tokens.size(i) : " + tokens.get(i) + "tokens.size(j) :" + tokens.get(j));
                // find longest commonPrefixes using LongestCommon method
                int max2 = LongestCommon(tokens.get(i), tokens.get(j));
                // now max2=4 max=0
                if (max2 > max) {

                    max = max2;
                    commonPrefixes = tokens.get(i).substring(0, max);
                }
                System.out.println("commonPrefixes =" + commonPrefixes);
                output.appendText("\n" + "commonPrefixes =" + commonPrefixes);
                //
            }
        }

        LeftFactoring fact = new LeftFactoring();
        /*
        first=S
        commonPrefixes =iEtS
        tokens : [iEtS, iEtSes, a]
         */
        factor(first, commonPrefixes, tokens);

    }

    public int LongestCommon(String str1, String str2) {
        /*
        str1 =: iEtS         
        str2= : iEtSes
         */
        System.out.println("str1 =: " + str1 + "str2= : " + str2);
        output.appendText("\n" + "str1 =: " + str1 + "str2= : " + str2);
        if (str1 == null || str2 == null) {
            return 0;
        }
        int len = 0;
        int loopcount;
        /*
        1st time if condition is true
         */
        if (str1.length() < str2.length()) {
            loopcount = str1.length();
        } else {
            loopcount = str2.length();
        }
        /*
        loopcount=4
        str1 =: iEtS length=4
        str2= : iEtSes length=6
         */
        for (int i = 0; i < loopcount; i++) {
            if (str1.charAt(i) == str2.charAt(i)) {
                len++;
            } else {
                return len;
            }
        }

        return len;
    }

    void factor(String first, String commonPrefixes, ArrayList<String> tokens) {
        // Left Factring
        String next = new String();
        ArrayList<String> suffix = new ArrayList<String>();
        ArrayList<String> prefix = new ArrayList<String>();

        int tokensize;
        for (tokensize = 0; tokensize < tokens.size(); tokensize++) {
            next = tokens.get(tokensize);
            //next=iEtS
            System.out.println("next=" + next);
            output.appendText("\n" + "next=" + next);
            /*
            if condition is true 1st time
            if condition is false in 2nd iteration
             */
            if (next.equals(commonPrefixes)) {
                suffix.add("ε");
                prefix.add(commonPrefixes);

            } //else if condition is true in 2nd iteration
            else if (next.startsWith(commonPrefixes)) {
                prefix.add(commonPrefixes);
                char[] charArray = next.toCharArray();
                String x = new String(charArray, commonPrefixes.length(), next.length() - commonPrefixes.length());
                System.out.println("string x =" + x);
                output.appendText("\n" + "string x =" + x);
                //string x=es
                suffix.add(x);

            } else {
                prefix.add(next);

            }
        }
        
        int i;
        /*
        S=iEtSS`|a
         */
        String firstoutput = first + "=" + commonPrefixes + first + "`|";
        for (i = 0; i < prefix.size(); i++) {
            if (i == prefix.size() - 1) {
                break;
            }
            if (prefix.get(i) != commonPrefixes) {
                firstoutput += prefix.get(i);
                firstoutput += "|";
            }
        }
        firstoutput += prefix.get(i);
        System.out.println(firstoutput);
        output.appendText("\n" + firstoutput);
        /*
        S`=ε|es
         */
        String secondoutput = first + "`=";
        i = 0;
        for (i = 0; i < suffix.size() - 1; i++) {
            secondoutput += suffix.get(i);
            secondoutput += "|";
        }
        secondoutput += suffix.get(i);
        System.out.println(secondoutput);
        output.appendText("\n" + secondoutput);
        System.out.println("\n");
        output.appendText("\n" + "\n");
    }
}

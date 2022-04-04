import java.util.*;
import javax.swing.*;
import java.sql.*;

public class LottoController {

    private ConnectDatabase connDb;
    private Vector<Integer> numberList;
    private Vector<Integer> drawedList;
    private Vector<Integer> choosenList;
    private LottoForm lottoFrm;
    private int counter = 0;

    public LottoController( ConnectDatabase connDb){

        choosenList = new Vector<>();
        drawedList = new Vector<>();
        numberList = new Vector<>();
        this.connDb = connDb;
        lottoFrm = new LottoForm();
        lottoFrm.exitBtn.addActionListener( event->exit());
        lottoFrm.drawBtn.addActionListener( event -> drawing());
        numberCheckBoxes();
        fillList();

        lottoFrm.setVisible(true);
    }

    private void fillList(){

        for( int i = 1; i < 91; i++){
            numberList.add(i);
        }
    }

    private void numberCheckBoxes(){

        
        for( Integer i = 1; i < 91; i++){

            JCheckBox box = new JCheckBox();
            box.setText( i.toString() );
            lottoFrm.centerPnl.add(box);

            box.addItemListener( event -> {
                JCheckBox check = ( JCheckBox ) event.getSource();
                choosenList.add( Integer.parseInt( check.getText()));
                counter++;
                if( counter == 5 ){
                    lottoFrm.drawBtn.setEnabled(true);
                }else{
                    lottoFrm.drawBtn.setEnabled(false);
                }
            });

        }
    }

    private void drawing(){

        int numbers = 90;
        Random rand = new Random();

        for( int i = 0; i < 5; i++ ){

            int number = rand.nextInt( numbers ) + 1;
            numberList.remove( number - 1 );
            numbers --;
            drawedList.add( number );
            showResult();
        }

        
        numbersToDatabase();
    }

    private void showResult(){

        Integer result = 0;

        for( int i = 0; i < choosenList.size(); i++){
           
            for( int j = 0; j < drawedList.size(); j++){

                if( choosenList.get(i) == drawedList.get(j)){
                    result ++;
                }
            }
        }
        //String lblValue = lottoFrm.resultLbl.getText();
        lottoFrm.resultLbl.setText("talalatok"+ result.toString());

        String drawValue = lottoFrm.drawLbl.getText();
        for( int i = 0; i < drawedList.size(); i++){

            
            String number = String.valueOf( drawedList.get(i));
            lottoFrm.drawLbl.setText( drawValue + number + " ");
        }

        
    }

    private void numbersToDatabase(){

        Connection conn = connDb.getConnection();
        Statement stmt = null;
        String sqlData = "";
        for( int i = 0; i < drawedList.size(); i++ ){

            if( i < drawedList.size() - 1 ){

                sqlData += String.valueOf( drawedList.get(i)) + ":";
            }else{
                sqlData += String.valueOf( drawedList.get(i));
            }
        }

        String sql = "INSERT INTO drawed ( draw ) VALUES ('" + sqlData + "')";
        try {
            stmt = conn.createStatement();
        stmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        connDb.closeConnect();
        
    }

    private void exit(){
        System.exit(0);
    }
    
}

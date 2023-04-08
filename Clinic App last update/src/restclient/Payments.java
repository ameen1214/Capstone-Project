/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restclient;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Payments {
    
    BackEnd backend=new BackEnd();
    
    public Pane create_account(){
        Pane cretePane=new Pane();
        cretePane.setLayoutX(150);
        cretePane.setLayoutY(100);
        //text
        Text totalT=new Text("Total amount: ");
        Text recT=new Text("Receved amount: ");
        Text userT=new Text("User ID: ");
        //
        TextField total=new TextField();
        TextField receved=new TextField();
        TextField user=new TextField();
        //X
        totalT.setLayoutX(0);
        recT.setLayoutX(0);
        userT.setLayoutX(0);  
        
        total.setLayoutX(150);
        receved.setLayoutX(150);
        user.setLayoutX(150);
        //Y
        totalT.setLayoutY(20);
        recT.setLayoutY(70);
        userT.setLayoutY(120);   
        total.setLayoutY(0);
        receved.setLayoutY(50);
        user.setLayoutY(100);   
        
        Button create=new Button("Create account");
        create.setLayoutX(150);
        create.setLayoutY(150);
        create.setStyle("-fx-background-color: #A987A8");
        create.setOnAction(new EventHandler<ActionEvent>() {           
            @Override
            public void handle(ActionEvent event) {
                try {
                    Map<String,String> response=backend.create_account(total.getText(), receved.getText(),user.getText());
                    Alert alert=new Alert(Alert.AlertType.WARNING);                   
                    for(String key:response.keySet()){
                        alert.setHeaderText(key);
                        alert.setContentText(response.get(key));
                        alert.show();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Records.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        //add
        cretePane.getChildren().add(totalT);
        cretePane.getChildren().add(recT);
        cretePane.getChildren().add(userT);
        cretePane.getChildren().add(total);
        cretePane.getChildren().add(receved);
        cretePane.getChildren().add(user);
        cretePane.getChildren().add(create);
        return cretePane;
    }
    
    public Pane dataPane(Map<String,String> response){
        //inside pane
        Pane insidePane=new Pane();
        insidePane.setLayoutX(0);
        insidePane.setLayoutY(100);
        int y=50;
        int size=response.size();
        for(int index=0;index<size;index++){
            Text tot=new Text("");
            Text rec=new Text("");
            Text rem=new Text("");
            Text userID=new Text("");
            //X
            tot.setLayoutX(0);
            rec.setLayoutX(150);
            rem.setLayoutX(300);
            userID.setLayoutX(450);
            //Y
            tot.setLayoutY(20);
            rec.setLayoutY(20);
            rem.setLayoutY(20);
            userID.setLayoutY(20);

            //fonts
            Font f=new Font(15);
//            name.setFont(f);
//            address.setFont(f);
//            userID.setFont(f);
            //
            index++;
            String k=index+"";
            String v1=response.get(k);                   
            userID.setText(v1);
            response.remove(k);
            index++;
            k=index+"";
            String v2=response.get(k);
            tot.setText(v2);
            index++;
            k=index+"";
            String v3=response.get(k);
            rec.setText(v3);
            index++;
            k=index+"";
            String v4=response.get(k);
            rem.setText(v4);

            //update record
            Button update=new Button("add amount"); 
            update.setStyle("-fx-background-color: #A987A8");
            update.setLayoutX(600);
            update.setLayoutY(0);                   
            update.setOnAction(new EventHandler<ActionEvent>() {           
                @Override
                public void handle(ActionEvent event) {
                    TextInputDialog input=new TextInputDialog();
                    input.setHeaderText("Enter new receved amount");
                    input.showAndWait();
                    String recevedNow=input.getEditor().getText();
                    try {
                        Map<String,String> response=backend.update_account(v1, recevedNow);
                        Alert alert=new Alert(Alert.AlertType.WARNING); 
                            for(String k:response.keySet()){
                                alert.setHeaderText(k);
                                alert.setContentText(response.get(k));
                                alert.show();
                            }
                    } catch (Exception ex) {
                        Logger.getLogger(Records.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }); 

            //delete record
            Button delete=new Button("delete"); 
            delete.setStyle("-fx-background-color: #A987A8");
            delete.setLayoutX(710);
            delete.setLayoutY(0);                   
            delete.setOnAction(new EventHandler<ActionEvent>() {           
                @Override
                public void handle(ActionEvent event) {
                    try {
                        Map<String,String> response=backend.delete_account(v1);
                        Alert alert=new Alert(Alert.AlertType.WARNING); 
                        for(String k:response.keySet()){
                            if(k.equals("ok")){
                                tot.setFill(Color.CRIMSON);
                                rec.setFill(Color.CRIMSON);
                                rem.setFill(Color.CRIMSON);
                                userID.setFill(Color.CRIMSON);
                                update.setDisable(true);
                                delete.setDisable(true);
                            }
                            alert.setHeaderText(k);
                            alert.setContentText(response.get(k));
                            alert.show();
                        }

                    } catch (Exception ex) {
                        Logger.getLogger(Appointments.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }); 

            //add to pane
            Pane p=new Pane();
            p.setLayoutX(0);
            p.setLayoutY(y);
            y+=50;
            p.getChildren().add(tot);
            p.getChildren().add(rec);
            p.getChildren().add(rem);
            p.getChildren().add(userID);
            p.getChildren().add(update);
            p.getChildren().add(delete);
            insidePane.getChildren().add(p);                 
        }
        return insidePane;
    }
    
    public Pane dataPane2(Map<String,String> response){
        //inside pane
        Pane insidePane=new Pane();
        insidePane.setLayoutX(0);
        insidePane.setLayoutY(100);
        int y=50;
        int size=response.size();
        for(int index=0;index<size;index++){
            Text tot=new Text("");
            Text rec=new Text("");
            Text rem=new Text("");
            Text userID=new Text("");
            //X
            tot.setLayoutX(0);
            rec.setLayoutX(150);
            rem.setLayoutX(300);
            userID.setLayoutX(450);
            //Y
            tot.setLayoutY(20);
            rec.setLayoutY(20);
            rem.setLayoutY(20);
            userID.setLayoutY(20);

            index++;
            String k=index+"";
            String v1=response.get(k);                   
            userID.setText(v1);
            response.remove(k);
            index++;
            k=index+"";
            String v2=response.get(k);
            tot.setText(v2);
            index++;
            k=index+"";
            String v3=response.get(k);
            rec.setText(v3);
            index++;
            k=index+"";
            String v4=response.get(k);
            rem.setText(v4);
            
            //add to pane
            Pane p=new Pane();
            p.setLayoutX(0);
            p.setLayoutY(y);
            y+=50;
            p.getChildren().add(tot);
            p.getChildren().add(rec);
            p.getChildren().add(rem);
            p.getChildren().add(userID);;
            insidePane.getChildren().add(p);                 
        }
        return insidePane;
    }
    
    Pane insidePane=new Pane();
    Pane insidePane2=new Pane();
    
    public Pane view_Payments(){
        Pane viewPane=new Pane();
        viewPane.setLayoutX(10);
        viewPane.setLayoutY(50);
        
        try {
            Map<String,String> response=backend.get_all_payments_accounts();
            Alert alert=new Alert(Alert.AlertType.ERROR);  
            String key="Response code";
            if(response.containsKey(key)){
                alert.setHeaderText(key);
                alert.setContentText(response.get(key));alert.show();   
            }
            else if(response.containsKey("empty")){
                return viewPane;
            }
            else{                
                Text tot=new Text("Total amount: "+"\n");
                Text rec=new Text("Receved amount: "+"\n");
                Text rem=new Text("Remaining amount: "+"\n");
                Text ust=new Text("User ID: "+"\n");
                //X
                tot.setLayoutX(0);
                rec.setLayoutX(150);
                rem.setLayoutX(300);
                ust.setLayoutX(450);
                //Y
                tot.setLayoutY(100);
                rec.setLayoutY(100);
                rem.setLayoutY(100);
                ust.setLayoutY(100);
                
                viewPane.getChildren().add(tot);
                viewPane.getChildren().add(rec);
                viewPane.getChildren().add(rem);
                viewPane.getChildren().add(ust);
                //search
                TextField searcht=new TextField();
                searcht.setLayoutX(400);
                searcht.setLayoutY(0);
                searcht.setPrefWidth(80);
                Button search=new Button("search");
                search.setLayoutX(500);
                search.setLayoutY(0);
                //color
                search.setStyle("-fx-background-color: #A987A8");
                viewPane.getChildren().add(searcht);
                viewPane.getChildren().add(search);
                search.setOnAction(new EventHandler<ActionEvent>() {           
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("searchaaa");
                        try {
                            System.out.println("search");
                            Map<String,String> response2=backend.searchPayment(searcht.getText());
                            Alert alert=new Alert(Alert.AlertType.ERROR);  
                            String key="Response code";
                            if(response2.containsKey(key)){
                                alert.setHeaderText(key);
                                alert.setContentText(response.get(key));alert.show();   
                            }
                            else if(response2.containsKey("empty")){
                                alert.setHeaderText("empty");
                                alert.setContentText("No Payment accounts available");alert.show();
                            }
                            else{
                                System.out.println("else");
                                insidePane.getChildren().clear();
                                insidePane2.getChildren().clear();
                                insidePane2=dataPane(response2);
                                viewPane.getChildren().add(insidePane2);
                            }

                        } catch (Exception ex) {
                            Logger.getLogger(Appointments.class.getName()).log(Level.SEVERE, null, ex);
                        }
                   }
                }); 
                insidePane=dataPane(response);
                insidePane2.getChildren().clear();
                viewPane.getChildren().add(insidePane);
            }
        } catch (Exception ex) {
            Logger.getLogger(Appointments.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return viewPane;
    }
    
    public Pane view_owing_patients(){
        Pane viewPane=new Pane();
        viewPane.setLayoutX(10);
        viewPane.setLayoutY(50);
        
        try {
            Map<String,String> response=backend.getOwingPatients();
            Alert alert=new Alert(Alert.AlertType.ERROR);  
            String key="Response code";
            if(response.containsKey(key)){
                alert.setHeaderText(key);
                alert.setContentText(response.get(key));alert.show();   
            }
            else if(response.containsKey("empty")){
                return viewPane;
            }
            else{                
                Text tot=new Text("Total amount: "+"\n");
                Text rec=new Text("Receved amount: "+"\n");
                Text rem=new Text("Remaining amount: "+"\n");
                Text ust=new Text("User ID: "+"\n");
                //X
                tot.setLayoutX(0);
                rec.setLayoutX(150);
                rem.setLayoutX(300);
                ust.setLayoutX(450);
                //Y
                tot.setLayoutY(100);
                rec.setLayoutY(100);
                rem.setLayoutY(100);
                ust.setLayoutY(100);
                
                viewPane.getChildren().add(tot);
                viewPane.getChildren().add(rec);
                viewPane.getChildren().add(rem);
                viewPane.getChildren().add(ust);
                //search
                TextField searcht=new TextField();
                searcht.setLayoutX(400);
                searcht.setLayoutY(0);
                searcht.setPrefWidth(80);
                Button search=new Button("search");
                search.setLayoutX(500);
                search.setLayoutY(0);
                //color
                search.setStyle("-fx-background-color: #A987A8");
                viewPane.getChildren().add(searcht);
                viewPane.getChildren().add(search);
                search.setOnAction(new EventHandler<ActionEvent>() {           
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("searchaaa");
                        try {
                            System.out.println("search");
                            Map<String,String> response2=backend.searchPayment(searcht.getText());
                            Alert alert=new Alert(Alert.AlertType.ERROR);  
                            String key="Response code";
                            if(response2.containsKey(key)){
                                alert.setHeaderText(key);
                                alert.setContentText(response.get(key));alert.show();   
                            }
                            else if(response2.containsKey("empty")){
                                alert.setHeaderText("empty");
                                alert.setContentText("No Payment accounts available");alert.show();
                            }
                            else{
                                System.out.println("else");
                                insidePane.getChildren().clear();
                                insidePane2.getChildren().clear();
                                insidePane2=dataPane(response2);
                                viewPane.getChildren().add(insidePane2);
                            }

                        } catch (Exception ex) {
                            Logger.getLogger(Appointments.class.getName()).log(Level.SEVERE, null, ex);
                        }
                   }
                }); 
                insidePane=dataPane(response);
                insidePane2.getChildren().clear();
                viewPane.getChildren().add(insidePane);
            }
        } catch (Exception ex) {
            Logger.getLogger(Appointments.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return viewPane;
    }
    
    public Pane view_my_account(){
        Pane viewPane=new Pane();
        viewPane.setLayoutX(10);
        viewPane.setLayoutY(50);        
        try {
            Map<String,String> response=backend.getMyAccount();
            Alert alert=new Alert(Alert.AlertType.ERROR);  
            String key="Response code";
            if(response.containsKey(key)){
                alert.setHeaderText(key);
                alert.setContentText(response.get(key));alert.show();   
            }
            else if(response.containsKey("empty")){
                return viewPane;
            }
            else{                
                Text tot=new Text("Total amount: "+"\n");
                Text rec=new Text("Receved amount: "+"\n");
                Text rem=new Text("Remaining amount: "+"\n");
                Text ust=new Text("User ID: "+"\n");
                //X
                tot.setLayoutX(0);
                rec.setLayoutX(150);
                rem.setLayoutX(300);
                ust.setLayoutX(450);
                //Y
                tot.setLayoutY(100);
                rec.setLayoutY(100);
                rem.setLayoutY(100);
                ust.setLayoutY(100);

                viewPane.getChildren().add(tot);
                viewPane.getChildren().add(rec);
                viewPane.getChildren().add(rem);
                viewPane.getChildren().add(ust);
                insidePane=dataPane2(response);
                viewPane.getChildren().add(insidePane);
            }
        } catch (Exception ex) {
            Logger.getLogger(Appointments.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return viewPane;
    }
}

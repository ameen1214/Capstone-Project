/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restclient;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Records {
    
    BackEnd backend=new BackEnd();
    String selected_status;
    String [] statusArray={"First visit","Under treatment","Treatment completed"};
    
    public Pane create_record(){
        Pane cretePane=new Pane();
        cretePane.setLayoutX(150);
        cretePane.setLayoutY(100);
        //text
        Text nameT=new Text("Name");
        Text addressT=new Text("Address");
        Text phoneT=new Text("Phone");
        Text illT=new Text("Ill");
        Text statusT=new Text("Status");
        Text userT=new Text("User account");
        //
        TextField name=new TextField();
        TextField address=new TextField();
        TextField phone=new TextField();
        TextField ill=new TextField();
        ComboBox status=new ComboBox(FXCollections.observableArrayList(statusArray));
        status.getSelectionModel().select(0);
        
        TextField user=new TextField();
        //X
        nameT.setLayoutX(0);
        addressT.setLayoutX(0);
        phoneT.setLayoutX(0);
        illT.setLayoutX(0);
        statusT.setLayoutX(0);
        userT.setLayoutX(0);        
        name.setLayoutX(100);
        address.setLayoutX(100);
        phone.setLayoutX(100);
        ill.setLayoutX(100);
        status.setLayoutX(100);
        user.setLayoutX(100);
        //Y
        nameT.setLayoutY(20);
        addressT.setLayoutY(70);
        phoneT.setLayoutY(120);
        illT.setLayoutY(170);
        statusT.setLayoutY(210);
        userT.setLayoutY(270);         
        name.setLayoutY(0);
        address.setLayoutY(50);
        phone.setLayoutY(100);
        ill.setLayoutY(150);
        status.setLayoutY(200);
        user.setLayoutY(250);        
        
        status.setOnAction(new EventHandler<ActionEvent>() {           
            @Override
            public void handle(ActionEvent event) {
                selected_status=status.getSelectionModel().getSelectedItem().toString();
            }
        });
        //
        
        Button create=new Button("Create record");
        create.setLayoutX(150);
        create.setLayoutY(350);
        create.setStyle("-fx-background-color: #A987A8");
        create.setOnAction(new EventHandler<ActionEvent>() {           
            @Override
            public void handle(ActionEvent event) {
                try {
                    Map<String,String> response=backend.create_record(name.getText(), address.getText(), phone.getText(), ill.getText(), selected_status, user.getText());
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
        cretePane.getChildren().add(nameT);
        cretePane.getChildren().add(addressT);
        cretePane.getChildren().add(phoneT);
        cretePane.getChildren().add(illT);
        cretePane.getChildren().add(statusT);
        cretePane.getChildren().add(userT);
        cretePane.getChildren().add(name);
        cretePane.getChildren().add(address);
        cretePane.getChildren().add(phone);
        cretePane.getChildren().add(ill);
        cretePane.getChildren().add(status);
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
            Text name=new Text("");
            Text address=new Text("");
            Text phone=new Text("");
            Text ill=new Text("");
            Text status=new Text("");
            Text userID=new Text("");
            //X
            name.setLayoutX(0);
            address.setLayoutX(100);
            phone.setLayoutX(200);
            ill.setLayoutX(300);
            status.setLayoutX(400);
            userID.setLayoutX(550);
            //Y
            name.setLayoutY(20);
            address.setLayoutY(20);
            phone.setLayoutY(20);
            ill.setLayoutY(20);
            status.setLayoutY(20);
            userID.setLayoutY(20);

        //fonts
        Font f=new Font(15);
        name.setFont(f);
        address.setFont(f);
        phone.setFont(f);
        ill.setFont(f);
        status.setFont(f);
        userID.setFont(f);
        //
        index++;
        String k=index+"";
        String v1=response.get(k);                   
        userID.setText(v1);
        response.remove(k);
        index++;
        k=index+"";
        String v2=response.get(k);
        name.setText(v2);
        index++;
        k=index+"";
        String v3=response.get(k);
        address.setText(v3);
        index++;
        k=index+"";
        String v4=response.get(k);
        phone.setText(v4);
        index++;
        k=index+"";
        String v5=response.get(k);
        ill.setText(v5);
        index++;
        k=index+"";
        String v6=response.get(k);
        status.setText(v6);

        //update record
        Button update=new Button("update"); 
        update.setStyle("-fx-background-color: #A987A8");
        update.setLayoutX(630);
        update.setLayoutY(0);                   
        update.setOnAction(new EventHandler<ActionEvent>() {           
            @Override
            public void handle(ActionEvent event) {
                List<String> dialodData=Arrays.asList(statusArray);
                ChoiceDialog dialog=new ChoiceDialog(dialodData.get(0),dialodData);
                dialog.setHeaderText("Select new status");
                dialog.showAndWait();
                String updatedStatus=dialog.getSelectedItem().toString();
                try {
                    Map<String,String> response=backend.update_status(v1, updatedStatus);
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
                    Map<String,String> response=backend.delete_record(v1);
                    Alert alert=new Alert(Alert.AlertType.WARNING); 
                    for(String k:response.keySet()){
                        if(k.equals("ok")){
                            name.setFill(Color.CRIMSON);
                            address.setFill(Color.CRIMSON);
                            phone.setFill(Color.CRIMSON);
                            ill.setFill(Color.CRIMSON);
                            status.setFill(Color.CRIMSON);
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
            p.getChildren().add(name);
            p.getChildren().add(address);
            p.getChildren().add(phone);
            p.getChildren().add(ill);
            p.getChildren().add(status);
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
            Text name=new Text("");
            Text address=new Text("");
            Text phone=new Text("");
            Text ill=new Text("");
            Text status=new Text("");
            Text userID=new Text("");
            //X
            name.setLayoutX(0);
            address.setLayoutX(100);
            phone.setLayoutX(200);
            ill.setLayoutX(300);
            status.setLayoutX(400);
            userID.setLayoutX(550);
            //Y
            name.setLayoutY(20);
            address.setLayoutY(20);
            phone.setLayoutY(20);
            ill.setLayoutY(20);
            status.setLayoutY(20);
            userID.setLayoutY(20);

            //fonts
            Font f=new Font(15);
            name.setFont(f);
            address.setFont(f);
            phone.setFont(f);
            ill.setFont(f);
            status.setFont(f);
            userID.setFont(f);
            //
            index++;
            String k=index+"";
            String v1=response.get(k);                   
            userID.setText(v1);
            response.remove(k);
            index++;
            k=index+"";
            String v2=response.get(k);
            name.setText(v2);
            index++;
            k=index+"";
            String v3=response.get(k);
            address.setText(v3);
            index++;
            k=index+"";
            String v4=response.get(k);
            phone.setText(v4);
            index++;
            k=index+"";
            String v5=response.get(k);
            ill.setText(v5);
            index++;
            k=index+"";
            String v6=response.get(k);
            status.setText(v6);
            //add to pane
            Pane p=new Pane();
            p.setLayoutX(0);
            p.setLayoutY(y);
            y+=50;
            p.getChildren().add(name);
            p.getChildren().add(address);
            p.getChildren().add(phone);
            p.getChildren().add(ill);
            p.getChildren().add(status);
            p.getChildren().add(userID);
            insidePane.getChildren().add(p);                 
        }
        return insidePane;
    }
    
    Pane insidePane=new Pane();
    Pane insidePane2=new Pane();
    
    public Pane view_records(){
        Pane viewPane=new Pane();
        viewPane.setLayoutX(10);
        viewPane.setLayoutY(50);
        
        try {
            Map<String,String> response=backend.getAllRecords();
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
                Text namet=new Text("Name: "+"\n");
                Text addt=new Text("Address: "+"\n");
                Text pht=new Text("Phone: "+"\n");
                Text illt=new Text("ill: "+"\n");
                Text stt=new Text("Status: "+"\n");
                Text ust=new Text("User ID: "+"\n");
                //X
                namet.setLayoutX(0);
                addt.setLayoutX(100);
                pht.setLayoutX(200);
                illt.setLayoutX(300);
                stt.setLayoutX(400);
                ust.setLayoutX(550);
                //Y
                namet.setLayoutY(100);
                addt.setLayoutY(100);
                pht.setLayoutY(100);
                illt.setLayoutY(100);
                stt.setLayoutY(100);
                ust.setLayoutY(100);
                
                Font f=new Font(15);
                namet.setFont(f);
                addt.setFont(f);
                pht.setFont(f);
                illt.setFont(f);
                stt.setFont(f);
                ust.setFont(f);
                viewPane.getChildren().add(namet);
                viewPane.getChildren().add(addt);
                viewPane.getChildren().add(pht);
                viewPane.getChildren().add(illt);
                viewPane.getChildren().add(stt);
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
                        try {
                            Map<String,String> response2=backend.searchRecord(searcht.getText());
                            Alert alert=new Alert(Alert.AlertType.ERROR);  
                            String key="Response code";
                            if(response2.containsKey(key)){
                                alert.setHeaderText(key);
                                alert.setContentText(response.get(key));alert.show();   
                            }
                            else if(response2.containsKey("empty")){
                                alert.setHeaderText("empty");
                                alert.setContentText("No records available");alert.show();
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
    
    public Pane view_my_record(){
        Pane viewPane=new Pane();
        viewPane.setLayoutX(10);
        viewPane.setLayoutY(50);        
        try {
            Map<String,String> response=backend.getMyRecord();
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
                Text namet=new Text("Name: "+"\n");
                Text addt=new Text("Address: "+"\n");
                Text pht=new Text("Phone: "+"\n");
                Text illt=new Text("ill: "+"\n");
                Text stt=new Text("Status: "+"\n");
                Text ust=new Text("User ID: "+"\n");
                //X
                namet.setLayoutX(0);
                addt.setLayoutX(100);
                pht.setLayoutX(200);
                illt.setLayoutX(300);
                stt.setLayoutX(400);
                ust.setLayoutX(550);
                //Y
                namet.setLayoutY(100);
                addt.setLayoutY(100);
                pht.setLayoutY(100);
                illt.setLayoutY(100);
                stt.setLayoutY(100);
                ust.setLayoutY(100);
                
                Font f=new Font(15);
                namet.setFont(f);
                addt.setFont(f);
                pht.setFont(f);
                illt.setFont(f);
                stt.setFont(f);
                ust.setFont(f);
                viewPane.getChildren().add(namet);
                viewPane.getChildren().add(addt);
                viewPane.getChildren().add(pht);
                viewPane.getChildren().add(illt);
                viewPane.getChildren().add(stt);
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

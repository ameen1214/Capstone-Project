/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restclient;

import java.time.LocalDate;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Appointments{
    String input_date="";
    String input_time="";
    String input_am_pm="";
    BackEnd backend=new BackEnd();
    
    public Pane create_appointments(){
        
        Pane cretePane=new Pane();
        cretePane.setLayoutX(150);
        cretePane.setLayoutY(200);
        //Date
        FlowPane datePane=new FlowPane();
        datePane.setLayoutX(0);
        datePane.setLayoutY(0);
        datePane.setHgap(10);
        Text dateText=new Text("Select day: ");
        DatePicker date=new DatePicker();
        date.setShowWeekNumbers(true);
        date.setOnAction(new EventHandler<ActionEvent>() {           
            @Override
            public void handle(ActionEvent event) {
                LocalDate ld=date.getValue();
                input_date=ld.toString();
            }
        });
        
        //date validation error
        Text error1=new Text("");
        error1.setFill(Color.RED);
        error1.setLayoutX(0);
        error1.setLayoutY(50);
        
        //time validation error
        Text error2=new Text("");
        error2.setFill(Color.RED);
        error2.setLayoutX(0);
        error2.setLayoutY(150);
        
        //Time
        FlowPane timePane=new FlowPane();
        timePane.setLayoutX(0);
        timePane.setLayoutY(100);
        timePane.setHgap(10);
        timePane.setPrefWrapLength(600);
        Text timeText=new Text("Select time: ");
        Text hourText=new Text("hour: ");
        TextField hour=new TextField();
        Text minuteText=new Text("minute: ");
        TextField minute=new TextField();
        hour.setPrefWidth(50);
        minute.setPrefWidth(50);
        CheckBox am=new CheckBox("AM");
        CheckBox pm=new CheckBox("PM");
        Button create=new Button("create appointment");
        create.setLayoutX(150);
        create.setLayoutY(200);
        create.setStyle("-fx-background-color: #A987A8");
        create.setOnAction(new EventHandler<ActionEvent>() {           
            @Override
            public void handle(ActionEvent event) {
                error1.setText("");
                error2.setText("");
                String h=hour.getText();
                String m=minute.getText();
                if(!h.isEmpty()&&!m.isEmpty())
                    input_time=h+"/"+m+" "+input_am_pm;
                try {
                    Map<String,String> response=backend.create_appointment(input_date, input_time);
                    Alert alert=new Alert(Alert.AlertType.WARNING);                   
                    for(String key:response.keySet()){
                        alert.setHeaderText(key);
                        switch(key){
                            case "ok":alert.setContentText(response.get(key));alert.show();break;
                            case "date":error1.setText(response.get(key));break;
                            case "time":error2.setText(response.get(key));break;
                            case "403":alert.setContentText(response.get(key));alert.show();break;
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Appointments.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        am.setOnAction(new EventHandler<ActionEvent>() {         
            @Override
            public void handle(ActionEvent event) {
                input_am_pm="AM";
                pm.setSelected(false);
            }
        });
        
        pm.setOnAction(new EventHandler<ActionEvent>() {         
            @Override
            public void handle(ActionEvent event) {
                input_am_pm="PM";
                am.setSelected(false);
            }
        });

        //
        datePane.getChildren().add(dateText);
        datePane.getChildren().add(date);
        //
        timePane.getChildren().add(timeText);
        timePane.getChildren().add(hourText);
        timePane.getChildren().add(hour);
        timePane.getChildren().add(minuteText);
        timePane.getChildren().add(minute);
        timePane.getChildren().add(am);
        timePane.getChildren().add(pm);
        //
        cretePane.getChildren().add(datePane);
        cretePane.getChildren().add(timePane);
        cretePane.getChildren().add(error1);
        cretePane.getChildren().add(error2);
        cretePane.getChildren().add(create);
        return cretePane;
    }
    
    public Pane view_appointments(){
        Pane viewPane=new Pane();
        viewPane.setLayoutX(100);
        viewPane.setLayoutY(100);
        try {
            Map<String,String> response=backend.getAppointments();
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
                Text datet=new Text("Date: "+"\n");
                Text timet=new Text("Time: "+"\n");
                datet.setLayoutX(0);
                datet.setLayoutY(0);
                timet.setLayoutX(175);
                timet.setLayoutY(0);
                //
                Font f=new Font(20);
                timet.setFont(f);
                datet.setFont(f);
                viewPane.getChildren().add(timet);
                viewPane.getChildren().add(datet);
                int y=50;
                int size=response.size();
                for(int index=0;index<size;index++){
                    Text dateValue=new Text("");
                    Text timeValue=new Text("");
                    dateValue.setLayoutX(0);
                    dateValue.setLayoutY(20);
                    timeValue.setLayoutX(175);
                    timeValue.setLayoutY(20);
                    dateValue.setFont(f);
                    timeValue.setFont(f);
                    String k=index+"";
                    String dv=response.get(k);                   
                    dateValue.setText(dv);
                    response.remove(k);
                    index++;
                    k=index+"";
                    String tv=response.get(k);
                    timeValue.setText(tv);
                    //update appointment
                    Button update=new Button("update"); 
                    update.setStyle("-fx-background-color: #A987A8");
                    update.setLayoutX(300);
                    update.setLayoutY(0);                   
                    update.setOnAction(new EventHandler<ActionEvent>() {           
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                TextInputDialog input=new TextInputDialog();
                                input.setHeaderText("Enter new time like h:m AM");
                                input.showAndWait();
                                String updatedTime=input.getEditor().getText();
                                if(!updatedTime.contains(":")||(!updatedTime.contains("AM")&&!updatedTime.contains("PM"))){
                                    alert.setHeaderText("Input error");
                                    alert.setContentText("Time must be hour:minute ex: 10:30 AM");
                                    alert.show();
                                }
                                else{
                                    updatedTime=updatedTime.replace(":", "/");
                                    System.out.println(updatedTime);
                                    Map<String,String> response=backend.update_appointment(dv, tv.replace(":", "/"),updatedTime);
                                    Alert alert=new Alert(Alert.AlertType.WARNING); 
                                    for(String k:response.keySet()){
                                        alert.setHeaderText(k);
                                        alert.setContentText(response.get(k));
                                        alert.show();
                                    }
                                }                              
                            } catch (Exception ex) {
                                Logger.getLogger(Appointments.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }); 
                    
                    //delete appointment
                    Button delete=new Button("delete"); 
                    delete.setStyle("-fx-background-color: #A987A8");
                    delete.setLayoutX(400);
                    delete.setLayoutY(0);                   
                    delete.setOnAction(new EventHandler<ActionEvent>() {           
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                Map<String,String> response=backend.delete_appointment(dv, tv.replace(":", "/"));
                                Alert alert=new Alert(Alert.AlertType.WARNING); 
                                for(String k:response.keySet()){
                                    if(k.equals("ok")){
                                        timeValue.setFill(Color.CRIMSON);
                                        dateValue.setFill(Color.CRIMSON);
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
                    p.getChildren().add(timeValue);
                    p.getChildren().add(dateValue);
                    p.getChildren().add(update);
                    p.getChildren().add(delete);
                    viewPane.getChildren().add(p);
                    
                } 
            }
        } catch (Exception ex) {
            Logger.getLogger(Appointments.class.getName()).log(Level.SEVERE, null, ex);
        }
        return viewPane;
    }
    
    public Pane view_appointments_user(){
        Pane viewPane=new Pane();
        viewPane.setLayoutX(100);
        viewPane.setLayoutY(100);
        try {
            Map<String,String> response=backend.getAppointments();
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
                Text datet=new Text("Date: "+"\n");
                Text timet=new Text("Time: "+"\n");
                datet.setLayoutX(0);
                datet.setLayoutY(0);
                timet.setLayoutX(175);
                timet.setLayoutY(0);
                //
                Font f=new Font(20);
                timet.setFont(f);
                datet.setFont(f);
                viewPane.getChildren().add(timet);
                viewPane.getChildren().add(datet);
                int y=50;
                int size=response.size();
                for(int index=0;index<size;index++){
                    Text dateValue=new Text("");
                    Text timeValue=new Text("");
                    dateValue.setLayoutX(0);
                    dateValue.setLayoutY(20);
                    timeValue.setLayoutX(175);
                    timeValue.setLayoutY(20);
                    dateValue.setFont(f);
                    timeValue.setFont(f);
                    String k=index+"";
                    String dv=response.get(k);                   
                    dateValue.setText(dv);
                    response.remove(k);
                    index++;
                    k=index+"";
                    String tv=response.get(k);
                    timeValue.setText(tv);
                    //update appointment
                    Button book=new Button("book"); 
                    book.setStyle("-fx-background-color: #A987A8");
                    book.setLayoutX(300);
                    book.setLayoutY(0);                   
                    book.setOnAction(new EventHandler<ActionEvent>() {           
                        @Override
                        public void handle(ActionEvent event) {
                            try {                               
                                Map<String,String> response=backend.book_appointment(dv, tv.replace(":", "/"));
                                Alert alert=new Alert(Alert.AlertType.WARNING); 
                                for(String k:response.keySet()){
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
                    p.getChildren().add(timeValue);
                    p.getChildren().add(dateValue);
                    p.getChildren().add(book);
                    viewPane.getChildren().add(p);
                    
                } 
            }
        } catch (Exception ex) {
            Logger.getLogger(Appointments.class.getName()).log(Level.SEVERE, null, ex);
        }
        return viewPane;
    }
    
    
    
    public Pane view_all_booked_appointments(){
        Pane viewPane=new Pane();
        viewPane.setLayoutX(100);
        viewPane.setLayoutY(100);
        try {
            Map<String,String> response=backend.getBookedAppointments();
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
                Text datet=new Text("Date: "+"\n");
                Text timet=new Text("Time: "+"\n");
                Text usert=new Text("User ID: "+"\n");
                datet.setLayoutX(0);
                datet.setLayoutY(0);
                timet.setLayoutX(175);
                timet.setLayoutY(0);
                usert.setLayoutX(350);
                usert.setLayoutY(0);
                //
                Font f=new Font(20);
                timet.setFont(f);
                datet.setFont(f);
                usert.setFont(f);
                viewPane.getChildren().add(timet);
                viewPane.getChildren().add(datet);
                viewPane.getChildren().add(usert);
                int y=50;
                int size=response.size();
                for(int index=0;index<size;index++){
                    Text dateValue=new Text("");
                    Text timeValue=new Text("");
                    Text userValue=new Text("");
                    dateValue.setLayoutX(0);
                    dateValue.setLayoutY(20);
                    timeValue.setLayoutX(175);
                    timeValue.setLayoutY(20);
                    userValue.setLayoutX(350);
                    userValue.setLayoutY(20);
                    dateValue.setFont(f);
                    timeValue.setFont(f);
                    userValue.setFont(f);
                    String k=index+"";
                    String uv=response.get(k);                   
                    userValue.setText(uv);
                    index++;
                    k=index+"";
                    String dv=response.get(k);                   
                    dateValue.setText(dv);
                    index++;
                    k=index+"";
                    String tv=response.get(k);
                    timeValue.setText(tv);

                    //add to pane
                    Pane p=new Pane();
                    p.setLayoutX(0);
                    p.setLayoutY(y);
                    y+=50;
                    p.getChildren().add(timeValue);
                    p.getChildren().add(dateValue);
                    p.getChildren().add(userValue);
                    viewPane.getChildren().add(p);
                    
                } 
            }
        } catch (Exception ex) {
            Logger.getLogger(Appointments.class.getName()).log(Level.SEVERE, null, ex);
        }
        return viewPane;
    }
    
    
    
    public Pane view_my_booked_appointments(){
        Pane viewPane=new Pane();
        viewPane.setLayoutX(100);
        viewPane.setLayoutY(100);
        try {
            Map<String,String> response=backend.getMyAppointments();
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
                Text datet=new Text("Date: "+"\n");
                Text timet=new Text("Time: "+"\n");
                datet.setLayoutX(0);
                datet.setLayoutY(0);
                timet.setLayoutX(175);
                timet.setLayoutY(0);
                //
                Font f=new Font(20);
                timet.setFont(f);
                datet.setFont(f);
                viewPane.getChildren().add(timet);
                viewPane.getChildren().add(datet);
                int y=50;
                int size=response.size();
                for(int index=0;index<size;index++){
                    Text dateValue=new Text("");
                    Text timeValue=new Text("");
                    dateValue.setLayoutX(0);
                    dateValue.setLayoutY(20);
                    timeValue.setLayoutX(175);
                    timeValue.setLayoutY(20);
                    dateValue.setFont(f);
                    timeValue.setFont(f);
                    index++;
                    String k=index+"";
                    String dv=response.get(k);                   
                    dateValue.setText(dv);
                    response.remove(k);
                    index++;
                    k=index+"";
                    String tv=response.get(k);
                    timeValue.setText(tv);
                    //delete appointment
                    Button delete=new Button("unbook"); 
                    delete.setStyle("-fx-background-color: #A987A8");
                    delete.setLayoutX(400);
                    delete.setLayoutY(0);                   
                    delete.setOnAction(new EventHandler<ActionEvent>() {           
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                Map<String,String> response=backend.delete_appointment(dv, tv.replace(":", "/"));
                                Alert alert=new Alert(Alert.AlertType.WARNING); 
                                for(String k:response.keySet()){
                                    if(k.equals("ok")){
                                        timeValue.setFill(Color.CRIMSON);
                                        dateValue.setFill(Color.CRIMSON);
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
                    p.getChildren().add(timeValue);
                    p.getChildren().add(dateValue);
                    p.getChildren().add(delete);
                    viewPane.getChildren().add(p);
                    
                } 
            }
        } catch (Exception ex) {
            Logger.getLogger(Appointments.class.getName()).log(Level.SEVERE, null, ex);
        }
        return viewPane;
    }
    
}

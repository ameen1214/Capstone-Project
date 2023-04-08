/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restclient;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import static restclient.RestClient.bar;
import static restclient.RestClient.root;


public class UserBar extends Bar{

    public UserBar() {
        super();
        appointments.getItems().addAll(view_appointments(),view_booked_appointments());
        records.getItems().addAll(view_my_record());
        paymentsM.getItems().addAll(view_my_account());
    }
    
    private MenuItem view_appointments(){
        MenuItem view=new MenuItem("view available appointments");
        view.setOnAction(new EventHandler<ActionEvent>() {           
            @Override
            public void handle(ActionEvent event) {
                root.getChildren().clear();
                root.getChildren().add(bar);
                root.getChildren().add(app.view_appointments_user()); 
            }
        });
        return view;
    }
    
    private MenuItem view_booked_appointments(){
        MenuItem view=new MenuItem("view my appointments");
        view.setOnAction(new EventHandler<ActionEvent>() {           
            @Override
            public void handle(ActionEvent event) {
                root.getChildren().clear();
                root.getChildren().add(bar);
                root.getChildren().add(app.view_my_booked_appointments()); 
            }
        });
        return view;
    }
    
    private MenuItem view_my_record(){
        MenuItem view=new MenuItem("view my record");
        view.setOnAction(new EventHandler<ActionEvent>() {           
            @Override
            public void handle(ActionEvent event) {
                root.getChildren().clear();
                root.getChildren().add(bar);
                root.getChildren().add(record.view_my_record()); 
            }
        });
        return view;
    }
    
    private MenuItem view_my_account(){
        MenuItem view=new MenuItem("view my payment account");
        view.setOnAction(new EventHandler<ActionEvent>() {           
            @Override
            public void handle(ActionEvent event) {
                root.getChildren().clear();
                root.getChildren().add(bar);
                root.getChildren().add(payments.view_my_account()); 
            }
        });
        return view;
    }
    
    
    
    
}

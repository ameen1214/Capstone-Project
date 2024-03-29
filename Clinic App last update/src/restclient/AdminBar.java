
package restclient;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import static restclient.RestClient.bar;
import static restclient.RestClient.root;

public class AdminBar extends Bar{

    
    AdminBar(){
        super();
        appointments.getItems().addAll(create_appointments(),view_appointments(),view_all_booked_appointments());
        records.getItems().addAll(create_record(),view_records());
        paymentsM.getItems().addAll(create_payment(),view_payments(),view_owing());
    }
    
    //MenuBar items
        
    private MenuItem view_all_booked_appointments(){
        MenuItem view_all_booked=new MenuItem("view booked appointments");
        view_all_booked.setOnAction(new EventHandler<ActionEvent>() {           
            @Override
            public void handle(ActionEvent event) {
                root.getChildren().clear();
                root.getChildren().add(bar);
                root.getChildren().add(app.view_all_booked_appointments()); 
            }
        });
        return view_all_booked;
    }
    
    private MenuItem view_appointments(){
        MenuItem view=new MenuItem("view appointments");
        view.setOnAction(new EventHandler<ActionEvent>() {           
            @Override
            public void handle(ActionEvent event) {
                root.getChildren().clear();
                root.getChildren().add(bar);
                root.getChildren().add(app.view_appointments()); 
            }
        });
        return view;
    }
    
    private MenuItem create_appointments(){
        MenuItem create=new MenuItem("create appointments");
        create.setOnAction(new EventHandler<ActionEvent>() {           
            @Override
            public void handle(ActionEvent event) {               
                root.getChildren().clear();
                root.getChildren().add(bar);
                root.getChildren().add(app.create_appointments()); 
            }
        });
        return create;
    }
    
    private MenuItem create_record(){
        MenuItem create=new MenuItem("create record");
        create.setOnAction(new EventHandler<ActionEvent>() {           
            @Override
            public void handle(ActionEvent event) {               
                root.getChildren().clear();
                root.getChildren().add(bar);
                root.getChildren().add(record.create_record()); 
            }
        });
        return create;
    }
    
    private MenuItem view_records(){
        MenuItem view=new MenuItem("view all records");
        view.setOnAction(new EventHandler<ActionEvent>() {           
            @Override
            public void handle(ActionEvent event) {
                root.getChildren().clear();
                root.getChildren().add(bar);
                root.getChildren().add(record.view_records()); 
            }
        });
        return view;
    }
    
    private MenuItem view_payments(){
        MenuItem view=new MenuItem("view payments accounts");
        view.setOnAction(new EventHandler<ActionEvent>() {           
            @Override
            public void handle(ActionEvent event) {
                root.getChildren().clear();
                root.getChildren().add(bar);
                root.getChildren().add(payments.view_Payments()); 
            }
        });
        return view;
    }
    
    private MenuItem view_owing(){
        MenuItem view=new MenuItem("view owing patients");
        view.setOnAction(new EventHandler<ActionEvent>() {           
            @Override
            public void handle(ActionEvent event) {
                root.getChildren().clear();
                root.getChildren().add(bar);
                root.getChildren().add(payments.view_owing_patients()); 
            }
        });
        return view;
    }
    
    private MenuItem create_payment(){
        MenuItem create=new MenuItem("create payment account");
        create.setOnAction(new EventHandler<ActionEvent>() {           
            @Override
            public void handle(ActionEvent event) {
                root.getChildren().clear();
                root.getChildren().add(bar);
                root.getChildren().add(payments.create_account()); 
            }
        });
        return create;
    }
    
    
}

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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import static restclient.RestClient.loginPane;
import static restclient.RestClient.rightPane;
import static restclient.RestClient.root;


public class Bar extends MenuBar{
    
    BackEnd backend=new BackEnd();
    Appointments app=new Appointments();
    Records record=new Records();
    Payments payments=new Payments();
    Menu profile=new Menu("Profile");
    Menu appointments=new Menu(" Appointments ");
    Menu records=new Menu(" Records ");
    Menu paymentsM=new Menu(" Payments ");
    
    Bar(){
        setPrefSize(800, 30);
        setStyle("-fx-background-color:#A987A8 ; -fx-selection-bar:#A987A8");
        
        //Appointments options menu
        profile.getItems().addAll(logout(),change_password(),change_email());
        appointments.getItems().addAll();
        getMenus().add(0, appointments);
        getMenus().add(1, records);
        getMenus().add(2, paymentsM);
        getMenus().add(3, new Menu("                \t\t\t\t\t\t\t\t\t "));
        getMenus().add(4, profile);
    }
    
    //MenuBar items
    private MenuItem logout(){
        MenuItem logout=new MenuItem("logout");
        logout.setOnAction(new EventHandler<ActionEvent>() {           
            @Override
            public void handle(ActionEvent event) {
                try {
                    Map<String,String> response=backend.logout();
                    Alert alert=new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("logout");                  
                    if(response.containsKey("ok")){
                        backend.removeToken();
                        root.getChildren().clear();
                        root.getChildren().add(loginPane);
                        root.getChildren().add(rightPane);
                    }
                    else if(response.containsKey("no")){                       
                        alert.setContentText(response.get("no"));
                        alert.show();
                    }                  
                } catch (Exception ex) {
                    Logger.getLogger(AdminBar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        return logout;
    }
    
    private MenuItem change_password(){
        MenuItem change=new MenuItem("change password");
        change.setOnAction(new EventHandler<ActionEvent>() {           
            @Override
            public void handle(ActionEvent event) {
                TextInputDialog input=new TextInputDialog();
                input.setHeaderText("Enter new password");
                input.showAndWait();
                String password=input.getEditor().getText();
                try {                  
                    Map<String,String> response=backend.updatePassword(password);
                    Alert alert=new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Change password");                  
                    if(response.containsKey("ok")){                       
                        alert.setContentText(response.get("ok"));
                    }
                    else if(response.containsKey("no")){                       
                        alert.setContentText(response.get("no"));
                    }
                    alert.show();
                } catch (Exception ex) {
                    Logger.getLogger(AdminBar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        return change;
    }
    
    private MenuItem change_email(){
        MenuItem change=new MenuItem("change email");
        
        change.setOnAction(new EventHandler<ActionEvent>() {           
            @Override
            public void handle(ActionEvent event) {
                TextInputDialog input=new TextInputDialog();
                input.setHeaderText("Enter new email");
                input.showAndWait();
                String email=input.getEditor().getText();
                try {                  
                    Map<String,String> response=backend.updateEmail(email);
                    Alert alert=new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Change email");                  
                    if(response.containsKey("ok")){                       
                        alert.setContentText(response.get("ok"));
                    }
                    else if(response.containsKey("no")){                       
                        alert.setContentText(response.get("no"));
                    }
                    alert.show();
                } catch (Exception ex) {
                    Logger.getLogger(AdminBar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        return change;
    }
    
    
}

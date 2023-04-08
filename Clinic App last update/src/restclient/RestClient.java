/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restclient;

import java.util.Map;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class RestClient extends Application {
    
    static Pane root = new Pane();
    static Pane rightPane = new Pane();
    static Pane loginPane=new Pane();
    static Bar bar=new Bar();
    @Override
    public void start(Stage primaryStage) {
        Font font=new Font(20);
        //----------------------------------------Main screen components--------------------------------------------------
        
        //Main screen       
        loginPane.setLayoutX(30);
        loginPane.setLayoutY(190);
     
        Text emailText=new Text("Email: ");
        TextField email=new TextField();
        Text passwordText=new Text("Password: ");
        PasswordField password=new PasswordField();
        Text emailErrorText=new Text("");
        Text passwordErrorText=new Text("");
        Button login = new Button(" login ");
        Label newAccountL=new Label("Register now  ->");
        //layout X
        emailText.setLayoutX(0);
        passwordText.setLayoutX(0);
        emailErrorText.setLayoutX(0);
        passwordErrorText.setLayoutX(0);
        email.setLayoutX(100);
        password.setLayoutX(100);
        login.setLayoutX(100);
        newAccountL.setLayoutX(250);
        //layout Y
        emailText.setLayoutY(20);
        passwordText.setLayoutY(110);
        emailErrorText.setLayoutY(70);
        passwordErrorText.setLayoutY(150);
        email.setLayoutY(0);
        password.setLayoutY(90);
        login.setLayoutY(200);
        newAccountL.setLayoutY(210);
        
        //font
        emailText.setFont(font);
        passwordText.setFont(font);
        email.setFont(font);
        password.setFont(font);
        login.setFont(font);
        newAccountL.setFont(font);
        
        //color
        emailErrorText.setFill(Color.RED);
        passwordErrorText.setFill(Color.RED);
        
        login.setOnAction(new EventHandler<ActionEvent>() {           
            @Override
            public void handle(ActionEvent event) {
                //reset errors texts
                emailErrorText.setText("");
                passwordErrorText.setText("");              
                String e=email.getText();
                String p=password.getText();
                BackEnd bakend=new BackEnd();
                try{
                    Map<String,String> response=bakend.login(e, p);
                    for(String key:response.keySet()){
                        if(key.equals("email")){
                            emailErrorText.setText(response.get(key));
                        }
                        else if(key.equals("password")){
                            passwordErrorText.setText(response.get(key));
                        }
                        else if(key.equals("no")){
                            Alert alert=new Alert(Alert.AlertType.ERROR);
                            alert.setHeaderText("Login failed!!");
                            alert.setContentText(response.get(key));
                            alert.show();
                        }
                        else if(key.equals("token")){
                            email.setText("");
                            password.setText("");
                            //go to main screen
                            root.getChildren().remove(loginPane);
                            root.getChildren().remove(rightPane);
                            //user role
                            String role=bakend.getRole().get("role").toString();
                            if(role.equals("admin")){
                                bar=new AdminBar();                               
                            }
                            else{
                                bar=new UserBar(); 
                            }
                            root.getChildren().add(bar);
                        }
                    }
                }
                catch(Exception ex){
                    System.out.println(ex);
                }
            }
        });
        
        Pane registerPane=new Pane();
        Label backToLogin = new Label("<- back ");
        newAccountL.setOnMouseClicked(new EventHandler<MouseEvent>() { 
            @Override
            public void handle(MouseEvent event) {
                root.getChildren().remove(loginPane);
                root.getChildren().add(backToLogin);
                root.getChildren().add(registerPane);
            }
        });
        
        //add components to login pane
        loginPane.getChildren().add(emailText);
        loginPane.getChildren().add(email);
        loginPane.getChildren().add(passwordText);
        loginPane.getChildren().add(password);
        loginPane.getChildren().add(emailErrorText);
        loginPane.getChildren().add(passwordErrorText);
        loginPane.getChildren().add(login);
        loginPane.getChildren().add(newAccountL);
        
        //Right side pane
        
        rightPane.setLayoutX(400);
        rightPane.setLayoutY(120);
        ImageView imageV=new ImageView("logo.jpg");
        imageV.setFitHeight(300);
        imageV.setFitWidth(350);
        rightPane.getChildren().add(imageV);
               
        //add to main screen
        root.getChildren().add(rightPane);
        root.getChildren().add(loginPane);   
        root.setStyle("-fx-background-color: white;");
        Scene scene = new Scene(root, 800, 600);
        
        //-----------------------------------------------------Register screen------------------------------------------------
        //Register pane       
        registerPane.setLayoutX(30);
        registerPane.setLayoutY(90);
        
        Text nameText=new Text("Name: ");
        TextField name=new TextField();
        Text emailText2=new Text("Email: ");
        TextField email2=new TextField();
        Text passwordText2=new Text("Password: ");
        Text confirmPasswordText=new Text("Confirm : ");
        PasswordField password2=new PasswordField();
        PasswordField confirmPassword=new PasswordField();
        Text nameErrorText2=new Text("");
        Text emailErrorText2=new Text("");
        Text passwordErrorText2=new Text("");
        Button register = new Button(" Register ");
        //layout X
        nameText.setLayoutX(0);
        emailText2.setLayoutX(0);
        passwordText2.setLayoutX(0);
        nameErrorText2.setLayoutX(0);
        emailErrorText2.setLayoutX(0);
        passwordErrorText2.setLayoutX(0);
        name.setLayoutX(100);
        email2.setLayoutX(100);
        password2.setLayoutX(100);
        confirmPasswordText.setLayoutX(0);
        confirmPassword.setLayoutX(100);
        register.setLayoutX(175);
        backToLogin.setLayoutX(30);
        
        //layout Y
        nameText.setLayoutY(20);
        emailText2.setLayoutY(110);
        passwordText2.setLayoutY(200);
        confirmPasswordText.setLayoutY(290);
        nameErrorText2.setLayoutY(70);
        emailErrorText2.setLayoutY(160);
        passwordErrorText2.setLayoutY(250);
        name.setLayoutY(0);
        email2.setLayoutY(90);
        password2.setLayoutY(180);
        confirmPassword.setLayoutY(270);
        register.setLayoutY(350);
        backToLogin.setLayoutY(20);
        
        //font
        nameText.setFont(font);
        name.setFont(font);
        emailText2.setFont(font);
        passwordText2.setFont(font);
        email2.setFont(font);
        password2.setFont(font);
        confirmPasswordText.setFont(font);
        confirmPassword.setFont(font);
        register.setFont(font);
        backToLogin.setFont(font);
        
        //color
        login.setStyle("-fx-background-color: #A987A8");
        register.setStyle("-fx-background-color: #A987A8");
        backToLogin.setTextFill(Color.PURPLE);
        emailErrorText2.setFill(Color.RED);
        passwordErrorText2.setFill(Color.RED);
        nameErrorText2.setFill(Color.RED);
        
        register.setOnAction(new EventHandler<ActionEvent>() {           
            @Override
            public void handle(ActionEvent event) {
                //reset errors texts
                nameErrorText2.setText("");
                emailErrorText2.setText("");
                passwordErrorText2.setText("");
                register.setDisable(true);
                String n=name.getText();
                String e=email2.getText();
                String p=password2.getText();
                String cp=confirmPassword.getText();
                if(p.equals(cp)){
                    BackEnd backend=new BackEnd();
                    try {
                        Map<String,String> response=backend.register(n, e, p);
                        for(String key:response.keySet()){
                            if(key.equals("name")){
                                nameErrorText2.setText(response.get(key));
                            }
                            else if(key.equals("email")){
                                emailErrorText2.setText(response.get(key));
                            }
                            else if(key.equals("password")){
                                passwordErrorText2.setText(response.get(key));
                            }
                            else if(key.equals("no")){
                                Alert alert=new Alert(Alert.AlertType.ERROR);
                                alert.setHeaderText("Register failed!!");
                                alert.setContentText(response.get(key));
                                alert.show();
                            }
                            else if(key.equals("ok")){
                                Alert alert=new Alert(Alert.AlertType.ERROR);
                                alert.setHeaderText("Register successfull");
                                alert.setContentText(response.get(key));
                                alert.show();
                                name.setText("");
                                email2.setText("");
                                password2.setText("");
                                confirmPassword.setText("");
                            }
                        }
                    } catch (Exception ex) {
                        System.out.println("can not do register");
                    }
                }
                else{
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Register failed!!");
                    alert.setContentText("password and confirm password not the same");
                    alert.show();
                }
                register.setDisable(false);
            }
        });
        
        backToLogin.setOnMouseClicked(new EventHandler<MouseEvent>() { 
            @Override
            public void handle(MouseEvent event) {
                root.getChildren().remove(registerPane);
                root.getChildren().remove(backToLogin);
                root.getChildren().add(loginPane);
            }
        });
        
        //add components to login pane
        registerPane.getChildren().add(nameText);
        registerPane.getChildren().add(name);
        registerPane.getChildren().add(emailText2);
        registerPane.getChildren().add(email2);
        registerPane.getChildren().add(passwordText2);
        registerPane.getChildren().add(password2);
        registerPane.getChildren().add(emailErrorText2);
        registerPane.getChildren().add(passwordErrorText2);
        registerPane.getChildren().add(nameErrorText2);
        registerPane.getChildren().add(confirmPasswordText);
        registerPane.getChildren().add(confirmPassword);
        registerPane.getChildren().add(register);
        registerPane.getChildren().add(backToLogin);
        
        primaryStage.setTitle("My Clinic");
        primaryStage.setScene(scene);
        
        primaryStage.show();
         
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        launch(args);
//            BackEnd backend=new BackEnd();
//           // backend.getRole();
//            System.out.println(backend.getRole().get("role"));
////            Map<String,String> response=backend.update_account("10", "300");
////            for(String key:response.keySet()){
////                System.out.println(key+" "+response.get(key));
////            }
            System.exit(0);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restclient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BackEnd {
    
    static Map<String,String> responseData;
    
    public static void storeData(String data,String fileName) throws FileNotFoundException{
        PrintWriter bw=new PrintWriter(new File("storage/"+fileName+".txt"));
        bw.write(data);
        bw.close();
    }
    
    public static String readData(String fileName) throws FileNotFoundException{
        Scanner reader=new Scanner(new File("storage/"+fileName+".txt"));
        String data="";
        if(reader.hasNext()){
            data=reader.next();
        }
        return data;
    }
    
    public boolean removeToken(){
        File file=new File("storage/token.txt");
        if(file.exists()){
            file.delete();
            return true;
        }
        return false;
    }
    
    public Map stringToHashMap(String response){
        Map<String,String> data=new HashMap<>();
        System.out.println(response);
        if(response.startsWith("{")&& response.endsWith("}")){
            //remove '{' and '}' from response
            response=response.replace("{", "");
            response=response.replace("}", "");
            //remove " from response
            response=response.replace("\"", "");
            String [] data2=response.split(",");
            String prev_key="";
            int index=0;
            for(String s:data2){
                String [] data3=s.split(":");
                String key=data3[0];
                String value=data3[1];
                if(prev_key.equals(key)){
                    key+=index;
                    index++;
                }
                //actual data from response
                if(!key.contains("created_at")&&!key.contains("updated_at"))
                    data.put(key, value);
                prev_key=key;
            } 
        }
        return data;
    }
    
    public Map stringArrayToHashMap(String response){
        Map<String,String> data=new HashMap<>();
        System.out.println(response);
        String [] responseArray;       
        if(response.startsWith("[")&& response.endsWith("]")){
            response=response.replace("[", "");
            response=response.replace("]", "");
            responseArray=response.split("}");
            int index=0;
            for(String r:responseArray){
               // System.out.println("r= "+r);
                r=r.replace(",{", "");
                r=r.replace("{", "");
                r=r.replace("\"", "");
                //System.out.println("r= "+r);
                String [] data2=r.split(",");              
                for(String s:data2){
                    String [] data3=s.split(":");
                    String key=index+"";
                    String value=data3[1];                   
                    //actual data from response
                    if(!data3[0].contains("created_at")&&!data3[0].contains("updated_at")){
                        data.put(key, value.replace("\\/", ":"));
                        index++;
                    }
                } 
            }           
        }
        return data;
    }
    
    public void getData() throws Exception{
        HttpURLConnection connection=(HttpURLConnection) new URL("http://127.0.0.1:8000/api/").openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer "+"token");
        int responseCode=connection.getResponseCode();
        System.out.println("code: "+responseCode);
    }
    
    //--------------------------------------------------Authentication----------------------------------------------------------
    
    public Map login(String email,String password)throws Exception{
        HttpURLConnection connection=(HttpURLConnection) new URL("http://127.0.0.1:8000/api/login").openConnection();
        connection.setRequestMethod("POST");        
        //data to send
        String postData="email="+email;
        postData+="&password="+password;
        //send data
        connection.setDoOutput(true);
        OutputStreamWriter wr=new OutputStreamWriter(connection.getOutputStream());
        wr.write(postData);
        wr.flush();
        //get response
        int responseCode=connection.getResponseCode();
        System.out.println(responseCode);
        responseData=new HashMap<>();
        if(responseCode==200||responseCode==201){
            String response="";
            Scanner scanner=new Scanner(connection.getInputStream());
            while(scanner.hasNextLine()){
                response+=scanner.nextLine();
            }
            scanner.close();
            if(response.contains("{")&&response.contains(":")&&response.contains("}")){
                responseData=stringToHashMap(response);
            }
            else if(response.equals("no")){
                responseData.put("no", "Email or password incorrect");
            }
            else{
                //store api token in file token.txt
                storeData(response, "token"); 
                responseData.put("token",response);
            }
            System.out.println("response is: "+response);
        }
        return responseData;
    }
    
    public Map logout()throws Exception{
        HttpURLConnection connection=(HttpURLConnection) new URL("http://127.0.0.1:8000/api/logout").openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Accept", "application/json");
        String token=readData("token");
        connection.setRequestProperty("Authorization", "Bearer "+token);
        System.out.println(token);
        //get response
        int responseCode=connection.getResponseCode();
        System.out.println(responseCode);
        responseData=new HashMap<>();
        if(responseCode==200){
            String response="";
            Scanner scanner=new Scanner(connection.getInputStream());
            while(scanner.hasNextLine()){
                response+=scanner.nextLine();
            }
            if(response.equals("ok")){
                responseData.put("ok", "logout successfully");
            }
            else if(response.equals("no")){
                responseData.put("ok", "Password can not update");
            }
            scanner.close();
            System.out.println("response is: "+response);
        }
        return responseData;
    }
    
    public Map updatePassword(String new_password)throws Exception{
        HttpURLConnection connection=(HttpURLConnection) new URL("http://127.0.0.1:8000/api/updatePassword").openConnection();
        connection.setRequestMethod("POST");
        String token=readData("token");
        System.out.println(token);
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer "+token);
        //data to send
        String postData="password2="+new_password;
        //send data
        connection.setDoOutput(true);
        OutputStreamWriter wr=new OutputStreamWriter(connection.getOutputStream());
        wr.write(postData);
        wr.flush();
        //get response
        int responseCode=connection.getResponseCode();
        System.out.println(responseCode);
        responseData=new HashMap<>();
        if(responseCode==200){
            String response="";
            Scanner scanner=new Scanner(connection.getInputStream());
            while(scanner.hasNextLine()){
                response+=scanner.nextLine();
            }
            if(response.equals("ok")){
                responseData.put("ok", "Password updated successfully");
            }
            else if(response.equals("no")){
                responseData.put("ok", "Password can not update");
            }
            scanner.close();
            System.out.println("response is: "+response);
        }
        return responseData;
    }
    
    public Map updateEmail(String email)throws Exception{
        HttpURLConnection connection=(HttpURLConnection) new URL("http://127.0.0.1:8000/api/updateEmail").openConnection();
        connection.setRequestMethod("POST");
        String token=readData("token");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer "+token);
        //data to send
        String postData="email2="+email;
        //send data
        connection.setDoOutput(true);
        OutputStreamWriter wr=new OutputStreamWriter(connection.getOutputStream());
        wr.write(postData);
        wr.flush();
        //get response
        int responseCode=connection.getResponseCode();
        System.out.println(responseCode);
        responseData=new HashMap<>();
        if(responseCode==200){
            String response="";
            Scanner scanner=new Scanner(connection.getInputStream());
            while(scanner.hasNextLine()){
                response+=scanner.nextLine();
            }
            if(response.equals("ok")){
                responseData.put("ok", "Email updated successfully");
            }
            else if(response.equals("no")){
                responseData.put("ok", "Email can not update");
            }
            scanner.close();
            System.out.println("response is: "+response);
        }
        return responseData;
    }
    
    public Map register(String name,String email,String password)throws Exception{
        HttpURLConnection connection=(HttpURLConnection) new URL("http://127.0.0.1:8000/api/register").openConnection();
        connection.setRequestMethod("POST");
        //data to send
        String postData="name="+name;
        postData+="&email="+email;
        postData+="&password="+password;
        //send data
        connection.setDoOutput(true);
        OutputStreamWriter wr=new OutputStreamWriter(connection.getOutputStream());
        wr.write(postData);
        wr.flush();
        //get response
        int responseCode=connection.getResponseCode();
        System.out.println(responseCode);
        responseData=new HashMap<>();
        if(responseCode==200){
            String response="";
            Scanner scanner=new Scanner(connection.getInputStream());
            while(scanner.hasNextLine()){
                response+=scanner.nextLine();
            }
            if(response.equals("ok")){
                String userData=name+","+email+","+password;
                storeData(userData, "userData");
            }
            else if(response.equals("no")){
                responseData.put("no", "there is an error");
            }
            else{
                responseData=stringToHashMap(response);               
            }
            scanner.close();
            System.out.println("response is: "+response);
        }
        return responseData;
    }
    
    public Map getRole() throws Exception{
        HttpURLConnection connection=(HttpURLConnection) new URL("http://127.0.0.1:8000/api/My_role").openConnection();
        connection.setRequestMethod("GET");
        String token=readData("token");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer "+token);    
        int responseCode=connection.getResponseCode();
        System.out.println("code: "+responseCode);
        responseData=new HashMap<>();
        String response="";
        if(responseCode==200){
            Scanner scanner=new Scanner(connection.getInputStream());
            if(scanner.hasNextLine()){
                response+=scanner.nextLine();
            }
            scanner.close();
            if(response.length()>6)
                responseData=stringToHashMap(response);    
            else
                responseData.put("empty", "");
        }
        else {           
            responseData.put("Response code", responseCode+"");
        }
        return responseData;
    }
    
    //--------------------------------------------------Appointments----------------------------------------------------------
    
    public Map create_appointment(String date,String time)throws Exception{
        HttpURLConnection connection=(HttpURLConnection) new URL("http://127.0.0.1:8000/api/create_appointment").openConnection();
        connection.setRequestMethod("POST");
        //data to send
        String postData="date="+date;
        postData+="&time="+time;
        //auth by token
        String token=readData("token");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer "+token);
        //send data
        connection.setDoOutput(true);
        OutputStreamWriter wr=new OutputStreamWriter(connection.getOutputStream());
        wr.write(postData);
        wr.flush();
        //get response
        int responseCode=connection.getResponseCode();
        System.out.println(responseCode);
        responseData=new HashMap<>();
        if(responseCode==200){
            String response="";
            Scanner scanner=new Scanner(connection.getInputStream());
            while(scanner.hasNextLine()){
                response+=scanner.nextLine();
            }
            if(response.contains("{")&&response.contains(":")&&response.contains("}")){
                responseData=stringToHashMap(response);
            }
            else if(response.equals("ok")){
                responseData.put("ok", "Appointments created successfully");
            }
            scanner.close();
            System.out.println("response is: "+response);
        }
        else if(responseCode==403){
            responseData.put("403", "You don't have permission to create appointments");
        }
        return responseData;
    }
    
    //delete
    public Map delete_appointment(String date,String time)throws Exception{
        HttpURLConnection connection=(HttpURLConnection) new URL("http://127.0.0.1:8000/api/delete_appointment").openConnection();
        connection.setRequestMethod("POST");
        //data to send
        String postData="date="+date;
        postData+="&time="+time;
        String token=readData("token");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer "+token);
        //send data
        connection.setDoOutput(true);
        OutputStreamWriter wr=new OutputStreamWriter(connection.getOutputStream());
        wr.write(postData);
        wr.flush();
        //get response
        int responseCode=connection.getResponseCode();
        responseData=new HashMap<>();
        System.out.println(responseCode);
        if(responseCode==200){
            String response="";
            Scanner scanner=new Scanner(connection.getInputStream());
            if(scanner.hasNextLine()){
                response+=scanner.nextLine();
            }
            scanner.close();
            if(response.equals("ok")){
                responseData.put("ok", "Appointment deleted successfully");
            }
            else{
                responseData.put("no", response);
            }
            System.out.println("response is: "+response);
        }
        else{
            responseData.put("Response code", responseCode+"");
        }
        return responseData;
    }
    
    //update
    public Map update_appointment(String date,String time,String update)throws Exception{
        HttpURLConnection connection=(HttpURLConnection) new URL("http://127.0.0.1:8000/api/update_appointment").openConnection();
        connection.setRequestMethod("POST");
        //data to send
        String postData="date="+date;
        postData+="&time="+time;
        postData+="&updatedTime="+update;
        String token=readData("token");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer "+token);
        //send data
        connection.setDoOutput(true);
        OutputStreamWriter wr=new OutputStreamWriter(connection.getOutputStream());
        wr.write(postData);
        wr.flush();
        //get response
        int responseCode=connection.getResponseCode();
        System.out.println(responseCode);
        responseData=new HashMap<>();
        if(responseCode==200){
            String response="";
            Scanner scanner=new Scanner(connection.getInputStream());
            if(scanner.hasNextLine()){
                response+=scanner.nextLine();
            }
            scanner.close();
            if(response.equals("ok")){
                responseData.put("ok", "Appointment updated successfully");
            }
            System.out.println("response is: "+response);
        }
        else{
            responseData.put("Response code", responseCode+"");
        }
        return responseData;
    }
    
    //view appointments
    public Map getAppointments() throws Exception{
        HttpURLConnection connection=(HttpURLConnection) new URL("http://127.0.0.1:8000/api/view_appointments").openConnection();
        connection.setRequestMethod("GET");
        String token=readData("token");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer "+token);    
        int responseCode=connection.getResponseCode();
        System.out.println("code: "+responseCode);
        responseData=new HashMap<>();
        String response="";
        if(responseCode==200){
            Scanner scanner=new Scanner(connection.getInputStream());
            if(scanner.hasNextLine()){
                response+=scanner.nextLine();
            }
            scanner.close();
            if(response.length()>6)
                responseData=stringArrayToHashMap(response);    
            else
                responseData.put("empty", "");
        }
        else {           
            responseData.put("Response code", responseCode+"");
        }
        return responseData;
    }
    
    //view appointments
    public Map getBookedAppointments() throws Exception{
        HttpURLConnection connection=(HttpURLConnection) new URL("http://127.0.0.1:8000/api/view_all_booked_appointments").openConnection();
        connection.setRequestMethod("GET");
        String token=readData("token");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer "+token);    
        int responseCode=connection.getResponseCode();
        System.out.println("code: "+responseCode);
        responseData=new HashMap<>();
        String response="";
        if(responseCode==200){
            Scanner scanner=new Scanner(connection.getInputStream());
            if(scanner.hasNextLine()){
                response+=scanner.nextLine();
            }
            scanner.close();
            if(response.length()>6)
                responseData=stringArrayToHashMap(response);    
            else
                responseData.put("empty", "");
        }
        else {           
            responseData.put("Response code", responseCode+"");
        }
        return responseData;
    }
    
    //book new appointment
    public Map book_appointment(String date,String time)throws Exception{
        HttpURLConnection connection=(HttpURLConnection) new URL("http://127.0.0.1:8000/api/book_appointment").openConnection();
        connection.setRequestMethod("POST");
        //data to send
        String postData="date="+date;
        postData+="&time="+time;
        //auth by token
        String token=readData("token");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer "+token);
        //send data
        connection.setDoOutput(true);
        OutputStreamWriter wr=new OutputStreamWriter(connection.getOutputStream());
        wr.write(postData);
        wr.flush();
        //get response
        int responseCode=connection.getResponseCode();
        System.out.println(responseCode);
        responseData=new HashMap<>();
        if(responseCode==200){
            String response="";
            Scanner scanner=new Scanner(connection.getInputStream());
            while(scanner.hasNextLine()){
                response+=scanner.nextLine();
            }
            if(response.contains("{")&&response.contains(":")&&response.contains("}")){
                responseData=stringToHashMap(response);
            }
            else if(response.equals("ok")){
                responseData.put("ok", "Appointments created successfully");
            }
            scanner.close();
            System.out.println("response is: "+response);
        }
        else if(responseCode==403){
            responseData.put("403", "You don't have permission to create appointments");
        }
        return responseData;
    }
    
    //un book booked appointment
    public Map unbook_appointment(String date,String time)throws Exception{
        HttpURLConnection connection=(HttpURLConnection) new URL("http://127.0.0.1:8000/api/unbook_appointment").openConnection();
        connection.setRequestMethod("POST");
        //data to send
        String postData="date="+date;
        postData+="&time="+time;
        String token=readData("token");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer "+token);
        //send data
        connection.setDoOutput(true);
        OutputStreamWriter wr=new OutputStreamWriter(connection.getOutputStream());
        wr.write(postData);
        wr.flush();
        //get response
        int responseCode=connection.getResponseCode();
        responseData=new HashMap<>();
        System.out.println(responseCode);
        if(responseCode==200){
            String response="";
            Scanner scanner=new Scanner(connection.getInputStream());
            if(scanner.hasNextLine()){
                response+=scanner.nextLine();
            }
            scanner.close();
            if(response.equals("ok")){
                responseData.put("ok", "Appointment deleted successfully");
            }
            else{
                responseData.put("no", response);
            }
            System.out.println("response is: "+response);
        }
        else{
            responseData.put("Response code", responseCode+"");
        }
        return responseData;
    }
    
    //view my appointment(booked appointment)
    public Map getMyAppointments() throws Exception{
        HttpURLConnection connection=(HttpURLConnection) new URL("http://127.0.0.1:8000/api/view_booked_appointments").openConnection();
        connection.setRequestMethod("GET");
        String token=readData("token");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer "+token);    
        int responseCode=connection.getResponseCode();
        System.out.println("code: "+responseCode);
        responseData=new HashMap<>();
        String response="";
        if(responseCode==200){
            Scanner scanner=new Scanner(connection.getInputStream());
            if(scanner.hasNextLine()){
                response+=scanner.nextLine();
            }
            scanner.close();
            if(response.length()>6)
                responseData=stringArrayToHashMap(response);    
            else
                responseData.put("empty", "");
        }
        else {           
            responseData.put("Response code", responseCode+"");
        }
        return responseData;
    }
    
    //--------------------------------------------------Records----------------------------------------------------------
    
    public Map create_record(String name,String address,String phone,String ill,String status,String user_id)throws Exception{
        HttpURLConnection connection=(HttpURLConnection) new URL("http://127.0.0.1:8000/api/create_record").openConnection();
        connection.setRequestMethod("POST");
        //data to send
        String postData="name="+name;
        postData+="&address="+address;
        postData+="&phone="+phone;
        postData+="&ill="+ill;
        postData+="&status="+status;
        postData+="&user_id="+user_id;
        //auth by token
        String token=readData("token");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer "+token);
        //send data
        connection.setDoOutput(true);
        OutputStreamWriter wr=new OutputStreamWriter(connection.getOutputStream());
        wr.write(postData);
        wr.flush();
        //get response
        int responseCode=connection.getResponseCode();
        System.out.println(responseCode);
        responseData=new HashMap<>();
        if(responseCode==200){
            String response="";
            Scanner scanner=new Scanner(connection.getInputStream());
            while(scanner.hasNextLine()){
                response+=scanner.nextLine();
            }
            if(response.contains("{")&&response.contains(":")&&response.contains("}")){
                responseData=stringToHashMap(response);
            }
            else if(response.equals("ok")){
                responseData.put("ok", "Record created successfully");
            }
            scanner.close();
            System.out.println("response is: "+response);
        }
        else if(responseCode==403){
            responseData.put("403", "You don't have permission to create record");
        }
        return responseData;
    }
    
    //update patient status (record status)
    public Map update_status(String id,String status)throws Exception{
        HttpURLConnection connection=(HttpURLConnection) new URL("http://127.0.0.1:8000/api/update_record").openConnection();
        connection.setRequestMethod("POST");
        //data to send
        String postData="user_id="+id;
        postData+="&status="+status;
        String token=readData("token");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer "+token);
        //send data
        connection.setDoOutput(true);
        OutputStreamWriter wr=new OutputStreamWriter(connection.getOutputStream());
        wr.write(postData);
        wr.flush();
        //get response
        int responseCode=connection.getResponseCode();
        System.out.println(responseCode);
        responseData=new HashMap<>();
        if(responseCode==200){
            String response="";
            Scanner scanner=new Scanner(connection.getInputStream());
            if(scanner.hasNextLine()){
                response+=scanner.nextLine();
            }
            scanner.close();
            if(response.equals("ok")){
                responseData.put("ok", "Record updated successfully");
            }
            System.out.println("response is: "+response);
        }
        else{
            responseData.put("Response code", responseCode+"");
        }
        return responseData;
    }
    
    //delete record
    public Map delete_record(String id)throws Exception{
        HttpURLConnection connection=(HttpURLConnection) new URL("http://127.0.0.1:8000/api/delete_record").openConnection();
        connection.setRequestMethod("POST");
        //data to send
        String postData="user_id="+id;
        String token=readData("token");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer "+token);
        //send data
        connection.setDoOutput(true);
        OutputStreamWriter wr=new OutputStreamWriter(connection.getOutputStream());
        wr.write(postData);
        wr.flush();
        //get response
        int responseCode=connection.getResponseCode();
        responseData=new HashMap<>();
        System.out.println(responseCode);
        if(responseCode==200){
            String response="";
            Scanner scanner=new Scanner(connection.getInputStream());
            if(scanner.hasNextLine()){
                response+=scanner.nextLine();
            }
            scanner.close();
            if(response.equals("ok")){
                responseData.put("ok", "Record deleted successfully");
            }
            else{
                responseData.put("no", response);
            }
            System.out.println("response is: "+response);
        }
        else{
            responseData.put("Response code", responseCode+"");
        }
        return responseData;
    }
    
    //view my record
    public Map getMyRecord() throws Exception{
        HttpURLConnection connection=(HttpURLConnection) new URL("http://127.0.0.1:8000/api/view_patient_record").openConnection();
        connection.setRequestMethod("GET");
        String token=readData("token");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer "+token);    
        int responseCode=connection.getResponseCode();
        System.out.println("code: "+responseCode);
        responseData=new HashMap<>();
        String response="";
        if(responseCode==200){
            Scanner scanner=new Scanner(connection.getInputStream());
            if(scanner.hasNextLine()){
                response+=scanner.nextLine();
            }
            scanner.close();
            if(response.length()>6){
                response=response.replace("{", "[{");
                response=response.replace("}", "}]");
                responseData=stringArrayToHashMap(response); 
            }   
            else
                responseData.put("empty", "");
        }
        else {           
            responseData.put("Response code", responseCode+"");
        }
        return responseData;
    }
    
    //view all records
    public Map getAllRecords() throws Exception{
        HttpURLConnection connection=(HttpURLConnection) new URL("http://127.0.0.1:8000/api/view_all_records").openConnection();
        connection.setRequestMethod("GET");
        String token=readData("token");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer "+token);    
        int responseCode=connection.getResponseCode();
        System.out.println("code: "+responseCode);
        responseData=new HashMap<>();
        String response="";
        if(responseCode==200){
            Scanner scanner=new Scanner(connection.getInputStream());
            if(scanner.hasNextLine()){
                response+=scanner.nextLine();
            }
            scanner.close();
            if(response.length()>6)
                responseData=stringArrayToHashMap(response);    
            else
                responseData.put("empty", "");
        }
        else {           
            responseData.put("Response code", responseCode+"");
        }
        return responseData;
    }
    
    //search for a record by patient name
    public Map searchRecord(String name) throws Exception{
        HttpURLConnection connection=(HttpURLConnection) new URL("http://127.0.0.1:8000/api/search_record").openConnection();
        connection.setRequestMethod("POST");
        String token=readData("token");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer "+token);   
        //data to send
        String postData="name="+name;
        //send data
        connection.setDoOutput(true);
        OutputStreamWriter wr=new OutputStreamWriter(connection.getOutputStream());
        wr.write(postData);
        wr.flush();
        int responseCode=connection.getResponseCode();
        System.out.println("code: "+responseCode);
        responseData=new HashMap<>();
        String response="";
        if(responseCode==200){
            Scanner scanner=new Scanner(connection.getInputStream());
            if(scanner.hasNextLine()){
                response+=scanner.nextLine();
            }
            scanner.close();
            if(response.length()>6){
                response=response.replace("{", "[{");
                response=response.replace("}", "}]");
                responseData=stringArrayToHashMap(response);
            }                   
            else
                responseData.put("empty", "");
        }
        else {           
            responseData.put("Response code", responseCode+"");
        }
        return responseData;
    }
    
    //------------------------------------------------Payments-------------------------------------------------------
    
    public Map create_account(String total,String receved,String user_id)throws Exception{
        HttpURLConnection connection=(HttpURLConnection) new URL("http://127.0.0.1:8000/api/create_account").openConnection();
        connection.setRequestMethod("POST");
        //data to send
        String postData="total="+total;
        postData+="&receved="+receved;
        postData+="&user_id="+user_id;
        //auth by token
        String token=readData("token");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer "+token);
        //send data
        connection.setDoOutput(true);
        OutputStreamWriter wr=new OutputStreamWriter(connection.getOutputStream());
        wr.write(postData);
        wr.flush();
        //get response
        int responseCode=connection.getResponseCode();
        System.out.println(responseCode);
        responseData=new HashMap<>();
        if(responseCode==200){
            String response="";
            Scanner scanner=new Scanner(connection.getInputStream());
            while(scanner.hasNextLine()){
                response+=scanner.nextLine();
            }
            if(response.contains("{")&&response.contains(":")&&response.contains("}")){
                responseData=stringToHashMap(response);
            }
            else if(response.equals("ok")){
                responseData.put("ok", "Record created successfully");
            }
            scanner.close();
            System.out.println("response is: "+response);
        }
        else if(responseCode==403){
            responseData.put("403", "You don't have permission to create record");
        }
        return responseData;
    }
    
    public Map update_account(String id,String recevedNow)throws Exception{
        HttpURLConnection connection=(HttpURLConnection) new URL("http://127.0.0.1:8000/api/update_account").openConnection();
        connection.setRequestMethod("POST");
        //data to send
        System.out.println("id: "+id+" "+"rec: "+recevedNow);
        String postData="user_id="+id;
        postData+="&recevedNow="+recevedNow;
        String token=readData("token");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer "+token);
        //send data
        connection.setDoOutput(true);
        OutputStreamWriter wr=new OutputStreamWriter(connection.getOutputStream());
        wr.write(postData);
        wr.flush();
        //get response
        int responseCode=connection.getResponseCode();
        System.out.println(responseCode);
        responseData=new HashMap<>();
        if(responseCode==200){
            String response="";
            Scanner scanner=new Scanner(connection.getInputStream());
            if(scanner.hasNextLine()){
                response+=scanner.nextLine();
            }
            scanner.close();
            if(response.equals("ok")){
                responseData.put("ok", "Account updated successfully");
            }
            System.out.println("response is: "+response);
        }
        else{
            responseData.put("Response code", responseCode+"");
        }
        return responseData;
    }
    
    public Map delete_account(String id)throws Exception{
        HttpURLConnection connection=(HttpURLConnection) new URL("http://127.0.0.1:8000/api/delete_account").openConnection();
        connection.setRequestMethod("POST");
        //data to send
        String postData="user_id="+id;
        String token=readData("token");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer "+token);
        //send data
        connection.setDoOutput(true);
        OutputStreamWriter wr=new OutputStreamWriter(connection.getOutputStream());
        wr.write(postData);
        wr.flush();
        //get response
        int responseCode=connection.getResponseCode();
        responseData=new HashMap<>();
        System.out.println(responseCode);
        if(responseCode==200){
            String response="";
            Scanner scanner=new Scanner(connection.getInputStream());
            if(scanner.hasNextLine()){
                response+=scanner.nextLine();
            }
            scanner.close();
            if(response.equals("ok")){
                responseData.put("ok", "Record deleted successfully");
            }
            else{
                responseData.put("no", response);
            }
            System.out.println("response is: "+response);
        }
        else{
            responseData.put("Response code", responseCode+"");
        }
        return responseData;
    }
    
    public Map getMyAccount() throws Exception{
        HttpURLConnection connection=(HttpURLConnection) new URL("http://127.0.0.1:8000/api/view_my_account").openConnection();
        connection.setRequestMethod("GET");
        String token=readData("token");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer "+token);    
        int responseCode=connection.getResponseCode();
        System.out.println("code: "+responseCode);
        responseData=new HashMap<>();
        String response="";
        if(responseCode==200){
            Scanner scanner=new Scanner(connection.getInputStream());
            if(scanner.hasNextLine()){
                response+=scanner.nextLine();
            }
            scanner.close();
            if(response.length()>6){
                response=response.replace("{", "[{");
                response=response.replace("}", "}]");
                responseData=stringArrayToHashMap(response); 
            }       
            else
                responseData.put("empty", "");
        }
        else {           
            responseData.put("Response code", responseCode+"");
        }
        return responseData;
    }
    
    public Map getOwingPatients() throws Exception{
        HttpURLConnection connection=(HttpURLConnection) new URL("http://127.0.0.1:8000/api/owing_users").openConnection();
        connection.setRequestMethod("GET");
        String token=readData("token");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer "+token);    
        int responseCode=connection.getResponseCode();
        System.out.println("code: "+responseCode);
        responseData=new HashMap<>();
        String response="";
        if(responseCode==200){
            Scanner scanner=new Scanner(connection.getInputStream());
            if(scanner.hasNextLine()){
                response+=scanner.nextLine();
            }
            scanner.close();
            if(response.length()>6)
                responseData=stringArrayToHashMap(response);    
            else
                responseData.put("empty", "");
        }
        else {           
            responseData.put("Response code", responseCode+"");
        }
        return responseData;
    }
    
    public Map get_all_payments_accounts() throws Exception{
        HttpURLConnection connection=(HttpURLConnection) new URL("http://127.0.0.1:8000/api/view_all_payments").openConnection();
        connection.setRequestMethod("GET");
        String token=readData("token");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer "+token);    
        int responseCode=connection.getResponseCode();
        System.out.println("code: "+responseCode);
        responseData=new HashMap<>();
        String response="";
        if(responseCode==200){
            Scanner scanner=new Scanner(connection.getInputStream());
            if(scanner.hasNextLine()){
                response+=scanner.nextLine();
            }
            scanner.close();
            if(response.length()>6)
                responseData=stringArrayToHashMap(response);    
            else
                responseData.put("empty", "");
        }
        else {           
            responseData.put("Response code", responseCode+"");
        }
        return responseData;
    }
    
    public Map searchPayment(String user_id) throws Exception{
        HttpURLConnection connection=(HttpURLConnection) new URL("http://127.0.0.1:8000/api/view_account").openConnection();
        connection.setRequestMethod("POST");
        String token=readData("token");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer "+token);   
        //data to send
        String postData="user_id="+user_id;
        //send data
        connection.setDoOutput(true);
        OutputStreamWriter wr=new OutputStreamWriter(connection.getOutputStream());
        wr.write(postData);
        wr.flush();
        int responseCode=connection.getResponseCode();
        System.out.println("code: "+responseCode);
        responseData=new HashMap<>();
        String response="";
        if(responseCode==200){
            Scanner scanner=new Scanner(connection.getInputStream());
            if(scanner.hasNextLine()){
                response+=scanner.nextLine();
            }
            scanner.close();
            if(response.length()>6){
                response=response.replace("{", "[{");
                response=response.replace("}", "}]");
                responseData=stringArrayToHashMap(response);
            }                   
            else
                responseData.put("empty", "");
        }
        else {           
            responseData.put("Response code", responseCode+"");
        }
        return responseData;
    }
}

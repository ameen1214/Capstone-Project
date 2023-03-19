<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Available_appointment;
use App\Models\appointment;
use Validator;
use Illuminate\Support\Facades\Gate;


class AppointmentController extends Controller
{
	//Available appointments----------------------------------------

	//create
    public function create_appointment(Request $request){
        if(!Gate::allows('create_appointment')){
            return "403";
        }
    	$validator=Validator::make($request->all(),[
    		'date' => 'required|max:191|string',
    		'time' => 'required|max:191|string'
    	]);
    	if ($validator->fails()) {
    		return $validator->errors();
    	}
    	$a=new	Available_appointment;
    	$a->date=$request->date;
    	$a->time=$request->time;
    	$a->save();
    	return "ok";   		
    }

    //update
    public function update_appointment(Request $request){
        if(!Gate::allows('update_appointment')){
            abort(403);
        }
    	$validator=Validator::make($request->all(),[
    		'date' => 'required|max:191|string',
    		'time' => 'required|max:191|string',
            'updatedTime' => 'required|max:191|string'
    	]);
    	if ($validator->fails()) {
    		return $validator->errors();
    	}
    	Available_appointment::where('date',$request->input('date'))->where('time',$request->input('time'))->update(['time'=>$request->input('updatedTime')]);
    	return "ok";   		
    }

    //delete
    public function delete_appointment(Request $request){
        if(!Gate::allows('delete_appointment')){
            abort(403);
        }
    	$a=Available_appointment::where('date',$request->input('date'))->where('time',$request->input('time'));
    	$d=$a->delete();
    	$deleted="could not delete";
    	if($d==1){
    		$deleted="ok";
    	}
    	return $deleted;
    }

    //view
    public function view_appointments(){
    	$a=Available_appointment::all();
    	foreach ($a as $b) {
    		return $b;
    	}	
    }

    //---------------------------------------------------------------------

    //book an appointment
    public function book(Request $request){
        $validator=Validator::make($request->all(),[
            'date' => 'required|max:191|string',
            'time' => 'required|max:191|string'
        ]);
        if ($validator->fails()) {
            return $validator->errors();
        }
        $user=auth()->user();
        appointment::create([
            'time'=>$request->time,
            'date'=>$request->date,
            'user_id'=>$user->id
        ]);
        return "ok";
    }

    //unbook appointment
    public function unbook(Request $request){
        $validator=Validator::make($request->all(),[
            'date' => 'required|max:191|string',
            'time' => 'required|max:191|string'
        ]);
        if ($validator->fails()) {
            return $validator->errors();
        }
        $user=auth()->user();
        $id=$user->id;
        $a=appointment::where('date',$request->input('date'))->where('time',$request->input('time'))->where('user_id',$id);
        $d=$a->delete();
        $deleted="could not delete";
        if($d==1){
            $deleted="ok";
        }
        return $deleted;
    }

    //view appointments booked by some user
    public function view_booked_appointments(){
        $user=auth()->user();
        $id=$user->id;
        $a=appointment::where('user_id',$id);
        foreach ($a as $b) {
            return $b;
        }   
    }

    //view all booked appointments
    public function view_all_booked_appointments(){
        if(!Gate::allows('view_all_booked_appointments')){
            abort(403);
        }
        $a=appointment::all();
        foreach ($a as $b) {
            return $b;
        }   
    }
}

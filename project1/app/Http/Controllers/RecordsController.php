<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Record;

class RecordsController extends Controller
{
    public function create_record(Request $request){
    	Record::create([
            'name'=>$request->name,
            'address'=>$request->address,
            'phone'=>$request->phone,
            'ill'=>$request->ill,
            'status'=>$request->status,
            'user_id'=>$request->user_id,
        ]);

    }
    
    public function delete_record(Request $request){
    	$id=$request->id;
    	$record=Record::find($id);
    	$record->delete();
    }

    public function view_record(Request $request){
    	$id=$request->id;
    	$record=Record::find($id);
    	return $record;
    }

    public function view_all_records(){
        $record=Record::all();
        foreach ($record as $r) {
            return $r;
        }   
    }

    public function view_patient_record(){
        $user=auth()->user();
        $id=$user->id;
        $a=Record::where('user_id',$id);
        foreach ($a as $b) {
            return $b;
        }   
    }

    public function search_record(Request $request){
    	$name=$request->name;
    	$record=Record::where('name',$name);
    	return $record;
    }

    public function update_record(Request $request){
    	$id=$request->id;
    	$record=Record::where('name',$name);
    	Record::where('id',$id)->update(['status'=>$request->input('status')]);
    	return "ok"; 
    }	
}

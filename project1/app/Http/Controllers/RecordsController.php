<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Record;
use Illuminate\Support\Facades\Gate;
use Validator;

class RecordsController extends Controller
{
    public function create_record(Request $request){
    	if(!Gate::allows('create_record')){
    		abort(403);
    	}
    	$validator=Validator::make($request->all(),[
    		'name' => 'required|max:191|string|unique:records',
    		'address' => 'required|max:191|string',
    		'phone' => 'required|max:191|string',
    		'ill' => 'required|max:191|string',
    		'status' => 'required|max:191|string',
    		'user_id' => 'required|max:191|string|unique:records'
    	]);
    	if ($validator->fails()) {
    		return $validator->errors();
    	}
    	Record::create([
            'name'=>$request->name,
            'address'=>$request->address,
            'phone'=>$request->phone,
            'ill'=>$request->ill,
            'status'=>$request->status,
            'user_id'=>$request->user_id,
        ]);
        return "ok";
    }
    
    public function delete_record(Request $request){
    	if(!Gate::allows('delete_record')){
    		abort(403);
    	}
    	$id=$request->user_id;
    	$record=Record::where('user_id',$id);
    	$record->delete();
    	return "ok";
    }

    public function view_all_records(){
    	if(!Gate::allows('view_all_records')){
    		abort(403);
    	}
        $records=Record::all();
        return $records;
    }

    public function view_patient_record(){
        $user=auth()->user();
        $id=$user->id;
        $a=Record::where('user_id',$id)->first();
        return $a;      
    }

    public function search_record(Request $request){
    	if(!Gate::allows('search_record')){
    		abort(403);
    	}
    	$name=$request->name;
    	$record=Record::where('name','like',$name);
    	return $record->first();   
    }

    public function update_record(Request $request){
    	if(!Gate::allows('update_record')){
    		abort(403);
    	}
    	$validator=Validator::make($request->all(),[
    		'status' => 'required|max:191|string'
    	]);
    	if ($validator->fails()) {
    		return $validator->errors();
    	}
    	$id=$request->user_id;
    	Record::where('user_id',$id)->update(['status'=>$request->input('status')]);
    	return "ok"; 
    }	
}

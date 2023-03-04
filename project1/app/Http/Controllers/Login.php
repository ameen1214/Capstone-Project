<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Str;
use App\Models\User;
use Validator;
class Login extends Controller
{
    public function login(Request $request){
    	$validator=Validator::make($request->all(),[
    		'email' => 'required|max:191|string',
    		'password' => 'required|max:191|string'
    	]);
    	if ($validator->fails()) {
    		return $validator->errors();
    	}
        if(auth()->attempt([
        	'email'=>$request->input('email'),
        	'password'=>$request->input('password')
        ])){
        	$user=auth()->user();
        	$user->api_token=Str::random(60);
        	$user->save();
        	return "ok";
        }
        return "no";
    }

    public function logout(Request $request){
    	$validator=Validator::make($request->all(),[
    		'email' => 'required|max:191|string',
    		'password' => 'required|max:191|string'
    	]);
    	if ($validator->fails()) {
    		return $validator->errors();
    	}
        if(auth()->attempt([
        	'email'=>$request->input('email'),
        	'password'=>$request->input('password')
        ])){
        	$user=auth()->user();
        	$user->api_token="";
        	$user->save();
        	return "ok";
        }
        return "no";
    }

    public function register(Request $request) {		
		$validator=Validator::make($request->all(),[
    		'name' => 'required|max:191|string',
    		'email' => 'required|max:191|string|unique:users',
    		'password' => 'required|max:191|string'
    	]);
    	if ($validator->fails()) {
    		return $validator->errors();
    	}
    	else{
    		$data=User::create([
    			'name'=>$request->name,
    			'email'=>$request->email,
    			'password'=>Hash::make($request->password),
    			'api_token' =>""
    		]);
    		return "ok";
    	}
		
	}

	public function updatePassword(Request $request){
		$validator=Validator::make($request->all(),[
    		'email' => 'required|max:191|string',
    		'password' => 'required|max:191|string|unique:users',
    		'password2' => 'required|max:191|string'
    	]);
    	if ($validator->fails()) {
    		return $validator->errors();
    	}
    	else{
    		if(auth()->attempt(['email'=>$request->input('email'),
        		'password'=>$request->input('password')]))
    		{
        		User::where('email',$request->input('email'))->update(['password'=>Hash::make($request->password2)]);
        		return "updated";
        	}
        	return "could not update";
    	}
	}
}

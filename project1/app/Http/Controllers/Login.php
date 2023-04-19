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
    		'email' => 'required|max:191|email',
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
        	//$a=$user->api_token=Str::random(60);
            $token=$user->createToken('Laravel8Passp');
        	//$user->save();
        	return $token->plainTextToken;
        }
        return "no";
    }

    public function logout(){
        auth()->user()->tokens()->delete();
        return "ok";
    }

    public function register(Request $request) {		
		$validator=Validator::make($request->all(),[
    		'name' => 'required|max:191|string',
    		'email' => 'required|max:191|email|unique:users',
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
    		]);
            return "ok";
    	}
		return "no";
	}

	public function updatePassword(Request $request){
		$validator=Validator::make($request->all(),[
    		'password2' => 'required|max:191|string'
    	]);
    	if ($validator->fails()) {
    		return $validator->errors();
    	}
    	else{
                $user=auth()->user();    		
        		$user->update(['password'=>Hash::make($request->password2)]);
        		return "ok";
        	}
        	return "no";
	}

    public function updateEmail(Request $request){
        $validator=Validator::make($request->all(),[
            'email2' => 'required|max:191|string|email'
        ]);
        if ($validator->fails()) {
            return $validator->errors();
        }
        else{
                $user=auth()->user();           
                $user->update(['email'=>$request->email2]);
                return "ok";
            }
            return "no";
    }

    public function get_user_role(){
        $user=auth()->user(); 
        return response()->json(['role'=>$user->role]);
    }
}

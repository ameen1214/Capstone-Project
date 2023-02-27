<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class Login extends Controller
{
    public function login(Request $request){
    	if(auth->attempt(['email'=>$request->input('email'), 'password'=>$request->input('password') ])){
    		return "ok";
    	}
    	return "no";
    }
}

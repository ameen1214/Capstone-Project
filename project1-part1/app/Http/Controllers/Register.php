<?php

namespace App\Http\Controllers;
use App\Models\User;
use Illuminate\Http\Request;

class Register extends Controller
{
    public function register(Request $request) {
         $data=User::create([
         	'name'=>$request->name,
         	'email'=>$request->email,
         	'password'=>$request->password
         ]);
         return "ok200";
    }
}

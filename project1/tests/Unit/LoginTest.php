<?php

namespace Tests\Unit;

use Tests\TestCase;
use App\Models\User;
use App\Http\Controllers\Login;
use Illuminate\Http\Request;
use Auth;

class LoginTest extends TestCase
{
    //Note: please delete this user and change id each time you test or input new data.

    public function test_register(){
        $controller= new Login();
        $request=$this->app['request'];
        $request->merge(['name' => 'user1' , 'email' => 'email1@email.com' , 'password' => 'password1' ]);
        $rerult=$controller->register($request);
        $this->assertEquals($rerult,"ok");
    }

    public function test_login(){
    	$controller= new Login();
    	$request=$this->app['request'];
    	$request->merge(['email' => 'email1@email.com' , 'password' => 'passwordNotCorrect']);
    	$rerult=$controller->login($request);
    	$this->assertEquals($rerult,"no");
    }

    public function test_logout(){
    	$controller= new Login();
    	//user not admin
    	$user=User::find(16);
    	Auth::login($user);
    	$rerult=$controller->logout();
    	$this->assertEquals($rerult,"ok");
    }    

    public function test_updatePassword(){
    	$controller= new Login();
    	//user not admin
    	$user=User::find(16);
    	Auth::login($user);
    	$request=$this->app['request'];
    	$request->merge(['password2' => 'password2' ]);  	
    	$rerult=$controller->updatePassword($request);
    	$this->assertEquals($rerult,"ok");
    }

    public function test_updateEmail(){
        $controller= new Login();
        //user not admin
        $user=User::find(16);
        Auth::login($user);
        $request=$this->app['request'];
        $request->merge(['email2' => 'email2@email.com' ]);     
        $rerult=$controller->updateEmail($request);
        $this->assertEquals($rerult,"ok");
    }

}

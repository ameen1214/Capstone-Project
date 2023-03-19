<?php

namespace Tests\Unit;

use Tests\TestCase;
use App\Models\User;
use App\Http\Controllers\Login;
use Illuminate\Http\Request;
use Auth;

class LoginTest extends TestCase
{
    /**
     * A basic unit test example.
     *
     * @return void
     */
    /*
    public function test_login(){
    	$controller= new Login();
    	$request=$this->app['request'];
    	$request->merge(['email' => 'email2' , 'password' => 'password2']);
    	$rerult=$controller->login($request);
    	$this->assertEquals($rerult,"no");
    }

    public function test_logout(){
    	$controller= new Login();
    	//user not admin
    	$user=User::find(4);
    	Auth::login($user);
    	$rerult=$controller->logout();
    	$this->assertEquals($rerult,"ok");
    }
    
    public function test_register(){
    	$controller= new Login();
    	$request=$this->app['request'];
    	$request->merge(['name' => 'sally' , 'email' => 't@gmail.com' , 'password' => 'pass111' ]);
    	$rerult=$controller->register($request);
    	$this->assertEquals($rerult,"ok");
    }

    public function test_updatePassword(){
    	$controller= new Login();
    	//user not admin
    	$user=User::find(6);
    	Auth::login($user);
    	$request=$this->app['request'];
    	$request->merge(['password2' => 'passr3533' ]);  	
    	$rerult=$controller->updatePassword($request);
    	$this->assertEquals($rerult,"ok");
    }
    */
}

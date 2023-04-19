<?php

namespace Tests\Unit;

use Tests\TestCase;
use App\Http\Controllers\PaymentsController;
use Illuminate\Http\Request;
use App\Models\User;
use App\Models\Account;
use Auth;

class PaymentsTest extends TestCase
{

    public function test_create_account(){
    	$payment=new PaymentsController();
    	//admin
    	$user=User::find(2);
    	Auth::login($user);
    	$request=$this->app['request'];
    	$request->merge(['user_id' => '4' , 'total' => '1000' , 'receved' => '600']);
    	$rerult=$payment->create_account($request);
    	$this->assertEquals($rerult,"ok");
    }
    
    public function test_update_account(){
    	$payment=new PaymentsController();
    	//admin
    	$user=User::find(2);
    	Auth::login($user);
    	$request=$this->app['request'];
    	$request->merge(['user_id' => '4' , 'recevedNow' => '400' ]);
    	$rerult=$payment->update_account($request);
    	$this->assertEquals($rerult,"ok");
    }

    public function test_delete_account(){
    	$payment=new PaymentsController();
    	//admin
    	$user=User::find(2);
    	Auth::login($user);
    	$request=$this->app['request'];
    	$request->merge(['user_id' => '4']);
    	$rerult=$payment->delete_account($request);
    	$this->assertEquals($rerult,"ok");
    }

    
    public function test_view_account(){
    	$payment=new PaymentsController();
    	//admin
    	$user=User::find(2);
    	Auth::login($user);
    	$request=$this->app['request'];
    	$request->merge(['user_id' => '4']);
    	$rerult=$payment->view_account($request);
    	$this->assertEquals($rerult,"");
    }

    public function test_view_my_account(){
    	$payment=new PaymentsController();
    	//user not admin
    	$user=User::find(4);
    	Auth::login($user);
    	$rerult=$payment->view_my_account();
    	$this->assertEquals($rerult,"");
    }

    public function test_owing_users(){
    	$payment=new PaymentsController();
    	//admin
    	$user=User::find(2);
    	Auth::login($user);
    	$rerult=$payment->owing_users();
    	$this->assertEquals($rerult,"[]");
    }

    public function test_view_all_payments(){
        $payment=new PaymentsController();
        //admin
        $user=User::find(2);
        Auth::login($user);
        $rerult=$payment->view_all_payments();
        $this->assertEquals($rerult,"[]");
    }


}

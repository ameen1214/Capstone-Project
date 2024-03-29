<?php

namespace Tests\Unit;

use Tests\TestCase;
use App\Http\Controllers\AppointmentController;
use Illuminate\Http\Request;
use App\Models\User;
use Auth;

class AppointmentsTest extends TestCase
{

    public function test_create_appointment(){
    	$appointment=new AppointmentController();
    	//admin
    	$user=User::find(2);
    	Auth::login($user);
    	$request=$this->app['request'];
    	$request->merge(['date' => '15-3-2023' , 'time' => '10:30 AM']);
    	$rerult=$appointment->create_appointment($request);
    	$this->assertEquals($rerult,"ok");
    }


    public function test_update_appointment(){
    	$appointment=new AppointmentController();
    	//admin
    	$user=User::find(2);
    	Auth::login($user);
    	$request=$this->app['request'];
    	$request->merge(['date' => '15-3-2023' , 'time' => '10:30 AM', 'updatedTime' => '12:00 PM']);
    	$rerult=$appointment->update_appointment($request);
    	$this->assertEquals($rerult,"ok");
    }

    public function test_book(){
    	$appointment=new AppointmentController();
    	//user not admin
    	$user=User::find(4);
    	Auth::login($user);
    	$request=$this->app['request'];
    	$request->merge(['date' => '15-3-2023' , 'time' => '12:00 PM']);
    	$rerult=$appointment->book($request);
    	$this->assertEquals($rerult,"ok");
    }

    public function test_unbook(){
    	$appointment=new AppointmentController();
    	//user not admin
    	$user=User::find(4);
    	Auth::login($user);
    	$request=$this->app['request'];
    	$request->merge(['date' => '15-3-2023' , 'time' => '12:00 PM']);
    	$rerult=$appointment->unbook($request);
    	$this->assertEquals($rerult,"ok");
    }

    public function test_view_booked_appointments(){
    	$appointment=new AppointmentController();
    	//user not admin
    	$user=User::find(4);
    	Auth::login($user);
    	$rerult=$appointment->view_booked_appointments();
    	$this->assertEquals($rerult,"[]");
    }

    public function test_view_all_booked_appointments(){
    	$appointment=new AppointmentController();
    	//admin
    	$user=User::find(2);
    	Auth::login($user);
    	$rerult=$appointment->view_all_booked_appointments();
    	$this->assertEquals($rerult,"[]");
    }

    public function test_delete_appointment(){
        $appointment=new AppointmentController();
        //admin
        $user=User::find(2);
        Auth::login($user);
        $request=$this->app['request'];
        $request->merge(['date' => '15-3-2023' , 'time' => '12:00 PM']);
        $rerult=$appointment->delete_appointment($request);
        $this->assertEquals($rerult,"ok");
    }

    public function test_view_appointments(){
        $appointment=new AppointmentController();
        //user not admin
        $user=User::find(4);
        Auth::login($user);
        $rerult=$appointment->view_appointments();
        $r='[]';
        $this->assertEquals($rerult,$r);
    }

}

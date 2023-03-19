<?php

namespace Tests\Unit;

use Tests\TestCase;
use App\Http\Controllers\RecordsController;
use Illuminate\Http\Request;
use App\Models\User;
use App\Models\Record;
use Auth;

class RecordTest extends TestCase
{
    /**
     * A basic unit test example.
     *
     * @return void
     */
    
    /*
    public function test_create_record(){
    	$record=new RecordsController();
    	//admin
    	$user=User::find(2);
    	Auth::login($user);
    	$request=$this->app['request'];
    	$request->merge(['name' => 'sally' , 'address' => 'london' , 'phone' => '0412333566' , 'ill' => 'fever' , 'status' => 'under treatment' , 'user_id' => '4']);
    	$rerult=$record->create_record($request);
    	$this->assertEquals($rerult,"ok");
    }

    public function test_update_record(){
    	$record=new RecordsController();
    	//admin
    	$user=User::find(2);
    	Auth::login($user);
    	$request=$this->app['request'];
    	$request->merge(['id' => '8' , 'status' => 'treatment completed']);
    	$rerult=$record->update_record($request);
    	$this->assertEquals($rerult,"ok");
    }

    public function test_delete_record(){
    	$record=new RecordsController();
    	//admin
    	$user=User::find(2);
    	Auth::login($user);
    	$request=$this->app['request'];
    	$request->merge(['id' => '6']);
    	$rerult=$record->delete_record($request);
    	$this->assertEquals($rerult,"ok");
    }

    public function test_view_record(){
    	$record=new RecordsController();
    	//admin
    	$user=User::find(2);
    	Auth::login($user);
    	$request=$this->app['request'];
    	$request->merge(['id' => '6']);
    	$rerult=$record->view_record($request);
    	$this->assertEquals($rerult,"");
    }

    public function test_view_patient_record(){
    	$record=new RecordsController();
    	//user not admin
    	$user=User::find(4);
    	Auth::login($user);
    	$rerult=$record->view_patient_record();
    	$this->assertEquals($rerult,"");
    }

    public function test_view_all_records(){
    	$record=new RecordsController();
    	//admin
    	$user=User::find(2);
    	Auth::login($user);
    	$rerult=$record->view_all_records();
    	$this->assertEquals($rerult,"");
    }
    
    public function test_search_record(){
    	$record=new RecordsController();
    	//admin
    	$user=User::find(2);
    	Auth::login($user);
    	$request=$this->app['request'];
    	$request->merge(['name' => 'sami']);
    	$rerult=$record->search_record($request);
    	$this->assertEquals($rerult,"");
    }

    */
    
}

<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Account;

class PaymentsController extends Controller
{
    public function create_account(Request $request){
    	$remaining=$request->total-$request->receved;
    	Record::create([
            'user_id'=>$request->user_id,
            'total'=>$request->total,
            'receved'=>$request->receved,
            'remaining'=>$remaining,
        ]);

    }
    
    public function delete_account(Request $request){
    	$id=$request->id;
    	$account=Account::find($id);
    	$account->delete();
    }
}

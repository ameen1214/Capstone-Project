<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Account;
use App\Models\User;
use Illuminate\Support\Facades\Gate;
use Validator;

class PaymentsController extends Controller
{
    public function create_account(Request $request){
    	if(!Gate::allows('create_account')){
            abort(403);
        }
        $validator=Validator::make($request->all(),[
            'user_id' => 'required|max:191|string',
            'total' => 'required|max:191|string',
            'receved' => 'required|max:191|string',
        ]);
        if ($validator->fails()) {
            return $validator->errors();
        }
    	$remaining=$request->total-$request->receved;
    	Account::create([
            'user_id'=>$request->user_id,
            'total'=>$request->total,
            'receved'=>$request->receved,
            'remaining'=>$remaining,
        ]);
        return "ok";

    }
    
    public function delete_account(Request $request){
    	if(!Gate::allows('delete_account')){
            abort(403);
        }
    	$id=$request->user_id;
    	$account=Account::where('user_id',$id)->first();
    	$account->delete();
        return "ok";
    }

    public function view_account(Request $request){
    	if(!Gate::allows('view_account')){
            abort(403);
        }
    	$id=$request->user_id;
    	$account=Account::where('user_id',$id)->first();
    	return $account;
    }

    public function view_my_account(){
    	$user=auth()->user();
        $id=$user->id;
    	$account=Account::where('user_id',$id)->first();
    	return $account;
    }

    public function owing_users(){
    	if(!Gate::allows('owing_users')){
            abort(403);
        }
    	$accounts=Account::where('remaining','>',0)->get();
    	return $accounts;
    }

    public function view_all_payments(){
        if(!Gate::allows('owing_users')){
            abort(403);
        }
        $accounts=Account::all();
        return $accounts;
    }

    public function update_account(Request $request){
    	if(!Gate::allows('update_account')){
            abort(403);
        }
    	$id=$request->user_id;
    	$recevedNow=$request->recevedNow;
        $account=Account::where('user_id',$id)->first();  	
    	$remainingOld=$account->remaining;
        $recevedOld=$account->receved;
    	$receved=$recevedNow+$recevedOld;
    	$remaining=$remainingOld-$recevedNow;
        $account->update(['receved'=>$receved, 'remaining'=>$remaining]);
    	return "ok";
    }

}

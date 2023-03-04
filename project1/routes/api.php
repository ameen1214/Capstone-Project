<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/
//
Route::post('register',[App\Http\Controllers\Login::class, 'register']);
Route::post('login',[App\Http\Controllers\Login::class, 'login']);
Route::post('logout',[App\Http\Controllers\Login::class, 'logout']);
Route::post('updatePassword',[App\Http\Controllers\Login::class, 'updatePassword']);
Route::get('/', function () {
    return "ok";
});
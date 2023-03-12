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

Route::get('view_appointments',[App\Http\Controllers\AppointmentController::class, 'view_appointments'])->middleware('auth:sanctum');

Route::post('register',[App\Http\Controllers\Login::class, 'register']);
Route::post('login',[App\Http\Controllers\Login::class, 'login']);
Route::post('logout',[App\Http\Controllers\Login::class, 'logout'])->middleware('auth:sanctum');
Route::post('updatePassword',[App\Http\Controllers\Login::class, 'updatePassword']);

Route::post('create_appointment',[App\Http\Controllers\AppointmentController::class, 'create_appointment']);
Route::post('delete_appointment',[App\Http\Controllers\AppointmentController::class, 'delete_appointment']);
Route::post('update_appointment',[App\Http\Controllers\AppointmentController::class, 'update_appointment']);
Route::post('book_appointment',[App\Http\Controllers\AppointmentController::class, 'book'])->middleware('auth:sanctum');
Route::post('unbook_appointment',[App\Http\Controllers\AppointmentController::class, 'unbook'])->middleware('auth:sanctum');
Route::post('view_booked_appointments',[App\Http\Controllers\AppointmentController::class, 'view_booked_appointments'])->middleware('auth:sanctum');
Route::post('view_all_booked_appointments',[App\Http\Controllers\AppointmentController::class, 'view_all_booked_appointments']);

//Records
Route::post('create_record',[App\Http\Controllers\RecordsController::class, 'create_record']);
Route::post('update_record',[App\Http\Controllers\RecordsController::class, 'update_record']);
Route::post('delete_record',[App\Http\Controllers\RecordsController::class, 'delete_record']);
Route::post('view_record',[App\Http\Controllers\RecordsController::class, 'view_record']);
Route::post('view_all_records',[App\Http\Controllers\RecordsController::class, 'view_all_records']);
Route::post('view_all_records',[App\Http\Controllers\RecordsController::class, 'view_all_records']);
Route::post('view_patient_record',[App\Http\Controllers\RecordsController::class, 'view_patient_record']);

//payments
Route::post('create_account',[App\Http\Controllers\PaymentsController::class, 'create_account']);
Route::post('update_account',[App\Http\Controllers\PaymentsController::class, 'update_account']);
Route::post('delete_account',[App\Http\Controllers\PaymentsController::class, 'delete_account']);
Route::post('view_account',[App\Http\Controllers\PaymentsController::class, 'view_account']);

Route::get('/', function () {
    return "ok";
});
<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Available_appointment extends Model
{
    use HasFactory;
    protected $table='available_appointments';
    protected $fillable=['date','time'];
}

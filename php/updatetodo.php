<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
  
// include database and object files
include_once './database.php';
include_once './todo.php';
  
// get database connection
$database = new Database();
$db = $database->getConnection();
  
// prepare object
$todo = new Todo($db);
  
// get id of todo to be edited
$data = json_decode(file_get_contents("php://input"));
  
// set ID property of todo to be edited
$todo->id = $data->id;
  
// set property values
$todo->name = $data->name;
$todo->done = $data->done;
$todo->updated_at = date('Y-m-d H:i:s');
  
// update the todo
if ($todo->update()) { 
  // set response code - 200 ok
  http_response_code(200);

  // tell the user
  echo json_encode(array("code" => 0, "message" => "todo updated"));
} else {
  // set response code - 503 service unavailable
  http_response_code(503);

  // tell the user
  echo json_encode(array("code" => 2, "message" => "fail update"));
}
?>
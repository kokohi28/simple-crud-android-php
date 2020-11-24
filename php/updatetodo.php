<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
  
include_once './database.php';
include_once './todo.php';
  
$database = new Database();
$db = $database->getConnection();
  
$todo = new Todo($db);
  
$data = json_decode(file_get_contents("php://input"));
  
$todo->id = $data->id;
  
$todo->name = $data->name;
$todo->done = $data->done;
$todo->updated_at = date('Y-m-d H:i:s');
  
if ($todo->update()) { 
  http_response_code(200);
  echo json_encode(array("code" => 0, "message" => "todo updated"));
} else {
  http_response_code(503);
  echo json_encode(array("code" => 2, "message" => "fail update"));
}
?>
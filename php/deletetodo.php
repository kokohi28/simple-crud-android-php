<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
  
// include database and object file
include_once './database.php';
include_once './todo.php';
  
// get database connection
$database = new Database();
$db = $database->getConnection();
  
// prepare object
$todo = new Todo($db);
  
// get product id
$data = json_decode(file_get_contents("php://input"));
  
// set todo id to be deleted
$todo->id = $data->id;
  
// delete the product
if ($todo->delete()) {
  // set response code - 200 ok
  http_response_code(200);

  // tell the user
  echo json_encode(array("code" => 0, "message" => "todo deleted"));
} else {  
  // set response code - 503 service unavailable
  http_response_code(503);

  // tell the user
  echo json_encode(array("code" => 2, "message" => "fail delete"));
}
?>
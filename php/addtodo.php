<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
  
// get database connection
include_once './database.php';
  
// instantiate todo object
include_once './todo.php';
  
$database = new Database();
$db = $database->getConnection();
  
$todo = new Todo($db);
  
// get posted data
$data = json_decode(file_get_contents("php://input"));

// make sure data is not empty
if (!empty($data->name)) {
  // set property values
  $todo->name = $data->name;
  $todo->done = 0;
  $todo->created_at = date('Y-m-d H:i:s');
  $todo->updated_at = date('Y-m-d H:i:s');

  // create todo
  if ($todo->create()) {
    // set response code - 200
    http_response_code(201);

    // tell the user
    echo json_encode(array("code" => 0, "message" => "todo inserted"));
  } else {
    // set response code - 503 service unavailable
    http_response_code(503);

    // tell the user
    echo json_encode(array("code" => 2, "message" => "error db"));
  }
} else {
  // set response code - 200
  http_response_code(200);

  // tell the user
  echo json_encode(array("code" => 1, "message" => "empty field"));
}
?>
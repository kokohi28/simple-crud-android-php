<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
  
// include database and object files
include_once './database.php';
include_once './todo.php';
  
// instantiate database and todo object
$database = new Database();
$db = $database->getConnection();
  
// initialize object
$todo = new Todo($db);

// query todos
$stmt = $todo->read();
$num = $stmt->rowCount();
  
// check if more than 0 record found
if ($num > 0) {
  // todos array
  $todos_arr = array();
  $todos_arr["data"] = array();

  // retrieve our table contents
  // fetch() is faster than fetchAll()
  // http://stackoverflow.com/questions/2770630/pdofetchall-vs-pdofetch-in-a-loop
  while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
    // extract row
    // this will make $row['name'] to
    // just $name only
    extract($row);

    $todo_item = array(
      "id" => (int)$id,
      "name" => $name,
      "done" => (int)$done,
      "created_at" => $created_at,
      "updated_at" => $updated_at,
    );

    array_push($todos_arr["data"], $todo_item);
  }
  
  // set response code - 200 OK
  http_response_code(200);
  
  // show todos data in json format
  echo json_encode($todos_arr);
} else {
  // no todos
  echo json_encode(array());
}
  
?>

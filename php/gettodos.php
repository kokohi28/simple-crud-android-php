<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
  
include_once './database.php';
include_once './todo.php';
  
$database = new Database();
$db = $database->getConnection();
  
$todo = new Todo($db);

$stmt = $todo->read();
$num = $stmt->rowCount();
  
if ($num > 0) {
  $todos_arr = array();
  $todos_arr["data"] = array();

  // retrieve our table contents
  // fetch() is faster than fetchAll()
  // http://stackoverflow.com/questions/2770630/pdofetchall-vs-pdofetch-in-a-loop
  while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
    // extract row this will make $row['name'] to just $name only
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
  
  http_response_code(200);
  echo json_encode($todos_arr);
} else {
  // no todos
  echo json_encode(array());
}
  
?>

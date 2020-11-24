<?php // router.php
switch ($_SERVER["REQUEST_URI"]) {
  case "/":
    echo "Hello world!";
    break;

  case "/addTodo":
    include("addtodo.php");
    break;

  case "/getTodos":
    include("gettodos.php");
    break;

  case "/markTodo":
    include("updatetodo.php");
    break;

  case "/deleteTodo":
    include("deletetodo.php");
    break;

  default:
    echo "404: Page not found";
}
?>

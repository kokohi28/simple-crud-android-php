<?php
class Todo {
  private $conn;
  private $table_name = "todos";
  
  // object properties
  public $id;
  public $name;
  public $done;
  public $created_at;
  public $updated_at;

  // constructor with $db as database connection
  public function __construct($db){
    $this->conn = $db;
  }

  // create todo
  function create() {      
    // query to insert record
    $query = "INSERT INTO " . $this->table_name . " SET name=:name, done=:done, created_at=:created_at, updated_at=:updated_at";

    // prepare query
    $stmt = $this->conn->prepare($query);

    // sanitize
    $this->name = htmlspecialchars(strip_tags($this->name));
    $this->done = htmlspecialchars(strip_tags($this->done));
    $this->created_at = htmlspecialchars(strip_tags($this->created_at));
    $this->updated_at = htmlspecialchars(strip_tags($this->updated_at));

    // bind values
    $stmt->bindParam(":name", $this->name);
    $stmt->bindParam(":done", $this->done);
    $stmt->bindParam(":created_at", $this->created_at);
    $stmt->bindParam(":updated_at", $this->updated_at);

    // execute query
    if ($stmt->execute()) {
      return true;
    }

    return false;        
  }

  // read todos
  function read(){
    // select all query
    $query = "SELECT * FROM " . $this->table_name;

    // prepare query statement
    $stmt = $this->conn->prepare($query);

    // execute query
    $stmt->execute();

    return $stmt;
  }

  // update todo
  function update() {
    // update query
    $query = "UPDATE " . $this->table_name .
             " SET
                 done = :done,
                 updated_at = :updated_at
               WHERE
                 id = :id";

    // prepare query statement
    $stmt = $this->conn->prepare($query);

    // sanitize
    $this->done = htmlspecialchars(strip_tags($this->done));
    $this->updated_at = htmlspecialchars(strip_tags($this->updated_at));
    $this->id = htmlspecialchars(strip_tags($this->id));

    // bind new values
    $stmt->bindParam(':done', $this->done);
    $stmt->bindParam(':updated_at', $this->updated_at);
    $stmt->bindParam(':id', $this->id);

    // execute the query
    if ($stmt->execute()) {
      return true;
    }

    return false;
  }

  // delete todo
  function delete() {    
    // delete query
    $query = "DELETE FROM " . $this->table_name . " WHERE id = ?";

    // prepare query
    $stmt = $this->conn->prepare($query);

    // sanitize
    $this->id = htmlspecialchars(strip_tags($this->id));

    // bind id of record to delete
    $stmt->bindParam(1, $this->id);

    // execute query
    if ($stmt->execute()) {
      return true;
    }

    return false;
  }
}
?>
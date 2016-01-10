<?php
  define('HOST','localhost');
  define('USER','root');
  define('PASS','');
  define('DB','mgt');
  $con = mysqli_connect(HOST,USER,PASS,DB);
  $course=$_POST['course'];
  //echo $course;
  $result = "SELECT uid,marks FROM courses_student where course='$course'";
  $res=mysqli_query($con,$result);
  $response["success"]=0;
  $response["message"]="FAIL";
  $response=array();
  if($res)
  {
    $response["message"]="PASS";
      $response["uid"]=array();
    $response["success"]=1;
        while ($row = mysqli_fetch_assoc($res)) {
          $temp=array();
          $temp["uid"]=$row["uid"];
          $temp["marks"]=$row["marks"];
          array_push($response["uid"],$temp);
        }
        echo json_encode($response);
  }
?>
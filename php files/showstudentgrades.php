<?php
	  define('HOST','localhost');
  define('USER','root');
  define('PASS','');
  define('DB','mgt');
  $con = mysqli_connect(HOST,USER,PASS,DB);
  $uid=$_POST['uid'];
  $result = "SELECT course,marks,grade FROM courses_student where uid='$uid'";
  $res=mysqli_query($con,$result);
  $response=array();
  $response["success"]=0;
  if($res)
  {
  	$response["success"]=1;
  	$response["info"]=array();
  	while ($row = mysqli_fetch_assoc($res)) {
          $temp=array();
          $temp["grade"]=$row["grade"];
          $temp["marks"]=$row["marks"];
          $temp["course"]=$row["course"];
          array_push($response["info"],$temp);
        }
        echo json_encode($response);
  }
?>

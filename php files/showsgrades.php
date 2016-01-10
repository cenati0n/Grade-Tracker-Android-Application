<?php
  define('HOST','localhost');
  define('USER','root');
  define('PASS','');
  define('DB','mgt');
  $con = mysqli_connect(HOST,USER,PASS,DB);
	$response=array();
	$course=$_POST['course'];
	$result = "SELECT uid,marks,grade FROM courses_student where course='$course'";
  $res=mysqli_query($con,$result);
  $response["success"]=0;
  response["list"]=array();
  if($res)
  {
  		  $response["success"]=1;
	  while($row = mysqli_fetch_assoc($res1))
	  {
	      $temp=array();
		  $temp["uid"]=$row["uid"];
		  $temp["marks"]=$row["marks"];
		  $temp["grade"]=$row["grade"];
	      array_push($response["list"],$temp);
		}
  }
?>
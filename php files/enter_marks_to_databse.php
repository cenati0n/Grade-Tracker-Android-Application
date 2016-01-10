<?php
  define('HOST','localhost');
  define('USER','root');
  define('PASS','');
  define('DB','mgt');
  $con = mysqli_connect(HOST,USER,PASS,DB);
  $total=$_POST['total'];//no of students
  $course=$_POST['course'];
  $i=0;
  $str_uid="uid";
  $str_marks="marks";
  $response=array();
  $response["success"]=0;
  while($i< $total)
  {
  	$str=$str_uid.$i;
  	$uid=$_POST[$str];
  	$str=$str_marks.$i;
  	$marks=$_POST[$str];
  	$response[$uid]=$marks;
  	$result = "UPDATE courses_student SET marks=$marks where course LIKE '%$course%' AND uid=$uid";
 	 $res=mysqli_query($con,$result);
 	if($res)
 	 {	
		$response["success"]=1;
 	 }
  	$i=$i+1;
  }
  echo json_encode($response);
mysqli_close($con);
?>

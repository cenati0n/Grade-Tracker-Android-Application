<?php
//error_reporting(E_ALL ^ E_DEPRECATED);
//course table sent ,student ,faculty tabel sent
 // $response_course=array();
  $response = array();//for student table and faculty table
  $response["success"]=0;
  define('HOST','localhost');
  define('USER','root');
  define('PASS','');
  define('DB','mgt');
  $con = mysqli_connect(HOST,USER,PASS,DB);
  $uid=$_POST['uid'];
  $pswd=$_POST['pswd'];
  $flag=0;

 // $sql = "insert into course_faculty (uid,course) values ('$uid','$course')"
  $result = "SELECT uid,pswd,type FROM user";
  $res=mysqli_query($con,$result);
  if($res){
    //$row = mysqli_fetch_array($res);
    while ($row = mysqli_fetch_assoc($res)) {
      if($row["uid"]==$uid && $row["pswd"]==$pswd)
      {
	  $response["success"]=1;
          $response["uid"]=$uid;
        if($row["type"]=="s")
        {
            $response["type"]=1;
          $result="select name,branch,batch from student where uid=$uid";
          $res=mysqli_query($con,$result);
          $row = mysqli_fetch_array($res);
          $response["batch"]=$row["batch"];
          $response["branch"]=$row["branch"];
          $response["name"]=$row["name"];
          $response["message"]="Student Login Successful!";
          $result="select * from courses_student where uid=$uid";
          $res=mysqli_query($con,$result);
          $i=0;
          $response["course"]=array();
          while($row = mysqli_fetch_assoc($res))
          {
            $temp=array();
            $temp["course"]=$row["course"];
            $temp["credit"]=$row["credit"];
            $temp["marks"]=$row["marks"];
            $temp["grade"]=$row["grade"];
          array_push($response["course"],$temp);
          //echo $response["course"]["course"];
            $i=$i+1;
          }
          $response["size"]=$i;
          $arr=$response["course"];
         // $re=array_merge($response_course,$response);
          echo json_encode($response);
         // $temp=$response["course"];
         // echo $temp[0]["course"];
         // foreach($arr as $element) echo $element["course"];
          //echo $response["course"][$0];
        }
        else if($row["type"]=="f")
        {
            $response["type"]=2;
          $result="select name,dept from faculty where uid=$uid" ;
          $res=mysqli_query($con,$result);
          $row = mysqli_fetch_array($res);
          $response["dept"]=$row["dept"];
          $response["name"]=$row["name"];
          $response["message"]="Faculty Login Successful!";
          $result="select * from course_faculty where uid=$uid  AND grade='A'";
          $res=mysqli_query($con,$result);
          $i=0;
          $response["course"]=array();
          while($row = mysqli_fetch_assoc($res))
          {
            $temp=array();
            $temp["course"]=$row["course"];
            $temp["credit"]=$row["credit"];
          array_push($response["course"],$temp);
            $i=$i+1;
          }
          $response["size"]=$i;
          echo json_encode($response);
        }
        $flag=1;
        break;
      }
    }
    if($flag==0)
    {
      $response["message"]="Invalid Username or Password";
      echo json_encode($response);
    }
  }
  else{
    echo 'Connection failed';
  }
  mysqli_close($con);
?>

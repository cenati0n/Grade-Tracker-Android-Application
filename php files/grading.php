<?php
  define('HOST','localhost');
  define('USER','root');
  define('PASS','');
  define('DB','mgt');
  $con = mysqli_connect(HOST,USER,PASS,DB);
  $type=$_POST['type'];
  $course=$_POST['course'];
  $total=8;
  $response["success"]=0;
  $send=array();
  if($type=="Absolute")
  {
    $i=0;
    $lower="l";
    $upper="u";
    $result1="SELECT marks,uid from courses_student where course LIKE '$course'";
    while($i<$total)
    {
      $str_l=$_POST[$lower.$i];
      $str_u=$_POST[$upper.$i];
      if($i==0)
      {
        // if($res1)
        //         echo"HELLO";
        $result="UPDATE course_faculty SET lower_bound=$str_l,upper_bound=$str_u where course='$course' AND grade='A'";
        $res=mysqli_query($con,$result);
        //   if($res)
        // {
        //   echo "Successful\n";
        // }
        $res1=mysqli_query($con,$result1);
        while($row = mysqli_fetch_assoc($res1))
        {
         // echo $row["marks"];
          if($row["marks"]<=$str_u && $row["marks"]>$str_l)
          {
           // echo $row["marks"];
            $val=$row["uid"];
            $result="UPDATE courses_student SET grade='A' where uid=$val and course='$course' ";
             $res=mysqli_query($con,$result);
             $send[$val]="A";
          }
        }
      }
      else if($i==1)
      {
        echo $str_l;
        echo $str_u;
        $result="UPDATE course_faculty SET lower_bound=$str_l,upper_bound=$str_u where course='$course' AND grade='AB'";
         $res=mysqli_query($con,$result);
         if($res)
         {
         // echo "Success";
         }
         $res1=mysqli_query($con,$result1);
        while($row = mysqli_fetch_assoc($res1))
        {
          //echo $row["marks"];
          if($row["marks"]<=$str_u && $row["marks"]>$str_l)
          {
        //    echo $row["marks"];
            $val=$row["uid"];
            $result="UPDATE courses_student SET grade='AB' where uid=$val and course='$course' ";
             $res=mysqli_query($con,$result);
             $send[$val]="AB";
          }
        }
      }
      else if($i==2)
      {
        $result="UPDATE course_faculty SET lower_bound=$str_l,upper_bound=$str_u where course='$course' AND grade='B'";
        $res=mysqli_query($con,$result);
        $res1=mysqli_query($con,$result1);
        while($row = mysqli_fetch_assoc($res1))
        {
       //   echo $row["marks"];
          if($row["marks"]<=$str_u && $row["marks"]>$str_l)
          {
         //   echo $row["marks"];
            $val=$row["uid"];
            $result="UPDATE courses_student SET grade='B' where uid=$val and course='$course' ";
             $res=mysqli_query($con,$result);
             $send[$val]="B";
          }
        }
        if($res)
        {
          //echo "Successful\n";
        }
      }
      else if($i==3)
      {
        $result="UPDATE course_faculty SET lower_bound=$str_l,upper_bound=$str_u where course='$course' AND grade='BC'";
        $res=mysqli_query($con,$result);
        $res1=mysqli_query($con,$result1);
        while($row = mysqli_fetch_assoc($res1))
        {
          //echo $row["marks"];
          if($row["marks"]<=$str_u && $row["marks"]>$str_l)
          {
            //echo $row["marks"];
            $val=$row["uid"];
            $result="UPDATE courses_student SET grade='BC' where uid=$val and course='$course' ";
             $res=mysqli_query($con,$result);
             $send[$val]="BC";
          }
        }
        if($res)
        {
          //echo "Successful\n";
        }
      }
      else if($i==4)
      {
        $result="UPDATE course_faculty SET lower_bound=$str_l,upper_bound=$str_u where course='$course' AND grade='C'";
        $res=mysqli_query($con,$result);
        $res1=mysqli_query($con,$result1);
        while($row = mysqli_fetch_assoc($res1))
        {
        //  echo $row["marks"];
          if($row["marks"]<=$str_u && $row["marks"]>$str_l)
          {
          //  echo $row["marks"];
            $val=$row["uid"];
            $result="UPDATE courses_student SET grade='C' where uid=$val and course='$course' ";
             $res=mysqli_query($con,$result);
             $send[$val]="C";
          }
        }
          if($res)
        {
        //  echo "Successful\n";
        }
      }
      else if($i==5)
      {
        $result="UPDATE course_faculty SET lower_bound=$str_l,upper_bound=$str_u where course='$course' AND grade='CD'";
        $res=mysqli_query($con,$result);
        $res1=mysqli_query($con,$result1);
        while($row = mysqli_fetch_assoc($res1))
        {
        //  echo $row["marks"];
          if($row["marks"]<=$str_u && $row["marks"]>$str_l)
          {
          //  echo $row["marks"];
            $val=$row["uid"];
            $result="UPDATE courses_student SET grade='CD' where uid=$val and course='$course' ";
             $res=mysqli_query($con,$result);
             $send[$val]="CD";
          }
        }
        if($res)
        {
        //  echo "Successful\n";
        }
      }
      else if($i==6)
      {
        $result="UPDATE course_faculty SET lower_bound=$str_l,upper_bound=$str_u where course='$course' AND grade='D'";
        $res=mysqli_query($con,$result);
        $res1=mysqli_query($con,$result1);
        while($row = mysqli_fetch_assoc($res1))
        {
         // echo $row["marks"];
          if($row["marks"]<=$str_u && $row["marks"]>$str_l)
          {
           // echo $row["marks"];
            $val=$row["uid"];
            $result="UPDATE courses_student SET grade='D' where uid=$val and course='$course' ";
             $res=mysqli_query($con,$result);
             $send[$val]="D";
          }
        }
        if($res)
        {
      //    echo "Successful\n";
        }
      }
      else if($i==7)
      {
        $result="UPDATE course_faculty SET lower_bound=$str_l,upper_bound=$str_u where course ='$course' AND grade='F'";
        $res=mysqli_query($con,$result);
        $res1=mysqli_query($con,$result1);
        while($row = mysqli_fetch_assoc($res1))
        {
        //  echo $row["marks"];
          if($row["marks"]<=$str_u && $row["marks"]>$str_l)
          {
          //  echo $row["marks"];
            $val=$row["uid"];
            $result="UPDATE courses_student SET grade='F' where uid=$val and course='$course' ";
             $res=mysqli_query($con,$result);
             
             $send[$val]="F";
          }
        }
        if($res)
        {
           $response["success"]=1;
          //echo "Successful\n";
        }
      }
      $i=$i+1;
    }
    echo json_encode($send);
  }
?>

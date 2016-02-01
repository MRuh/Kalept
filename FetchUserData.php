<?php 

	$con=mysqli_connect('mysql.hostinger.fr','u618105674_sad','yopyop159','u618105674_kalep');

	$password=$_POST['password'];
	$username=$_POST['username'];
	

	$statement = mysqli_prepare($con,'SELECT * FROM User WHERE username = ? AND password = ?');
	mysqli_stmt_bind_param($statement,'ss',$username,$password);
	mysqli_stmt_execute($statement);


	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement,$userID,$name,$age,$username,$password);

	$user = array();

	while (mysqli_stmt_fetch($statement)) {

		$user[name]=$name;
		$user[age]=$age;
		$user[username]=$username;
		$user[password]=$password;
	}
	
	
	
	if (!function_exists('json_encode'))
{
  function json_encode($a=false)
  {
    if (is_null($a)) return 'null';
    if ($a === false) return 'false';
    if ($a === true) return 'true';
    if (is_scalar($a))
    {
      if (is_float($a))
      {
        // Always use "." for floats.
        return floatval(str_replace(",", ".", strval($a)));
      }
      if (is_string($a))
      {
        static $jsonReplaces = array(array("\\", "/", "\n", "\t", "\r", "\b", "\f", '"'), array('\\\\', '\\/', '\\n', '\\t', '\\r', '\\b', '\\f', '\"'));
        return '"' . str_replace($jsonReplaces[0], $jsonReplaces[1], $a) . '"';
      }
      else
        return $a;
    }
    $isList = true;
    for ($i = 0, reset($a); $i < count($a); $i++, next($a))
    {
      if (key($a) !== $i)
      {
        $isList = false;
        break;
      }
    }
    $result = array();
    if ($isList)
    {
      foreach ($a as $v) $result[] = json_encode($v);
      return '[' . join(',', $result) . ']';
    }
    else
    {
      foreach ($a as $k => $v) $result[] = json_encode($k).':'.json_encode($v);
      return '{' . join(',', $result) . '}';
    }
  }
}
		echo json_encode($user);

	mysqli_stmt_close($statement);
	mysqli_close($con);


 ?>
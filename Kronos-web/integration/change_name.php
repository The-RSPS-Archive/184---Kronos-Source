<?php
$user_id = $_POST['id'];
$name = $_POST['name'];
$name_requested = $_POST['name_requested'];
if (!isset($user_id, $name, $name_requested)) {
    die();
}
/**
 * Create the bridge to Xenforo
 **/
$fileDir = '../../';
require($fileDir . 'forum/src/XF.php');
XF::start($fileDir);
/**
 * Check if the username is registered
 */
$finder = \XF::finder('XF:User');
$potentialUser = $finder->where('username', $name_requested)->fetchOne();
if($potentialUser)
    die('Username is already registered');
/**
 * If we can't find the user.. try searching by usernames held by players
 */
$db = \XF::db();
$result = $db->fetchOne("SELECT username FROM xf_user WHERE last_username = '$name_requested'");
if(!empty($result) && $result != $name) {
    die('Username is being held');
}
/**
 * Validate the username before proceeding with registration
 */
$validator = \XF::app()->validator('Username');
$username = $validator->coerceValue($name_requested);
if (!$validator->isValid($username, $errorKey)) {
    if($errorKey == 'too_long') {
        die('Username too long');
    } else if($errorKey == 'disallowed' || $errorKey == 'censored') {
        die('Username contained disallowed words');
    } else if($errorKey == 'regex' || $errorKey == 'comma') {
        die('Username contains incorrect characters');
    } else if($errorKey == 'duplicate') {
        die('Username must be unique');
    }
}
/**
 * Looks like we're a go! Change the username :-)
 **/
$db = \XF::db();
$result = $db->query("UPDATE xf_user SET username = '$name_requested', last_username ='$name' WHERE user_id = '$user_id'");
die('Success');
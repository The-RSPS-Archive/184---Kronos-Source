<?php
/**
 * Create the bridge to Xenforo
 **/
$fileDir = '../../';
require($fileDir . 'forum/src/XF.php');
XF::start($fileDir);


if ($_POST['t'] == 0) {
    /* get by id */
    $finder = \XF::finder('XF:User');
    $user = $finder->where('user_id', $_POST['v'])->fetchOne();
} else {
    /* get by name */
    $finder = \XF::finder('XF:User');
    $user = $finder->where('username', $_POST['v'])->fetchOne();
}
$columns = array("user_id", "username", "last_username", "user_group_id", "email");
for ($i = 0; $i < sizeof($columns); $i++) {
    $column = $columns[$i];
    $columns[$i] = array($column, $user[$column]);
}
die('d=' . json_encode($columns));
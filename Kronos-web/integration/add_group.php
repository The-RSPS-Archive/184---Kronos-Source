<?php
$userID = $_POST['userId'];
$type = $_POST['type'];
$groupID = $_POST['groupId'];
if (!isset($userID, $type, $groupID)) {
    die(0);
}
/**
 * Create the bridge to Xenforo
 **/
$fileDir = '../../';
require($fileDir . 'forum/src/XF.php');
XF::start($fileDir);

/** @var \XF\Service\User\UserGroupChange $changeService */
$changeService = XF::service('XF:User\UserGroupChange');
$success = $changeService->addUserGroupChange($_POST['userId'], $type, $groupID);
die($success ? '1' : '0');
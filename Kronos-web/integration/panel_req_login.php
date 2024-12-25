<?php
$ip = $_POST['ip'];
$name = $_POST['name'];
$pass = $_POST['pass'];
$email = $_POST['email'];
if (!isset($ip, $name, $pass)) {
    die();
}
/**
 * Create the bridge to Xenforo
 **/
$fileDir = '../../';
require($fileDir . 'forum/src/XF.php');
XF::start($fileDir);

/**
 * Check if the username is registered (login and prompt for e-mail to create account)
 */
$finder = \XF::finder('XF:User');
$user = $finder->where('username', $name)->fetchOne();

if (!$user) {
    /**
     * Validate the username before proceeding with registration
     */
    $validator = \XF::app()->validator('Username');
    $username = $validator->coerceValue($name);
    if (!$validator->isValid($username, $errorKey)) {
        if ($errorKey == 'too_long') {
            die('Username too long.');
        } else if ($errorKey == 'disallowed' || $errorKey == 'censored') {
            die('Username contained disallowed words.');
        } else if ($errorKey == 'regex' || $errorKey == 'comma') {
            die('Username contains incorrect characters.');
        } else if ($errorKey == 'duplicate') {
            die('Username must be unique.');
        }
    }
    /**
     * Log the user in
     */
    $columns = array("user_id", "username", "user_group_id", "secondary_group_ids");
    $data = array();
    foreach ($columns as $c) {
        $data[$c] = $user[$c];
    }
    die('d=' . json_encode($data));
}

/**
 * Check if the user is banned
 */
if ($user->is_banned) {
    die('Banned.');
}

/**
 * Verify the account details
 **/
$loginService = \XF::app()->service('XF:User\Login', $name, $ip);
$success = $loginService->validate($pass);
if (!$success) {
    die('Incorrect password. Please try again.');
}

/**
 * Verify two-factor authentication
$tfaEnabled = $user->Option->use_tfa;
if ($tfaEnabled) {
    $tfaRepository = $user->repository('XF:Tfa');
    if ($tfaRepository->userRequiresTfa($user)) {
        $tfaTrustValue = $_POST['key'];//Key - usually a value from 0-300
        $milliseconds = round(microtime(true) * 1000);
        $tfaTrustRepo = $user->repository('XF:UserTfaTrusted');
        if (!$tfaTrustRepo->getTfaTrustRecord($user->user_id, $tfaTrustValue)) {
            $tfaCode = $_POST['code']; //Int - value entered in the auth prompt
            if ($tfaCode == 0) {
                die("Two-factor authentication required.");
            }

            $providerId = 'totp';
            $tfaService = $loginService->service('XF:User\Tfa', $user);

            $providers = $tfaService->getProviders();
            $provider = $providers[$providerId];
            $providerData = $provider->getUserProviderConfig($user->user_id);
            $handler = $provider->handler;
            if (!$handler->verify('login', $user, $providerData, \XF::app()->request())) {
                die("Two-factor authentication invalid!");
            } else {
                if ($_POST['trust'] === true) {
                    $db = \XF::db();
                    $db->insert('xf_user_tfa_trusted', [
                        'user_id' => $user->user_id,
                        'trusted_key' => $tfaTrustValue,
                        'trusted_until' => \XF::$time + 86400 * 30
                    ]);
                }
            }
        }
    }
} **/

/**
 * Successful login
 **/
$columns = array("user_id", "username", "last_username", "user_group_id", "secondary_group_ids", "conversations_unread");
$data = array();
foreach ($columns as $c) {
    $data[$c] = $user[$c];
}
die('d=' . json_encode($data));
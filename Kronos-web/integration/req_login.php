<?php
$ip = $_POST['ip'];
$name = $_POST['name'];
$pass = $_POST['pass'];
$email = "changeme@gmail.com";//$_POST['email'];
if (!isset($ip, $name, $pass)) {
    die();
}

/**
 * Create the bridge to Xenforo
 **/
$fileDir = '../../';
require($fileDir . 'forums/src/XF.php');
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
     * If the account isn't registered and POST doesn't include email.. request! :-)
     */
    if (empty($email)) {
        die('Email required.');
    }

    /**
     * Ensure email isn't in use
     */
	 $email = $name."changeme@gmail.com";
    $emailInUse = \XF::finder('XF:User')->where('email', $email)->fetchOne();
    if ($emailInUse) {
        die('Email in use.');
    }

    /**
     * Validate email
     
    $emailValidator = \XF::app()->validator('Email');
    $emailValidator->setOption('check_typos', true);

    if (!$emailValidator->isValid($email, $errorKey)) {
        if ($errorKey == 'banned') {
            die('Email banned.');
        } else if ($errorKey == 'typo') {
            die('Email type.');
        } else {
            die('Invalid email.');
        }
    }*/

    /**
     * Register the new user
     */
    $registration = XF::service('XF:User\Registration');
    $input['username'] = $name;
    $input['email'] = $email;
    $input['password'] = $pass;
    $registration->setFromInput($input);
    $user = $registration->save();

    /**
     * Set the user groups
     */
    $user->user_group_id = 2;
    $user->save();

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
 *
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
}*/

/**
 * Check AntiKnox information (to ensure our user isn't logging in from a proxy)
 */
$key = "91952438ecca55ed50af01a13aa1b3d32a59360335f645bb63b1a89d8a06e573"; // Shown in the AntiKnox dashboard

// AntiKnox accepts HTTP too, however we suggest using HTTPS.
$raw  = file_get_contents_curl("https://api.antiknox.net/lookup/$ip?auth=$key");
$data = json_decode($raw);

// The 'match' entry is true if a direct hit was returned.
if ($data->{"match"}) {
    $type     = $data->{"direct"}->{"type"};
    $provider = $data->{"direct"}->{"provider"};
    die("Proxy login attempt");
}

// Heuristics data is always available.
$heuristics = $data->{"heuristics"};
$company    = $heuristics->{"company"};
$label      = $heuristics->{"label"};

if ($label === "hosting" || $label === "hosting-mixed") {
    die("Proxy login attempt");
}

/**
 * Successful login
 **/
$columns = array("user_id", "username", "last_username", "user_group_id", "secondary_group_ids", "conversations_unread");
$data = array();
foreach ($columns as $c) {
    $data[$c] = $user[$c];
}
die('d=' . json_encode($data));


/**
 * Function to curl AntiKnox proxy information
 * @param $url
 * @param int $retries
 * @return bool|mixed|string
 */
function file_get_contents_curl($url, $retries=5) {
    $ua = 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.82 Safari/537.36';
    if (extension_loaded('curl') === true) {
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $url); // The URL to fetch. This can also be set when initializing a session with curl_init().
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, TRUE); // TRUE to return the transfer as a string of the return value of curl_exec() instead of outputting it out directly.
        curl_setopt($ch, CURLOPT_CONNECTTIMEOUT, 10); // The number of seconds to wait while trying to connect.
        curl_setopt($ch, CURLOPT_USERAGENT, $ua); // The contents of the "User-Agent: " header to be used in a HTTP request.
        curl_setopt($ch, CURLOPT_FAILONERROR, TRUE); // To fail silently if the HTTP code returned is greater than or equal to 400.
        curl_setopt($ch, CURLOPT_FOLLOWLOCATION, TRUE); // To follow any "Location: " header that the server sends as part of the HTTP header.
        curl_setopt($ch, CURLOPT_AUTOREFERER, TRUE); // To automatically set the Referer: field in requests where it follows a Location: redirect.
        curl_setopt($ch, CURLOPT_TIMEOUT, 10); // The maximum number of seconds to allow cURL functions to execute.
        curl_setopt($ch, CURLOPT_MAXREDIRS, 5); // The maximum number of redirects
        $result = curl_exec($ch);
        curl_close($ch);
    } else {
        $result = file_get_contents($url);
    }
    if (empty($result) === true) {
        $result = false;
        if ($retries >= 1)
        {
            sleep(1);
            return file_get_contents_curl($url, --$retries);
        }
    }
    return $result;
}
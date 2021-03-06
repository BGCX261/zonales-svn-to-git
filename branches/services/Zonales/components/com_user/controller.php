<?php
/**
 * @version		$Id: controller.php 12389 2009-07-01 00:34:45Z ian $
 * @package		Joomla
 * @subpackage	Content
 * @copyright	Copyright (C) 2005 - 2008 Open Source Matters. All rights reserved.
 * @license		GNU/GPL, see LICENSE.php
 * Joomla! is free software. This version may have been modified pursuant to the
 * GNU General Public License, and as distributed it includes or is derivative
 * of works licensed under the GNU General Public License or other free or open
 * source software licenses. See COPYRIGHT.php for copyright notices and
 * details.
 */

// Check to ensure this file is included in Joomla!
defined('_JEXEC') or die( 'Restricted access' );

jimport('joomla.application.component.controller');
#jimport('joomla.mail.mail');
jimport( 'joomla.utilities.utility' );
jimport('joomla.user.helper');

/**
 * User Component Controller
 *
 * @package		Joomla
 * @subpackage	Weblinks
 * @since 1.5
 */
class UserController extends JController {
/**
 * Method to display a view
 *
 * @access	public
 * @since	1.5
 */
    function display() {
        parent::display();
    }

    function edit() {
        global $mainframe, $option;

        $db		=& JFactory::getDBO();
        $user	=& JFactory::getUser();

        if ( $user->get('guest')) {
            JError::raiseError( 403, JText::_('Access Forbidden') );
            return;
        }

        JRequest::setVar('layout', 'form');

        parent::display();
    }

    function save() {
    // Check for request forgeries
        JRequest::checkToken() or jexit( 'Invalid Token' );

        $user	 =& JFactory::getUser();
        $userid = JRequest::getVar( 'id', 0, 'post', 'int' );

        // preform security checks
        if ($user->get('id') == 0 || $userid == 0 || $userid <> $user->get('id')) {
            JError::raiseError( 403, JText::_('Access Forbidden') );
            return;
        }

        //clean request
        $post = JRequest::get( 'post' );
        $post['username']	= JRequest::getVar('username', '', 'post', 'username');
        $post['password']	= JRequest::getVar('password', '', 'post', 'string', JREQUEST_ALLOWRAW);
        $post['password2']	= JRequest::getVar('password2', '', 'post', 'string', JREQUEST_ALLOWRAW);

        // get the redirect
        $return = JURI::base();

        // do a password safety check
        if(strlen($post['password']) || strlen($post['password2'])) { // so that "0" can be used as password e.g.
            if($post['password'] != $post['password2']) {
                $msg	= JText::_('PASSWORDS_DO_NOT_MATCH');
                // something is wrong. we are redirecting back to edit form.
                // TODO: HTTP_REFERER should be replaced with a base64 encoded form field in a later release
                $return = str_replace(array('"', '<', '>', "'"), '', @$_SERVER['HTTP_REFERER']);
                if (empty($return) || !JURI::isInternal($return)) {
                    $return = JURI::base();
                }
                $this->setRedirect($return, $msg, 'error');
                return false;
            }
        }

        // we don't want users to edit certain fields so we will unset them
        unset($post['gid']);
        unset($post['block']);
        unset($post['usertype']);
        unset($post['registerDate']);
        unset($post['activation']);

        // store data
        $model = $this->getModel('user');


        $db = &JFactory::getDBO();
        $query='update #__users set birthdate=' . $db->Quote($post['birthdate']) . ' where id=' . $userid;
        $db->setQuery($query);
        $db->query();

        if ($model->store($post)) {
            $msg	= JText::_( 'Your settings have been saved.' );
        } else {
        //$msg	= JText::_( 'Error saving your settings.' );
            $msg	= $model->getError();
        }


        $this->setRedirect( $return, $msg );
    }


    private function logme($db,$message) {
        $query='insert into #__logs(info,timestamp) values ("' .
            $message . '","' . date('Y-m-d h:i:s') . '")';
        $db->setQuery($query);
        $db->query();
    }


    function cancel() {
        $this->setRedirect( 'index.php' );
    }

    private function aliasreg($providerid,$externalid) {

        if ($providerid != 0 && $externalid != '') {
            $db = &JFactory::getDBO();
            $user =& JFactory::getUser();

            $externalid = urldecode($externalid);
            $this->logme($db, 'se va a crear alias. el userid es: ' . $user->id);
            $status = $this->insertAlias('0', $user->id, $externalid, $providerid);
            return $status;
        }
        return false;
    }

    function aliasregister() {
    // Check for request forgeries
        JRequest::checkToken('request') or jexit( 'Invalid Token' );

        $providerid = JRequest::getInt('providerid', '0', 'method');
        $externalid = JRequest::getVar('externalid', '', 'method', 'string');

        $statusAux = $this->aliasreg($providerid,$externalid);

        $status = ($statusAux) ? '0' : '1';

        $this->endAuthentication();
        global $mainframe;
        $mainframe->redirect(JRoute::_('index.php?option=com_alias&view=addmessage&status=' . $status));
    }

    function login($epUserdata = null) {
        $credentials = array();
        $options = array();
        if ($epUserdata == null) {
        // Check for request forgeries
            JRequest::checkToken('request') or jexit( 'Invalid Token' );
            $credentials['provider'] = JRequest::getVar('provider', null, 'method', 'string');
            $credentials['username'] = JRequest::getVar('username', '', 'method', 'username');
            $credentials['userid'] = JRequest::getInt('userid', '0', 'method');
        }
        else {
            $credentials['provider'] = $epUserdata['epprovider'];
            $credentials['username'] = $epUserdata['epusername'];
            $options['userid'] = $epUserdata['userid'];
        }


        global $mainframe;

        if ($return = JRequest::getVar('return', '', 'method', 'base64')) {
            $return = base64_decode($return);
            if (!JURI::isInternal($return)) {
                $return = '';
            }
        }


        $options['remember'] = JRequest::getBool('remember', false);
        $options['return'] = $return;

        $credentials['password'] = JRequest::getString('password', '', 'post', JREQUEST_ALLOWRAW);

        # agregado por G2P

        $options['providerid'] = JRequest::getInt('providerid', '0', 'method');
        $options['externalid'] = JRequest::getVar('externalid', '', 'method', 'string');

        ##### testing ##########
        $db = &JFactory::getDBO();
        $this->logme($db, 'el external id es: ' . $options['externalid']);
        $this->logme($db, 'el provider id es: ' . $options['providerid']);
        $this->logme($db, 'el user id es: ' . $credentials['userid']);

        //preform the login action
        $error = $mainframe->login($credentials, $options);

        $this->endAuthentication();

        if(!JError::isError($error)) {
            $this->logme($db, 'no hubo error');
            // Redirect if the return url is not registration or login
            if ( ! $return ) {
                $return	= 'index.php?option=com_user';
            }

            if ($credentials['userid'] == 0) {
                $this->aliasreg($options['providerid'],$options['externalid']);
            }
            else {
                $session = & JFactory :: getSession();
                $selectProvider = 'select p.id from #__providers p where p.name = "' . $credentials['provider'] . '"';
                $db->setQuery($selectProvider);
                $dbprovider = $db->loadObject();

                $this->insertAlias(0, $credentials['userid'], $session->get('externalidentifier'), $dbprovider->id);
            }


            $mainframe->redirect( $return );
        }
        else {
            $this->logme($db, 'SI hubo error');
            // Facilitate third party login forms
            if ( ! $return ) {
                $return	= 'index.php?option=com_user&view=zlogin';
            }

            // Redirect to a login form
            $mainframe->redirect( $return );
        }
    }

    private function endAuthentication() {
        $session = & JFactory :: getSession();
        $session->set('authenticationonprogress','false');
    }

    function logout() {
        global $mainframe;

        //preform the logout action
        $error = $mainframe->logout();

        if(!JError::isError($error)) {
            if ($return = JRequest::getVar('return', '', 'method', 'base64')) {
                $return = base64_decode($return);
                if (!JURI::isInternal($return)) {
                    $return = '';
                }
            }

            // Redirect if the return url is not registration or login
            if ( $return && !( strpos( $return, 'com_user' )) ) {
                $mainframe->redirect( $return );
            }
        } else {
            parent::display();
        }
    }

    /**
     * Prepares the registration form
     * @return void
     */
    function register() {
        $usersConfig = &JComponentHelper::getParams( 'com_users' );
        if (!$usersConfig->get( 'allowUserRegistration' )) {
            JError::raiseError( 403, JText::_( 'Access Forbidden' ));
            return;
        }

        $user 	=& JFactory::getUser();

        if ( $user->get('guest')) {
            JRequest::setVar('view', 'register');


        } else {
            $this->setredirect('index.php?option=com_user&task=edit',JText::_('You are already registered.'));
        }

        parent::display();
    }

    private function getVariations($string) {
        $variations = array();

        $variations[] = $string;
        $variations[] = strtolower($string);
        $variations[] = ucwords(strtolower($string));
        $variations[] = strtoupper($string);

        return $variations;

    }

    private function getUserId($db,$user,$username = false) {
    //            $id_query = 'SELECT u.id FROM #__users u'.
    //                ' WHERE u.name = ' . $db->Quote($user->get('name')) .
    //                ' AND u.birthdate="' . $user->get('birthdate') . '"';

        $variations = $this->getVariations($user->get('name'));
        $end = implode('","', $variations);
        $end = '"' . $end . '"';

        $useUsername = ($username) ? ' AND u.username ="' . $user->get('username') . '"' : '';

        $id_query = 'SELECT u.id FROM #__users u'.
            ' WHERE u.birthdate="' . $user->get('birthdate') . '"' .
            $useUsername .
            ' AND u.name IN (' . $end . ')';
        $db->setQuery($id_query);
        $dbuserid = $db->loadObject();

        return ($dbuserid) ? $dbuserid->id : null;
    }

    private function userExists($db,$user) {
        return ($this->getUserId($db, $user) != null);
    }

    private function insertAlias($block,$userid,$alias,$providerid) {
        $db = &JFactory::getDBO();
        $passphrase = JUtility::getHash( JUserHelper::genRandomPassword());

        $insertAlias = 'insert into #__alias(user_id,name,provider_id,association_date,block,activation) ' .
            'values (' . $userid . ',"' . $alias .
            '",' . $providerid . ',"' . date('Y-m-d') . '",' .
            $block . ',"' . $passphrase . '")';

        $db->setQuery($insertAlias);
        if (!$db->query()) {
            $this->logme($db, 'No se pudo insertar el alias');
            $this->logme($db, $userid);
            return false;
        }
        return true;
    }

    /**
     * Save user registration and notify users and admins if required
     * @return void
     */
    function register_save() {
        global $mainframe;

        // Check for request forgeries
        JRequest::checkToken() or jexit( 'Invalid Token' );

        $providerid = JRequest::getInt('providerid', '0', 'method');
        $externalid = JRequest::getVar('externalid', '', 'method', 'string');
        $force = JRequest::getInt('force', '0', 'method');

        $db = &JFactory::getDBO();

        // Get required system objects
        $user 		= clone(JFactory::getUser());
        $pathway 	=& $mainframe->getPathway();
        $config		=& JFactory::getConfig();
        $authorize	=& JFactory::getACL();
        $document   =& JFactory::getDocument();

        // If user registration is not allowed, show 403 not authorized.
        $usersConfig = &JComponentHelper::getParams( 'com_users' );
        if ($usersConfig->get('allowUserRegistration') == '0') {
            JError::raiseError( 403, JText::_( 'Access Forbidden' ));
            return;
        }

        // Initialize new usertype setting
        $newUsertype = $usersConfig->get( 'new_usertype' );
        if (!$newUsertype) {
            $newUsertype = 'Registered';
        }

        // Bind the post array to the user object
        if (!$user->bind( JRequest::get('post'), 'usertype' )) {
            JError::raiseError( 500, $user->getError());
        }

        $userClone = clone($user);
        $useractivation = (int) $usersConfig->get( 'useractivation' );
        $this->logme($db, 'user activation es: ' . $useractivation);
        //$block = ($useractivation == 1) ? '1' : '0';
        $block = $useractivation;

        $this->logme($db, 'fecha de nacimiento: ' . $user->get('birthdate'));
        $this->logme($db, 'nombre completo: ' . $user->get('name'));
        $this->logme($db, 'nombre de usuario: ' . $user->get('username'));
        $this->logme($db, 'email: ' . $user->get('email'));
        $this->logme($db, 'email2: ' . $user->get('email2'));
        $this->logme($db, 'sexo: ' . $user->get('sex'));

        $userid = $this->getUserId($db, $user);
        $userExists = $this->userExists($db, $user);
        $requestNewAlias = true;
        $this->logme($db, 'usuario existe: ' . $userExists);
        if (!$userExists || $force == 1) {
            $this->logme($db, 'no existe o force');
            $password = JRequest::getString('password', '', 'post', JREQUEST_ALLOWRAW);

            // if ($password == '' && $externalid != '' && $providerid != 0){
            if ($password == '') {
                $password = JUserHelper::genRandomPassword();
                $block = '0';
            }

            // Set some initial user values
            $user->set('id', 0);
            $user->set('usertype', '');
            $user->set('gid', $authorize->get_group_id( '', $newUsertype, 'ARO' ));

            $date =& JFactory::getDate();
            $user->set('registerDate', $date->toMySQL());
            $user->set('password',md5($password));


            $this->logme($db, 'se va a chequear activacion');


            // If user activation is turned on, we need to set the activation information

            if ($useractivation == '1') {
                jimport('joomla.user.helper');
                $user->set('activation', JUtility::getHash( JUserHelper::genRandomPassword()) );
                $user->set('block', $block);
            }
            $this->logme($db, 'se lo va a guardar');
            // If there was an error with registration, set the message and display form
            if ( !$user->save() ) {
                $this->logme($db, 'no se lo pudo guardar');
                JError::raiseWarning('', JText::_( $user->getError()));
                $this->register();
                return false;
            }
            $this->logme($db, 'se lo guardo exitosamente');
            $userid = $this->getUserId($db, $user);
            $userExists = true;

            // Send registration confirmation mail

            $password = preg_replace('/[\x00-\x1F\x7F]/', '', $password); //Disallow control chars in the email
            UserController::_sendMail($user, $password);


        }


        $this->logme($db, 'por el alias');



        ######### agregado por G2P ##############

        ##### testing ##########
        $this->logme($db, 'el external id es: ' . $externalid);
        $this->logme($db, 'el provider id es: ' . $providerid);

        if ($userExists && $externalid != '' && $providerid != 0) {
        // hay que agregar un alias

            $this->logme($db, 'vamos a gregar un alias');

            // $userid = $this->getUserId($db, $user);

            $externalid = urldecode($externalid);

            $this->insertAlias($block, $userid, $externalid,$user->get('providerid'));

            $this->logme($db, 'alias agregado');

            $requestNewAlias = false;

            // Send registration confirmation mail
            $selectpass = 'select u.password from #__users u where id=' . $userid;
            $db->setQuery($selectpass);
            $dbpass = $db->loadObject();

            $password = preg_replace('/[\x00-\x1F\x7F]/', '', $dbpass->password); //Disallow control chars in the email
            $userClone->set('activation', $passphrase);
        //UserController::_sendMail($userClone, $password);
        }

        //$return = 'index.php' . ($requestNewAlias) ? '?option=com_alias&view=alias&userid=' . $userid .'&'. JUtility::getToken() . '=1' : '';
        ############## fin #####################





        // Everything went fine, set relevant message depending upon user activation state and display message
        if ( $useractivation == '1' ) {
            $message  = JText::_( 'REG_COMPLETE_ACTIVATE' );
        } else {
            $message = JText::_( 'REG_COMPLETE' );
        }


        // tomo los valores especificos del proveedor externo
        $epProveedor = JRequest::getVar('selprovider', '', 'method', 'string');

        if ($epProveedor != 'Zonales') {
            $epUserData = array();
            $epUserData['epusername'] = JRequest::getVar('epusername', '', 'method', 'string');
            $epUserData['epprovider'] = $epProveedor;
            $epUserData['userid'] = $userid;
            $this->login($epUserData);
        }

        $this->endAuthentication();
        // fin ------

        $this->setRedirect('index.php', $message);
    }

    function activate() {
        global $mainframe;

        // Initialize some variables
        $db			=& JFactory::getDBO();
        $user 		=& JFactory::getUser();
        $document   =& JFactory::getDocument();
        $pathway 	=& $mainframe->getPathWay();

        $usersConfig = &JComponentHelper::getParams( 'com_users' );
        $userActivation			= $usersConfig->get('useractivation');
        $allowUserRegistration	= $usersConfig->get('allowUserRegistration');

        // Check to see if they're logged in, because they don't need activating!
        if ($user->get('id')) {
        // They're already logged in, so redirect them to the home page
            $mainframe->redirect( 'index.php' );
        }

        if ($allowUserRegistration == '0' || $userActivation == '0') {
            JError::raiseError( 403, JText::_( 'Access Forbidden' ));
            return;
        }

        // create the view
        require_once (JPATH_COMPONENT.DS.'views'.DS.'register'.DS.'view.html.php');
        $view = new UserViewRegister();

        $message = new stdClass();

        // Do we even have an activation string?
        $activation = JRequest::getVar('activation', '', '', 'alnum' );
        $activation = $db->getEscaped( $activation );

        if (empty( $activation )) {
        // Page Title
            $document->setTitle( JText::_( 'REG_ACTIVATE_NOT_FOUND_TITLE' ) );
            // Breadcrumb
            $pathway->addItem( JText::_( 'REG_ACTIVATE_NOT_FOUND_TITLE' ));

            $message->title = JText::_( 'REG_ACTIVATE_NOT_FOUND_TITLE' );
            $message->text = JText::_( 'REG_ACTIVATE_NOT_FOUND' );
            $view->assign('message', $message);
            $view->display('message');
            return;
        }

        // Lets activate this user
        jimport('joomla.user.helper');
        if (JUserHelper::activateUser($activation)) {
        // Page Title
            $document->setTitle( JText::_( 'REG_ACTIVATE_COMPLETE_TITLE' ) );
            // Breadcrumb
            $pathway->addItem( JText::_( 'REG_ACTIVATE_COMPLETE_TITLE' ));

            $message->title = JText::_( 'REG_ACTIVATE_COMPLETE_TITLE' );
            $message->text = JText::_( 'REG_ACTIVATE_COMPLETE' );
        }
        else {
        // Page Title
            $document->setTitle( JText::_( 'REG_ACTIVATE_NOT_FOUND_TITLE' ) );
            // Breadcrumb
            $pathway->addItem( JText::_( 'REG_ACTIVATE_NOT_FOUND_TITLE' ));

            $message->title = JText::_( 'REG_ACTIVATE_NOT_FOUND_TITLE' );
            $message->text = JText::_( 'REG_ACTIVATE_NOT_FOUND' );
        }

        $view->assign('message', $message);
        $view->display('message');
    }

    /**
     * Password Reset Request Method
     *
     * @access	public
     */
    function requestreset() {
    // Check for request forgeries
        JRequest::checkToken() or jexit( 'Invalid Token' );

        // Get the input
        $email		= JRequest::getVar('email', null, 'post', 'string');

        // Get the model
        $model = &$this->getModel('Reset');

        // Request a reset
        if ($model->requestReset($email) === false) {
            $message = JText::sprintf('PASSWORD_RESET_REQUEST_FAILED', $model->getError());
            $this->setRedirect('index.php?option=com_user&view=reset', $message);
            return false;
        }

        $this->setRedirect('index.php?option=com_user&view=reset&layout=confirm');
    }

    /**
     * Password Reset Confirmation Method
     *
     * @access	public
     */
    function confirmreset() {
    // Check for request forgeries
        JRequest::checkToken() or jexit( 'Invalid Token' );

        // Get the input
        $token = JRequest::getVar('token', null, 'post', 'alnum');

        // Get the model
        $model = &$this->getModel('Reset');

        // Verify the token
        if ($model->confirmReset($token) === false) {
            $message = JText::sprintf('PASSWORD_RESET_CONFIRMATION_FAILED', $model->getError());
            $this->setRedirect('index.php?option=com_user&view=reset&layout=confirm', $message);
            return false;
        }

        $this->setRedirect('index.php?option=com_user&view=reset&layout=complete');
    }

    /**
     * Password Reset Completion Method
     *
     * @access	public
     */
    function completereset() {
    // Check for request forgeries
        JRequest::checkToken() or jexit( 'Invalid Token' );

        // Get the input
        $password1 = JRequest::getVar('password1', null, 'post', 'string', JREQUEST_ALLOWRAW);
        $password2 = JRequest::getVar('password2', null, 'post', 'string', JREQUEST_ALLOWRAW);

        // Get the model
        $model = &$this->getModel('Reset');

        // Reset the password
        if ($model->completeReset($password1, $password2) === false) {
            $message = JText::sprintf('PASSWORD_RESET_FAILED', $model->getError());
            $this->setRedirect('index.php?option=com_user&view=reset&layout=complete', $message);
            return false;
        }

        $message = JText::_('PASSWORD_RESET_SUCCESS');
        $this->setRedirect('index.php?option=com_user&view=zlogin', $message);
    }

    /**
     * Username Reminder Method
     *
     * @access	public
     */
    function remindusername() {
    // Check for request forgeries
        JRequest::checkToken() or jexit( 'Invalid Token' );

        // Get the input
        $email = JRequest::getVar('email', null, 'post', 'string');

        // Get the model
        $model = &$this->getModel('Remind');

        // Send the reminder
        if ($model->remindUsername($email) === false) {
            $message = JText::sprintf('USERNAME_REMINDER_FAILED', $model->getError());
            $this->setRedirect('index.php?option=com_user&view=remind', $message);
            return false;
        }

        $message = JText::sprintf('USERNAME_REMINDER_SUCCESS', $email);
        $this->setRedirect('index.php?option=com_user&view=zlogin', $message);
    }

    function _sendMail(&$user, $password) {
        global $mainframe;

        $db		=& JFactory::getDBO();

        $name 		= $user->get('name');
        $email 		= $user->get('email');
        $username 	= $user->get('username');

        $usersConfig 	= &JComponentHelper::getParams( 'com_users' );
        $sitename 		= $mainframe->getCfg( 'sitename' );
        $useractivation = $usersConfig->get( 'useractivation' );
        $mailfrom 		= $mainframe->getCfg( 'mailfrom' );
        $fromname 		= $mainframe->getCfg( 'fromname' );
        $siteURL		= JURI::base();

        $subject 	= sprintf ( JText::_( 'Account details for' ), $name, $sitename);
        $subject 	= html_entity_decode($subject, ENT_QUOTES);

        if ( $useractivation == 1 ) {
            $message = sprintf ( JText::_( 'SEND_MSG_ACTIVATE' ), $name, $sitename, $siteURL."index.php?option=com_user&task=activate&activation=".$user->get('activation'), $siteURL, $username, $password);
        } else {
            $message = sprintf ( JText::_( 'SEND_MSG' ), $name, $sitename, $siteURL,$username, $password);
        }

        $message = html_entity_decode($message, ENT_QUOTES);

        //get all super administrator
        $query = 'SELECT name, email, sendEmail' .
            ' FROM #__users' .
            ' WHERE LOWER( usertype ) = "super administrator"';
        $db->setQuery( $query );
        $rows = $db->loadObjectList();

        // Send email to user
        if ( ! $mailfrom  || ! $fromname ) {
            $fromname = $rows[0]->name;
            $mailfrom = $rows[0]->email;
        }

        JUtility::sendMail($mailfrom, $fromname, $email, $subject, $message);

        // Send notification to all administrators
        $subject2 = sprintf ( JText::_( 'Account details for' ), $name, $sitename);
        $subject2 = html_entity_decode($subject2, ENT_QUOTES);

        // get superadministrators id
        foreach ( $rows as $row ) {
            if ($row->sendEmail) {
                $message2 = sprintf ( JText::_( 'SEND_MSG_ADMIN' ), $row->name, $sitename, $name, $email, $username);
                $message2 = html_entity_decode($message2, ENT_QUOTES);
                JUtility::sendMail($mailfrom, $fromname, $row->email, $subject2, $message2);
            }
        }
    }
}
?>


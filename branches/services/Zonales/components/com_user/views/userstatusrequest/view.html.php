<?php
// Check to ensure this file is included in Joomla!
defined( '_JEXEC' ) or die( 'Restricted access' );

jimport( 'joomla.application.component.view');

class UserViewUserStatusRequest extends JView {

    function display($tpl = null){
        $externalid = JRequest::getVar('externalid','','method','string');
        $providerid = JRequest::getVar('providerid','','method','int');

        $aliasNotFoundMessage = JText::_('ZONALES_ALIAS_NOT_FOUND');
        $requestMessage = JText::_('ZONALES_STATUS_REQUEST');
        $iamuserMessage = JText::_('ZONALES_STATUS_USER');
        $notuserMessage = JText::_('ZONALES_STATUS_GUEST');

        $urlLogin = 'index.php?option=com_user&view=zlogin&externalid='.urlencode($externalid). '&providerid=' . $providerid.'&' . JUtility::getToken() .'=1';
        $urlRegister = 'index.php?option=com_user&view=register&force=1&externalid='.urlencode($externalid).'&providerid='.$providerid.'&' . JUtility::getToken() .'=1';

        $this->assignRef('urlLogin', $urlLogin);
	$this->assignRef('urlRegister',$urlRegister);
        $this->assignRef('aliasNotFoundMessage',$aliasNotFoundMessage);
        $this->assignRef('requestMessage',$requestMessage);
        $this->assignRef('userMessage',$iamuserMessage);
        $this->assignRef('notUserMessage',$notuserMessage);
        parent::display($tpl);
    }
}

?>

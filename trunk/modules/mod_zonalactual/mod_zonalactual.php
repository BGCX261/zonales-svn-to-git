<?php
/**
 * @version	$Id$
 * @package	Zonales
 * @copyright	Copyright (C) 2009 Mediabit. All rights reserved.
 * @license	GNU/GPL, see LICENSE.php
 *
 * Zonales is free software. This version may have been modified pursuant
 * to the GNU General Public License, and as distributed it includes or
 * is derivative of works licensed under the GNU General Public License or
 * other free or open source software licenses.
 * See COPYRIGHT.php for copyright notices and details.
 */

// no direct access
defined('_JEXEC') or die('Restricted access');

require_once (JPATH_BASE.DS.'components'.DS.'com_zonales'.DS.'helper.php');
jimport ('joomla.filesystem.file');

// parametros
$showLabel = $params->get('show_label', 'show_label');
$labelText = $params->get('label_text', 'label_text');

// lista de zonales, zonal actualmente seleccionado
$helper = new comZonalesHelper();
$zonal = $helper->getZonal();
$zonalName = $zonal ? $zonal->label : $params->get('nozonal_text');

// template
$app =& JFactory::getApplication();
$template = $app->getTemplate();

// valores para los parametros del template
$tparams = new JParameter(JFile::read(JPATH_BASE.DS.'templates'.DS.
		$template.DS.'params.ini'));

$mainColor = $tparams->get('mainColor', null);
if ($mainColor) $mainColor = '_' . $mainColor;

require(JModuleHelper::getLayoutPath('mod_zonalactual'));

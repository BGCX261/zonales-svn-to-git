<?php require_once("config.php")?>
<head>
    <meta content="text/html; charset=utf-8" http-equiv="Content-Type">
    <title>Content Manager Utilities - Zonales</title>
    <link href="ZoneStyle.css" rel="stylesheet" type="text/css" />
    <link type="text/css" rel="stylesheet" href="content.css">
    <link type="text/css" rel="stylesheet" href="global.css">
	<script type="text/javascript">
			var host = '<?php echo $tomcat_host;?>';
			var port = '<?php echo $tomcat_port;?>';
	</script>
    <script type="text/javascript" src="mootools.js"></script>
    <script type="text/javascript" src="cmutils.js"></script>	
    </head>

    <body>
        <div id="container"/>
        <div id="header">
            <div id="logo">
                <a href="/CMUtils"><img alt="Zonales" src="logo.gif"></a>
            </div>
            <a style="float:right" href="index.php">Gestión de Extracciones</a>
            <br>
            <a style="float:right" href="crud_crawl_configs.php">Gestión de Fuentes de Extracción</a>
            <br>
            <a style="float:right" href="http://<?php echo $host; ?>:<?php echo $tomcat_port; ?>/ZCrawlScheduler/listJobs">Lista de extracciones programadas en Scheduler</a>
            <br>
            <a style="float:right" href="twutils.php">Twitter Utilities</a>
        </div>
        <div class="clearfix" id="wrapper">
            <div id="container_content">
                <table width="100%" cellspacing="0" cellpadding="0" border="0" class="advanced_search">
                    <tbody>
                        <tr>
                            <td colspan="2"><h2>Get Facebook User ID</h2></td>
                        </tr>
                        <tr>
                            <td width="35%" class="label"><label>Username</label></td>
                            <td class="input"><input type="text" style="width: 100%;" id="getIdUsername" value=""></td>
                        </tr>
                        <tr>
                            <td colspan="2"><input style="width: 100%" type="button" value="Search" onclick="getFacebookUserId();"></td>
                        </tr>
                        <tr><td colspan="2"><h2 style="margin-top: 0.7em;">Show Facebook User Profile</h2></td></tr>
                        <tr>
                            <td width="35%" class="label"><label>User Id</label></td>
                            <td class="input"><input type="text" style="width: 100%;" id="getProfileUserId" value=""></td>
                        </tr>
                        <tr>
                            <td colspan="2"><input style="width: 100%" type="button" value="Show Profile" onclick="showFacebookUserProfile()"></td>
                        </tr>
                        <tr><td colspan="2"><h2 style="margin-top: 0.7em;">Show Facebook User Wall Commenters</h2></td></tr>
                        <tr>
                            <td width="35%" class="label"><label>User Id</label></td>
                            <td class="input"><input type="text" style="width: 100%;" id="getCommentersUserId" value=""></td>
                        </tr>
                        <tr>
                            <td colspan="2"><input style="width: 100%" type="button" value="Show Commenters" onclick="showFacebookUserCommenters()"></td>
                        </tr>
                        <tr><td colspan="2"><h2 style="margin-top: 0.7em;">Show Facebook Commenters by Keywords</h2></td></tr>
                        <tr>
                            <td width="35%" class="label"><label>Keywords</label></td>
                            <td class="input"><input type="text" style="width: 100%;" id="getCommentersKeywords" value=""></td>
                        </tr>
                        <tr>
                            <td colspan="2"><input style="width: 100%" type="button" value="Show Commenters" onclick="showFacebookCommentersByKeywords()"></td>
                        </tr>
                        <tr><td colspan="2"><h2 style="margin-top: 0.7em;">Otras utilidades</h2></td></tr>
                        <tr><td colspan="2"><a href="qbuilder.php">Query builder</a></td></tr>
                    </tbody>
                </table>
            </div>
            <div id="results_content">
            </div>
        </div>
</body>

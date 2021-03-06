package reservasjavafx;

import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.scene.shape.*;
import javafx.stage.*;

import reservasjavafx.menu.*;

import javafx.io.http.HttpRequest;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import java.lang.Void;

import domain.forms.nameform.NameForm;
import java.util.StringTokenizer;
import java.util.Vector;
import reservasjavafx.CustomResource;


var image = Image{
    url:"{__DIR__}puzzle_picture.jpg"};

var maxCol:Integer = ((image.width as Integer) / 100) - 1;
var maxRow:Integer = ((image.height as Integer) / 100) - 1;

var mesa: Integer = 0;

var gapX = 10;
var gapY = 40;
var TEXT_COLOR = Color.WHITE;

var stageDragInitialX:Number;
var stageDragInitialY:Number;

var imagen:ImageView = ImageView {
            translateX: gapX
            translateY: gapY
            image: Image { url: "{__DIR__}images/restaurante2.jpg" }
            onMouseClicked: function(e:MouseEvent):Void {
                coords = "{e.x}-{e.y}";
            }

};

// Primer menu flotante
var popupMenu:popupMenu = popupMenu {
    animate:true
    corner:20
    padding:8
    borderWidth:4
    opacity: 0.9
    stroke: Color.web("#FF9900");
    fill: Color.WHITE;
    containerWidth: bind imagen.image.width
    containerHeight: bind imagen.image.height
};

var coords :String;

var g:Group = Group {
    content: [

        // the background and top header
        Rectangle {
            fill: Color.web("#FF9900")
            width: imagen.image.width+gapX*2
            height: 400 },
        Text {
            x: gapX+0
            y:17 content: bind coords
            fill: TEXT_COLOR },
        Line {
            stroke: TEXT_COLOR
            startX: 0
            endX: imagen.image.width+gapX*2
            startY: 30
            endY: 30 },

        // the actual image to be adjusted
        imagen,
        CustomResource {
            cursor: Cursor.HAND
            points: [   x(0),y(190),
                        x(71),y(172),
                        x(110),y(178),
                        x(113),y(223),
                        x(0),y(266)     ]
            fill: Color.TRANSPARENT
            stroke: Color.RED
            nroRecurso: 1
            onMouseClicked:function(e) {
                mousePressed(e);
            }
        },
        CustomResource {
            cursor: Cursor.HAND
            points: [   x(128),y(161),
                        x(165),y(147),
                        x(213),y(151),
                        x(211),y(161),
                        x(171),y(172),
                        x(128),y(171) ]
            fill: Color.TRANSPARENT
            stroke: Color.RED
            nroRecurso: 2
            onMouseClicked:function(e) {
                mousePressed(e);
            }
        },
        CustomResource {
            cursor: Cursor.HAND
            points: [   x(210),y(141),
                        x(246),y(131),
                        x(279),y(135),
                        x(246),y(151),
                        x(210),y(147)     ]
            fill: Color.TRANSPARENT
            stroke: Color.RED
            nroRecurso: 3
            onMouseClicked:function(e) {
                mousePressed(e);
            }
        },
        CustomResource {
            cursor: Cursor.HAND
            points: [   x(342),y(135),
                        x(355),y(130),
                        x(399),y(128),
                        x(394),y(138) ]
            fill: Color.TRANSPARENT
            stroke: Color.RED
            nroRecurso: 4
            onMouseClicked:function(e) {
                mousePressed(e);
            }
        },
        CustomResource {
            cursor: Cursor.HAND
            points: [   x(271),y(127),
                        x(280),y(123),
                        x(310),y(124),
                        x(312),y(131),
                        x(299),y(135)   ]
            fill: Color.TRANSPARENT
            stroke: Color.RED
            nroRecurso: 5
            onMouseClicked:function(e) {
                mousePressed(e);
            }
        },
        CustomResource {
            cursor: Cursor.HAND
            points: [   x(450),y(137),
                        x(450),y(130),
                        x(507),y(128),
                        x(526),y(136)   ]
            fill: Color.TRANSPARENT
            stroke: Color.RED
            nroRecurso: 6
            onMouseClicked:function(e) {
                mousePressed(e);
            }
        },
        Ellipse {
            cursor: Cursor.HAND
            centerX: x(377)
            centerY: y(172)
            radiusX: 90
            radiusY: 20
            stroke: Color.RED
            fill: Color.TRANSPARENT
            onMouseClicked:function(e) {
                mesa = 7;
                mousePressed(e);
            }
        }

        popupMenu
    ]
};

function x(x:Integer):Integer {
    return x + gapX;
}

function y(y:Integer):Integer {
    return y + gapY;
}

function clickMenuItem(Void):Void {
    java.lang.System.out.println("CLICK!");
}

var getRequest: HttpRequest;

function startConsultaRequest(e:MouseEvent, url: String) {
    getRequest = HttpRequest {

        location: url;

        onStarted: function() {
            println("onStarted - started performing method: {getRequest.method} on location: {getRequest.location}");
        }

        onConnecting: function() { println("onConnecting") }
        onDoneConnect: function() { println("onDoneConnect") }
        onReadingHeaders: function() { println("onReadingHeaders") }
        onResponseCode: function(code:Integer) { println("onResponseCode - responseCode: {code}") }
        onResponseMessage: function(msg:String) { println("onResponseMessage - responseMessage: {msg}") }

        onResponseHeaders: function(headerNames: String[]) {
            println("onResponseHeaders - there are {headerNames.size()} response headers:");
            for (name in headerNames) {
                println("    {name}: {getRequest.getResponseHeaderValue(name)}");
            }
        }

        onReading: function() { println("onReading") }

        onToRead: function(bytes: Long) {

            if (bytes < 0) {
                println("onToRead - Content length not specified by server; bytes: {bytes}");
            } else {
                println("onToRead - total number of content bytes to read: {bytes}");
            }
        }

        // The onRead callback is called when some more data has been read into
        // the input stream's buffer.  The input stream will not be available until
        // the onInput call back is called, but onRead can be used to show the
        // progress of reading the content from the location.

        onRead: function(bytes: Long) {
            // The toread variable is non negative only if the server provides the content length
            def progress =
                if (getRequest.toread > 0) "({(bytes * 100 / getRequest.toread)}%)" else "";
                println("onRead - bytes read: {bytes} {progress}");
        }

        // The content of a response can be accessed in the onInput callback function.
        // Be sure to close the input sream when finished with it in order to allow
        // the HttpRequest implementation to clean up resources related to this
        // request as promptly as possible.

        onInput: function(is: java.io.InputStream) {
            // use input stream to access content here.
            // can use input.available() to see how many bytes are available.
            try {
                var message = new String();
                var item:menuItem;

                while(is.available() > 0)
                    message += Character.toString(Character.valueOf(is.read()));

                var tokenizer : StringTokenizer = new StringTokenizer(message, ";");

                popupMenu.deleteOptions();
                while (tokenizer.hasMoreTokens()) {
                    var texto : String = tokenizer.nextToken();
                    item = menuItem {
                        //call: clickMenuItem
                        text: texto
                        customCall: startConfirmaRequest
                    };

                    if(javafx.util.Sequences.indexOf(popupMenu.content, item) == -1) {
                        insert item into popupMenu.content;
                    }
                }
                popupMenu.event = e;

            } finally {
                is.close();
            }
        }

        onException: function(ex: java.lang.Exception) {
            println("onException - exception: {ex.getClass()} {ex.getMessage()}");
        }

        onDoneRead: function() {
            println("onDoneRead")
        }

        onDone: function() {
            println("onDone") ;
            getRequest.stop();
        }
    };
    getRequest.start();
}

function startConfirmaRequest(texto:String):Void {

    getRequest = HttpRequest {

        location: "http://localhost:8080/pruebasJava/Main?accion=confirma&locacion=resto&nroMesa={mesa}&horario={texto}";

        onStarted: function() {
            println("onStarted - started performing method: {getRequest.method} on location: {getRequest.location}");
        }

        onConnecting: function() { println("onConnecting") }
        onDoneConnect: function() { println("onDoneConnect") }
        onReadingHeaders: function() { println("onReadingHeaders") }
        onResponseCode: function(code:Integer) { println("onResponseCode - responseCode: {code}") }
        onResponseMessage: function(msg:String) { println("onResponseMessage - responseMessage: {msg}") }

        onResponseHeaders: function(headerNames: String[]) {
            println("onResponseHeaders - there are {headerNames.size()} response headers:");
            for (name in headerNames) {
                println("    {name}: {getRequest.getResponseHeaderValue(name)}");
            }
        }

        onReading: function() { println("onReading") }

        onToRead: function(bytes: Long) {

            if (bytes < 0) {
                println("onToRead - Content length not specified by server; bytes: {bytes}");
            } else {
                println("onToRead - total number of content bytes to read: {bytes}");
            }
        }

        // The onRead callback is called when some more data has been read into
        // the input stream's buffer.  The input stream will not be available until
        // the onInput call back is called, but onRead can be used to show the
        // progress of reading the content from the location.

        onRead: function(bytes: Long) {
            // The toread variable is non negative only if the server provides the content length
            def progress =
                if (getRequest.toread > 0) "({(bytes * 100 / getRequest.toread)}%)" else "";
                println("onRead - bytes read: {bytes} {progress}");
        }

        // The content of a response can be accessed in the onInput callback function.
        // Be sure to close the input sream when finished with it in order to allow
        // the HttpRequest implementation to clean up resources related to this
        // request as promptly as possible.

        onInput: function(is: java.io.InputStream) {
            // use input stream to access content here.
            // can use input.available() to see how many bytes are available.
           
        }

        onException: function(ex: java.lang.Exception) {
            println("onException - exception: {ex.getClass()} {ex.getMessage()}");
        }

        onDoneRead: function() {
            println("onDoneRead")
        }

        onDone: function() {
            println("onDone") ;
            getRequest.stop();
        }
    };
    getRequest.start();
}

function mousePressed(e:MouseEvent) {
    var recurso: CustomResource = e.node as CustomResource;

    if(e.button == MouseButton.SECONDARY) {
        mesa = recurso.nroRecurso as Integer;
        startConsultaRequest(e, "http://localhost:8080/pruebasJava/Main?accion=consulta&locacion=resto&nroMesa={recurso.nroRecurso as Integer}");
    }
}

    var personBean1:domain.model.PersonBean = new domain.model.PersonBean();
    var personBean2:domain.model.PersonBean = new domain.model.PersonBean();
    println("1 -----------> personBean1 {personBean1} firstname = {personBean1.getFirstName()}");
    println("2 -----------> personBean2 {personBean2} firstname = {personBean2.getFirstName()}");
    println("====================================================");
    personBean1.setFirstName("SpongeBob");
    personBean1.setLastName("SquarePants");
    personBean1.setMiddleName("Nickelodeon");
    personBean1.setSuffixName("Jr.");

    personBean2.setFirstName("Squidward");
    personBean2.setLastName("Tentacles");
    personBean2.setMiddleName("Nickelodeon");
    personBean2.setSuffixName("Sr.");
    println("3 -----------> personBean1 {personBean1} firstname = {personBean1.getFirstName()}");
    println("4 -----------> personBean2 {personBean2} firstname = {personBean2.getFirstName()}");
    println("====================================================");
    var value:String;
    var slideFormX:Float;
    var personForm:NameForm = NameForm{
        presentationModel:domain.model.personpresentationmodel.EditPersonPM{}
        translateX: bind slideFormX
    };
    personForm.presentationModel.jBean = personBean2;

    println("9 -----------> personBean1 {personBean1} firstname = {personBean1.getFirstName()}");
    println("10 -----------> personBean2 {personBean2} firstname = {personBean2.getFirstName()}");
    println("====================================================");

    var diff:Float = bind personForm.boundsInParent.width;
    var slideLeft:Timeline = Timeline {
            repeatCount: 1
            keyFrames : [
                KeyFrame {
                    time : 0s
                    canSkip : true
                    values: slideFormX => 0
                },
                KeyFrame {
                    time : 350ms
                    canSkip : true
                    values: slideFormX => -diff tween Interpolator.EASEIN
                }
            ]
        };
    var slideRight:Timeline = Timeline {
            repeatCount: 1
            keyFrames : [
                KeyFrame {
                    time : 0s
                    canSkip : true
                    values: slideFormX => -diff
                }
                KeyFrame {
                    time : 350ms
                    canSkip : true
                    values: slideFormX => 0 tween Interpolator.EASEBOTH
                }
            ]
        };



    var nextButton:Button = Button {
            translateX: bind myScene.width - nextButton.width - 5
            translateY: bind myScene.height - nextButton.height - 5
            text: " Next >> "
            action: function() {
                personForm.setVisibleErrWarnNodes(false);
                slideRight.stop();
                slideLeft.play();
            }
        };

    var backButton:Button = Button {
            translateX: bind myScene.width - backButton.width - 5 - nextButton.width - 5
            translateY: bind myScene.height - backButton.height - 5
            text: " << Back "
            action: function() {
                slideLeft.stop();
                slideRight.play();
                personForm.setVisibleErrWarnNodes(true);
                personForm.toBack();
            }
        };

    var mainOpacity : Float = 0.0;

    /*var startTransitionApp = Timeline {
       repeatCount: 1
       keyFrames : [
           KeyFrame {
               time : 0s
               canSkip : true
               values: mainOpacity => 0.0
           }
           KeyFrame {
               time : 4s
               canSkip : true
               values: mainOpacity => 1.0 tween Interpolator.LINEAR
           }
       ]
    }
    startTransitionApp.play();*/


var myScene:Scene = Scene {
    content: [g, personForm, backButton, nextButton]
}

// show it all on screen
var stage:Stage = Stage {
    style: StageStyle.UNDECORATED
    visible: true
    scene: myScene
}

personForm.presentationModel.mainScene = myScene;
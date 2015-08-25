function ZIRClient(a,b){this.host="localhost";this.port=38081;this.firstIndexTime=null;this.lastIndexTime=null;this.minRelevance=null;this.firstModifiedTime=null;this.timeInterval=10800001;this.rows=20;this.ids=new Array();this.socket=a;this.sessionId=b;this.setSolrRows=function(c){this.rows=c};this.getSolrRows=function(){return this.rows};this.addIdToSolrSearch=function(c){this.ids.push(c)};this.clearSolrIds=function(){this.ids.empty()};this.setFirstIndexTime=function(c){this.firstIndexTime=c};this.getFirstIndexTime=function(){return this.firstIndexTime};this.setFirstModifiedTime=function(c){this.firstModifiedTime=c};this.getFirstModifiedTime=function(){return this.firstModifiedTime};this.setLastIndexTime=function(c){this.lastIndexTime=c};this.getLastIndexTime=function(){return this.lastIndexTime};this.setMinRelevance=function(c){this.minRelevance=c};this.getMinRelevance=function(){return this.minRelevance};this.getSolrDate=function(d){var c=new Date(d);return c.getFullYear()+"-"+complete(c.getMonth()+1)+"-"+complete(c.getDate())+"T"+complete(c.getHours())+":"+complete(c.getMinutes())+":"+complete(c.getSeconds())+"."+c.getMilliseconds()+"Z"};this.getSolrSort=function(d){var c="";if(d=="portada"){c="score+desc"}if(d=="enlared"||d=="noticiasenlared"){c="modified+desc"}if(d=="relevantes"||d=="noticiasenlaredrelevantes"){c="relevance+desc"}return c};this.getSolrSources=function(d){var c="";if(d=="enlared"||d=="relevantes"){c="q=source:(facebook+OR+twitter)"}if(d=="noticiasenlared"||d=="noticiasenlaredrelevantes"){c="q=!source:(facebook+OR+twitter)"}if(d=="portada"){c="bq=(tags:(Portada)+AND+modified:[NOW-48HOURS+TO+*])^999999+OR+(tags:(Portada)+AND+modified:[NOW-7DAYS TO NOW-48HOURS])^999"}if(d=="geoActivos"){c="q="}return c};this.getSolrZones=function(c){var d="";if(zcGetTab()!="geoActivos"){if(typeof(c)=="undefined"||!c||c==""){return""}d='+AND+zone:"';d+=c.replace(/_/g," ").capitalize();d+='"'}return d.replace(" ","+")};this.getSolrKeyword=function(c){if(typeof(c)=="undefined"||!c||c==""||c==null){return""}var d="+AND+"+c.replace(/ /g,"+");return d};this.getSolrIds=function(){var c="";if(zcGetTab()=="geoActivos"){this.ids.each(function(e,d){c+=(d!=0?"+OR+":"")+'id:"'+encodeURIComponent(e)+'"'})}return c};this.getSolrRange=function(e,d){var c="";if(e=="enlared"||e=="noticiasenlared"||e=="portada"){if(!d){c=(this.lastIndexTime?"&fq=indexTime:["+this.getSolrDate(this.lastIndexTime+this.timeInterval)+"+TO+*]":"")}else{if(e=="portada"){c="&fq=modified:[*+TO+"+reduceMilli(this.firstModifiedTime.replace("Z",".000Z"))+"]"}else{c="&fq=indexTime:[*+TO+"+reduceMilli(this.firstIndexTime)+"]"}}}if(e=="relevantes"||e=="noticiasenlaredrelevantes"){if(!d){c=(this.lastIndexTime?"&fq=indexTime:["+this.getSolrDate(this.lastIndexTime+this.timeInterval)+"+TO+*]":"&fq=modified:["+($("tempoSelect").value!="0"?"NOW-"+($("tempoSelect").value):"*")+"+TO+*]")+"&fq=!relevance:0+AND+relevance:["+(this.minRelevance?this.minRelevance:0)+"+TO+*]"}else{c="&fq=indexTime:[*+TO+"+reduceMilli(this.firstIndexTime)+"]&fq=!relevance:0+AND+relevance:[*+TO+"+(this.minRelevance?this.minRelevance:0)+"]"}}return c};this.getSolrBoostQuery=function(d){var c="";return c};this.getSolrUrl=function(f,c,e,d){var g="/solr/select?indent=on&version=2.2&start=0&fl=*%2Cscore&rows="+this.rows+"&qt=zonalesContent&sort="+this.getSolrSort(f)+"&wt=json&explainOther=&hl.fl=&"+this.getSolrSources(f)+this.getSolrZones(c)+this.getSolrKeyword(d)+this.getSolrIds()+this.getSolrRange(f,e)+this.getSolrBoostQuery(f);return g};this.loadSolrPost=function(d,c){this.socket.emit("getSolrPost",{id:d,method:"GET"},function(f){console.log(f);var e;f.response.docs.each(function(g){e=JSON.parse(g.verbatim)});c(e)})};this.loadMoreSolrPost=function(){this.socket.emit("loadMorePosts",{sessionId:this.sessionId,method:"GET"})};this.loadNewSolrPost=function(){this.socket.emit("loadNewPosts",{sessionId:this.sessionId,method:"GET"})};this.resetStart=function(){this.socket.emit("resetStart",{sessionId:this.sessionId,method:"GET"})};this.getSolrHost=function(){return this.host};this.getSolrPort=function(){return this.port}};
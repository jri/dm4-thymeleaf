
DeepaMehta 4 Thymeleaf
======================

A DeepaMehta 4 plugin for server-side HTML generation based on the Thymeleaf template engine.

Simple demo application:  
<https://github.com/jri/dm4-thymeleaf-demo>

DeepaMehta 4 is a platform for collaboration and knowledge management.  
<https://github.com/jri/deepamehta>

Thymeleaf template engine:  
<http://www.thymeleaf.org/>


Version History
---------------

**0.6** -- Aug 04, 2016

* Support for setting up many `BundleResourceResolvers`
* Resolve included th:fragments by their template name only
* Thymeleaf Upgrade: 2.0.18 -> 2.1.3

**0.5** -- Apr 17, 2016

* Renamed from "DM4 Web Activator" to "DM4 Thymeleaf".
* Compatible with DeepaMehta 4.8

**0.4.6** -- Oct 24, 2015

* Compatible with DeepaMehta 4.7

**0.4.5** -- Aug 19, 2015

* Compatible with DeepaMehta 4.6

**0.4.4** -- Dec 2, 2014

* Fix: Thymeleaf logging via SLF4J works.
* Compatible with DeepaMehta 4.4

**0.4.3** -- Jun 8, 2014

* Compatible with DeepaMehta 4.3

**0.4.2** -- Feb 18, 2014

* Compatible with DeepaMehta 4.2

**0.4.1** -- Dec 14, 2013

* Compatible with DeepaMehta 4.1.3

**0.4** -- Sep 2, 2013

* Access session attributes from within template.
* Concurrent requests to the same web application.
* Deployment of more than one web application at the same time.
* Updated Thymeleaf 2.0.16 -> 2.0.18
* Compatible with DeepaMehta 4.1.1

**0.3.1** -- Mar 19, 2013

* Updated Thymeleaf 2.0.14 -> 2.0.16
* Uses deepamehta-plugin-parent as the parent POM.
* Compatible with DeepaMehta 4.1

**0.3** -- Dec 24, 2012

* Compatible with DeepaMehta 4.0.13

**0.2** -- Nov 17, 2012

* Hides Thymeleaf from the webapp developer. Uses Jersey's Viewable abstraction instead.
* The WebActivatorPlugin base class replaces the ThymeleafService.
* Project is renamed to "DeepaMehta 4 Web Activator" (formerly "DeepaMehta 4 Thymeleaf").
* Compatible with DeepaMehta 4.0.13-SNAPSHOT (branch "master" or "neo4j-1.8")

**0.1** -- Nov 16, 2012

* Provides ThymeleafService
* Compatible with DeepaMehta 4.0.13-SNAPSHOT (branch "master" or "neo4j-1.8")


------------
Jörg Richter & Malte Reißig<br/>
Aug 04, 2016

# Aggregation Plugin for DWR

This plugin aggregates all DWR interface scripts into single JS resource including engine.js and dtoall.js 
to reduce amount of http requests.

__Supported DWR version__: plugin supports [DWR 3.0 RC2](http://directwebremoting.org/dwr/downloads/index.html "The current best version of DWR"). 
It wasn't tested with [DWR 3.0 RC3](http://oss.sonatype.org/content/repositories/snapshots/org/directwebremoting/dwr/3.0.0-rc3-SNAPSHOT/ "Development version") 
since it's in development stage.

## Usage
### Add DWR aggregation plugin to your local Maven repository
In command line perform:

1. git clone git&#64;github.com:oraz/dwr-aggregation.git

2. mvn install

Or download [zip archive](https://github.com/oraz/dwr-aggregation/archive/master.zip "Master zip") 
with sources, unzip it and run mvn install.

### Add dependency in your project
```xml
<dependency>
    <groupId>dwr-aggregation</groupId>
    <artifactId>dwr-aggregation</artifactId>
    <version>1.0</version>
</dependency>
````

### Configure DWR servlet in web.xml
```xml
<servlet>
    <!-- DWR servlet definition here -->
    <init-param>
        <param-name>url:/dwr-aggregated.js</param-name>
        <param-value>org.dwr.aggregation.impl.DwrAggregationHandler</param-value>
    </init-param>
    <init-param>
        <param-name>org.directwebremoting.extend.CreatorManager</param-name>
        <param-value>org.dwr.aggregation.impl.DebugModeIgnoredCreatorManager</param-value>
    </init-param>
</servlet>
````
If you configured DWR to [map Java classes to JavaScript classes](http://directwebremoting.org/dwr/documentation/server/configuration/dwrxml/converters/bean.html#mappingJavaToJavaScript "Mapping Java classes to JavaScript classes") 
you should add one more param to reduce size of aggregated script.
```xml
<servlet>
    <!-- DWR servlet definition here -->
    <init-param>
        <param-name>generateDtoClasses</param-name>
        <param-value>dtoall</param-value>
    </init-param>
</servlet>
````

### Change your html to load all interfaces as single js resource.
Was:
```html
<script src="/dwr/engine.je"></script>
<script src="/dwr/dtoall.je"></script>
<script src="/dwr/interface/userManager.je"></script>
<script src="/dwr/interface/cityManager.je"></script>
````
Now:
```html
<script src="/dwr/dwr-aggregated.je"></script>
````

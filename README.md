# Aggregation Plugin for DWR


This plugin aggregates all DWR modules into single JS resource including engine.js and dtoall.js

__Warning__: Plugin supports [DWR 3.0 RC2] (http://directwebremoting.org/dwr/downloads/index.html "The current best version of DWR") and wasn't tested with [DWR 3.0 RC3] (http://oss.sonatype.org/content/repositories/snapshots/org/directwebremoting/dwr/3.0.0-rc3-SNAPSHOT/ "Development version") since it's under development.

## Usage
### Install DWR aggregation to your local Maven repository
In command line perform:
1. git clone ...
2. mvn install

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
    <init-param>
        <param-name>url:/dwr-aggregated.js</param-name>
        <param-value>org.dwr.aggregation.impl.DwrAggregationHandler</param-value>
    </init-param>
    <init-param>
        <param-name>org.directwebremoting.extend.CreatorManager</param-name>
        <param-value>org.dwr.aggregation.impl.DebugModeIgnoredCreatorManager</param-value>
    </init-param>
    <init-param>
        <param-name>generateDtoClasses</param-name>
        <param-value>dtoall</param-value>
    </init-param>
</servlet>
````



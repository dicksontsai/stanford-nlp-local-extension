# Stanford Parser Servlet

A simple Java [servlet](https://javaee.github.io/javaee-spec/javadocs/javax/servlet/http/HttpServlet.html)
that parses sentences based on Stanford NLP's parser. I use Java here, because
Stanford CoreNLP is distributed as a `.jar` (Java archive) file.

Below is one way of running this Java servlet through [Apache Tomcat](http://tomcat.apache.org/).

## Setup

### Tomcat Setup

_Tomcat is a Java servlet container. The instructions here are not specific to this repo._

In this section, you will install and start a local Tomcat server to serve this servlet (default port is 8080).

1. Download Tomcat from https://tomcat.apache.org/download-90.cgi
1. Add `$CATALINA_HOME` to your `.bashrc`/`.zshrc` pointing to the downloaded directory.
1. You may need to run `chmod +x $CATALINA_HOME/bin/*.sh` to execute those scripts.
1. Add manager roles to Tomcat by editing `$CATALINA_HOME/conf/tomcat-users.xml`.
   ```
   <role rolename="tomcat"/>
   <role rolename="manager-gui"/>
   <role rolename="manager-script"/>
   <user username="tc" password="123" roles="tomcat,manager-gui"/>
   <user username="tcs" password="456" roles="tomcat,manager-script"/>
   ```
1. Create a new `setenv.sh` file: `$CATALINA_HOME/bin/setenv.sh`.

   1. Locate your JDK installation. I found my MacOS' JDK in `ls /Library/java/ -> /Library/Java/JavaVirtualMachines/jdk1.8.0_251.jdk/Contents/Home`.
   1. Add `JAVA_HOME` to `setenv.sh`:
      ```
      JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_251.jdk/Contents/Home
      ```

1. Start the Tomcat server using `$CATALINA_HOME/bin/startup.sh`. The default port is 8080.

### Repo Setup

1. Download Apache Ant from https://ant.apache.org/bindownload.cgi
   1. Install and add to your path. You should now have `$ANT_HOME` defined.
1. Download Apache Ivy and copy the Ivy jar to `$ANT_HOME/lib`.
1. Copy the file "lib/catalina-ant.jar" from your Tomcat installation into the "lib" directory of your Ant installation.
1. Create a "build.properties" file in your application's top-level
   source directory (or your user login home directory) that defines
   appropriate values for the "manager.password", "manager.url", and
   "manager.username" properties described above. In our case:
   1. manager.username: `tcs`
   1. manager.password: `456`
   1. manager.url: Can leave blank. Defaults to `localhost:8080`.

### Stanford NLP Setup

The `ivy.xml` file should already take care of fetching Stanford NLP's CoreNLP library. However, you will also need
to download the parse model separately. Download the English model at https://nlp.stanford.edu/software/stanford-corenlp-4.0.0-models-english.jar
and copy it to the `lib` directory.

## Build the Servlet and Install to Tomcat

1. Run `ant install`.
1. If you have made changes to the server, run `ant reload`.

## Example Request

Currently, the only supported endpoint is `POST http://localhost:8080/parser/parse`.

- Input: sentence
- Output: JSON object containing a `tree` field and a `postcount` field.

```
curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d "text=The quick brown fox jumps over the lazy dog" http://localhost:8080/parser/parse

{"poscount":{"DT":{"the":2},"JJ":{"brown":1,"quick":1,"lazy":1},"NN":{"dog":1,"fox":1},"IN":{"over":1},"VBZ":{"jumps":1}},
"tree":["(ROOT\n  (S\n    (NP (DT The) (JJ quick) (JJ brown) (NN fox))\n    (VP (VBZ jumps)\n      (PP (IN over)\n        (NP (DT the) (JJ lazy) (NN dog))))))\n"]}
```

## Configuring New Endpoints

`build/WEB-INF/web.xml` has a mapping from routes to Java servlet classes. You
can basically implement your own class and expose it there.

## VSCode Setup

While you can run `ant` through the CLI, working with VSCode is very seamless.
Install the `Ant Target Runner` and `Tomcat For Java` extensions.

### Debugging with `Tomcat For Java`

With `Tomcat For Java`, you can add breakpoints to your code, then issue
requests through cURL/your browser to step through the code line-by-line in
VSCode's debugger.

The `Tomcat For Java` extension shows up as `Tomcat Servers` under the Explorer.

1. Click the plus sign (infotip says "Add Tomcat Server"). Choose `$CATALINA_HOME` from the Finder.
   Now, you should see `$CATALINA_HOME` (in my case, \$CATALINA_HOME=apache-tomcat-9.0.36).
1. You will need to shutdown your regular Tomcat (`$CATALINA_HOME/bin/shutdown.sh`),
   because Tomcat For Java needs port 8080 as well. (I have yet to figure out how
   to change the ports.)
1. Run `ant dist` to create a `.war` file.
1. Right-click `$CATALINA_HOME` under "Tomcat Servers" and select "Debug War Package".
   Choose the `.war` package in the `dist` directory. The debugger should start to run.
1. Issue a POST request using cURL or your browser. The request (**specifically for debugging**)
   looks like `curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d "sentence=The quick brown fox jumps over the lazy dog" http://localhost:8080/parser-0.1-dev/parse`.
   Note the different context name from `Example Request` below.
1. When you are done debugging, make sure to stop the debug server by right-clicking `$CATALINA_HOME` again.

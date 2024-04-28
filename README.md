[![Build Status](https://travis-ci.org/bootique-examples/bootique-kotlin-demo.svg)](https://travis-ci.org/bootique-examples/bootique-kotlin-demo)
# bootique-kotlin-demo

A simple example that explains how to write [Bootique](https://bootique.io) application with [Kotlin](http://kotlinlang.org/). 

*For additional help/questions about this example send a message to
[Bootique forum](https://groups.google.com/forum/#!forum/bootique-user).*

You can find different versions of framework in use at
* [1.x](https://github.com/bootique-examples/bootique-kotlin-demo/tree/2.x)
* [2.x](https://github.com/bootique-examples/bootique-kotlin-demo/tree/2.x)

## Prerequisites

* Java 1.8 or newer.

## Build the Demo

Here is how to build it:

```bash
git clone git@github.com:bootique-examples/bootique-kotlin-demo.git
cd bootique-kotlin-demo
./gradlew installDist
```

## Run the Demo

Now you can check the options available in your app:

```bash
$ ./build/install/bootique-kotlin-demo/bin/bootique-kotlin-demo

NAME
      bootique-kotlin-demo-1.0-SNAPSHOT.jar

OPTIONS
      -c yaml_location, --config=yaml_location
           Specifies YAML config location, which can be a file path or a URL.

      -h, --help
           Prints this message.

      -H, --help-config
           Prints information about application modules and their configuration options.

      -s, --server
           Starts Undertow server.
```
    
And run it:

```bash
$ ./build/install/bootique-kotlin-demo/bin/bootique-kotlin-demo --server --config=classpath:config.bq.kts
```

Then open `http://localhost:9988` in browser.

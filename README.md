#jetty-embedded

This project will configure and deploy an exploded WAR webapp inside an embedded jetty. For a full explanation including other alternative solutions, you should <a href="http://blog.extrema.com/lightweight-java-webapp">read the blog post</a>.

The project is composed of an embedded jetty and a deployable web application using web 3.0 annotations.

##Getting started

To create the webapp and deploy inside Jetty, execute the following (you can change `idea` with `eclipse`):

```bash
cd helloworld
gradle idea
cd jetty
gradle jetty
```

To test your app, open a web browser at `http://localhost:8080/helloworld/foo`

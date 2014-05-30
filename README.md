zenfoot
=============

zenfoot is a bet application for the Football World Cup

# Dependencies

Use restx forked version on [github](https://github.com/boillodmanuel/restx/)
In order to use the fork version, clone the restx fork to any directory, and use

```
 mvn -DskipTests clean install
```
The pom.xml in zenfoot project is set to correspond to that forked version.

# Deployment

Required :
-App engine SDK
-npm and node.js if you want to modify the UI significantly

Locally :
---

Download app engine SDK and add it to your PATH.

Then run 

```
mvn clean -DskipTests package
dev_appserver.sh target/zen-foot-1
```

You can run the static files with nodes (recommended), so you don't have to redeploy the whole project with GAE every
time you modify you client.

To do this, you have to install node, and then in src/main/webapp run :

If it is the first time you run it, you'll first have to run the following line to load the dependencies listed in the 
package.json :
```
npm install
```
And then every time you start deploying locally, run the following :
```
node server.js
```
The static files will be running on localhost:9000, and every call to the api will be redirected to localhost:8080. So you can
now test the app on localhost:9000

Alternatively, you can use Grunt with the provided `gruntfile.coffee` to run both the server and a watch task that reloads the browser on every client-side code file modification.
```
npm install
grunt
```

On Google App Engine (GAE) :
---

If you are contributing to zenfoot, you won't have to deploy the application to app engine, as we are functioning
with pull requests. You can see the deployed app [here](http://1-dot-zen-foot.appspot.com/)
```
mvn package
appcfg.sh --no_cookies --email=<EMAIL> update target/gae-restx-poc-0-1

```
remove --no_cookies if you want to type your PW only once.

# How to log in ?

You can login with these two users :

Admin account : 

 user : raphael.martignoni@zenika.com (for older commits only)

 PW : 2205
 
 For newest commits, the admin is admin@zenika.com (same password)
 
 Basic user (gambler) : 

user : jean.bon@zenika.com

PW : 999

# Bug, Backlog, Trello

The backlog is on the trello, which is also used for bug reports.

About bugs :

- if they are related to an "in progress" or "ready for acceptance story", please add a checklist or a comment
into the checklist

- Otherwise, if the bug is related to a done story (in "Done" or "sprint X (done)", or is not related to any story,
 you can create a new ticket to add to the backlog.

# Any comment to make about user stories ?

Feel free to add a comment on the trello !

# Architecture of the application

For more information about the architecture of the project, have a look at the [architecture document](./architecture.md)

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

Locally :
---

Download app engine SDK and add it to your PATH.

Then run 

```
mvn -DskipTests package
dev_appserver.sh target/zen-foot-1
```

You can run the static files with nodes (recommended), so you don't have to redeploy the whole project with GAE every
time you modify you client.

To do this, you have to install node, and then in src/main/webapp run :

```
node server.js
```

On Google App Engine (GAE) :
---

```
mvn package
appcfg.sh --no_cookies --email=<EMAIL> update target/gae-restx-poc-0-1

```
remove --no_cookies if you want to type your PW only once.

# How to log in ?

Two accounts are registered in the database :
user : raphael.martignoni@zenika.com
PW : 2205

user : jean.bon@zenika.com
PW : 999

# Bug, Backlog, Trello

The backlog is on the trello, which is also used for bug reports.

About bugs :

- if they are related to an "in progress" or "ready for acceptance story", please add a checklist or a comment
into the checklist

- Otherwise, if the bug is related to a done story (in "Done" or "sprint X (done)", you can create a new ticket to add to
the backlog

# Any comment to make about user stories ?

Feel free to add a comment on the trello !


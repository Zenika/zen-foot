zenfoot
=============

zenfoot is a bet application for the Football World Cup
# Dependencies

Use restx forked version on [github](https://github.com/boillodmanuel/restx/)

# Deployment

Locally :
---

Download app engine SDK and add it to your PATH.

Then run 

```
mvn -DskipTests package
dev_appserver.sh target/zen-foot-1
```

On Google App Engine (GAE) :
---

```
mvn package
appcfg.sh --no_cookies --email=<EMAIL> update target/gae-restx-poc-0-1

```
remove --no_cookies if you want to type your PW only once.

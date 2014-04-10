gae-restx-poc
=============

POC for deployment of RESTX application on google app engine

# Dependencies

Use restx forked version on my [github](https://github.com/boillodmanuel/restx/)

# Deployment

```
mvn package
appcfg.sh --no_cookies --email=<EMAIL> update target/gae-restx-poc-0-1

```

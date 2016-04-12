# Development team workflow plan for containerized micro-services architecture

* [Development](#dev)
  * [Base images](#bi)
  * [Services as modules](#sam)
  * [Images for services](#ifs)
  * [Local testing](#lt)
* [Continuous Integration](#ci)
  * [Tests running in containers](#tric)
  * [Building and distributing images](#badi)
* [Continuous Deployment](#dep)
  * [Cluster management](#cm)
  * [Scaling](#s)
  * [Credentials management](#crm)
  * [Deploying to test server](#dtts)
  * [Deploying to prod server](#dtps)
* References

<h2 id="dev">Development</h2>

<h3 id="bi">Base images</h3>

Each docker image could be built upon a "base image".

For example, the very root base image could include ubuntu os, java and tomcat, since they are highly likely to be needed everywhere.

By doing this we can ensure all developers' machines will be running the same infrastructure as well as all the servers.

<h3 id="sam">Services as modules</h3>

Modules in a monolithic architecture could be thought of as counterparts of a micro-services architecture.

<h3 id="ifs">Images for services</h3>

Each micro-service, either in its own git repo or in a directory of a central repo, will have its own docker file that derives from the base image.

For example:
* A service that talks to the DB may have a docker file that adds postgres driver atop the base image.
* A service that serves static files could add nginx on top of the base image.

(Note: Having multiple images with different tags for one service to deploy to different environments is not recommended. Rather, having all of config files in the code base, packed into the same image and passing a parameter at run time would be preferable. Reasoning being that this will help to bring down network traffic.)

<h3 id="lt">Local testing</h3>

When running locally, developers should avoid using their local OS and infrastructures, instead, the code should be ran in containers.

This is to ensure environment configuration problems can be spotted as early as possible and also to put the topology of containers through most frequent scrutiny.

<h2 id="ci">Continuous Deployment</h2>

<h3 id="tric">Tests running in containers</h3>

To ensure maximum level of environment similarity, the CI server(jenkins and alike) should run tests in a container that is identical to the container that will be deployed to any server.

<h3 id="badi">Building and distributing images</h3>

Instead of jar or war files, the end product of a CI pipeline would be docker images that are pushed to a docker registry(the official one or our own).

(Note: for open source code, it might make more sense if the docker image is as public as the code)

<h2 id="dep">Continuous Deployment</h2>

<h3 id="cm">Cluster management</h3>

Swarm, TBD

<h3 id="s">Scaling</h3>

Swarm, TBD

<h3 id="crm">Credentials management</h3>

For an open source project, DB credentials, email service credentials, and other sensitive information for a specific country/region should not be stored in git or a public docker image.

Rather, the credentials should be stored in a secure and private place(TBD) and only accessible by Swarm and the development team.

<h3 id="dtts">Deploying to test server</h3>

Swarm gets triggered automatically after a CI job finishes, and ... TBD

<h3 id="dtps">Deploying to prod server</h3>

Swarm gets triggered manually, ... ... TBD

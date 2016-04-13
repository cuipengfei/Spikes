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
  * [Cluster management and scaling](#cmas)
  * [Credentials management](#crm)
  * [Deploying to test server](#dtts)
  * [Deploying to prod server](#dtps)
* [Storage](#st)
* [References](#refs)

<h2 id="dev">Development</h2>

<h3 id="bi">Base images</h3>

Each docker image could be built upon a "base image".

For example, the very root base image could include ubuntu os, java, since they are highly likely to be needed everywhere.

By doing this we can ensure all developers' machines will be running the same infrastructure at the same version with the same config as well as all the servers.

Any change to the base image will be recorded in the source control system, used by all developers and all servers. 
This helps to achieve full traceability and avoid untracked ad-hoc changes to the base image, and keeps us away from [snow flake server](http://martinfowler.com/bliki/SnowflakeServer.html).

<h3 id="sam">Services as modules</h3>

Modules in a monolithic architecture could be thought of as counterparts of services in a micro-services architecture.

(TBD, granularity)

<h3 id="ifs">Images for services</h3>

Each micro-service, either in its own git repo or in a directory of a central repo, will have its own docker file that derives from the base image.

For example:
* A service that talks to the DB may have a docker file that adds postgres driver atop the base image.
* A service that serves static files(html, css, js) could add nginx on top of the base image.

Like the base image, these docker files will also be tracked by the source control system, for the same reason.

(Note: Having multiple images with different tags for one service to deploy to different environments is not recommended. Rather, having all of config files in the code base, packed into the same image and passing a parameter at run time would be preferable. Reasoning being that this will help to bring down network traffic for downloading images.)

<h3 id="lt">Local testing</h3>

When running locally, either for testing purposes or for debugging, developers should avoid using their local OS and infrastructures, instead, the code should be ran in containers.

This is to ensure environment configuration problems can be spotted as early as possible and also to put the topology of docker containers through most frequent use and scrutiny.

A script(or scripts) should be in place for developers to perform local testing, the existence of the extra layer brought by docker should be kept as transparent as possible.

<h2 id="ci">Continuous Deployment</h2>

<h3 id="tric">Tests running in containers</h3>

To ensure maximum level of environment similarity, the CI server(jenkins and alike) should run tests in a container that is identical to the container that will be deployed to any server, following the same reasoning for the local testing section.

<h3 id="badi">Building and distributing images</h3>

Instead of jar or war files, the end product of a CI pipeline would be docker images that are pushed to a docker registry(the official one or a privately set up one).

(Note: for open source code, it might make more sense if the docker image is as publicly accessible as the code)

<h2 id="dep">Continuous Deployment</h2>

<h3 id="cmas">Cluster management and scaling</h3>

Presumably, a cluster of servers might be needed for the production deployment environment, and some of the services might be under more pressure than others.

Docker provides Swarm as a cluster management tool to deploy and scale horizontally as well as vertically. 

<h3 id="crm">Credentials management</h3>

For an open source project, DB credentials, email service credentials, and other sensitive information for a specific country/region should not be stored in git or a public docker image.

Rather, the credentials should be stored in a secure and private place(TBD) and only accessible by Swarm and the development team.

<h3 id="dtts">Deploying to test server</h3>

Swarm could be running on a machine or in a container where it's accessible by CI.

Thus, a redeploy command could be issue from CI to Swarm once the build pipeline finishes, this will make sure environments for QA is always running the latest code.

Other less frequently updated environments like Staging or UAT could also be redeployed through a manual operation exposed by CI's web gui.

<h3 id="dtps">Deploying to prod server</h3>

Technically, this could be done similarly to how test servers are deployed. 

However, given the sensitivity, it would be preferable to deploy to prod server via a jump box that is only accessible by few.

<h2 id="st">Storage</h2>

If docker were utilized to build, manage the deployment environment, then it would be preferable to not involve another provisioning solution for DB server setup.
 
The database could be running in contain/containers, same as any other component that makes up the whole application. 

Or, another solution would be to use AWS RDS, which is Amazon's hosted relational database as a service.

RDS would remove the necessity for us to set up/maintain a database and its replicates/clusters.

<h2 id="refs">References</h2>

[Docker image](https://docs.docker.com/engine/userguide/containers/dockerimages/)

[Swarm](https://docs.docker.com/swarm/overview/)

[RDS](https://aws.amazon.com/rds/)
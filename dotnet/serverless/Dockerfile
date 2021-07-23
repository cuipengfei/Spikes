FROM node:lts-buster AS node_base

FROM mcr.microsoft.com/dotnet/sdk:3.1.411-buster AS dotnet_base

COPY --from=node_base . .
RUN yarn global add serverless

# install azure func tool
ARG DEBIAN_VERSION=10
RUN wget -qO- https://packages.microsoft.com/keys/microsoft.asc | gpg --dearmor > microsoft.asc.gpg
RUN mv microsoft.asc.gpg /etc/apt/trusted.gpg.d/
RUN wget -q https://packages.microsoft.com/config/debian/$DEBIAN_VERSION/prod.list
RUN mv prod.list /etc/apt/sources.list.d/microsoft-prod.list
RUN chown root:root /etc/apt/trusted.gpg.d/microsoft.asc.gpg
RUN chown root:root /etc/apt/sources.list.d/microsoft-prod.list
RUN apt-get update
RUN apt-get install azure-functions-core-tools-3

COPY . /app
WORKDIR /app
RUN yarn install
RUN dotnet build
EXPOSE 7071
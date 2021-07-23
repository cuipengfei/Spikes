FROM node:lts-buster AS node_base

FROM mcr.microsoft.com/dotnet/sdk:3.1.411-buster AS dotnet_base

COPY --from=node_base . .

# RUN npm install --global yarn
RUN yarn global add serverless

COPY . .

RUN npm install

EXPOSE 7071
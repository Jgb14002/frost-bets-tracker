FROM node:lts-alpine3.13 As development

WORKDIR /user/src/server

COPY package*.json ./

RUN npm install --only=development

COPY . .

RUN npm run build

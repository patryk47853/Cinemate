FROM node:19-alpine
WORKDIR /app
COPY package*.json .
RUN npm i --silent
COPY . .
RUN echo "VITE_API_BASE_URL=http://0.0.0.0:5332" > .env
EXPOSE 5173
CMD ["npm", "run", "dev"]
const jsonServer = require("json-server");
const server = jsonServer.create();
const middlewares = jsonServer.defaults();

// Custom middleware to enable CORS
server.use((req, res, next) => {
  res.setHeader("Access-Control-Allow-Origin", "http://localhost:6006");
  res.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
  res.setHeader(
    "Access-Control-Allow-Headers",
    "Content-Type, Access-Control-Allow-Headers"
  );
  next();
});

server.use(middlewares);
server.use(jsonServer.router("db.json"));

const port = 3003;
server.listen(port, () => {
  console.log(`JSON Server is running on port ${port}`);
});

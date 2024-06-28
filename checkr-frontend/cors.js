module.exports = (req, res, next) => {
  res.setHeader("Access-Control-Allow-Origin", "http://localhost:6006");
  res.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
  res.setHeader(
    "Access-Control-Allow-Headers",
    "Content-Type, Access-Control-Allow-Headers"
  );
  next();
};

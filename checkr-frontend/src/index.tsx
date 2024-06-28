import App from "./App";
import ReactDOM from "react-dom/client";
import "./index.css";
import theme from "./theme/theme";
import { ThemeProvider } from "@emotion/react";
import { BrowserRouter } from "react-router-dom";
import { Auth0Provider } from "@auth0/auth0-react";
import "cors";
import {
  AUTH0_DOMAIN,
  AUTH0_CLIENT_ID,
  AUTH0_CALLBACK_URL,
} from "../src/constants";

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);
root.render(
  <ThemeProvider theme={theme}>
    <BrowserRouter>
      <Auth0Provider
        domain={AUTH0_DOMAIN}
        clientId={AUTH0_CLIENT_ID}
        authorizationParams={{
          redirect_uri: window.location.href,
          // initiate_login_uri: "https://localhost:6006/",
        }}
      >
        <App />
      </Auth0Provider>
    </BrowserRouter>
  </ThemeProvider>
);
